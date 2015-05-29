package onestonetwobirds.capstonuitest3.privateHouseKeeping;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.rey.material.widget.Button;

import onestonetwobirds.capstonuitest3.R;

public class WidgetFragment extends Fragment {


    public static WidgetFragment newInstance() {
        WidgetFragment fragment = new WidgetFragment();

        return fragment;
    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_widget, container, false);

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
