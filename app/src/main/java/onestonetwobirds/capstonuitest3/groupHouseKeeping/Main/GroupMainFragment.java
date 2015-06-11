package onestonetwobirds.capstonuitest3.groupHouseKeeping.Main;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import onestonetwobirds.capstonuitest3.R;
import onestonetwobirds.capstonuitest3.httpClient.HttpClient;

/**
 * Created by YeomJi on 15. 6. 8..
 */
public class GroupMainFragment extends Fragment {
    private Bitmap bitmap=null;
    private IconTextListAdapterGroup adapter;
    private ListView lv_main;

    public static GroupMainFragment newInstance() {
        GroupMainFragment fragment = new GroupMainFragment();

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_group_main, container, false);

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
                    final JSONArray jsonarr = new JSONArray(new String(responseBody));
                    Log.e("GroupMainActivity", "Post count: " + jsonarr.length());

                    if (jsonarr.length() != 0) {
                        for (int i = 0; i < jsonarr.length(); i++) {
                            System.out.println(jsonarr.get(i));
                            final JSONObject got = new JSONObject(jsonarr.get(i).toString());
                            String num = got.get("img_num").toString();
                            if (!num.equals("0")) {
                                Log.e("GroupMainActivity", "Here!!");
                                adapter.addItem(new IconTextItemGroup(getResources().getDrawable(getNumResources(num)), got.get("group_name").toString(), got.get("owner_name").toString(), got.get("member_cnt").toString()));
                            } else {
                                //adaptor.addItem(new IconTextItemGroup(, got.get("groupname").toString()));
                                Bitmap b = getMemberProfileImg(got.get("group_id").toString());
                                adapter.addItem(new IconTextItemGroup(b, got.get("group_name").toString(), got.get("owner_name").toString(), got.get("member_cnt").toString()));
                            }
                        }

                        lv_main.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                                try {
                                    JSONObject obj = new JSONObject(jsonarr.get(position).toString());
                                    Intent intent = new Intent(v.getContext(), InGroupActivity.class);
                                    intent.putExtra("group_id", obj.get("group_id").toString());
                                    startActivity(intent);
                                } catch (JSONException e) {
                                }
                            }
                        });

                        lv_main.setAdapter(adapter);                       // DrawerLayout ������
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

        return v;
    }

    public Bitmap getMemberProfileImg(final String group_id){
        // Get Group Img
        // If Internal Storage has user's Img, get this one
        // else get from server.
        final String filename = group_id+"_group.jpg";

        try {
            FileInputStream fis = getActivity().openFileInput(filename);
            Bitmap scaled = BitmapFactory.decodeStream(fis);
            fis.close();

            //string temp contains all the data of the file.
            System.out.println("Error here?");
            bitmap = scaled;
        }catch( FileNotFoundException e){
            RequestParams param = new RequestParams();
            param.put("group_id", group_id);
            System.out.println("Error? : " + e);
            HttpClient.post("getGroupImg/", param, new AsyncHttpResponseHandler() {

                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    if (new String(responseBody).equals("No Image")) {
                        bitmap = null;
                    } else {
                        Bitmap d = BitmapFactory.decodeByteArray(responseBody, 0, responseBody.length);

                        int nh = (int) (d.getHeight() * (512.0 / d.getWidth()));
                        Bitmap scaled = Bitmap.createScaledBitmap(d, 512, nh, true);
                        bitmap = scaled;

                        FileOutputStream outputStream;
                        try {
                            outputStream = getActivity().openFileOutput(filename, Context.MODE_PRIVATE);
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
        return bitmap;
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
