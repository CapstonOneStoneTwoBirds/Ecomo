package onestonetwobirds.capstonuitest3.user.MyInformation;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import onestonetwobirds.capstonuitest3.R;
import onestonetwobirds.capstonuitest3.httpClient.HttpClient;

/**
 * Created by YeomJi on 15. 6. 7..
 */
public class MyInfoActivity extends Activity implements View.OnClickListener {
    private static int RESULT_LOAD_IMG = 1;
    String imgDecodableString;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.check_info);

        final TextView email_tv = (TextView)findViewById(R.id.check_info_email);
        final TextView name_tv = (TextView)findViewById(R.id.check_info_name);
        final TextView phone_tv = (TextView)findViewById(R.id.check_info_phone);
        ImageView iv = (ImageView)findViewById(R.id.check_info_iv);
        Button btn = (Button)findViewById(R.id.check_info_fix_btn);

        iv.setOnClickListener(this);
        RequestParams param = new RequestParams();

        SharedPreferences mPreference = getSharedPreferences("myInfo", MODE_PRIVATE);
        final String email = mPreference.getString("email", "");
        param.put("email", email);
        HttpClient.post("getMember/", param, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    JSONObject obj = new JSONObject(new String(responseBody));
                    email_tv.setText(obj.get("email").toString());
                    name_tv.setText(obj.get("name").toString());
                    phone_tv.setText(obj.get("phone").toString());
                } catch (Exception e) {
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                System.out.println(error);
            }
        });

        getMemberProfileImg(email, iv);
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

    public void loadImagefromGallery(View view) {
        // Create intent to Open Image applications like Gallery, Google Photos
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        // Start the Intent
        startActivityForResult(galleryIntent, RESULT_LOAD_IMG);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            System.out.println("Test here");
            // When an Image is picked
            if (requestCode == RESULT_LOAD_IMG && resultCode == RESULT_OK
                    && null != data) {
                // Get the Image from data

                Uri selectedImage = data.getData();
                String[] filePathColumn = { MediaStore.Images.Media.DATA };

                // Get the cursor
                Cursor cursor = getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                // Move to first row
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                imgDecodableString = cursor.getString(columnIndex);
                cursor.close();
                ImageView imgView = (ImageView) findViewById(R.id.check_info_iv);
                // Set the Image in ImageView after decoding the String

                Bitmap d = BitmapFactory.decodeFile(imgDecodableString);

                int nh = (int) ( d.getHeight() * (512.0 / d.getWidth()) );
                Bitmap scaled = Bitmap.createScaledBitmap(d, 512, nh, true);
                imgView.setImageBitmap(scaled);

                //imgView.setImageBitmap(BitmapFactory.decodeFile(imgDecodableString));
                System.out.println(":::::" + imgDecodableString);

                File file = new File(imgDecodableString);
                System.out.println("file : " + file);
                SharedPreferences mPreference = getSharedPreferences("myInfo", MODE_PRIVATE);
                String email = mPreference.getString("email", "");

                FileOutputStream outputStream;
                try {
                    String filename = email+"_profile.jpg";
                    outputStream = openFileOutput(filename, Context.MODE_PRIVATE);
                    scaled.compress(Bitmap.CompressFormat.JPEG, 90, outputStream);
                    outputStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                RequestParams param = new RequestParams();
                param.put("email", email);
                param.put("image", file, "image/jpg");
                System.out.println("Param : " + param);
                HttpClient.post("uploadImg_Profile/", param, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                        System.out.println("img Save Success");
                        System.out.println("output : " + new String(responseBody));
                        switch ( new String(responseBody) ) {
                            case "1":
                                System.out.println("img save error");
                                break;

                            case "Saved":
                                System.out.println("img save Success");
                                break;
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                        System.out.println("error message : " + error);
                    }
                });

            } else {
                Toast.makeText(this, "You haven't picked Image",
                        Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG)
                    .show();
        }
    }

    @Override
    public void onClick(View v) {
        switch( v.getId() ){
            case R.id.check_info_iv:
                loadImagefromGallery(v);
                break;

            case R.id.check_info_fix_btn:

                break;
        }
    }
}
