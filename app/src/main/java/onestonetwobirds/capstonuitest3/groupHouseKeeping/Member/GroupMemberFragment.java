package onestonetwobirds.capstonuitest3.groupHouseKeeping.Member;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.rey.material.app.Dialog;
import com.rey.material.app.DialogFragment;
import com.rey.material.app.SimpleDialog;
import com.rey.material.widget.Button;
import com.rey.material.widget.EditText;
import com.rey.material.widget.FloatingActionButton;
import com.rey.material.widget.SnackBar;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
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
    View view = null;
    Bitmap[] ret= null;

    int n = 0;
    ListView listMember, listMe, listKing;
    IconTextListAdapterMember adapterMember, adapterMe, adapterKing;

    public static GroupMemberFragment newInstance() {
        GroupMemberFragment fragment = new GroupMemberFragment();

        return fragment;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.fragment_group_member, container, false);
        view = inflater.inflate(R.layout.fragment_group_member, container, false);
        Button NewMemberButton = (Button) v.findViewById(R.id.new_member_btn);

        final SharedPreferences mPreference;
        mPreference = v.getContext().getSharedPreferences("myInfo", v.getContext().MODE_PRIVATE);
        group_id = mPreference.getString("group_id", "");

        // 그룹 멤버 리스트
        listMember = (ListView) v.findViewById(R.id.group_member_list);
        adapterMember = new IconTextListAdapterMember(getActivity());

        // 내 리스트
        listMe = (ListView) v.findViewById(R.id.group_member_me);
        adapterMe = new IconTextListAdapterMember(getActivity());

        RequestParams param = new RequestParams();
        param.add("groupid", group_id); // 가져와야한다.

        HttpClient.post("getMemberList/", param, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                try {
                    final JSONArray member = new JSONArray(new String(responseBody));
                    ret = new Bitmap[member.length()];
                    Log.e(tag, member.toString());
                    //arrListInsert.add(result);
                    for (int i = 0; i < member.length(); i++) {
                        JSONObject got = new JSONObject(member.get(i).toString());

                        if(got.get("member").toString().equals(mPreference.getString("email", ""))){
                            if( got.get("ownership").toString().equals("true") ){
                                Bitmap bitmap = getMemberProfileImg(i, got.get("member").toString());
                                adapterMe.addItem(new IconTextItemMember(bitmap, got.get("name").toString() + " ☆"));
                                n=i;
                            }else{
                                Bitmap bitmap = getMemberProfileImg(i, got.get("member").toString());
                                adapterMe.addItem(new IconTextItemMember(bitmap, got.get("name").toString()));
                                n=i;
                            }
                        }
                        else{
                            if( got.get("ownership").toString().equals("true") ){
                                Bitmap bitmap = getMemberProfileImg(i, got.get("member").toString());
                                adapterMember.addItemOnFirst(new IconTextItemMember(bitmap, got.get("name").toString() + " ☆"));
                            }else{
                                Bitmap bitmap = getMemberProfileImg(i, got.get("member").toString());
                                adapterMember.addItem(new IconTextItemMember(bitmap, got.get("name").toString()));
                            }
                        }
                    }

                    listMember.setAdapter(adapterMember);
                    listMe.setAdapter(adapterMe);

                    listMember.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            try {
                                IconTextItemMember icon = (IconTextItemMember)adapterMember.getItem(position);
                                String data = icon.getData(1);
                                if( data.indexOf(' ') == -1){
                                    for( int i = 0 ; i < member.length() ; i ++ ){
                                        JSONObject obj = new JSONObject(member.get(i).toString());
                                        if( obj.get("name").toString().equals(data)) {
                                            Intent intent = new Intent(v.getContext(), GroupMemberInfoActivity.class);
                                            intent.putExtra("jsonobject", obj.toString());
                                            startActivity(intent);
                                        }
                                    }
                                }
                            } catch (JSONException e) {
                            }
                        }
                    });

                    listMe.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                            try {
                                JSONObject obj = new JSONObject(member.get(n).toString());
                                Intent intent = new Intent(v.getContext(), GroupMemberInfoActivity.class);
                                intent.putExtra("jsonobject", obj.toString());
                                startActivity(intent);
                            } catch (JSONException e) {
                            }
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
                Intent intent = new Intent(getActivity(), WriteMemberActivity.class);
                startActivity(intent);
                break;
        }

    }

    @Override
    public void onPause() { super.onPause(); }

    @Override
    public void onResume() { super.onResume(); }

    public Bitmap getMemberProfileImg(final int i, final String email){
        // Get Member Profile Img
        // If Internal Storage has user's Img, get this one
        // else get from server.
        final String filename = email+"_profile.jpg";
        ret[i] = null;
        try {
            FileInputStream fis = view.getContext().openFileInput(filename);
            Bitmap scaled = BitmapFactory.decodeStream(fis);
            fis.close();

            //string temp contains all the data of the file.
            System.out.println("img catch exception");
            ret[i] = scaled;
        }catch( FileNotFoundException e){
            RequestParams param = new RequestParams();
            param.put("email", email);
            Log.e(tag, "Error? : " + e);
            HttpClient.post("getMemberImg/", param, new AsyncHttpResponseHandler() {

                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    if (new String(responseBody).equals("No Image")) {
                        ret[i] = BitmapFactory.decodeResource(getResources(), R.drawable.default_person);
                    } else {
                        Bitmap d = BitmapFactory.decodeByteArray(responseBody, 0, responseBody.length);

                        int nh = (int) (d.getHeight() * (512.0 / d.getWidth()));
                        Bitmap scaled = Bitmap.createScaledBitmap(d, 512, nh, true);
                        ret[i] = scaled;

                        FileOutputStream outputStream;
                        try {
                            outputStream = view.getContext().openFileOutput(filename, Context.MODE_PRIVATE);
                            scaled.compress(Bitmap.CompressFormat.JPEG, 90, outputStream);
                            outputStream.close();
                        } catch (Exception err) {
                            err.printStackTrace();
                        }
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    System.out.println("error message 1 : " + error);
                }
            });
        }catch(IOException e){
            e.printStackTrace();
        }
        try {
            while(true) {
                Thread.sleep(500);
                if( ret[i] != null )
                    break;
            }
        }catch(Exception e){}
        return ret[i];
    }

}
