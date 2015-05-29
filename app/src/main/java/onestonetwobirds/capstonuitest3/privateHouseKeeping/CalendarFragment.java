package onestonetwobirds.capstonuitest3.privateHouseKeeping;


import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.rey.material.widget.Button;
import com.rey.material.widget.FloatingActionButton;

import onestonetwobirds.capstonuitest3.R;

public class CalendarFragment extends Fragment {

    public static CalendarFragment newInstance() {
        CalendarFragment fragment = new CalendarFragment();

        return fragment;
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_calendar, container, false);

        Button TestBtn = (Button) v.findViewById(R.id.test_btn);



        TestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity().getApplicationContext(), "클릭~!", Toast.LENGTH_LONG).show();
            }
        });




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
