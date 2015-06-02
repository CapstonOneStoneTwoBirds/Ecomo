package onestonetwobirds.capstonuitest3.privateHouseKeeping;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import com.rey.material.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.rey.material.app.DatePickerDialog;
import com.rey.material.app.Dialog;
import com.rey.material.app.DialogFragment;
import com.rey.material.app.TimePickerDialog;
import com.rey.material.widget.Button;
import java.text.SimpleDateFormat;

import onestonetwobirds.capstonuitest3.R;

/**
 * Created by YeomJi on 15. 5. 31..
 */

public class InsertActivity  extends Activity {


    Button InsertBtnDay, InsertBtnTime;
    TextView IsertTextDay, IsertTextTime;
    EditText InsertTitle, InsertContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.insert_main);

        InsertBtnDay = (Button)findViewById(R.id.insert_btn_day);
        InsertBtnTime = (Button)findViewById(R.id.insert_btn_time);
        IsertTextDay = (TextView)findViewById(R.id.insert_text_day);
        IsertTextTime = (TextView)findViewById(R.id.insert_text_time);
        InsertTitle = (EditText)findViewById(R.id.insert_title);
        InsertContent = (EditText)findViewById(R.id.insert_content);

        InsertBtnDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Check day");
                Dialog.Builder builder = new TimePickerDialog.Builder(6, 00){
                    @Override
                    public void onPositiveActionClicked(DialogFragment fragment) {
                        TimePickerDialog dialog = (TimePickerDialog)fragment.getDialog();
                        Toast.makeText(InsertActivity.this, "Time is " + dialog.getFormattedTime(SimpleDateFormat.getTimeInstance()), Toast.LENGTH_SHORT).show();
                        super.onPositiveActionClicked(fragment);
                    }

                    @Override
                    public void onNegativeActionClicked(DialogFragment fragment) {
                        Toast.makeText(InsertActivity.this, "Cancelled" , Toast.LENGTH_SHORT).show();
                        super.onNegativeActionClicked(fragment);
                    }
                };

                builder.positiveAction("OK")
                        .negativeAction("CANCEL");
                builder.contentView(R.layout.insert_main);
            }
        });

        InsertBtnTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Check time");
                Dialog.Builder builder = new DatePickerDialog.Builder(){
                    @Override
                    public void onPositiveActionClicked(DialogFragment fragment) {
                        DatePickerDialog dialog = (DatePickerDialog)fragment.getDialog();
                        String date = dialog.getFormattedDate(SimpleDateFormat.getDateInstance());
                        Toast.makeText(InsertActivity.this, "Date is " + date, Toast.LENGTH_SHORT).show();
                        super.onPositiveActionClicked(fragment);
                    }

                    @Override
                    public void onNegativeActionClicked(DialogFragment fragment) {
                        Toast.makeText(InsertActivity.this, "Cancelled" , Toast.LENGTH_SHORT).show();
                        super.onNegativeActionClicked(fragment);
                    }
                };
                builder.positiveAction("OK")
                        .negativeAction("CANCEL");

            }
        });

    }
}

