package onestonetwobirds.capstonuitest3.user;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import onestonetwobirds.capstonuitest3.R;
import onestonetwobirds.capstonuitest3.gcm.PreferenceUtil;
import onestonetwobirds.capstonuitest3.httpClient.HttpClient;
import onestonetwobirds.capstonuitest3.privateHouseKeeping.Main.PrivateMainActivity;


public class StartActivity extends Activity implements View.OnClickListener{

    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    private static final String SENDER_ID = "394987658992";

    private GoogleCloudMessaging _gcm;
    private String _regId;

    Button button1, button2, button3, button4;
    EditText email;
    EditText pw;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences mPreference;
        mPreference = getSharedPreferences("myInfo", MODE_PRIVATE);

        if( mPreference.getString("email", "") == "" ) {
            setContentView(R.layout.start_page_main);

            button1 = (Button) findViewById(R.id.button_bt_start);
            button2 = (Button) findViewById(R.id.button_bt_main_sign_in);
            button3 = (Button) findViewById(R.id.button_bt_main_find_pwd);
            button4 = (Button) findViewById(R.id.temp_btn);
            button1.setOnClickListener(this);
            button2.setOnClickListener(this);
            button3.setOnClickListener(this);
            button4.setOnClickListener(this);

            email = (EditText)findViewById(R.id.login_email);
            pw = (EditText)findViewById(R.id.login_pwd);
        }
        else {
            String email = mPreference.getString("email", "");
            String pw = mPreference.getString("pw", "");

            Login(email, pw);
        }
    }

    public void Login(final String email, final String pw){

        /////////////////////// GCM Start

        // google play service가 사용가능한가
        if (checkPlayServices())
        {
            _gcm = GoogleCloudMessaging.getInstance(getApplicationContext());
            _regId = getRegistrationId();
            Log.e(":::::::::::_regId ", _regId);

            RequestParams param = new RequestParams();
            param.put("add", _regId);
            param.put("CODE", "addGcm");
            param.put("email", email);
            HttpClient.post("addGcmAddress/", param, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    Log.i("StartActivity ::::: ", "onSuccess");
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    Log.i("StartActivity ::::: ", error.toString());
                }
            });

            if (TextUtils.isEmpty(_regId))
                registerInBackground();
        }
        else
        {
            Log.i("StartActivity.java | onCreate", "|No valid Google Play Services APK found.|");
        }

        /////////////////////// GCM End

        JSONObject login_info = new JSONObject();
        try {
            login_info.put("email", email);
            login_info.put("pw", pw);
        } catch (JSONException e) {}

        // Post Request
        try {
            StringEntity entity = new StringEntity(login_info.toString());
            entity.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));

            HttpClient.post(this, "login/", entity, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int i, Header[] headers, byte[] bytes) {
                    if (new String(bytes).equals("null")) {
                        SharedPreferences mPreference = getSharedPreferences("myInfo", MODE_PRIVATE);
                        SharedPreferences.Editor editor = mPreference.edit();
                        editor.clear();
                        editor.commit();

                        startActivity(new Intent(getApplicationContext(), StartActivity.class));
                        overridePendingTransition(R.anim.fade, R.anim.hold);
                    } else {
                        System.out.println("Login Success");
                        SharedPreferences mPreference = getSharedPreferences("myInfo", MODE_PRIVATE);
                        SharedPreferences.Editor editor = mPreference.edit();
                        editor.putString("email", email);
                        editor.putString("pw", pw);
                        editor.commit();
                        String code="";
                        try {
                            JSONObject obj = new JSONObject(new String(bytes));
                            code = obj.get("key").toString();
                        }catch(JSONException e){}

                        if(code.length()==4){
                            Intent intent = new Intent(StartActivity.this, KeyCheckActivity.class);
                            intent.putExtra("email", email);
                            startActivity(intent);
                            overridePendingTransition(R.anim.fade, R.anim.hold);
                        }
                        else{
                            Intent intent = new Intent(StartActivity.this, KeySetActivity.class);
                            intent.putExtra("email", email);
                            startActivity(intent);
                            overridePendingTransition(R.anim.fade, R.anim.hold);
                        }
                    }
                }

                @Override
                public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                    System.out.println("error : " + throwable.getMessage());
                    System.out.println("Fail here");
                }
            });
        } catch (UnsupportedEncodingException e) { }
    }
    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.fade, R.anim.hold);
    }
    @Override
    protected void onNewIntent(Intent intent)
    {
        super.onNewIntent(intent);

        // display received msg
        String msg = intent.getStringExtra("msg");
        Log.i("MainActivity.java | onNewIntent", "|" + msg + "|");
    }

    // google play service가 사용가능한가
    private boolean checkPlayServices()
    {
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS)
        {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode))
            {
                GooglePlayServicesUtil.getErrorDialog(resultCode, this, PLAY_SERVICES_RESOLUTION_REQUEST).show();
            }
            else
            {
                Log.i("MainActivity.java | checkPlayService", "|This device is not supported.|");
                finish();
            }
            return false;
        }
        return true;
    }

    // registration  id를 가져온다.
    private String getRegistrationId()
    {
        String registrationId = PreferenceUtil.instance(getApplicationContext()).regId();
        if (TextUtils.isEmpty(registrationId))
        {
            Log.i("MainActivity.java | getRegistrationId", "|Registration not found.|");
            return "";
        }
        int registeredVersion = PreferenceUtil.instance(getApplicationContext()).appVersion();
        int currentVersion = getAppVersion();
        if (registeredVersion != currentVersion)
        {
            Log.i("MainActivity.java | getRegistrationId", "|App version changed.|");
            return "";
        }
        return registrationId;
    }

    // app version을 가져온다. 뭐에 쓰는건지는 모르겠다.
    private int getAppVersion()
    {
        try
        {
            PackageInfo packageInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            return packageInfo.versionCode;
        }
        catch (PackageManager.NameNotFoundException e)
        {
            // should never happen
            throw new RuntimeException("Could not get package name: " + e);
        }
    }

    // gcm 서버에 접속해서 registration id를 발급받는다.
    private void registerInBackground()
    {
        new AsyncTask<Void, Void, String>()
        {
            @Override
            protected String doInBackground(Void... params)
            {
                String msg = "";
                try
                {
                    if (_gcm == null)
                    {
                        _gcm = GoogleCloudMessaging.getInstance(getApplicationContext());
                    }

                    _regId = _gcm.register(SENDER_ID);
                    Log.e("RedID : ", _regId);
                    msg = "Device registered, registration ID=" + _regId;

                    // For this demo: we don't need to send it because the device
                    // will send upstream messages to a server that echo back the
                    // message using the 'from' address in the message.

                    // Persist the regID - no need to register again.
                    storeRegistrationId(_regId);
                }
                catch (IOException ex)
                {
                    msg = "Error :" + ex.getMessage();
                    // If there is an error, don't just keep trying to register.
                    // Require the user to click a button again, or perform
                    // exponential back-off.
                }

                return msg;
            }

            @Override
            protected void onPostExecute(String msg)
            {
                Log.i("MainActivity.java | onPostExecute", "|" + msg + "|");
            }
        }.execute(null, null, null);
    }

    // registraion id를 preference에 저장한다.
    private void storeRegistrationId(String regId)
    {
        int appVersion = getAppVersion();
        Log.i("MainActivity.java | storeRegistrationId", "|" + "Saving regId on app version " + appVersion + "|");
        PreferenceUtil.instance(getApplicationContext()).putRedId(regId);
        PreferenceUtil.instance(getApplicationContext()).putAppVersion(appVersion);
    }

    @Override
    public void onClick(View v) {
        Intent intent=null;

        switch( v.getId() ){
            case R.id.button_bt_start:
                String email_str = email.getText().toString();
                String pw_str = pw.getText().toString();

                Login(email_str, pw_str);
                break;

            case R.id.button_bt_main_sign_in:
                intent = new Intent(StartActivity.this, SignInActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade, R.anim.hold);
                break;

            case R.id.button_bt_main_find_pwd:
                intent = new Intent(StartActivity.this, PwdFindActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade, R.anim.hold);
                break;

            case R.id.temp_btn:
                intent = new Intent(StartActivity.this, PrivateMainActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade, R.anim.hold);
                break;
        }
    }
}
