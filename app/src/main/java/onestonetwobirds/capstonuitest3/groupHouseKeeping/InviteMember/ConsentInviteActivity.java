package onestonetwobirds.capstonuitest3.groupHouseKeeping.InviteMember;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.rey.material.widget.Button;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import onestonetwobirds.capstonuitest3.R;
import onestonetwobirds.capstonuitest3.groupHouseKeeping.Main.InGroupActivity;
import onestonetwobirds.capstonuitest3.httpClient.HttpClient;
import onestonetwobirds.capstonuitest3.user.AfterLoginActivity;

/**
 * Created by YeomJi on 15. 6. 9..
 */
public class ConsentInviteActivity extends Activity implements View.OnClickListener {

    TextView ConsentInviteGroupName, ConsentInviteGroupKng, ConsentInviteGroupCreateDay, ConsentInviteGroupMember;
    Button InviteOK, InviteNO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.consent_invite_main);

        ConsentInviteGroupName = (TextView)findViewById(R.id.consent_invite_group_name);
        ConsentInviteGroupKng = (TextView)findViewById(R.id.consent_invite_group_king);
        ConsentInviteGroupCreateDay = (TextView)findViewById(R.id.consent_invite_group_create_day);
        ConsentInviteGroupMember = (TextView)findViewById(R.id.consent_invite_group_member);
        InviteOK = (Button)findViewById(R.id.invite_OK);
        InviteNO = (Button)findViewById(R.id.invite_NO);
        InviteOK.setOnClickListener(this);
        InviteNO.setOnClickListener(this);

        String group_id = getIntent().getStringExtra("group_id");
        RequestParams param = new RequestParams();
        param.put("group_id", group_id);

        HttpClient.post("getGroupData/", param, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    System.out.println("getGroupDate Success");
                    JSONObject obj = new JSONObject(new String(responseBody));
                    ConsentInviteGroupName.setText(obj.get("groupname").toString());
                    ConsentInviteGroupKng.setText(obj.get("owner").toString());
                    ConsentInviteGroupMember.setText(obj.get("member").toString());
                    ConsentInviteGroupCreateDay.setText(obj.get("since").toString());
                } catch (JSONException e) {
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                System.out.println("getGroupDate Fail");
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch( v.getId() ){
            case R.id.invite_OK:
                // 이걸 초대받은사람이 OK 했을때 실행해야해.

                final String group_id = getIntent().getStringExtra("group_id");
                String email = getIntent().getStringExtra("email");

                RequestParams param = new RequestParams();
                param.put("group_id", group_id);
                param.put("email", email);

                HttpClient.post("writeMember/", param, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                        System.out.println("Member Save Success");
                        System.out.println("output : " + new String(responseBody));
                        switch (new String(responseBody)) {
                            case "1":
                                System.out.println("write member error");
                                break;

                            case "2":
                                System.out.println("write member Success");

                                Intent intent = new Intent(getApplicationContext(), InGroupActivity.class);
                                intent.putExtra("group_id", group_id);
                                startActivity(intent);
                                finish();

                                break;
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                        System.out.println("error message 1 : " + error);
                    }
                });

                break;
            case R.id.invite_NO:
                Toast toastView = Toast.makeText(getApplicationContext(),
                        "denied Invitation", Toast.LENGTH_LONG);
                toastView.setGravity(Gravity.CENTER, 40, 25);
                toastView.show();
                Intent intent = new Intent(getApplicationContext(), AfterLoginActivity.class);
                startActivity(intent);
                break;
        }
    }
}
