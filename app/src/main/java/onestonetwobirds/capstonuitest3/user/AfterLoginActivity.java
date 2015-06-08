package onestonetwobirds.capstonuitest3.user;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import onestonetwobirds.capstonuitest3.R;
import onestonetwobirds.capstonuitest3.groupHouseKeeping.Main.GroupMainActivity;
import onestonetwobirds.capstonuitest3.privateHouseKeeping.Main.PrivateMainActivity;

/**
 * Created by user on 2015-05-29.
 */
public class AfterLoginActivity extends Activity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.after_login_main);

        ImageButton privateBtn = (ImageButton)findViewById(R.id.privateBtn);
        ImageButton groupBtn = (ImageButton)findViewById(R.id.groupBtn);
        privateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AfterLoginActivity.this, PrivateMainActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade, R.anim.hold);
                finish();
            }
        });

        groupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AfterLoginActivity.this, GroupMainActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade, R.anim.hold);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.fade, R.anim.hold);
    }
}
