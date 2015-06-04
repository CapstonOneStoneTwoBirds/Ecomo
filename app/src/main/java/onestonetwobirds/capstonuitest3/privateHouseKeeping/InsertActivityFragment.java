package onestonetwobirds.capstonuitest3.privateHouseKeeping;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.rey.material.app.DatePickerDialog;
import com.rey.material.app.Dialog;
import com.rey.material.app.DialogFragment;
import com.rey.material.app.TimePickerDialog;
import com.rey.material.widget.Button;
import com.rey.material.widget.SnackBar;
import com.rey.material.widget.Spinner;

import java.text.SimpleDateFormat;
import java.util.StringTokenizer;

import onestonetwobirds.capstonuitest3.R;
import onestonetwobirds.capstonuitest3.control.database.MyDatabase;

/**
 * Created by YeomJi on 15. 6. 2..
 */
public class InsertActivityFragment extends Fragment implements View.OnClickListener {

    String year, month, date, AMPM, time, minute;
    String cate;
    int total, food, play, house, traffic, saving;

    Button InsertBtnDay, InsertBtnTime, InsertOK, InsertCancel;
    TextView IsertTextDay, IsertTextTime;
    EditText InsertTitle, InsertMoney, InsertContent;
    Spinner InsertSpinner;

    SnackBar mSnackBar;

    private final String tag = "InsertActivity";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.insert_main, container, false);

        InsertBtnDay = (Button) v.findViewById(R.id.insert_btn_day);
        InsertBtnTime = (Button) v.findViewById(R.id.insert_btn_time);
        IsertTextDay = (TextView) v.findViewById(R.id.insert_text_day);
        IsertTextTime = (TextView) v.findViewById(R.id.insert_text_time);
        InsertTitle = (EditText) v.findViewById(R.id.insert_title);
        InsertMoney = (EditText) v.findViewById(R.id.insert_money);
        InsertContent = (EditText) v.findViewById(R.id.insert_content);
        InsertSpinner = (Spinner) v.findViewById(R.id.spinner_insert);
        InsertOK = (Button) v.findViewById(R.id.insert_OK);
        InsertCancel = (Button) v.findViewById(R.id.insert_Cancel);


        final String[] items = new String[5];

        items[0] = "식비";
        items[1] = "여가비";
        items[2] = "주거비";
        items[3] = "교통비";
        items[4] = "저축비";

        cate = items[0];

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), R.layout.row_spn, items);
        adapter.setDropDownViewResource(R.layout.row_spn_dropdown);
        InsertSpinner.setAdapter(adapter);

        InsertSpinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(Spinner spinner, View view, int i, long l) {
                cate = items[i];
            }
        });


        InsertBtnDay.setOnClickListener(this);
        InsertBtnTime.setOnClickListener(this);
        InsertOK.setOnClickListener(this);
        InsertCancel.setOnClickListener(this);

        mSnackBar = ((InsertActivity)getActivity()).getSnackBar();

        return v;
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
            case R.id.insert_OK:

                MyDatabase myDB = new MyDatabase(getActivity());
                final SQLiteDatabase db = myDB.getWritableDatabase();

                if (InsertTitle.getText().toString().equals("")) // 누르면 이상한거 트는 문제 해결하셈
                    mSnackBar.applyStyle(R.style.SnackBarSingleLine)
                            .text("지출 물건을 입력해주세요.")
                            .duration(2000).show();
                else if (InsertMoney.getText().toString().equals(""))
                    mSnackBar.applyStyle(R.style.SnackBarSingleLine)
                            .text("금액을 입력해주세요.")
                            .duration(2000).show();
                else if (Integer.valueOf(InsertMoney.getText().toString()) <= 0) {
                    mSnackBar.applyStyle(R.style.SnackBarSingleLine)
                            .text("잘못된 금액을 입력하셨습니다.")
                            .duration(2000).show();
                } else { // 가계 내역 입력 갱신
                    ContentValues values = new ContentValues();
                    values.put("year", year);
                    values.put("month", month);
                    values.put("date", date);
                    values.put("AMPM", AMPM);
                    values.put("time", time);
                    values.put("minute", minute);
                    values.put("account", InsertTitle.getText().toString());
                    values.put("category", cate);
                    values.put("money", Integer.valueOf(InsertMoney.getText().toString()));
                    values.put("content", InsertContent.getText().toString());

                    db.insert("daymoney", null, values);

                    System.out.println("TestOK : " + year+" / "+month+" / "+date+" / "+AMPM+" / "+time+" / "+minute+
                            " / "+InsertTitle.getText().toString()+" / "+cate+" / "+InsertMoney.getText().toString()+
                            " / "+InsertContent.getText().toString());

                    String sql = "SELECT * FROM moneybook";
                    Cursor cursor = db.rawQuery(sql, null);

                    int recordCount = cursor.getCount();
                    Log.d(tag, "cursor count : " + recordCount + "\n");

                    int foodCol = cursor.getColumnIndex("food");
                    int totalCol = cursor.getColumnIndex("total");
                    int playCol = cursor.getColumnIndex("play");
                    int houseCol = cursor.getColumnIndex("house");
                    int trafficCol = cursor.getColumnIndex("traffic");
                    int savingCol = cursor.getColumnIndex("saving");

                    while (cursor.moveToNext()) {
                        food = cursor.getInt(foodCol);
                        total = cursor.getInt(totalCol);
                        play = cursor.getInt(playCol);
                        house = cursor.getInt(houseCol);
                        traffic = cursor.getInt(trafficCol);
                        saving = cursor.getInt(savingCol);
                    }

                    // 위젯에 적용하기 위해 디비에 추가된 금액 갱신

                    if (cate.equals("식비")) {
                        db.execSQL("UPDATE moneybook SET food = food + " + InsertMoney.getText().toString());
                        db.execSQL("UPDATE checkamount SET acc = acc + " + InsertMoney.getText().toString() + " WHERE title = 'food';");
                    } else if (cate.equals("여가비")) {
                        db.execSQL("UPDATE moneybook SET play = play + " + InsertMoney.getText().toString());
                        db.execSQL("UPDATE checkamount SET acc = acc + " + InsertMoney.getText().toString() + " WHERE title = 'play';");
                    } else if (cate.equals("주거비")) {
                        db.execSQL("UPDATE moneybook SET house = house + " + InsertMoney.getText().toString());
                        db.execSQL("UPDATE checkamount SET acc = acc + " + InsertMoney.getText().toString() + " WHERE title = 'house';");
                    } else if (cate.equals("교통비")) {
                        db.execSQL("UPDATE moneybook SET traffic = traffic + " + InsertMoney.getText().toString());
                        db.execSQL("UPDATE checkamount SET acc = acc + " + InsertMoney.getText().toString() + " WHERE title = 'traffic';");
                    } else if (cate.equals("저축비")) {
                        db.execSQL("UPDATE moneybook SET saving = saving + " + InsertMoney.getText().toString());
                        db.execSQL("UPDATE checkamount SET acc = acc + " + InsertMoney.getText().toString() + " WHERE title = 'saving';");
                    }

                    db.execSQL("UPDATE moneybook SET total = total + " + InsertMoney.getText().toString());
                    db.execSQL("UPDATE checkamount SET acc = acc + " + InsertMoney.getText().toString() + " WHERE title = 'total';");


                /* -- 위젯 관련 --
                try {
                    sql = "SELECT * FROM checkamount WHERE title LIKE ? ";
                    cursor = db.rawQuery(sql, new String[]{MyCustomWidget.titleWidget});

                    int accCol = cursor.getColumnIndex("acc");
                    while (cursor.moveToNext()) {
                        accCheck = cursor.getInt(accCol);
                        getContent(MyCustomWidget.titleWidget, MyCustomWidget.goalWidget, accCheck);
                    }
                } catch (IllegalArgumentException e) { }

*/

                    db.close();
                    getActivity().finish();
                }


                break;

            case R.id.insert_Cancel:
                getActivity().finish();
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


    }

}