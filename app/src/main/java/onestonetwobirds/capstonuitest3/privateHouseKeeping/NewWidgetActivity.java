package onestonetwobirds.capstonuitest3.privateHouseKeeping;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Toast;

import com.rey.material.widget.Button;
import com.rey.material.widget.SnackBar;
import com.rey.material.widget.Spinner;

import onestonetwobirds.capstonuitest3.R;
import onestonetwobirds.capstonuitest3.control.database.MyDatabase;

/**
 * Created by YeomJi on 15. 6. 5..
 */
public class NewWidgetActivity extends Activity implements View.OnClickListener {

    Spinner NewWidgetSpinner;
    EditText txtWidget;
    Button NWidgetOK, NWidgetCancel;

    String cate;

    int IDcount;


    SnackBar mSnackBar;

    String[] items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_widget_main);

        NewWidgetSpinner = (Spinner) findViewById(R.id.spinner_widget);
        txtWidget = (EditText) findViewById(R.id.txtWidget);
        NWidgetOK = (Button) findViewById(R.id.Nwidget_OK);
        NWidgetCancel = (Button) findViewById(R.id.Nwidget_Cancel);

        mSnackBar = (SnackBar) findViewById(R.id.Nwidget_sn);


        items = new String[6];

        items[0] = "총액";
        items[1] = "식비";
        items[2] = "여가비";
        items[3] = "주거비";
        items[4] = "교통비";
        items[5] = "저축비";

        cate = items[0];

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.row_spn, items);
        adapter.setDropDownViewResource(R.layout.row_spn_dropdown);
        NewWidgetSpinner.setAdapter(adapter);

        NewWidgetSpinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(Spinner spinner, View view, int i, long l) {
                cate = items[i];
            }
        });

        NWidgetOK.setOnClickListener(this);
        NWidgetCancel.setOnClickListener(this);

        mSnackBar = this.getSnackBar();
    }

    @Override
    public void onClick(View v) {
        //Dialog.Builder builder = null;

        switch (v.getId()) {
            case R.id.Nwidget_OK:
                MyDatabase myDB = new MyDatabase(this);
                final SQLiteDatabase db = myDB.getWritableDatabase();

                if (txtWidget.getText().toString().equals(""))
                    mSnackBar.applyStyle(R.style.SnackBarSingleLine).text("금액을 입력해주세요.").duration(2000).show();
                else {
                    String sql = "SELECT * FROM checkamount";
                    Cursor cursor = db.rawQuery(sql, null);

                    IDcount = 0;

                    ContentValues values = new ContentValues();

                    if (cursor.getCount() == 0) values.put("number", 1);
                    else {
                        while (IDcount < cursor.getCount()) {
                            IDcount++;
                        }
                        Toast.makeText(NewWidgetActivity.this, cursor.getCount() + "",
                                Toast.LENGTH_SHORT).show();
                        values.put("number", ++IDcount);
                        Toast.makeText(NewWidgetActivity.this, IDcount + "",
                                Toast.LENGTH_SHORT).show();
                    }


                    values.put("title", cate);
                    values.put("goal", Integer.valueOf(txtWidget.getText().toString()));
                    values.put("acc", 0);

                    db.insert("checkamount", null, values);

                    db.close();
                    finish();
                }
                break;
            case R.id.Nwidget_Cancel:
                finish();
                break;
        }
    }

    public SnackBar getSnackBar() {
        return mSnackBar;
    }


}
