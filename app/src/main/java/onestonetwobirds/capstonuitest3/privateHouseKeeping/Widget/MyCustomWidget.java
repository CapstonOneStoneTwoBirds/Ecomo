package onestonetwobirds.capstonuitest3.privateHouseKeeping.Widget;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.RemoteViews;

import onestonetwobirds.capstonuitest3.R;
import onestonetwobirds.capstonuitest3.control.database.MyDatabase;


/**
 * Created by YeomJi on 15. 4. 30..
 */
public class MyCustomWidget extends AppWidgetProvider
{
    private static final String TAG = "MyCustomWidget";
    private static final int WIDGET_UPDATE_INTERVAL = 5000;
    private static PendingIntent mSender;
    private static AlarmManager mManager;

    public static String titleWidget;
    public static int goalWidget, accWidget;


    @Override
    public void onReceive(Context context, Intent intent)
    {
        super.onReceive(context, intent);

        // 위치 수정
        MyDatabase myDB = new MyDatabase(context);
        final SQLiteDatabase db = myDB.getWritableDatabase();

        String sql = "SELECT * FROM checkamount WHERE isWidget LIKE ? ";
        Cursor cursor = db.rawQuery(sql, new String[]{"1"});

        int titleCol = cursor.getColumnIndex("title");
        int goalCol = cursor.getColumnIndex("goal");
        int accCol = cursor.getColumnIndex("acc");

        while (cursor.moveToNext()) {
            titleWidget = cursor.getString(titleCol);
            goalWidget = cursor.getInt(goalCol);
            accWidget = cursor.getInt(accCol);
        }


            String action = intent.getAction();
        if(action.equals("android.appwidget.action.APPWIDGET_UPDATE"))
        {
            Log.w(TAG, "android.appwidget.action.APPWIDGET_UPDATE");
            removePreviousAlarm();

            long firstTime = System.currentTimeMillis() + WIDGET_UPDATE_INTERVAL;
            mSender = PendingIntent.getBroadcast(context, 0, intent, 0);
            mManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            mManager.set(AlarmManager.RTC, firstTime, mSender);
        }
        else if(action.equals("android.appwidget.action.APPWIDGET_DISABLED"))
        {
            Log.w(TAG, "android.appwidget.action.APPWIDGET_DISABLED");
            removePreviousAlarm();
        }
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds)
    {
        appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(context, getClass()));
        super.onUpdate(context, appWidgetManager, appWidgetIds);

        final int N = appWidgetIds.length;
        for(int i = 0 ; i < N ; i++)
        {
            int appWidgetId = appWidgetIds[i];
            updateAppWidget(context, appWidgetManager, appWidgetId);

            //Toast.makeText(context, "onUpdate(): [" + String.valueOf(i) + "] " + String.valueOf(appWidgetId), Toast.LENGTH_SHORT).show();
        }
    }


    public static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId)
    {
        getContent();
        String content = titleWidget + " / 최대 : " + goalWidget + " / 현재 : " + accWidget;
        RemoteViews updateViews = new RemoteViews(context.getPackageName(), R.layout.my_custom_widget);
        //updateViews.setTextViewText(R.id.widgettext, content);
        updateViews.setTextViewText(R.id.widgetTitle, titleWidget);
        updateViews.setTextViewText(R.id.widgetLimit, String.valueOf(goalWidget)+" 원");
        updateViews.setTextViewText(R.id.widgetCurrent, String.valueOf(accWidget)+" 원");
        appWidgetManager.updateAppWidget(appWidgetId, updateViews);
    }


    public void removePreviousAlarm()
    {
        if(mManager != null && mSender != null)
        {
            mSender.cancel();
            mManager.cancel(mSender);
        }
    }

    public static void getContent() {
        titleWidget = WidgetFragment.t;
        goalWidget = WidgetFragment.g;
        accWidget = WidgetFragment.c;
    }


}

