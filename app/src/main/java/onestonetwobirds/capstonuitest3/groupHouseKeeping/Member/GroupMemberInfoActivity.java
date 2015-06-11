package onestonetwobirds.capstonuitest3.groupHouseKeeping.Member;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import onestonetwobirds.capstonuitest3.R;
import onestonetwobirds.capstonuitest3.httpClient.HttpClient;

/**
 * Created by New on 2015-06-07.
 */
public class GroupMemberInfoActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.group_member_info);
        try{
            JSONObject obj = new JSONObject(getIntent().getStringExtra("jsonobject").toString());
            TextView email = (TextView)findViewById(R.id.memberinfo_email_tv);
            TextView name = (TextView)findViewById(R.id.memberinfo_name_tv);
            final TextView phone = (TextView)findViewById(R.id.memberinfo_phone_tv);
            ImageView iv = (ImageView)findViewById(R.id.member_iv);

            email.setText(obj.get("member").toString());
            name.setText(obj.get("name").toString());

            RequestParams param = new RequestParams();
            param.put("email", email.getText().toString());

            HttpClient.post("getMember/", param, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    try {
                        Log.e("GroupMemberInfoActivity ", "OnSuccess");
                        JSONObject o = new JSONObject(new String(responseBody));
                        phone.setText(o.get("phone").toString());
                    } catch (JSONException e) {
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    Log.e("GroupMemberInfoActivity ", "OnFailure");
                }
            });


            getMemberProfileImg(email.getText().toString(), iv);
        }catch(JSONException e){}
    }

    ///////////////////////////////////////////////////////////
    public void getMemberProfileImg(final String email, final ImageView img){
        // Get Member Profile Img
        // If Internal Storage has user's Img, get this one
        // else get from server.
        final String filename = email+"_profile.jpg";

        try {
            FileInputStream fis = openFileInput(filename);
            Bitmap scaled = BitmapFactory.decodeStream(fis);
            fis.close();

            //string temp contains all the data of the file.
            System.out.println("Error here?");
            img.setImageBitmap(scaled);
        }catch( FileNotFoundException e){
            RequestParams param = new RequestParams();
            param.put("email", email);
            System.out.println("Error? : " + e);
            HttpClient.post("getMemberImg/", param, new AsyncHttpResponseHandler() {

                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    if (new String(responseBody).equals("No Image")) {
                        img.setImageResource(R.drawable.default_person);
                    } else {
                        Bitmap d = BitmapFactory.decodeByteArray(responseBody, 0, responseBody.length);

                        int nh = (int) (d.getHeight() * (512.0 / d.getWidth()));
                        Bitmap scaled = Bitmap.createScaledBitmap(d, 512, nh, true);
                        img.setImageBitmap(scaled);

                        FileOutputStream outputStream;
                        try {
                            outputStream = openFileOutput(filename, Context.MODE_PRIVATE);
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
    }
}
