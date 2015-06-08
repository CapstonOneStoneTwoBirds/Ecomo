package onestonetwobirds.capstonuitest3.groupHouseKeeping.Announce;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;


import com.rey.material.app.Dialog;
import com.rey.material.app.DialogFragment;
import com.rey.material.app.SimpleDialog;
import com.rey.material.widget.Button;
import com.rey.material.widget.EditText;
import com.rey.material.widget.FloatingActionButton;
import com.rey.material.widget.SnackBar;

import onestonetwobirds.capstonuitest3.R;
import onestonetwobirds.capstonuitest3.groupHouseKeeping.Main.InGroupActivity;

/**
 * Created by YeomJi on 15. 6. 7..
 */
public class GroupAnnounceFragment extends Fragment implements View.OnClickListener {

    ListView listView;
    SnackBar mSnackBar;
    EditText NAnnounceTitle, NAnnouncePlace, NAnnounceContent;

    public static GroupAnnounceFragment newInstance() {
        GroupAnnounceFragment fragment = new GroupAnnounceFragment();

        return fragment;
    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_group_announce, container, false);

        Button NewAnnounceButton = (Button) v.findViewById(R.id.new_announce_btn);
        listView = (ListView) v.findViewById(R.id.group_announce_list);

        NAnnounceTitle = (EditText) v.findViewById(R.id.new_announce_title);
        NAnnouncePlace = (EditText) v.findViewById(R.id.new_announce_place);
        NAnnounceContent = (EditText) v.findViewById(R.id.new_announce_content);



        mSnackBar = ((InGroupActivity)getActivity()).getSnackBar();

        NewAnnounceButton.setOnClickListener(this);

        return v;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.new_announce_btn:
                Dialog.Builder builder = new SimpleDialog.Builder(R.style.SimpleDialog) {

                    @Override
                    protected void onBuildDone(Dialog dialog) {
                        dialog.layoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    }

                    @Override
                    public void onPositiveActionClicked(DialogFragment fragment) { // OK 버튼 눌렀을 때 액션 취하기(추가된 데이터 리스트에 띄우기)

                        // 여기에다 코딩

                        onResume();
                        super.onPositiveActionClicked(fragment);
                    }

                    @Override
                    public void onNegativeActionClicked(DialogFragment fragment) {
                        super.onNegativeActionClicked(fragment);
                    }
                };

                builder.title("새 공지")
                        .positiveAction("OK")
                        .negativeAction("CANCEL")
                        .contentView(R.layout.announce_dialog);

                FragmentManager fm = getFragmentManager();
                DialogFragment diaFM = DialogFragment.newInstance(builder);
                diaFM.show(fm, null);
                break;
        }

    }

    @Override
    public void onPause() { super.onPause(); }

    @Override
    public void onResume() { super.onResume(); }

}