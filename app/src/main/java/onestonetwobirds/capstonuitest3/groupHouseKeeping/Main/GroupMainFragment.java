package onestonetwobirds.capstonuitest3.groupHouseKeeping.Main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import onestonetwobirds.capstonuitest3.R;

/**
 * Created by YeomJi on 15. 6. 8..
 */
public class GroupMainFragment extends Fragment {

    ListView list;
    IconTextListAdapterGroup adapter;

    public static GroupMainFragment newInstance() {
        GroupMainFragment fragment = new GroupMainFragment();

        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_group_main, container, false);


        list = (ListView) v.findViewById(R.id.Group_main_List);
        adapter = new IconTextListAdapterGroup(getActivity());




        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                final IconTextItemGroup curItem = (IconTextItemGroup) adapter.getItem(position);

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
