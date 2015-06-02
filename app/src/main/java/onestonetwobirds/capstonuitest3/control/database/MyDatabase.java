package onestonetwobirds.capstonuitest3.control.database;

import android.content.Context;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

/**
 * Created by YeomJi on 15. 4. 23..
 */



public class MyDatabase extends SQLiteAssetHelper {

    private static final String DATABASE_NAME = "Capston.db";
    private static final int DATABASE_VERSION = 1;

    public MyDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
}