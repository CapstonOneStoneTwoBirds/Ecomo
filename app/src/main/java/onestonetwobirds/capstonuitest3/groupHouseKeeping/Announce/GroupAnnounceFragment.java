package onestonetwobirds.capstonuitest3.groupHouseKeeping.Announce;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import onestonetwobirds.capstonuitest3.R;

/**
 * Created by YeomJi on 15. 6. 7..
 */
public class GroupAnnounceFragment extends Fragment implements View.OnClickListener {

    public static GroupAnnounceFragment newInstance() {
        GroupAnnounceFragment fragment = new GroupAnnounceFragment();

        return fragment;
    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_group_announce, container, false);


        return v;
    }

    @Override
    public void onClick(View v) {
        /*
        switch (v.getId()) {
            case R.id.new_widget_btn:
                break;
        }
        */
    }

    @Override
    public void onPause() { super.onPause(); }

    @Override
    public void onResume() { super.onResume(); }

}