package onestonetwobirds.capstonuitest3.privateHouseKeeping.CurrentStateConfirm;



import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.content.ContextWrapper;
import android.widget.Toast;


import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.Highlight;
import com.github.mikephil.charting.utils.PercentFormatter;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;
import com.rey.material.widget.SnackBar;

import java.util.ArrayList;

import onestonetwobirds.capstonuitest3.R;
import onestonetwobirds.capstonuitest3.control.database.MyDatabase;
import onestonetwobirds.capstonuitest3.privateHouseKeeping.Main.PrivateMainActivity;

public class CurrentConditionFragment extends Fragment implements OnChartValueSelectedListener {

    TextView CTotal, CFood, CPlay, CHouse, CTraffic, CSaving;

    private PieChart mChart;
    private Typeface tf;

    SnackBar mSnackBar;


    protected String[] mParties = new String[] {"식비", "여가비", "주거비", "교통비", "저축비"};


    public static CurrentConditionFragment newInstance() {
        CurrentConditionFragment fragment = new CurrentConditionFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_current_condition, container, false);

        CTotal = (TextView) v.findViewById(R.id.confirm_total);
        CFood = (TextView) v.findViewById(R.id.confirm_food);
        CPlay = (TextView) v.findViewById(R.id.confirm_play);
        CHouse = (TextView) v.findViewById(R.id.confirm_house);
        CTraffic = (TextView) v.findViewById(R.id.confirm_traffic);
        CSaving = (TextView) v.findViewById(R.id.confirm_saving);

        mSnackBar = ((PrivateMainActivity)getActivity()).getSnackBar();

        mChart = (PieChart) v.findViewById(R.id.chart1);

        try {

            MyDatabase myDB = new MyDatabase(getActivity());
            final SQLiteDatabase db = myDB.getWritableDatabase();

            String sql = "SELECT * FROM moneybook";
            Cursor cursor = db.rawQuery(sql, null);

            int totalCol = cursor.getColumnIndex("total");
            int foodCol = cursor.getColumnIndex("food");
            int playCol = cursor.getColumnIndex("play");
            int houseCol = cursor.getColumnIndex("house");
            int trafficCol = cursor.getColumnIndex("traffic");
            int savingCol = cursor.getColumnIndex("saving");


            while (cursor.moveToNext()) {

                CTotal.setText(cursor.getString(totalCol));
                CFood.setText(cursor.getString(foodCol));
                CPlay.setText(cursor.getString(playCol));
                CHouse.setText(cursor.getString(houseCol));
                CTraffic.setText(cursor.getString(trafficCol));
                CSaving.setText(cursor.getString(savingCol));

            }

            cursor.close();
            db.close();

            mChart.setUsePercentValues(true); // 퍼센트 나타내기
            mChart.setDescription("");

            mChart.setDragDecelerationFrictionCoef(0.95f);

            //tf = Typeface.createFromAsset(getActivity().getAssets(), "OpenSans-Regular.ttf");

            //mChart.setCenterTextTypeface(Typeface.createFromAsset(getActivity().getAssets(), "OpenSans-Light.ttf"));
            mChart.setDrawHoleEnabled(true); // 원 가운데 구멍 나타내기
            mChart.setHoleColorTransparent(true);

            mChart.setTransparentCircleColor(Color.WHITE);

            mChart.setHoleRadius(58f);
            mChart.setTransparentCircleRadius(61f);

            mChart.setDrawCenterText(true); // 가운데 글자 나타내기
            mChart.setCenterTextSize(20);

            mChart.setRotationAngle(0);
            // enable rotation of the chart by touch
            mChart.setRotationEnabled(true); // 원이 회전할수 있게 하기

            // mChart.setUnit(" €");
            // mChart.setDrawUnitsInChart(true);

            // add a selection listener
            mChart.setOnChartValueSelectedListener(this);


            mChart.setCenterText("총 소비금액\n" + CTotal.getText() + "원"); // 차트 가운데 총액 적는거

            mChart.animateXY(1800, 1800);

            setData(4, 100);

            Legend l = mChart.getLegend();
            //l.setPosition(Legend.LegendPosition.RIGHT_OF_CHART); --> 범례인듯
            l.setXEntrySpace(7f);
            l.setYEntrySpace(5f);

            onResume();

        } catch (SQLiteAssetHelper.SQLiteAssetException e) {

            mSnackBar.applyStyle(R.style.Material_Widget_SnackBar_Tablet_MultiLine).
                    text("입력된 가계부가 없습니다.").duration(3000).show();
        }



        return v;
    }

    private void setData(int count, float range) { // count : 갯수 / range : 100%

        float mult = range;

        ArrayList<Entry> yVals1 = new ArrayList();

        yVals1.add(new Entry(Float.valueOf(CFood.getText().toString()) / Float.valueOf(CTotal.getText().toString()) * 100, 0));
        yVals1.add(new Entry(Float.valueOf(CPlay.getText().toString())/Float.valueOf(CTotal.getText().toString())*100,1));
        yVals1.add(new Entry(Float.valueOf(CHouse.getText().toString())/Float.valueOf(CTotal.getText().toString())*100,2));
        yVals1.add(new Entry(Float.valueOf(CTraffic.getText().toString())/Float.valueOf(CTotal.getText().toString())*100,3));
        yVals1.add(new Entry(Float.valueOf(CSaving.getText().toString())/Float.valueOf(CTotal.getText().toString())*100,4));

        ArrayList<String> xVals = new ArrayList();

        for (int i = 0; i < count + 1; i++)
            xVals.add(mParties[i % mParties.length]);

        PieDataSet dataSet = new PieDataSet(yVals1, null);

        ArrayList<Integer> colors = new ArrayList();


        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);


        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);

        colors.add(ColorTemplate.getHoloBlue());

        dataSet.setColors(colors);

        PieData data = new PieData(xVals, dataSet);
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.WHITE);
        //data.setValueTypeface(tf);
        mChart.setData(data);

        // undo all highlights
        mChart.highlightValues(null);

        mChart.invalidate();
    }

    @Override
    public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {

        if (e == null)
            return;
        Log.i("VAL SELECTED",
                "Value: " + e.getVal() + ", xIndex: " + e.getXIndex()
                        + ", DataSet index: " + dataSetIndex);
    }

    @Override
    public void onNothingSelected() {
        Log.i("PieChart", "nothing selected");
    }


    @Override
    public void onPause() { super.onPause(); }

    @Override
    public void onResume() { super.onResume(); }

}
