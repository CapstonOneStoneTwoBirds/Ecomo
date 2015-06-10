package onestonetwobirds.capstonuitest3.groupHouseKeeping.Member;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
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
import onestonetwobirds.capstonuitest3.groupHouseKeeping.Main.InGroupActivity;
import onestonetwobirds.capstonuitest3.httpClient.HttpClient;

/**
 * Created by New on 2015-05-14.
 */
public class WriteMemberActivity extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.group_member_write);

        Button btn = (Button)findViewById(R.id.member_invite_btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText emailedt = (EditText)findViewById(R.id.member_email_edt);
                String email = emailedt.getText().toString();
                final SharedPreferences mPreference;
                mPreference = v.getContext().getSharedPreferences("myInfo", v.getContext().MODE_PRIVATE);

                final String _id = mPreference.getString("group_id", "");
                RequestParams param = new RequestParams();
                param.put("groupid", _id);
                param.put("email", email);

                // email에 초대를 날리는거 gcm으로. 작성하자.
                HttpClient.post("inviteMember/", param, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                        Log.e("WriteMemberActivity |||| ", new String(responseBody));
                        if (new String(responseBody).equals("all done")) {
                            System.out.println("Invite GCM send done");
                            Intent intent = new Intent(getApplicationContext(), InGroupActivity.class);
                            intent.putExtra("group_id", _id);
                            finish();
                            startActivity(intent);
                        } else if (new String(responseBody).equals("already existed member")) {
                            Toast toastView = Toast.makeText(getApplicationContext(),
                                    new String(responseBody), Toast.LENGTH_LONG);
                            toastView.setGravity(Gravity.CENTER, 40, 25);
                            toastView.show();
                        } else if (new String(responseBody).equals("non-existed email")) {
                            Toast toastView = Toast.makeText(getApplicationContext(),
                                    new String(responseBody), Toast.LENGTH_LONG);
                            toastView.setGravity(Gravity.CENTER, 40, 25);
                            toastView.show();
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                        System.out.println("Invite GCM send error");
                    }
                });
            }
        });

    }
}
