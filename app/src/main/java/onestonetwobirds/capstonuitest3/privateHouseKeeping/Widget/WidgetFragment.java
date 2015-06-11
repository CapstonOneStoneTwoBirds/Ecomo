package onestonetwobirds.capstonuitest3.privateHouseKeeping.Widget;


import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;
import com.rey.material.app.Dialog;
import com.rey.material.app.DialogFragment;
import com.rey.material.app.SimpleDialog;
import com.rey.material.widget.Button;
import com.rey.material.widget.SnackBar;

import onestonetwobirds.capstonuitest3.R;
import onestonetwobirds.capstonuitest3.control.database.MyDatabase;
import onestonetwobirds.capstonuitest3.privateHouseKeeping.Main.PrivateMainActivity;

public class WidgetFragment extends Fragment implements View.OnClickListener {

    Button NewWidgetButton;

    View v;
    private Cursor cursor;

    MyDatabase myDB2;
    SQLiteDatabase db2;

    int checkID;

    private String title;
    private int goal, acc;

    SnackBar mSnackBar;

    public static String t = "";
    public static int g = 0, c = 0;


    public static WidgetFragment newInstance() {
        WidgetFragment fragment = new WidgetFragment();

        return fragment;
    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_widget, container, false);

        NewWidgetButton = (Button) v.findViewById(R.id.new_widget_btn);


        mSnackBar = ((PrivateMainActivity) getActivity()).getSnackBar();


        NewWidgetButton.setOnClickListener(this);


        return v;
    }

    @Override
    public void onClick(View v) {
        //Dialog.Builder builder = null;

        switch (v.getId()) {
            case R.id.new_widget_btn:
                Intent intent = new Intent(getActivity().getApplicationContext(), NewWidgetActivity.class);
                startActivity(intent);
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

        try {

            MyDatabase myDB = new MyDatabase(getActivity());
            final SQLiteDatabase db = myDB.getWritableDatabase();


            ListView listView = (ListView) v.findViewById(R.id.WidgetList);

            String sql = "SELECT rowid _id, * FROM checkamount";
            cursor = db.rawQuery(sql, null);


            if (cursor.getCount() != 0) {
                cursor.moveToFirst();
                String[] from = new String[]{"title", "goal", "acc"};
                int[] to = new int[]{R.id.lf_tv_title, R.id.lf_tv_goal, R.id.lf_tv_acc};
                final SimpleCursorAdapter adapter = new SimpleCursorAdapter(listView.getContext(), R.layout.widget_list, cursor, from, to);

                listView.setAdapter(adapter);
            } else
                //mSnackBar.applyStyle(R.style.SnackBarSingleLine).text("등록된 내용이 없습니다.").duration(2000).show();

            db.close();
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View v, int position,
                                        long id) {

                /*
                if (id != 0)
                    Toast.makeText(getActivity().getApplicationContext(), (int) id + "", Toast.LENGTH_LONG).show();
                else Toast.makeText(getActivity().getApplicationContext(), "No id", Toast.LENGTH_LONG).show();
                */
                    checkID = (int) id;
                    DialogSimple();

                }
            });
        } catch (SQLiteAssetHelper.SQLiteAssetException e) {
        }

    }

    private void DialogSimple() {

        Dialog.Builder builder = new SimpleDialog.Builder(R.style.SimpleDialogLight) {

            @Override
            protected void onBuildDone(Dialog dialog) {
                dialog.layoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                myDB2 = new MyDatabase(getActivity());
                db2 = myDB2.getWritableDatabase();


                String sql = "select * from checkamount where number like ?";
                Cursor cursor = db2.rawQuery(sql, new String[]{String.valueOf(checkID)});

                int recordCount = cursor.getCount();

                int titleCol = cursor.getColumnIndex("title");
                int goalCol = cursor.getColumnIndex("goal");
                int accCol = cursor.getColumnIndex("acc");

                while (cursor.moveToNext()) {
                    title = cursor.getString(titleCol);
                    goal = cursor.getInt(goalCol);
                    acc = cursor.getInt(accCol);
                }

            }

            // 수정 구현 아직 안함.

            @Override
            public void onPositiveActionClicked(DialogFragment fragment) {
                super.onPositiveActionClicked(fragment);

                db2.execSQL("UPDATE checkamount SET isWidget = 0 ;");
                db2.execSQL("UPDATE checkamount SET isWidget = 1 WHERE title = '" + title + "';");
                getContent(title, goal, acc);
            }

            @Override
            public void onNegativeActionClicked(DialogFragment fragment) {
                super.onNegativeActionClicked(fragment);
                db2.execSQL("delete from checkamount where number = " + checkID + ";");
                //db.execSQL("delete from checkamount ;");
                db2.close();
                onResume();
            }
        };

        ((SimpleDialog.Builder) builder).title("새로운 Widget 추가").positiveAction("SELECT").negativeAction("DELETE");

        FragmentManager fm = getFragmentManager();
        DialogFragment diaFM = DialogFragment.newInstance(builder);
        diaFM.show(fm, null);

    }

    public void getContent(String tt, int gg, int cc) {
        t = tt;
        g = gg;
        c = cc;
        /*
        System.out.println("OKCheck1 ===> t : "+WidgetFragment.t+"  g : "+WidgetFragment.g+"  c : "+WidgetFragment.c);
        System.out.println("OKCheck1 ===> tt : "+tt+"  gg : "+gg+"  cc : "+cc);
        */
    }


}
