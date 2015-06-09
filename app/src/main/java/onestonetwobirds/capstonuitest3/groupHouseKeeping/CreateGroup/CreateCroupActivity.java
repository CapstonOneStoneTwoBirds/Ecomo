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
    ImageView setGroupImg1, setGroupImg2, setGroupImg3, setGroupImg4, setGroupImg5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_group_main);

        CreateGroupTitle = (EditText)findViewById(R.id.create_group_title);
        GreateGroupImage = (Button)findViewById(R.id.create_group_image_btn);
        setGroupImg1 = (ImageView)findViewById(R.id.set_group_image1);
        setGroupImg2 = (ImageView)findViewById(R.id.set_group_image2);
        setGroupImg3 = (ImageView)findViewById(R.id.set_group_image3);
        setGroupImg4 = (ImageView)findViewById(R.id.set_group_image4);
        setGroupImg5 = (ImageView)findViewById(R.id.set_group_image5);

    }
}
