package onestonetwobirds.capstonuitest3.privateHouseKeeping.Insert;

import android.app.Activity;
import android.os.Bundle;
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



    }
}
