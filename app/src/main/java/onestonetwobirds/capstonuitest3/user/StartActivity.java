package onestonetwobirds.capstonuitest3.user;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import onestonetwobirds.capstonuitest3.R;
import onestonetwobirds.capstonuitest3.privateHouseKeeping.Main.PrivateMainActivity;


public class StartActivity extends Activity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_page_main);
        Button button1 = (Button) findViewById(R.id.button_bt_start);
        Button button2 = (Button) findViewById(R.id.button_bt_main_sign_in);
        Button button3 = (Button) findViewById(R.id.button_bt_main_find_pwd);
        Button button4 = (Button) findViewById(R.id.temp_btn);
        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);
    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.fade, R.anim.hold);
    }

    @Override
    public void onClick(View v) {
        switch( v.getId() ){
            case R.id.button_bt_start:
                Intent intent = new Intent(StartActivity.this, AfterLoginActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade, R.anim.hold);
                break;

            case R.id.button_bt_main_sign_in:
                intent = new Intent(StartActivity.this, SignInActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade, R.anim.hold);
                break;

            case R.id.button_bt_main_find_pwd:
                intent = new Intent(StartActivity.this, PwdFindActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade, R.anim.hold);
                break;

            case R.id.temp_btn:
                intent = new Intent(StartActivity.this, PrivateMainActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade, R.anim.hold);
                break;
        }

    }
}
