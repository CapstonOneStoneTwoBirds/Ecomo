package onestonetwobirds.capstonuitest3.privateHouseKeeping;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.Window;
import com.rey.material.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.rey.material.app.DialogFragment;

import com.rey.material.app.DatePickerDialog;
import com.rey.material.app.Dialog;
import com.rey.material.app.TimePickerDialog;
import com.rey.material.widget.Button;
import java.text.SimpleDateFormat;

import onestonetwobirds.capstonuitest3.R;

/**
 * Created by YeomJi on 15. 5. 31..
 */

public class InsertActivity  extends FragmentActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.insert_for_fragment_main);

        Fragment newFragment = new InsertActivityFragment();

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        transaction.replace(R.id.ll_fragment, newFragment);

        transaction.commit();

    }
}

