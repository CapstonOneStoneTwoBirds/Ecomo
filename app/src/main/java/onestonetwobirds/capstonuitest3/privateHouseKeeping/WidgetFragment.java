package onestonetwobirds.capstonuitest3.privateHouseKeeping;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.rey.material.app.Dialog;
import com.rey.material.app.DialogFragment;
import com.rey.material.app.SimpleDialog;
import com.rey.material.widget.Button;
import com.rey.material.widget.Spinner;

import onestonetwobirds.capstonuitest3.R;

public class WidgetFragment extends Fragment {

    Button NewWidgetButton;
    Spinner NewWidgetSpinner;

    String cate;

    public static WidgetFragment newInstance() {
        WidgetFragment fragment = new WidgetFragment();

        return fragment;
    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_widget, container, false);

        NewWidgetButton = (Button) v.findViewById(R.id.new_widget_btn);
        NewWidgetSpinner = (Spinner) v.findViewById(R.id.spinner_insert);

        final String[] items = new String[6];

        items[0] = "총액";
        items[1] = "식비";
        items[2] = "여가비";
        items[3] = "주거비";
        items[4] = "교통비";
        items[5] = "저축비";

        cate = items[0];



        NewWidgetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getActivity().getApplicationContext(), "클릭~!", Toast.LENGTH_LONG).show();
                Dialog.Builder builder = new SimpleDialog.Builder(R.style.SimpleDialogLight){

                    @Override
                    protected void onBuildDone(Dialog dialog) {
                        dialog.layoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), R.layout.row_spn, items);
                        adapter.setDropDownViewResource(R.layout.row_spn_dropdown);
                        NewWidgetSpinner.setAdapter(adapter);

                        NewWidgetSpinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(Spinner spinner, View view, int i, long l) {
                                cate = items[i];
                            }
                        });


                    }

                    @Override
                    public void onPositiveActionClicked(DialogFragment fragment) {
                        super.onPositiveActionClicked(fragment);
                    }

                    @Override
                    public void onNegativeActionClicked(DialogFragment fragment) {
                        super.onNegativeActionClicked(fragment);
                    }
                };

                ((SimpleDialog.Builder)builder).title("새로운 Widget 추가").positiveAction("OK").negativeAction("CANCEL");

                FragmentManager fm = getFragmentManager();
                DialogFragment diaFM = DialogFragment.newInstance(builder);
                diaFM.show(fm, null);
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
