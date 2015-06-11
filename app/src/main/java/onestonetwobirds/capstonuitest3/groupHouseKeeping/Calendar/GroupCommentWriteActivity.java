package onestonetwobirds.capstonuitest3.groupHouseKeeping.Calendar;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;

import onestonetwobirds.capstonuitest3.httpClient.HttpClient;

/**
 * Created by New on 2015-05-26.
 */
public class GroupCommentWriteActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String content = getIntent().getStringExtra("content");
        final String article_id = getIntent().getStringExtra("article_id");

        SharedPreferences mPreference = getSharedPreferences("myInfo", MODE_PRIVATE);
        RequestParams param = new RequestParams();
        param.add("content", content);
        param.add("article_id", article_id);
        param.add("writer", mPreference.getString("email",""));
        HttpClient.post("writeComment/", param, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                System.out.println("Comment Save Success");
                System.out.println("output : " + new String(responseBody));
                switch (new String(responseBody)) {
                    case "1":
                        System.out.println("write commment error");
                        break;

                    case "2":
                        System.out.println("write commment Success");
                        /*
                        Intent intent = new Intent(getApplicationContext(), GroupArticleCActivity.class);

                        intent.putExtra("article_id", article_id);
                        startActivity(intent);
                        finish();
                        break;
                        */
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                System.out.println("error message 1 : " + error);
            }
        });
    }
}
