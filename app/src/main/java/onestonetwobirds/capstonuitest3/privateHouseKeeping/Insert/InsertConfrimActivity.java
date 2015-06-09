package onestonetwobirds.capstonuitest3.privateHouseKeeping.Insert;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.rey.material.widget.Button;

import onestonetwobirds.capstonuitest3.R;

/**
 * Created by YeomJi on 15. 6. 7..
 */
public class InsertConfrimActivity extends Activity {

    TextView InsertConfirmTextDay, InsertComfirmTextTime, InsertConfirmTitle, InsertConfirmCategroy, InsertConfirmMoney, InsertConfirmContent;
    Button InsertConfrimOK, insesrtConfirmModify;

    String YearB, MonthB, DateB, AMPMB, TimeB, MinuteB, AccountB, CategroyB, ContentB;
    int MoneyB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.insert_confrim_main);

        InsertConfirmTextDay = (TextView)findViewById(R.id.insert_confirm_text_day);
        InsertComfirmTextTime = (TextView)findViewById(R.id.insert_confirm_text_time);
        InsertConfirmTitle = (TextView)findViewById(R.id.insert_confirm_title);
        InsertConfirmCategroy = (TextView)findViewById(R.id.insert_confirm_categroy);
        InsertConfirmMoney = (TextView)findViewById(R.id.insert_confirm_money);
        InsertConfirmContent = (TextView)findViewById(R.id.insert_confirm_content);

        InsertConfrimOK = (Button)findViewById(R.id.insert_confirm_OK);
        insesrtConfirmModify = (Button)findViewById(R.id.insert_confirm_Modify);


        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        YearB = bundle.getString("year");
        MonthB = bundle.getString("month");
        DateB = bundle.getString("date");
        AMPMB = bundle.getString("AMPM");
        TimeB = bundle.getString("time");
        MinuteB = bundle.getString("minute");
        AccountB = bundle.getString("account");
        CategroyB = bundle.getString("category");
        MoneyB = bundle.getInt("money");
        ContentB = bundle.getString("content");

        InsertConfirmTextDay.setText(YearB+". "+MonthB+". "+DateB);
        InsertComfirmTextTime.setText(AMPMB+" "+TimeB+":"+MinuteB);
        InsertConfirmTitle.setText(AccountB);
        InsertConfirmCategroy.setText(CategroyB);
        InsertConfirmContent.setText(ContentB);

        InsertConfirmMoney.setText(String.valueOf(MoneyB));

        InsertConfrimOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), InsertActivity.class);
                startActivity(intent);
                finish();

            }
        });

        insesrtConfirmModify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { finish(); }
        });



    }
}
