package onestonetwobirds.capstonuitest3.groupHouseKeeping.Calendar;

import android.annotation.TargetApi;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
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

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.rey.material.app.Dialog;
import com.rey.material.app.DialogFragment;
import com.rey.material.app.SimpleDialog;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import onestonetwobirds.capstonuitest3.R;
import onestonetwobirds.capstonuitest3.control.database.MyDatabase;
import onestonetwobirds.capstonuitest3.control.materialcalendar.CalendarDay;
import onestonetwobirds.capstonuitest3.control.materialcalendar.MaterialCalendarView;
import onestonetwobirds.capstonuitest3.control.materialcalendar.OnDateChangedListener;
import onestonetwobirds.capstonuitest3.groupHouseKeeping.Main.InGroupActivity;
import onestonetwobirds.capstonuitest3.httpClient.HttpClient;

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

    String group_id="";

    String inToDay, inToTitle;

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

        final SharedPreferences mPreference;
        mPreference = v.getContext().getSharedPreferences("myInfo", v.getContext().MODE_PRIVATE);
        group_id = mPreference.getString("group_id", "");

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

        ((InGroupActivity)getActivity()).getSnackBar();


        return v;
    }

    @Override
    public void onDateChanged(MaterialCalendarView widget, CalendarDay date) { // 다른 날짜가 눌러졌을 때 취하는 액션 --> 리스트를 다이얼로그로 띄웠다.

        final CalendarDay Cal = date;
        TokenYear = Cal.getYear();
        TokenMonth = Cal.getMonth()+1;
        TokenDay = Cal.getDay();

        Dialog.Builder builder = new SimpleDialog.Builder(R.style.SimpleDialogLight){

            @Override
            protected void onBuildDone(Dialog dialog) {
                dialog.layoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);



                PrepareDialog(DIALOG_INSERT, dialog); // 다이얼로그 안의 리스트뷰 내용 준비

                adapterInsert = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,resultArr);
                if(convertView.getParent() != null) ((ViewGroup) convertView.getParent()).removeView(convertView);
                dialog.setContentView(convertView);

                /*
                android.support.v4.app.FragmentManager fragmentManager = getFragmentManager();
                android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                //int c = contain.getId();
                //System.out.println("OK : "+c);
                fragmentTransaction.replace(2131427508, null);
                fragmentTransaction.commit();
                */


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

                final Bundle bundle = new Bundle();

                RequestParams param = new RequestParams();
                param.put("groupid", group_id);

                HttpClient.post("getArticleList/", param, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                        try {
                            if (responseBody != null) {
                                final JSONArray articles = new JSONArray(new String(responseBody));


                                //arrListInsert.add(result);
                                for (int i = 0; i < articles.length(); i++) {
                                    JSONObject got = new JSONObject(articles.get(i).toString());
                                    if(got.get("day").toString().equals(String.valueOf(TokenDay)) && got.get("month").toString().equals(String.valueOf(TokenMonth)) && got.get("year").toString().equals(String.valueOf(TokenYear))) {
                                        resultArr.add(got.get("title").toString() + "  " + got.get("price").toString());

                                    }
                                }
                                listViewInsert.setAdapter(adapterInsert);

                                listViewInsert.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                        try {
                                            JSONObject obj = new JSONObject(articles.get(position).toString());

                                            bundle.putString("day", obj.get("day").toString());
                                            bundle.putString("title", obj.get("title").toString());

                                            Intent intent = new Intent(getActivity().getApplicationContext(), GroupInsertContentActivity.class);
                                            intent.putExtra("jsonobject", obj.toString());
                                            intent.putExtras(bundle);
                                            startActivity(intent);

                                        } catch (JSONException e) {
                                        }
                                    }
                                });

                                System.out.println("Articles : " + articles);
                            } else {
                                System.out.println("Here Checker");
                            }

                        } catch (JSONException e) {
                            System.out.println(e);
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                        System.out.println("error message : " + error);
                    }
                });

                //listViewInsert.setAdapter(adapterInsert);

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
