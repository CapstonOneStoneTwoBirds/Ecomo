package onestonetwobirds.capstonuitest3.privateHouseKeeping.Calendar;


import android.annotation.TargetApi;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.rey.material.app.Dialog;
import com.rey.material.app.DialogFragment;
import com.rey.material.app.SimpleDialog;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import onestonetwobirds.capstonuitest3.R;
import onestonetwobirds.capstonuitest3.control.database.MyDatabase;
import onestonetwobirds.capstonuitest3.control.materialcalendar.CalendarDay;
import onestonetwobirds.capstonuitest3.control.materialcalendar.MaterialCalendarView;
import onestonetwobirds.capstonuitest3.control.materialcalendar.OnDateChangedListener;


public class CalendarFragment extends Fragment implements OnDateChangedListener {

    private static final DateFormat FORMATTER = SimpleDateFormat.getDateInstance();
    private TextView textView;

    final private static int DIALOG_INSERT = 1;

    ListView listViewInsert;
    View convertView;
    ArrayAdapter<String> adapterInsert;
    View v;

    int TokenYear, TokenMonth, TokenDay;
    String result;
    LayoutInflater infla;
    ViewGroup contain;

    ArrayList<String> resultArr;

    public static CalendarFragment newInstance() {
        CalendarFragment fragment = new CalendarFragment();

        return fragment;
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_calendar, container, false);

        infla = inflater;
        contain = container;

        MaterialCalendarView widget = (MaterialCalendarView) v.findViewById(R.id.calendarView);
        widget.setOnDateChangedListener(this);

        Calendar calendar = Calendar.getInstance();
        widget.setSelectedDate(calendar.getTime());

        calendar.set(calendar.get(Calendar.YEAR), Calendar.JANUARY, 1);
        widget.setMinimumDate(calendar.getTime());

        calendar.set(calendar.get(Calendar.YEAR) + 2, Calendar.OCTOBER, 31);
        widget.setMaximumDate(calendar.getTime());

        convertView = (View) inflater.inflate(R.layout.calendar_list, null, false);
        listViewInsert = (ListView) convertView.findViewById(R.id.insertList);
        resultArr = new ArrayList<String>();


        return v;
    }

    @Override
    public void onDateChanged(MaterialCalendarView widget, CalendarDay date) { // 다른 날짜가 눌러졌을 때 취하는 액션 --> 리스트를 다이얼로그로 띄우면 될듯

        final CalendarDay Cal = date;

        Dialog.Builder builder = new SimpleDialog.Builder(R.style.SimpleDialogLight){

            @Override
            protected void onBuildDone(Dialog dialog) {
                dialog.layoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                //ViewGroup parentViewGroup = (ViewGroup)convertView.getParent();
                //parentViewGroup.removeView(convertView);
                //parentViewGroup.removeAllViews();
                //((ViewGroup)convertView.getParent()).removeView(convertView);
                //contain.removeAllViews();

                TokenYear = Cal.getYear();
                TokenMonth = Cal.getMonth()+1;
                TokenDay = Cal.getDay();

                PrepareDialog(DIALOG_INSERT, dialog);

                adapterInsert = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,resultArr);
                if(convertView.getParent() != null) ((ViewGroup) convertView.getParent()).removeView(convertView);
                dialog.setContentView(convertView);

            }

            @Override
            public void onNegativeActionClicked(DialogFragment fragment) {
                super.onNegativeActionClicked(fragment);
            }
        };

        ((SimpleDialog.Builder)builder).title("이 날에 적힌 가계부 목록").negativeAction("CANCEL");

        FragmentManager fm = getFragmentManager();
        DialogFragment diaFM = DialogFragment.newInstance(builder);
        diaFM.show(fm, null);
        resultArr.clear();


    }

    protected void PrepareDialog(int id, Dialog dialog) {
        switch (id) {
            case DIALOG_INSERT:
                //final Dialog dialogD = (Dialog) dialog;


                MyDatabase myDB = new MyDatabase(getActivity());
                final SQLiteDatabase db = myDB.getWritableDatabase();


                String sql = "SELECT * FROM daymoney WHERE date LIKE ?";
                Cursor cursor = db.rawQuery(sql, new String[]{String.valueOf(TokenDay)});

                int recordCount = cursor.getCount();

                int yearCol = cursor.getColumnIndex("year");
                int monthCol = cursor.getColumnIndex("month");
                int AMPMCol = cursor.getColumnIndex("AMPM");
                int timeCol = cursor.getColumnIndex("time");
                int minuteCol = cursor.getColumnIndex("minute");
                int accountCol = cursor.getColumnIndex("account");
                int moneyCol = cursor.getColumnIndex("money");

                final String year[] = new String[recordCount];
                final String month[] = new String[recordCount];
                final String AMPM[] = new String[recordCount];
                final String time[] = new String[recordCount];
                final String minute[] = new String[recordCount];
                final String account[] = new String[recordCount];
                final int money[] = new int[recordCount];

                //String year[], month[], AMPM[], time[], minute[], account[], money[];

                //cursor.moveToFirst();
                System.out.println("TestCheck : " + cursor.getCount());

                while (cursor.moveToNext()) {
                    // 0->1->2->......
                    if (cursor.getString(yearCol).equals(String.valueOf(TokenYear)) && cursor.getString(monthCol).equals(String.valueOf(TokenMonth))) {
                        year[cursor.getPosition()] = cursor.getString(yearCol);
                        month[cursor.getPosition()] = cursor.getString(monthCol);
                        AMPM[cursor.getPosition()] = cursor.getString(AMPMCol);
                        time[cursor.getPosition()] = cursor.getString(timeCol);
                        minute[cursor.getPosition()] = cursor.getString(minuteCol);
                        account[cursor.getPosition()] = cursor.getString(accountCol);
                        money[cursor.getPosition()] = cursor.getInt(moneyCol);

                        result = AMPM[cursor.getPosition()] + "/" + time[cursor.getPosition()] + "/" +
                                minute[cursor.getPosition()] + "  " + account[cursor.getPosition()] + " " +
                                money[cursor.getPosition()];

                        System.out.println("TestOK : " + result);
                        resultArr.add(result);
                    }
                    if (cursor.getCount() == 0) resultArr.add("입력된 가계부가 없습니다."); // 작동하지 않음. 고쳐야댐.

                }
                listViewInsert.setAdapter(adapterInsert);



/*
                listViewInsert.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        //position == id
                        //System.out.println("cursor ----> "+account[position]+" / "+category[position]+" / "+money[position]+" / "+content[position]);

                        Bundle bundle = new Bundle();
                        bundle.putString("date", subDate);
                        bundle.putString("account", account[position]);
                        bundle.putString("category", category[position]);
                        //bundle.putInt("money", money[position]);
                        bundle.putString("money", String.valueOf(money[position]));
                        bundle.putString("content", content[position]);

                        // Intent로 새 액티비티 띄우기
                        Intent intent = new Intent(getActivity(), InsertResultActivity.class);
                        intent.putExtras(bundle);
                        startActivity(intent);

                    }
                });

 */

                break;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

}
