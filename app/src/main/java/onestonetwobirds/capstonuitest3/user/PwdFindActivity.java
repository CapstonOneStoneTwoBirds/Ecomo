package onestonetwobirds.capstonuitest3.user;

import android.app.Activity;
import android.os.Bundle;

import onestonetwobirds.capstonuitest3.R;

/**
 * Created by user on 2015-05-29.
 */
public class PwdFindActivity extends Activity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.find_pwd_main);
    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.fade, R.anim.hold);
    }
}
