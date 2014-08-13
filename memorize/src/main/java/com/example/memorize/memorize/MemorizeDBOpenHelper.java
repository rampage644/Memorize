package com.example.memorize.memorize;

/**
 * Created by ramp on 8/12/14.
 */
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;


public class MemorizeDBOpenHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "memo";
    private static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_NAME + " IF NOT EXISTS" +
                    " (_id integer primary key autoincrement, ref blob, act blob, time blob, count integer);";

    MemorizeDBOpenHelper(Context context) {
        super(context, "main", null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {

    }
}
