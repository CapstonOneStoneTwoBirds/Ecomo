package onestonetwobirds.capstonuitest3.groupHouseKeeping.Announce;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;


import com.rey.material.app.Dialog;
import com.rey.material.app.DialogFragment;
import com.rey.material.app.SimpleDialog;
import com.rey.material.widget.Button;
import com.rey.material.widget.EditText;
import com.rey.material.widget.SnackBar;

import org.json.JSONException;

import java.util.ArrayList;

import onestonetwobirds.capstonuitest3.R;
import onestonetwobirds.capstonuitest3.groupHouseKeeping.Main.InGroupActivity;

/**
 * Created by YeomJi on 15. 6. 7..
 */
public class GroupAnnounceFragment extends Fragment implements View.OnClickListener {

    ListView listView;
    ArrayList<ExamEntity> announceArrayList;
    AnnounceListAdapter announceListAdapter;

    SnackBar mSnackBar;
    EditText NAnnounceTitle, NAnnouncePlace, NAnnounceContent;
    TextView CAnnounceTitle, CAnnouncePlace, CAnnounceContent;

    public static GroupAnnounceFragment newInstance() {
        GroupAnnounceFragment fragment = new GroupAnnounceFragment();

        return fragment;
    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.fragment_group_announce, container, false);

        Button NewAnnounceButton = (Button) v.findViewById(R.id.new_announce_btn);
        listView = (ListView) v.findViewById(R.id.group_announce_list);


        NAnnounceTitle = (EditText) v.findViewById(R.id.new_announce_title);
        NAnnouncePlace = (EditText) v.findViewById(R.id.new_announce_place);
        NAnnounceContent = (EditText) v.findViewById(R.id.new_announce_content);

        CAnnounceTitle = (TextView) v.findViewById(R.id.announce_confirm_title);
        CAnnouncePlace = (TextView) v.findViewById(R.id.announce_confirm_place);
        CAnnounceContent = (TextView) v.findViewById(R.id.announce_confirm_content);


        announceArrayList = new ArrayList<ExamEntity>();
        announceListAdapter = new AnnounceListAdapter(getActivity().getApplicationContext(),
                announceArrayList, R.layout.group_announce_list);

        listView.setAdapter(announceListAdapter);

        // 이부분에 데이터 삽입 ----------------------------------------------------

            for (int itemCount = 0; itemCount < 20; itemCount++) {
                ExamEntity mExamEntity = new ExamEntity();

                mExamEntity.title = "Title : " + itemCount;
                mExamEntity.title = "Title : " + itemCount;
                mExamEntity.content = "Content : " + itemCount;

                announceArrayList.add(mExamEntity);

            }

            announceListAdapter.notifyDataSetChanged();

        // --------------------------------------------------------------------

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                /*
                try {
                    JSONObject obj = new JSONObject(announces.get(position).toString());

                    Intent intent = new Intent(getActivity().getApplicationContext(), GroupAnnounceActivity.class);
                    intent.putExtra("jsonobject", obj.toString());
                    startActivity(intent);

                    Toast toastView = Toast.makeText(getApplicationContext(),
                          obj.get("title").toString(), Toast.LENGTH_LONG);
                    toastView.setGravity(Gravity.CENTER, 40, 25);
                    toastView.show();

                } catch (JSONException e) {
                }
                */
                Dialog.Builder builder = new SimpleDialog.Builder(R.style.SimpleDialog) {

                    @Override
                    protected void onBuildDone(Dialog dialog) {
                        dialog.layoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                        // 이런식으로...
                        //CAnnounceTitle.setText(obj.get("title"));
                    }

                    @Override
                    public void onPositiveActionClicked(DialogFragment fragment) { // OK 버튼 눌렀을 때 액션 취하기(추가된 데이터 리스트에 띄우기)
                        // 여기에다 코딩

                        onResume();
                        super.onPositiveActionClicked(fragment);
                    }

                };

                builder.title("공지 확인")
                        .positiveAction("OK")
                        .contentView(R.layout.announce_confirm_dialog);

                FragmentManager fm = getFragmentManager();
                DialogFragment diaFM = DialogFragment.newInstance(builder);
                diaFM.show(fm, null);
            }
        });



        
        /*


        _id = getIntent().getStringExtra("groupid");
        System.out.println("_id : " + _id);

        RequestParams param = new RequestParams();
        param.put("groupid", _id);

        HttpClient.post("getAnnounceList/", param, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    if (responseBody != null) {
                        final JSONArray announces = new JSONArray(new String(responseBody));

                        //arrListInsert.add(result);
                        for (int i = 0; i < announces.length(); i++) {
                            JSONObject got = new JSONObject(announces.get(i).toString());
                            arrListInsert.add(got.get("title").toString() + " / " + got.get("content").toString());
                        }
                        listView.setAdapter(adapterInsert);
                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                try {
                                    JSONObject obj = new JSONObject(announces.get(position).toString());

                                    Intent intent = new Intent(getApplicationContext(), GroupAnnounceCActivity.class);
                                    intent.putExtra("jsonobject", obj.toString());
                                    startActivity(intent);

                                    //Toast toastView = Toast.makeText(getApplicationContext(),
                                      //      obj.get("title").toString(), Toast.LENGTH_LONG);
                                    //toastView.setGravity(Gravity.CENTER, 40, 25);
                                    //toastView.show();

                                } catch (JSONException e) {
                                }
                            }
                        });
                        System.out.println("Announces : " + announces);
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


*/



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