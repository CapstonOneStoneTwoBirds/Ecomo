package onestonetwobirds.capstonuitest3.user;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;

import java.util.Random;

import onestonetwobirds.capstonuitest3.R;
import onestonetwobirds.capstonuitest3.httpClient.HttpClient;

/**
 * Created by user on 2015-05-29.
 */
public class PwdFindActivity extends Activity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.find_pwd_main);

        final EditText email = (EditText)findViewById(R.id.input_email_find_pwd);
        final EditText name = (EditText)findViewById(R.id.input_name_find_pwd);

        Button find = (Button)findViewById(R.id.button_bt_get_password);
        find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email_str = email.getText().toString();
                final String name_str = name.getText().toString();
                RequestParams param = new RequestParams();
                param.put("email", email_str);
                param.put("name", name_str);

                System.out.println("email : " + email_str + " / name : " + name_str);
                HttpClient.post("checkAccount/", param, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                        String response = new String(responseBody);
                        if (response.equals("correct")) {
                            System.out.println(response);

                            try {
                                // Generate Encrypted String
                                char[] chars = "abcdefghijklmnopqrstuvwxyz".toCharArray();
                                StringBuilder sb = new StringBuilder();
                                Random random = new Random();
                                for (int i = 0; i < 20; i++) {
                                    char c = chars[random.nextInt(chars.length)];
                                    sb.append(c);
                                }
                                final String output = sb.toString();

                                SendGmail sg = new SendGmail();
                                sg.execute(email_str, name_str, output);

                                // Save Encrypted String to Server
                                RequestParams param = new RequestParams();
                                param.put("email", email_str);
                                param.put("code", output);
                                HttpClient.post("resetPassword_save/", param, new AsyncHttpResponseHandler() {
                                    @Override
                                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                                        System.out.println("Send Email success");
                                        if (new String(responseBody).equals("code save error")) {
                                            System.out.println("fail here T_T");
                                        } else {
                                            Toast toastView = Toast.makeText(getApplicationContext(),
                                                    "Email Sent", Toast.LENGTH_LONG);
                                            toastView.setGravity(Gravity.CENTER, 40, 25);
                                            toastView.show();

                                            startActivity(new Intent(getApplicationContext(), StartActivity.class));
                                        }
                                    }

                                    @Override
                                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                                        System.out.println("resetPassword fail");
                                    }
                                });

                            } catch (Exception e) {
                                System.out.println("Error ; " + e);
                            }

                        } else if (response.equals("wrong info")) {
                            System.out.println(response);
                            Toast toastView = Toast.makeText(getApplicationContext(),
                                    "Wrong Info", Toast.LENGTH_SHORT);
                            toastView.setGravity(Gravity.CENTER, 40, 25);
                            toastView.show();
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                        System.out.println("checkAccount _ on Failure called");
                    }
                });
            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.fade, R.anim.hold);
    }
}
