package onestonetwobirds.capstonuitest3.groupHouseKeeping.Announce;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.rey.material.widget.Button;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import onestonetwobirds.capstonuitest3.R;
import onestonetwobirds.capstonuitest3.httpClient.HttpClient;

/**
 * Created by New on 2015-06-06.
 */
public class GroupAnnounceCActivity extends Activity {

    TextView CAnnounceTitle, CAnnouncePlace, CAnnounceContent;
    Button CAnnounceOK;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.group_announce_confirm_main);

        CAnnounceTitle = (TextView)findViewById(R.id.announce_confirm_title);
        CAnnouncePlace = (TextView)findViewById(R.id.announce_confirm_place);
        CAnnounceContent = (TextView)findViewById(R.id.announce_confirm_content);

        CAnnounceOK = (Button)findViewById(R.id.announce_confirm_OK);

        String jsonobject = getIntent().getStringExtra("jsonobj");
        if( jsonobject == null ){
            try{
                final JSONObject jsonobj = new JSONObject(jsonobject);
                RequestParams param = new RequestParams();

                param.add("announce_id", jsonobj.get("_id").toString());
                HttpClient.post("getAnnounce/", param, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                        try {
                            if (responseBody != null) {
                                final JSONObject obj = new JSONObject(new String(responseBody));
                                Log.e("GroupAnnounceFragment", "obj : " + obj);

                                CAnnounceTitle.setText(obj.get("title").toString());
                                CAnnouncePlace.setText(obj.get("place").toString());
                                CAnnounceContent.setText(obj.get("content").toString());
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
            }catch(JSONException e){}
        }
        else {
            Log.e("GroupAnnounceCActivity | ", jsonobject);
            try {
                final JSONObject obj = new JSONObject(jsonobject);
                try {
                    CAnnounceTitle.setText(obj.get("title").toString());
                }catch(Exception e){}
                try {
                    CAnnouncePlace.setText(obj.get("place").toString());
                }catch(Exception e){}
                try {
                    CAnnounceContent.setText(obj.get("content").toString());
                }catch(Exception e){}
            } catch (Exception e) { Log.e("Heree!!!!!!!!!!!", e.toString());            }
        }

        CAnnounceOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

}
