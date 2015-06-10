package onestonetwobirds.capstonuitest3.groupHouseKeeping.Member;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.rey.material.app.Dialog;
import com.rey.material.app.DialogFragment;
import com.rey.material.app.SimpleDialog;
import com.rey.material.widget.Button;
import com.rey.material.widget.FloatingActionButton;
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
public class GroupMemberFragment extends Fragment implements View.OnClickListener {
    String tag = "GruopMemberFragment";
    SnackBar mSnackBar;
    String group_id="";

    EditText email_edt;

    ListView listMember, listMe, listKing;
    IconTextListAdapterMember adapterMember, adapterMe, adapterKing;

    public static GroupMemberFragment newInstance() {
        GroupMemberFragment fragment = new GroupMemberFragment();

        return fragment;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.fragment_group_member, container, false);
        final View v1 = inflater.inflate(R.layout.new_member_dialog, container, false);
        Button NewMemberButton = (Button) v.findViewById(R.id.new_member_btn);
        email_edt = (EditText)v1.findViewById(R.id.new_announce_content);
        Log.e("GroupMemberFragment", "email_edt : " + email_edt);

        final SharedPreferences mPreference;
        mPreference = v.getContext().getSharedPreferences("myInfo", v.getContext().MODE_PRIVATE);
        group_id = mPreference.getString("group_id", "");

        // 그룹 멤버 리스트
        listMember = (ListView) v.findViewById(R.id.group_member_list);
        adapterMember = new IconTextListAdapterMember(getActivity());

        // 내 리스트
        listMe = (ListView) v.findViewById(R.id.group_member_me);
        adapterMe = new IconTextListAdapterMember(getActivity());

        // 총무 리스트
        listKing = (ListView) v.findViewById(R.id.group_member_king);
        adapterKing = new IconTextListAdapterMember(getActivity());

        RequestParams param = new RequestParams();
        param.add("groupid", group_id); // 가져와야한다.
        Log.e(tag, "Test Here T_T");

        HttpClient.post("getMemberList/", param, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    final JSONArray member = new JSONArray(new String(responseBody));

                    //arrListInsert.add(result);
                    for (int i = 0; i < member.length(); i++) {
                        JSONObject got = new JSONObject(member.get(i).toString());
                        if (got.get("ownership").toString().equals("true")) {
                            Log.e(tag, "Here1");
                            adapterKing.addItem(new IconTextItemMember(getResources().getDrawable(R.drawable.default_person), got.get("name").toString()));
                        } else if (got.get("member").toString().equals(mPreference.getString("email", "")) && !got.get("ownership").toString().equals("true")) {
                            adapterMe.addItem(new IconTextItemMember(getResources().getDrawable(R.drawable.default_person), got.get("name").toString()));
                            Log.e(tag, "Here2");
                        } else {
                            Log.e(tag, "Here3");
                            adapterMember.addItem(new IconTextItemMember(getResources().getDrawable(R.drawable.default_person), got.get("name").toString()));
                        }
                    }

                    listMember.setAdapter(adapterMember);
                    listMe.setAdapter(adapterMe);
                    listKing.setAdapter(adapterKing);

                    listMember.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            try {
                                JSONObject obj = new JSONObject(member.get(position).toString());

                                System.out.println("members Click OK ");

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

                                builder.title("멤버 확인")
                                        .positiveAction("OK")
                                        .contentView(R.layout.member_confirm_dialog);

                                FragmentManager fm = getFragmentManager();
                                DialogFragment diaFM = DialogFragment.newInstance(builder);
                                diaFM.show(fm, null);

                            } catch (JSONException e) {
                            }
                        }
                    });

                    listMe.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                            final IconTextListAdapterMember curItem = (IconTextListAdapterMember) adapterMe.getItem(position);

                        }
                    });

                    listKing.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                            final IconTextListAdapterMember curItem = (IconTextListAdapterMember) adapterKing.getItem(position);

                        }
                    });

                    System.out.println("getMemberList Success 1");
                    System.out.println("members : " + member);
                    switch (new String(responseBody)) {
                        case "1":
                            System.out.println("getMemberList error");
                            break;

                        case "2":
                            System.out.println("getMemberList Success 2");
                            break;
                    }
                } catch (Exception e) {
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                System.out.println("error message 1 : " + error);
            }
        });
        mSnackBar = ((InGroupActivity) getActivity()).getSnackBar();

        NewMemberButton.setOnClickListener(this);
        return v;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.new_member_btn:
                Dialog.Builder builder = new SimpleDialog.Builder(R.style.SimpleDialog) {

                    @Override
                    protected void onBuildDone(Dialog dialog) {
                        dialog.layoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    }

                    @Override
                    public void onPositiveActionClicked(DialogFragment fragment) { // OK 버튼 눌렀을 때 액션 취하기(멤버 추가)

                        // 여기에다 코딩
                        RequestParams param = new RequestParams();
                        param.put("groupid", group_id);
                        param.put("email", email_edt.getText().toString());

                        // email에 초대를 날리는거 gcm으로. 작성하자.
                        HttpClient.post("inviteMember/", param, new AsyncHttpResponseHandler() {
                            @Override
                            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                                Log.e("WriteMemberActivity |||| ", new String(responseBody));
                                if (new String(responseBody).equals("all done")) {

                                } else if (new String(responseBody).equals("already existed member")) {
                                    Toast toastView = Toast.makeText(getActivity(),
                                            new String(responseBody), Toast.LENGTH_LONG);
                                    toastView.setGravity(Gravity.CENTER, 40, 25);
                                    toastView.show();
                                } else if (new String(responseBody).equals("non-existed email")) {
                                    Toast toastView = Toast.makeText(getActivity(),
                                            new String(responseBody), Toast.LENGTH_LONG);
                                    toastView.setGravity(Gravity.CENTER, 40, 25);
                                    toastView.show();
                                }
                            }

                            @Override
                            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                                System.out.println("Invite GCM send error");
                            }
                        });
                        onResume();
                        super.onPositiveActionClicked(fragment);
                    }

                    @Override
                    public void onNegativeActionClicked(DialogFragment fragment) {
                        super.onNegativeActionClicked(fragment);
                    }
                };

                builder.title("새로운 멤버 추가")
                        .positiveAction("OK")
                        .negativeAction("CANCEL")
                        .contentView(R.layout.new_member_dialog);

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
