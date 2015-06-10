package onestonetwobirds.capstonuitest3.control;

/**
 * Created by YeomJi on 14. 12. 8..
 */
import android.app.Activity;
import android.widget.Toast;

import com.rey.material.widget.SnackBar;

import onestonetwobirds.capstonuitest3.R;

public class BackPressCloseHandler {

    private long backKeyPressedTime = 0;
    private Toast toast;

    SnackBar mSnackBar;

    private Activity activity;

    public BackPressCloseHandler(Activity context) {
        this.activity = context;
    }

    public void onBackPressed() {
        if (System.currentTimeMillis() > backKeyPressedTime + 2000) {
            backKeyPressedTime = System.currentTimeMillis();
            showGuide();
            return;
        }
        if (System.currentTimeMillis() <= backKeyPressedTime + 2000) {
            activity.finish();
            toast.cancel();
        }
    }

    public SnackBar getSnackBar() {
        return mSnackBar;
    }


    public void showGuide() {
        //SnackBar nSnackBar = this.getSnackBar();

        toast = Toast.makeText(activity,
                "\'뒤로\' 버튼을 한번 더 누르시면 \nECOMO가 종료됩니다.", Toast.LENGTH_SHORT);
        toast.show();
        /*
        mSnackBar = nSnackBar.applyStyle(R.style.SnackBarSingleLine)
                .text("\'뒤로\' 버튼을 한번 더 누르시면 ECOMO가 종료됩니다.")
                .duration(2000);
        mSnackBar.show();
        */
    }
}