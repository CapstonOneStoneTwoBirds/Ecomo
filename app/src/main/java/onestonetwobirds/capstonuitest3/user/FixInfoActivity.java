package onestonetwobirds.capstonuitest3.user;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONObject;
import org.w3c.dom.Text;

import onestonetwobirds.capstonuitest3.R;
import onestonetwobirds.capstonuitest3.httpClient.HttpClient;

/**
 * Created by New on 2015-06-09.
 */
public class FixInfoActivity extends Activity implements View.OnClickListener {
    String password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fix_info);

        final TextView email_tv = (TextView)findViewById(R.id.fix_info_email);
        final EditText oldpw_edt = (EditText)findViewById(R.id.fix_info_oldpw);
        final EditText newpw1_edt = (EditText)findViewById(R.id.fix_info_newpw1);
        final EditText newpw2_edt = (EditText)findViewById(R.id.fix_info_newpw2);
        final TextView name_tv = (TextView)findViewById(R.id.fix_info_name);
        final EditText phone_edt = (EditText)findViewById(R.id.fix_info_phone);
        final Button btn = (Button)findViewById(R.id.fix_info_btn);
        btn.setOnClickListener(this);

        RequestParams param = new RequestParams();

        SharedPreferences mPreference = getSharedPreferences("myInfo", MODE_PRIVATE);
        final String email = mPreference.getString("email", "");
        param.put("email", email);
        HttpClient.post("getMember/", param, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    JSONObject obj = new JSONObject(new String(responseBody));
                    password = obj.get("pw").toString();
                    email_tv.setText(obj.get("email").toString());
                    name_tv.setText(obj.get("name").toString());
                    phone_edt.setText(obj.get("phone").toString());
                } catch (Exception e) {}
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                System.out.println(error);
            }
        });
    }

    @Override
    public void onClick(View v) {
        TextView email_edt = (TextView)findViewById(R.id.fix_info_email);
        EditText pw0_edt = (EditText)findViewById(R.id.fix_info_oldpw);
        final EditText pw1_edt = (EditText)findViewById(R.id.fix_info_newpw1);
        EditText pw2_edt = (EditText)findViewById(R.id.fix_info_newpw2);
        EditText phone_edt = (EditText)findViewById(R.id.fix_info_phone);
        if ( !password.equals(pw0_edt.getText().toString())){
            Toast toastView = Toast.makeText(getApplicationContext(),
                    "Original password wrong", Toast.LENGTH_LONG);
            toastView.setGravity(Gravity.CENTER, 40, 25);
            toastView.show();
        }
        else if( !pw2_edt.getText().toString().equals(pw1_edt.getText().toString()) ){
            Toast toastView = Toast.makeText(getApplicationContext(),
                    "Enter new password correctly", Toast.LENGTH_LONG);
            toastView.setGravity(Gravity.CENTER, 40, 25);
            toastView.show();
        }
        else{
            RequestParams param = new RequestParams();
            param.put("email", email_edt.getText().toString());
            param.put("pw", pw1_edt.getText().toString());
            param.put("phone", phone_edt.getText().toString());
            HttpClient.post("fixInfo/", param, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    System.out.println("Test :::: " + responseBody);
                    if (new String(responseBody).equals("fix info complete")) {
                        //startActivity(new Intent(getApplicationContext(), SelectActivity.class));
                        Toast toastView = Toast.makeText(getApplicationContext(),
                                "Fix Success", Toast.LENGTH_LONG);
                        toastView.setGravity(Gravity.CENTER, 40, 25);
                        toastView.show();

                        SharedPreferences mPreference = getSharedPreferences("myInfo", MODE_PRIVATE);
                        SharedPreferences.Editor editor = mPreference.edit();
                        editor.putString("pw", pw1_edt.getText().toString());
                        finish();
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    System.out.println("error tt : " + error);
                }
            });
        }
    }
}
