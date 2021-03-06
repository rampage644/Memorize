package com.example.memorize.memorize;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;

public class RootActivity extends Activity {
    private int mID;
    private int mCount;

    public static final int ShowCode = 1;
    public static final int CheckCode = 2;

    public static final String ID_KEY = "id";
    public static final String COUNT_KEY = "count";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_root);
    }

    public void new_session_btn_clicked(View v) {
        MemorizeDBOpenHelper helper = new MemorizeDBOpenHelper(this);
        SQLiteDatabase db = helper.getWritableDatabase();

        // TODO move to settings
        mCount = 100;
        ContentValues cv = new ContentValues();
        cv.putNull("ref");
        cv.putNull("time");
        cv.putNull("act");
        cv.put("count", mCount);
        db.insert("memo", null, cv);
        Cursor c = db.query("memo", new String[] {"_id"}, null, null, null, null, "_id desc", "1");

        if (c.moveToFirst()) {
            mID = c.getInt(0);
            // start show activity with id and count
            start_show_activity();
         }
    }

    private void start_show_activity() {
        Intent intent = new Intent(this, ShowActivity.class);
        intent.putExtra(ID_KEY, mID);
        intent.putExtra(COUNT_KEY, mCount);
        startActivityForResult(intent, ShowCode);
    }

    private void start_check_activity() {
        Intent intent = new Intent(this, CheckActivity.class);
        intent.putExtra(ID_KEY, mID);
        intent.putExtra(COUNT_KEY, mCount);
        startActivityForResult(intent, CheckCode);
    }

    private void start_details_activty() {
        // start DetailsActivity
        Intent intent = new Intent(this, DetailsActivity.class);
        intent.putExtra(ID_KEY, mID);
        startActivity(intent);
    }

    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent data) {
        switch (requestCode) {
            case ShowCode:
                if (resultCode == ShowActivity.RESULT_OK)
                    start_check_activity();
                break;
            case CheckCode:
                if (resultCode == CheckActivity.RESULT_OK)
                    start_details_activty();
                break;
        }
    }


    public void history_btn_clicked(View v) {

    }

}
