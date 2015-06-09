package onestonetwobirds.capstonuitest3.groupHouseKeeping.InviteMember;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.rey.material.widget.Button;

import onestonetwobirds.capstonuitest3.R;

/**
 * Created by YeomJi on 15. 6. 9..
 */
public class ConsentInviteActivity extends Activity {

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


    }
}
