package onestonetwobirds.capstonuitest3.user;

import android.app.Activity;
import android.app.UiAutomation;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;

import onestonetwobirds.capstonuitest3.R;
import onestonetwobirds.capstonuitest3.httpClient.HttpClient;

/**
 * Created by New on 2015-06-09.
 */
public class KeyCheckActivity extends Activity implements View.OnClickListener{
    EditText key1_edt, key2_edt, key3_edt, key4_edt;
    String key="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.key_check);
        Log.e("KeyCheckActivity", "Here");

        key1_edt = (EditText)findViewById(R.id.input_check_key1);
        key2_edt = (EditText)findViewById(R.id.input_check_key2);
        key3_edt = (EditText)findViewById(R.id.input_check_key3);
        key4_edt = (EditText)findViewById(R.id.input_check_key4);

        Button btn = (Button)findViewById(R.id.key_check_btn);
        btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        key = key1_edt.getText().toString() +key2_edt.getText().toString() +key3_edt.getText().toString() +key4_edt.getText().toString();
        RequestParams param = new RequestParams();
        param.put("key", key);
        param.put("email", getIntent().getStringExtra("email"));

        HttpClient.post("checkKey/", param, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if(new String(responseBody).equals("null")){
                    Toast toastView = Toast.makeText(getApplicationContext(),
                            "Wrong Access", Toast.LENGTH_LONG);
                    toastView.setGravity(Gravity.CENTER, 40, 25);
                    toastView.show();
                }
                else{
                    System.out.println("here");
                    startActivity(new Intent(getApplicationContext(), AfterLoginActivity.class));
                    overridePendingTransition(R.anim.fade, R.anim.hold);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });

    }
}
