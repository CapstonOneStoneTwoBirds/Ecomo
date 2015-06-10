package onestonetwobirds.capstonuitest3.groupHouseKeeping.Announce;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;


import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.rey.material.app.Dialog;
import com.rey.material.app.DialogFragment;
import com.rey.material.app.SimpleDialog;
import com.rey.material.widget.Button;
import com.rey.material.widget.SnackBar;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import onestonetwobirds.capstonuitest3.R;
import onestonetwobirds.capstonuitest3.groupHouseKeeping.Main.InGroupActivity;
import onestonetwobirds.capstonuitest3.httpClient.HttpClient;

/**
 * Created by YeomJi on 15. 6. 7..
 */
public class GroupAnnounceFragment extends Fragment{

    ListView listView;
    ArrayList<ExamEntity> announceArrayList;
    AnnounceListAdapter announceListAdapter;
    SnackBar mSnackBar;

    public void setT(TextView tv, String str){
        tv.setText(str);
    }
    public static GroupAnnounceFragment newInstance() {
        GroupAnnounceFragment fragment = new GroupAnnounceFragment();

        return fragment;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.fragment_group_announce, container, false);
        Button NewAnnounceButton = (Button) v.findViewById(R.id.new_announce_btn);
        listView = (ListView) v.findViewById(R.id.group_announce_list);

        announceArrayList = new ArrayList();
        announceListAdapter = new AnnounceListAdapter(getActivity().getApplicationContext(),
                announceArrayList, R.layout.group_announce_list);

        listView.setAdapter(announceListAdapter);
        final SharedPreferences mPreference;
        mPreference = v.getContext().getSharedPreferences("myInfo", v.getContext().MODE_PRIVATE);
        String group_id = mPreference.getString("group_id", "");

        RequestParams param = new RequestParams();
        param.put("groupid", group_id);

        HttpClient.post("getAnnounceList/", param, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                try {
                    if (responseBody != null) {
                        final JSONArray announces = new JSONArray(new String(responseBody));

                        //arrListInsert.add(result);
                        for (int i = 0; i < announces.length(); i++) {
                            JSONObject got = new JSONObject(announces.get(i).toString());
                            Log.e("GroupAnnounceFragment", "obj : " + got);
                            ExamEntity temp = new ExamEntity();
                            try{
                                temp.title = got.get("title").toString();
                            }catch(JSONException e){}
                            try {
                                temp.place = got.get("place").toString();
                            }catch(JSONException e){}
                            try {
                                temp.content = got.get("content").toString();
                            }catch(JSONException e){}

                            announceArrayList.add(temp);
                        }

                        announceListAdapter.notifyDataSetChanged();

                        System.out.println("Announces : " + announces);

                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                                try {
                                    final JSONObject jsonobj = new JSONObject(announces.get(position).toString());
                                    //Log.e("GroupAnnounceGragment Here", jsonobj.toString());
                                    Intent intent = new Intent(v.getContext(), GroupAnnounceCActivity.class);
                                    intent.putExtra("jsonobj", jsonobj.toString());
                                    startActivity(intent);
                                }catch(JSONException e){}
                            }
                        });
                    } else {
                        System.out.println("Here Checker");
                    }

                } catch (JSONException e) {
                    System.out.println(e);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                System.out.println("error message : " + error);
            }
        });
        // 이부분에 데이터 삽입 ----------------------------------------------------
        /*
            for (int itemCount = 0; itemCount < announceArrayList.size(); itemCount++) {
                ExamEntity mExamEntity = new ExamEntity();

                mExamEntity.title = "Title : " + itemCount;
                mExamEntity.title = "Title : " + itemCount;
                mExamEntity.content = "Content : " + itemCount;

                announceArrayList.add(mExamEntity);

            }
        */

        // --------------------------------------------------------------------



        mSnackBar = ((InGroupActivity)getActivity()).getSnackBar();
        NewAnnounceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), WriteAnnounceActivity.class);
                startActivity(intent);
            }
        });

        return v;
    }

    @Override
    public void onPause() { super.onPause(); }

    @Override
    public void onResume() { super.onResume(); }

}