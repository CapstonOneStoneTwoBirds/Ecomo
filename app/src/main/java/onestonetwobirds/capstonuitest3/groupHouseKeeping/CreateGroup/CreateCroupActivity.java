package onestonetwobirds.capstonuitest3.groupHouseKeeping.CreateGroup;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.rey.material.widget.Button;
import com.rey.material.widget.EditText;

import onestonetwobirds.capstonuitest3.R;

/**
 * Created by YeomJi on 15. 6. 9..
 */
public class CreateCroupActivity extends Activity {

    EditText CreateGroupTitle;
    Button GreateGroupImage;
    ImageButton d;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_group_main);

        CreateGroupTitle = (EditText)findViewById(R.id.create_group_title);
        GreateGroupImage = (Button)findViewById(R.id.create_group_image_btn);
    }
}
