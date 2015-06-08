package onestonetwobirds.capstonuitest3.user;

import android.app.Activity;
import android.os.Bundle;

import onestonetwobirds.capstonuitest3.R;

/**
 * Created by user on 2015-05-28.
 */
public class SignInActivity extends Activity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_in_page_main);
    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.fade, R.anim.hold);
    }
}
