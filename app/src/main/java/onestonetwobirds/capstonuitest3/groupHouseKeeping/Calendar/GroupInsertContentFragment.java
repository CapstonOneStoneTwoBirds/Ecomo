package onestonetwobirds.capstonuitest3.groupHouseKeeping.Calendar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.rey.material.widget.Button;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import onestonetwobirds.capstonuitest3.R;
import onestonetwobirds.capstonuitest3.httpClient.HttpClient;

/**
 * Created by YeomJi on 15. 6. 8..
 */
public class GroupInsertContentFragment extends Fragment{

    TextView GroupInsertTitle, GroupInsertPrice, GroupInsertTime, GroupInsertContent;
    ListView GroupMemberComment;
    EditText MyCommentTxt;
    Button MyCommentBtn;

    static String inToDay, inToTitle;

    String group_id="";


    public static GroupInsertContentFragment newInstance() {
        GroupInsertContentFragment fragment = new GroupInsertContentFragment();

        return fragment;
    }

    public static void InToFragment(String day, String title) {
        inToDay = day;
        inToTitle = title;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_group_calendar_content, container, false);

        GroupInsertTitle = (TextView) v.findViewById(R.id.group_insert_title);
        GroupInsertPrice = (TextView) v.findViewById(R.id.group_insert_price);
        GroupInsertTime = (TextView) v.findViewById(R.id.group_insert_time);
        GroupInsertContent = (TextView) v.findViewById(R.id.group_insert_content);

        final SharedPreferences mPreference;
        mPreference = v.getContext().getSharedPreferences("myInfo", v.getContext().MODE_PRIVATE);
        group_id = mPreference.getString("group_id", "");

        RequestParams param = new RequestParams();
        param.put("groupid", group_id);

        HttpClient.post("getArticleList/", param, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    if (responseBody != null) {
                        final JSONArray articles = new JSONArray(new String(responseBody));

                        for (int i = 0; i < articles.length(); i++) {
                            JSONObject got = new JSONObject(articles.get(i).toString());
                            System.out.println("OK? " + inToDay + "  " + inToTitle);
                            if (got.get("day").toString().equals(inToDay) && got.get("title").toString().equals(inToTitle)) {
                                System.out.println("OK~");
                                GroupInsertTitle.setText(inToTitle);
                                GroupInsertPrice.setText(got.get("price").toString() + " ?›");
                                GroupInsertTime.setText(got.get("year").toString() + ". " + got.get("month").toString() + ". " + got.get("day").toString());
                                GroupInsertContent.setText(got.get("content").toString());
                            }
                        }

                        System.out.println("Articles : " + articles);
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