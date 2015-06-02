package onestonetwobirds.capstonuitest3.privateHouseKeeping;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.rey.material.app.DatePickerDialog;
import com.rey.material.app.Dialog;
import com.rey.material.app.DialogFragment;
import com.rey.material.app.TimePickerDialog;
import com.rey.material.widget.Button;
import com.rey.material.widget.Spinner;

import java.text.SimpleDateFormat;
import java.util.StringTokenizer;

import onestonetwobirds.capstonuitest3.R;

/**
 * Created by YeomJi on 15. 6. 2..
 */
public class InsertActivityFragment extends Fragment implements View.OnClickListener {

    String year, month, date, AMPM, time, minute;
    String cate;
    Button InsertBtnDay, InsertBtnTime, InsertOK, InsertCancel;
    TextView IsertTextDay, IsertTextTime;
    EditText InsertTitle, InsertContent;
    Spinner InsertSpinner;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.insert_main, container, false);

        InsertBtnDay = (Button) view.findViewById(R.id.insert_btn_day);
        InsertBtnTime = (Button) view.findViewById(R.id.insert_btn_time);
        IsertTextDay = (TextView) view.findViewById(R.id.insert_text_day);
        IsertTextTime = (TextView) view.findViewById(R.id.insert_text_time);
        InsertTitle = (EditText) view.findViewById(R.id.insert_title);
        InsertContent = (EditText) view.findViewById(R.id.insert_content);
        InsertSpinner = (Spinner) view.findViewById(R.id.spinner_insert);
        InsertOK = (Button) view.findViewById(R.id.insert_OK);
        InsertCancel = (Button) view.findViewById(R.id.insert_Cancel);

        String[] items = new String[5];

        items[0] = "식비";
        items[1] = "여가비";
        items[2] = "주거비";
        items[3] = "교통비";
        items[4] = "저축비";

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), R.layout.row_spn, items);
        adapter.setDropDownViewResource(R.layout.row_spn_dropdown);
        InsertSpinner.setAdapter(adapter);
        InsertSpinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(Spinner spinner, View view, int i, long l) {
                cate = (String) spinner.getSelectedItemPosition(i).toString();
            }
        });

                InsertBtnDay.setOnClickListener(this);
        InsertBtnTime.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        Dialog.Builder builder = null;

        switch (v.getId()){
            case R.id.insert_btn_day:
                builder = new DatePickerDialog.Builder() {
                    @Override
                    public void onPositiveActionClicked(DialogFragment fragment) {
                        DatePickerDialog dialog = (DatePickerDialog) fragment.getDialog();
                        String date = dialog.getFormattedDate(SimpleDateFormat.getDateInstance());
                        IsertTextDay.setText(date);
                        makeDate(date);
                        super.onPositiveActionClicked(fragment);
                    }

                    @Override
                    public void onNegativeActionClicked(DialogFragment fragment) {
                        super.onNegativeActionClicked(fragment);
                    }
                };
                builder.positiveAction("OK")
                        .negativeAction("CANCEL");

                builder.contentView(R.layout.insert_main);
                break;
            case R.id.insert_btn_time:
                builder = new TimePickerDialog.Builder(6, 00){
                    @Override
                    public void onPositiveActionClicked(DialogFragment fragment) {
                        TimePickerDialog dialog = (TimePickerDialog)fragment.getDialog();
                        String time = dialog.getFormattedTime(SimpleDateFormat.getTimeInstance());
                        IsertTextTime.setText(time);
                        makeTime(time);
                        super.onPositiveActionClicked(fragment);
                    }

                    @Override
                    public void onNegativeActionClicked(DialogFragment fragment) {
                        super.onNegativeActionClicked(fragment);
                    }
                };

                builder.positiveAction("OK")
                        .negativeAction("CANCEL");
                break;

        }

        DialogFragment fragment = DialogFragment.newInstance(builder);
        fragment.show(getFragmentManager(), null);

    }
    // ex : 2015. 6. 18
    void makeDate(String Ddate) {
        String token;
        int position = 0;
        StringTokenizer st = new StringTokenizer(Ddate);
        while (st.hasMoreTokens()) {

            token = st.nextToken();
            if(token.endsWith(".")) token = token.replace(".","");
            switch (position) {
                case 0:
                    year = token;
                    break;
                case 1:
                    month = token;
                    break;
                case 2:
                    date = token;
                    break;
                default:
                    break;
            }
            position++;
        }
    }

    // ex : 오후 5:20:00
    void makeTime(String Dtime) {
        String token, token2;
        int position = 0;
        StringTokenizer st = new StringTokenizer(Dtime);
        while (st.hasMoreTokens()) {

            token = st.nextToken();
            if(token.endsWith(".")) token = token.replace(".","");
            if (position == 0) {
                AMPM = token;
                position++;
            }
            else {
                StringTokenizer st2 = new StringTokenizer(token,":");
                while (st2.hasMoreTokens()) {
                    token2 = st2.nextToken();
                    if (position == 1) time = token2;
                    else if(position == 2) minute = token2;
                    position++;
                }
            }
        }
        //Toast.makeText(getActivity(), AMPM+" "+time+" "+minute, Toast.LENGTH_LONG).show();


    }

}