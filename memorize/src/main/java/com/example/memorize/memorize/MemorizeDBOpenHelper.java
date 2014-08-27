package com.example.memorize.memorize;

/**
 * Created by ramp on 8/12/14.
 */
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;

import java.nio.ByteBuffer;

public class MemorizeDBOpenHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "memo";
    private static final String TABLE_CREATE =
            "CREATE TABLE IF NOT EXISTS " + TABLE_NAME +
                    " (_id integer primary key autoincrement, ref blob, act blob, time blob, count integer, datetime TIMESTAMP NOT NULL DEFAULT current_timestamp);";
   public MemorizeDBOpenHelper(Context context) {
        super(context, "main", null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) { }

    public static void convertByteArrayToIntArray(byte[] in, int[] out) {
        ByteBuffer byteb = ByteBuffer.wrap(in);
        for (int i=0;i<out.length;++i) {
            out[i] = byteb.getInt();
        }
    }

    public static void convertIntArrayToByteArray(int[] in, byte[] out) {
        ByteBuffer byteb = ByteBuffer.allocate(in.length * 4);
        for (int i=0;i<in.length;++i) {
            byteb.putInt(in[i]);
        }

        for (int i=0;i<out.length;++i) {
            out[i]=byteb.array()[i];
        }
    }

}
