package onestonetwobirds.capstonuitest3.privateHouseKeeping;



import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import onestonetwobirds.capstonuitest3.R;

public class CurrentConditionFragment extends Fragment {



    public static CurrentConditionFragment newInstance() {
        CurrentConditionFragment fragment = new CurrentConditionFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_current_condition, container, false);



        return v;
    }

    @Override
    public void onPause() { super.onPause(); }

    @Override
    public void onResume() { super.onResume(); }

}
