package onestonetwobirds.capstonuitest3.groupHouseKeeping.Main;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import onestonetwobirds.capstonuitest3.R;
import onestonetwobirds.capstonuitest3.httpClient.HttpClient;

/**
 * Created by YeomJi on 15. 6. 8..
 */
public class GroupMainFragment extends Fragment {

    private IconTextListAdapterGroup adapter;
    private ListView lv_main;

    public static GroupMainFragment newInstance() {
        GroupMainFragment fragment = new GroupMainFragment();

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_group_main, container, false);

        /*
        list = (ListView) v.findViewById(R.id.Group_main_List);
        adapter = new IconTextListAdapterGroup(getActivity());




        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                final IconTextItemGroup curItem = (IconTextItemGroup) adapter.getItem(position);

            }
        });
        */

        lv_main = (ListView)v.findViewById(R.id.group_lv_main);
        adapter = new IconTextListAdapterGroup(v.getContext());

        RequestParams param = new RequestParams();
        SharedPreferences mPreference;
        mPreference = v.getContext().getSharedPreferences("myInfo", v.getContext().MODE_PRIVATE);
        param.put("owner", mPreference.getString("email", ""));

        HttpClient.post("getGroupList/", param, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    JSONArray jsonarr = new JSONArray(new String(responseBody));
                    Log.e("GroupMainActivity", "Post count: " + jsonarr.length());

                    if (jsonarr.length() != 0) {
                        for (int i = 0; i < jsonarr.length(); i++) {
                            System.out.println(jsonarr.get(i));
                            final JSONObject got = new JSONObject(jsonarr.get(i).toString());
                            String num = got.get("img_num").toString();
                            if (!num.equals("0")) {
                                Log.e("GroupMainActivity", "Here!!");
                                adapter.addItem(new IconTextItemGroup(getResources().getDrawable(getNumResources(num)), got.get("groupname").toString()));
                            } else {
                                //adaptor.addItem(new IconTextItemGroup(, got.get("groupname").toString()));
                            }
                        }
                    }
                } catch (JSONException e) {
                    System.out.println(e);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                System.out.println("Failure Here ?");
            }
        });

        lv_main.setAdapter(adapter);                       // DrawerLayout º¸¿©Áà
        return v;
    }

    public int getNumResources(String num){
        int n=0;
        switch( num ){
            case "1":
                n =  R.drawable.group_1;
                break;
            case "2":
                n =  R.drawable.group_2;
                break;
            case "3":
                n =  R.drawable.group_3;
                break;
            case "4":
                n =  R.drawable.group_4;
                break;
            case "5":
                n =  R.drawable.group_5;
                break;
        }
        return n;
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
