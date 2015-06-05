package onestonetwobirds.capstonuitest3.privateHouseKeeping.Insert;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.Window;

import com.rey.material.widget.SnackBar;

import onestonetwobirds.capstonuitest3.R;

/**
 * Created by YeomJi on 15. 5. 31..
 */

public class InsertActivity  extends FragmentActivity {

    private SnackBar mSnackBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.insert_for_fragment_main);

        mSnackBar = (SnackBar)findViewById(R.id.insert_sn);

        Fragment newFragment = new InsertActivityFragment();

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        transaction.replace(R.id.ll_fragment, newFragment);

        transaction.commit();

    }

    public SnackBar getSnackBar(){
        return mSnackBar;
    }
}

