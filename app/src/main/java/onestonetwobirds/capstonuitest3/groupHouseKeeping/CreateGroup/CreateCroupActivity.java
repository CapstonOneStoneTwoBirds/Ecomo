package onestonetwobirds.capstonuitest3.groupHouseKeeping.CreateGroup;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.rey.material.app.Dialog;
import com.rey.material.app.DialogFragment;
import com.rey.material.app.SimpleDialog;
import com.rey.material.widget.Button;

import onestonetwobirds.capstonuitest3.R;

/**
 * Created by YeomJi on 15. 6. 9..
 */
public class CreateCroupActivity extends FragmentActivity implements View.OnClickListener {

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


        setGroupImg1.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.set_group_image1:
                Dialog.Builder builder = new SimpleDialog.Builder(R.style.SimpleDialog) {

                    @Override
                    protected void onBuildDone(Dialog dialog) {

                        // 이미지 띄우는 거 수정 필요
                        dialog.layoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        setGroupImg1.setBackgroundResource(R.drawable.ic_user);
                        setGroupImg1.setImageResource(R.drawable.ocr);

                    }

                    @Override
                    public void onPositiveActionClicked(DialogFragment fragment) {

                        // 여기에다 코딩

                        onResume();
                        super.onPositiveActionClicked(fragment);
                    }

                    @Override
                    public void onNegativeActionClicked(DialogFragment fragment) {
                        super.onNegativeActionClicked(fragment);
                    }
                };

                builder.title("그룹 사진 설정")
                        .positiveAction("OK")
                        .negativeAction("CANCEL")
                        .contentView(R.layout.create_group_select_img_dialog);

                DialogFragment fragment = DialogFragment.newInstance(builder);
                fragment.show(getSupportFragmentManager(), null);

                break;
        }

    }
}
