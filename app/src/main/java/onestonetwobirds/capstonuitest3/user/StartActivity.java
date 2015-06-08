package onestonetwobirds.capstonuitest3.user;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import onestonetwobirds.capstonuitest3.R;


public class StartActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_page_main);
        Button button1 = (Button) findViewById(R.id.button_bt_start);
        Button button2 = (Button) findViewById(R.id.button_bt_main_sign_in);
        Button button3 = (Button) findViewById(R.id.button_bt_main_find_pwd);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StartActivity.this, AfterLoginActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade, R.anim.hold);

            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StartActivity.this, SignInActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade, R.anim.hold);

            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StartActivity.this, PwdFindActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade, R.anim.hold);

            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.fade, R.anim.hold);
    }
}
