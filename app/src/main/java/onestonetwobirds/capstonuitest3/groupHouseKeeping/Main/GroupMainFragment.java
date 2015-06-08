package onestonetwobirds.capstonuitest3.groupHouseKeeping.Main;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import onestonetwobirds.capstonuitest3.R;

/**
 * Created by YeomJi on 15. 6. 8..
 */
public class GroupMainFragment extends Fragment {


    public static GroupMainFragment newInstance() {
        GroupMainFragment fragment = new GroupMainFragment();

        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_group_main, container, false);



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
