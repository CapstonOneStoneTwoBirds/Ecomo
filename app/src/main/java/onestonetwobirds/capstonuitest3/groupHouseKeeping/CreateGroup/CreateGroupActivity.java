package onestonetwobirds.capstonuitest3.groupHouseKeeping.CreateGroup;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.rey.material.app.Dialog;
import com.rey.material.app.DialogFragment;
import com.rey.material.app.SimpleDialog;
import com.rey.material.widget.Button;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import onestonetwobirds.capstonuitest3.R;
import onestonetwobirds.capstonuitest3.groupHouseKeeping.Main.GroupMainActivity;
import onestonetwobirds.capstonuitest3.httpClient.HttpClient;

/**
 * Created by YeomJi on 15. 6. 9..
 */
public class CreateGroupActivity extends FragmentActivity implements View.OnClickListener {
    private static int RESULT_LOAD_IMG = 1;
    String imgDecodableString;
    EditText Title;
    int num;
    Button FromGal, CreateGroup_OK, CreateGroup_NO;
    ImageView setGroupImg1, setGroupImg2, setGroupImg3, setGroupImg4, setGroupImg5;
    File file = null;
    String group_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_group_main);

        Title = (EditText)findViewById(R.id.create_group_title);
        FromGal = (Button)findViewById(R.id.create_group_imgfromgal_btn);
        setGroupImg1 = (ImageView)findViewById(R.id.set_group_image1);
        setGroupImg2 = (ImageView)findViewById(R.id.set_group_image2);
        setGroupImg3 = (ImageView)findViewById(R.id.set_group_image3);
        setGroupImg4 = (ImageView)findViewById(R.id.set_group_image4);
        setGroupImg5 = (ImageView)findViewById(R.id.set_group_image5);
        CreateGroup_OK = (Button)findViewById(R.id.create_group_OK);
        CreateGroup_NO = (Button)findViewById(R.id.create_group_NO);

        CreateGroup_OK.setOnClickListener(this);
        CreateGroup_NO.setOnClickListener(this);
        FromGal.setOnClickListener(this);
        setGroupImg1.setOnClickListener(this);
        setGroupImg2.setOnClickListener(this);
        setGroupImg3.setOnClickListener(this);
        setGroupImg4.setOnClickListener(this);
        setGroupImg5.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        ImageView imgView;
        switch (v.getId()) {
            case R.id.create_group_OK:
                String title = Title.getText().toString();

                SharedPreferences mPreference;
                mPreference = getSharedPreferences("myInfo", MODE_PRIVATE);

                //Create Group Except Image
                try {
                    RequestParams param = new RequestParams();
                    param.put("groupname", title);
                    param.put("owner", mPreference.getString("email", ""));
                    param.put("img_num", num);
                    HttpClient.get("createGroup/", param, new AsyncHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                            if( new String(responseBody).equals("1")) {
                                // Already Exist
                                Toast toastView = Toast.makeText(getApplicationContext(),
                                        "Already you have the same group.", Toast.LENGTH_LONG);
                                toastView.setGravity(Gravity.CENTER, 40, 25);
                                toastView.show();
                                System.out.println("Success Here   1");
                            }
                            else{
                                // Create Success
                                System.out.println("Success Here   2");
                                try {
                                    JSONObject obj = new JSONObject(new String(responseBody));
                                    Log.e("CreateGroupActivity", "obj : " + obj);
                                    Log.e("CreateGroupActivity", "Gtoup_id : " + group_id);
                                    group_id = obj.getString("_id");
                                    // Set Group Image////////////////////////////////////////////////
                                    RequestParams param = new RequestParams();
                                    param.put("groupname", Title.getText().toString());
                                    param.put("group_id", group_id);
                                    if( num == 0 ) {
                                        try {
                                            param.put("image", file, "image/jpg");
                                        } catch (FileNotFoundException e) { }

                                        HttpClient.post("uploadImg_Group/", param, new AsyncHttpResponseHandler() {
                                            @Override
                                            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                                                System.out.println("img Save Success");
                                                System.out.println("output : " + new String(responseBody));
                                                switch (new String(responseBody)) {
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
                                    }
                                }catch(JSONException e){ e.printStackTrace();}

                                Intent intent = new Intent(getApplicationContext(), GroupMainActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                            System.out.println("Failure Here kl");
                        }
                    });

                }catch(Exception e){}
                break;
            case R.id.create_group_NO:
                finish();
                break;
            case R.id.create_group_imgfromgal_btn:
                loadImagefromGallery(v);
                break;
            case R.id.set_group_image1:
                imgView = (ImageView) findViewById(R.id.set_group_iv);
                imgView.setImageResource(R.drawable.group_1);
                num=1;
                break;
            case R.id.set_group_image2:
                imgView = (ImageView) findViewById(R.id.set_group_iv);
                imgView.setImageResource(R.drawable.group_2);
                num=2;
                break;
            case R.id.set_group_image3:
                imgView = (ImageView) findViewById(R.id.set_group_iv);
                imgView.setImageResource(R.drawable.group_3);
                num=3;
                break;
            case R.id.set_group_image4:
                imgView = (ImageView) findViewById(R.id.set_group_iv);
                imgView.setImageResource(R.drawable.group_4);
                num=4;
                break;
            case R.id.set_group_image5:
                imgView = (ImageView) findViewById(R.id.set_group_iv);
                imgView.setImageResource(R.drawable.group_5);
                num=5;
                break;
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
                ImageView imgView = (ImageView) findViewById(R.id.set_group_iv);
                // Set the Image in ImageView after decoding the String

                Bitmap d = BitmapFactory.decodeFile(imgDecodableString);

                int nh = (int) ( d.getHeight() * (512.0 / d.getWidth()) );
                Bitmap scaled = Bitmap.createScaledBitmap(d, 512, nh, true);
                imgView.setImageBitmap(scaled);

                //imgView.setImageBitmap(BitmapFactory.decodeFile(imgDecodableString));

                file = new File(imgDecodableString);
                num=0;
                /*
                SharedPreferences mPreference = getSharedPreferences("myInfo", MODE_PRIVATE);
                String email = mPreference.getString("email", "");

                FileOutputStream outputStream;
                try {
                    String filename = email+"_img.jpg"; // 그룹으로 바꾸자.
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
                        switch (new String(responseBody)) {
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
                */
            } else {
                Toast.makeText(this, "You haven't picked Image",
                        Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG)
                    .show();
        }
    }
}
