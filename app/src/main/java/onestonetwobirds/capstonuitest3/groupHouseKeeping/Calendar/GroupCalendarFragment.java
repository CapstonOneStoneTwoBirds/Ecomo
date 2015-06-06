package onestonetwobirds.capstonuitest3.groupHouseKeeping.Calendar;

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

/**
 * Created by YeomJi on 15. 6. 7..
 */
public class GroupCalendarFragment extends Fragment implements OnDateChangedListener {

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

    public static GroupCalendarFragment newInstance() {
        GroupCalendarFragment fragment = new GroupCalendarFragment();

        return fragment;
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_group_calendar, container, false);

        infla = inflater;
        contain = container;

        MaterialCalendarView widget = (MaterialCalendarView) v.findViewById(R.id.groupCalendarView);
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
    public void onDateChanged(MaterialCalendarView widget, CalendarDay date) { // 다른 날짜가 눌러졌을 때 취하는 액션 --> 리스트를 다이얼로그로 띄웠다.

        final CalendarDay Cal = date;

        Dialog.Builder builder = new SimpleDialog.Builder(R.style.SimpleDialogLight){

            @Override
            protected void onBuildDone(Dialog dialog) {
                dialog.layoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                TokenYear = Cal.getYear();
                TokenMonth = Cal.getMonth()+1;
                TokenDay = Cal.getDay();

                PrepareDialog(DIALOG_INSERT, dialog); // 다이얼로그 안의 리스트뷰 내용 준비

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
            case DIALOG_INSERT: // 여기 안에다가 써야함

/*      캘린더의 리스트뷰 글 클릭했을 경우
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
