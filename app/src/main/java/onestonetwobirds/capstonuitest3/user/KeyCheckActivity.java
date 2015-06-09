package onestonetwobirds.capstonuitest3.user;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import onestonetwobirds.capstonuitest3.R;

/**
 * Created by New on 2015-06-09.
 */
public class KeyCheckActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.key_check);
        Log.e("KeyCheckActivity", "Here");
    }
}
