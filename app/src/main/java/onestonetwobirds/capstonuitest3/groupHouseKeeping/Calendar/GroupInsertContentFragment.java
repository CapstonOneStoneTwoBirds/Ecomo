package onestonetwobirds.capstonuitest3.groupHouseKeeping.Calendar;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import onestonetwobirds.capstonuitest3.R;

/**
 * Created by YeomJi on 15. 6. 8..
 */
public class GroupInsertContentFragment extends Fragment{


    public static GroupInsertContentFragment newInstance() {
        GroupInsertContentFragment fragment = new GroupInsertContentFragment();

        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_group_calendar_content, container, false);



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