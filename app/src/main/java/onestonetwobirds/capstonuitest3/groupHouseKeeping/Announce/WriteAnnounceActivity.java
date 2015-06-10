package onestonetwobirds.capstonuitest3.groupHouseKeeping.Announce;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;

import onestonetwobirds.capstonuitest3.R;
import onestonetwobirds.capstonuitest3.httpClient.HttpClient;

/**
 * Created by New on 2015-05-14.
 */
public class WriteAnnounceActivity extends Activity{

    EditText NAnnounceTitle, NAnnouncePlace, NAnnounceContent;
    Button NAnnounceOK, NAnnounceCancel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.group_announce_write);
<<<<<<< Updated upstream
        final SharedPreferences mPreference;
        mPreference = getSharedPreferences("myInfo", MODE_PRIVATE);
        final String group_id = mPreference.getString("group_id", "");
=======

        NAnnounceOK = (Button)findViewById(R.id.new_announce_OK);
        NAnnounceCancel = (Button)findViewById(R.id.new_announce_Cancel);
        NAnnounceTitle = (EditText)findViewById(R.id.new_announce_title);
        NAnnouncePlace = (EditText)findViewById(R.id.new_announce_place);
        NAnnounceContent = (EditText)findViewById(R.id.new_announce_content);

>>>>>>> Stashed changes


        NAnnounceOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                System.out.println("title 1: " + NAnnounceTitle.getText().toString());

                RequestParams param = new RequestParams();
<<<<<<< Updated upstream
                param.put("groupid", group_id);
                param.put("title", titleedt.getText().toString());
                param.put("content", contentedt.getText().toString());
=======
                param.put("groupid", getIntent().getStringExtra("groupid"));
                param.put("title", NAnnounceTitle.getText().toString());
                param.put("place", NAnnouncePlace.getText().toString());
                param.put("content", NAnnounceContent.getText().toString());
>>>>>>> Stashed changes

                //System.out.println("title 2: " + title);
                HttpClient.post("writeAnnounce/", param, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                        System.out.println("Announce Save Success");
                        System.out.println("output : " + new String(responseBody));
                        switch (new String(responseBody)) {
                            case "1":
                                System.out.println("write announce error");
                                break;

                            case "2":
                                System.out.println("write announce Success");

                                finish();

                                break;
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                        System.out.println("error message 1 : " + error);
                    }
                });
            }
        });
    }
}
