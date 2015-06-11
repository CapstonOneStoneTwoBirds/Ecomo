package onestonetwobirds.capstonuitest3.user;

import android.app.Activity;
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
public class KeySetActivity extends Activity implements View.OnClickListener{
    String key="";
    String key1, key2, key3, key4;
    EditText key1_edt, key2_edt, key3_edt, key4_edt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.key_set);
        Log.e("KeySetActivity", "Here");

        key1_edt = (EditText)findViewById(R.id.input_set_key1);
        key2_edt = (EditText)findViewById(R.id.input_set_key2);
        key3_edt = (EditText)findViewById(R.id.input_set_key3);
        key4_edt = (EditText)findViewById(R.id.input_set_key4);

        Button btn = (Button)findViewById(R.id.key_set_btn);
        btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        key1 = key1_edt.getText().toString();
        key2 = key2_edt.getText().toString();
        key3 = key3_edt.getText().toString();
        key4 = key4_edt.getText().toString();

        key = key1+key2+key3+key4;
        Log.e("KeySetActivity", "Key : " + key);

        if(key.length() < 4){
            Toast toastView = Toast.makeText(getApplicationContext(),
                    "Please Enter key", Toast.LENGTH_LONG);
            toastView.setGravity(Gravity.CENTER, 40, 25);
            toastView.show();
        }
        else {
            RequestParams param = new RequestParams();
            param.put("key", key);
            param.put("email", getIntent().getStringExtra("email"));
            HttpClient.post("setKey/", param, new AsyncHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                            if (responseBody == null) {
                                Log.e("KeySetActivity", "onSuccess null");
                            } else {
                                Log.e("KeySetActivity", new String(responseBody));

                                Toast toastView = Toast.makeText(getApplicationContext(),
                                        "KEY set complete", Toast.LENGTH_LONG);
                                toastView.setGravity(Gravity.CENTER, 40, 25);
                                toastView.show();
                                finish();
                                Intent intent = new Intent(getApplicationContext(), AfterLoginActivity.class);
                                startActivity(intent);
                                overridePendingTransition(R.anim.fade, R.anim.hold);
                            }
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                        }
                    }
            );
        }
    }
}
