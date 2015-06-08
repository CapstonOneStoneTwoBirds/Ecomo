package onestonetwobirds.capstonuitest3.groupHouseKeeping.Calendar;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.rey.material.widget.Button;

import onestonetwobirds.capstonuitest3.R;

/**
 * Created by YeomJi on 15. 6. 8..
 */
public class GroupInsertContentFragment extends Fragment{

    TextView GroupInsertTitle, GroupInsertTime, GroupInsertPlace, GroupInsertContent;
    ImageView GroupInsertImage;
    ListView GroupMemberComment;
    EditText MyCommentTxt;
    Button MyCommentBtn;


    public static GroupInsertContentFragment newInstance() {
        GroupInsertContentFragment fragment = new GroupInsertContentFragment();

        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_group_calendar_content, container, false);

        GroupInsertTitle = (TextView) v.findViewById(R.id.group_insert_title);
        GroupInsertTime = (TextView) v.findViewById(R.id.group_insert_time);
        GroupInsertPlace = (TextView) v.findViewById(R.id.group_insert_place);
        GroupInsertContent = (TextView) v.findViewById(R.id.group_insert_content);
        GroupInsertImage = (ImageView) v.findViewById(R.id.group_insert_image);
        GroupMemberComment = (ListView) v.findViewById(R.id.group_member_comment);
        MyCommentTxt = (EditText) v.findViewById(R.id.my_comment_txt);
        MyCommentBtn = (Button) v.findViewById(R.id.my_comment_btn);




        return v;
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