package onestonetwobirds.capstonuitest3.privateHouseKeeping;


import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import onestonetwobirds.capstonuitest3.R;
import onestonetwobirds.capstonuitest3.control.materialcalendar.CalendarDay;
import onestonetwobirds.capstonuitest3.control.materialcalendar.MaterialCalendarView;
import onestonetwobirds.capstonuitest3.control.materialcalendar.OnDateChangedListener;


public class CalendarFragment extends Fragment implements OnDateChangedListener {

    private static final DateFormat FORMATTER = SimpleDateFormat.getDateInstance();
    private TextView textView;

    public static CalendarFragment newInstance() {
        CalendarFragment fragment = new CalendarFragment();

        return fragment;
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_calendar, container, false);


        MaterialCalendarView widget = (MaterialCalendarView) v.findViewById(R.id.calendarView);
        widget.setOnDateChangedListener(this);

        Calendar calendar = Calendar.getInstance();
        widget.setSelectedDate(calendar.getTime());

        calendar.set(calendar.get(Calendar.YEAR), Calendar.JANUARY, 1);
        widget.setMinimumDate(calendar.getTime());

        calendar.set(calendar.get(Calendar.YEAR) + 2, Calendar.OCTOBER, 31);
        widget.setMaximumDate(calendar.getTime());




        return v;
    }

    @Override
    public void onDateChanged(MaterialCalendarView widget, CalendarDay date) { // 다른 날짜가 눌러졌을 때 취하는 액션 --> 리스트를 다이얼로그로 띄우면 될듯

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
