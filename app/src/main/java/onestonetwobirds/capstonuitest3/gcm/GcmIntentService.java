package onestonetwobirds.capstonuitest3.gcm;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;


import com.google.android.gms.gcm.GoogleCloudMessaging;

import onestonetwobirds.capstonuitest3.R;

public class GcmIntentService extends IntentService
{
    public static final int NOTIFICATION_ID = 1;

    public GcmIntentService()
    {
        super("GcmIntentService");
    }

    @Override
    protected void onHandleIntent(final Intent intent)
    {

        Bundle extras = intent.getExtras();
        GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(getApplicationContext());
        // The getMessageType() intent parameter must be the intent you received
        // in your BroadcastReceiver.
        String messageType = gcm.getMessageType(intent);

        if (!extras.isEmpty()) { // has effect of unparcelling Bundle
         /*
          * Filter messages based on message type. Since it is likely that GCM
          * will be extended in the future with new message types, just ignore
          * any message types you're not interested in, or that you don't
          * recognize.
          */
            if (GoogleCloudMessaging.MESSAGE_TYPE_SEND_ERROR.equals(messageType)) {
                //sendNotification("Send error: " + extras.toString());
            } else if (GoogleCloudMessaging.MESSAGE_TYPE_DELETED.equals(messageType)) {
                //sendNotification("Deleted messages on server: " + extras.toString());
                // If it's a regular GCM message, do some work.
            } else if (GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE.equals(messageType)) {
                // Post notification of received message.
//            sendNotification("Received: " + extras.toString());
                sendNotification(intent);
                Log.i("GcmIntentService.java | onHandleIntent", "Received: " + extras.toString());
            }
        }
        // Release the wake lock provided by the WakefulBroadcastReceiver.
        GcmBroadcastReceiver.completeWakefulIntent(intent);
    }



    // Put the message into a notification and post it.
    // This is just one simple example of what you might choose to do with
    // a GCM message.
    private void sendNotification(Intent intentt) {
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // 팝업 누를시 갈 페이지. 해당 글로 가도록 해야돼. 공지든 지출내역이든
        String _id = intentt.getStringExtra("_id");
        String code = intentt.getStringExtra("code");
        String email="";
        try {
            email = intentt.getStringExtra("email");
        }catch(Exception e){}

        Log.e("GcmIntentService 1 Code ", code);
        Log.e("GcmIntentService | _id ", _id);

        String id = "";
        Class cls = null;

        if (code.equals("Article")){
            id = "article_id";
            //cls = GroupArticleCActivity.class; // 에러나서 임시 주석 아래 2개도.
        }
        else if( code.equals("Announce")){
            id = "announce_id";
            //cls = GroupAnnounceCActivity.class;
        }
        else if( code.equals("Invite")){
            id = "group_id";
            //cls = ConsentInviteActivity.class;
        }

        final Intent intent = new Intent(getApplicationContext(), cls);;
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(id, _id);
        if( code.equals("Invite")) {
            intent.putExtra("email", email);
        }
        /*
        HttpClient.post(path, param, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    JSONObject obj = new JSONObject(new String(responseBody));
                    intent.putExtra("jsonobject", obj.toString());
                } catch (JSONException e) {
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.i("GcmIntentService | ", "onFailure");
            }
        });
        */

        PendingIntent contentIntent;
        contentIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);


        Bitmap icon = BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.ic_launcher);
        Bitmap icon2 = Bitmap.createScaledBitmap(icon, 150, 120, false);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_launcher)
                .setLargeIcon(icon2)
                .setContentTitle(intentt.getStringExtra("title"))
                .setStyle(new NotificationCompat.BigTextStyle().bigText(intentt.getStringExtra("body")))
                .setContentText(intentt.getStringExtra("body"))
                .setAutoCancel(true)
                .setVibrate(new long[]{0, 1000});

        mBuilder.setContentIntent(contentIntent);
        mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());

    }
}