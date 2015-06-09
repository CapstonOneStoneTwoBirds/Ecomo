package onestonetwobirds.capstonuitest3.user;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
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
public class KeySetActivity extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.key_set);

        EditText key1_edt = (EditText)findViewById(R.id.input_set_key1);
        EditText key2_edt = (EditText)findViewById(R.id.input_set_key2);
        EditText key3_edt = (EditText)findViewById(R.id.input_set_key3);
        EditText key4_edt = (EditText)findViewById(R.id.input_set_key4);

        String key1 = key1_edt.getText().toString();
        String key2 = key2_edt.getText().toString();
        String key3 = key3_edt.getText().toString();
        String key4 = key4_edt.getText().toString();

        String key = key1+key2+key3+key4;
        Log.e("KeySetActivity", "Key : " + key);
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
                                    "KEY 설정 완료.", Toast.LENGTH_LONG);
                            toastView.setGravity(Gravity.CENTER, 40, 25);
                            toastView.show();

                            Intent intent = new Intent(getApplicationContext(), AfterLoginActivity.class);
                            startActivity(intent);
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                    }
                }
        );
    }
}
