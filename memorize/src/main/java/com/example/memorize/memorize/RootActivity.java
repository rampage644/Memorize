package com.example.memorize.memorize;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.MenuItem;
import com.example.memorize.memorize.R;

public class RootActivity extends Activity {
    private int mID;
    private int mCount = 100;

    public static final int ShowCode = 1;
    public static final int CheckCode = 2;

    public static final String ID_KEY = "id";
    public static final String COUNT_KEY = "count";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_root);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.root, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void new_session_btn_clicked(View v) {
        MemorizeDBOpenHelper helper = new MemorizeDBOpenHelper(this);
        SQLiteDatabase db = helper.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.putNull("ref");
        cv.putNull("time");
        cv.putNull("act");
        cv.put("count", 100);
        db.insert("memo", null, cv);
        Cursor c = db.query("memo", new String[] {"_id"}, null, null, null, null, "_id desc", "1");

        if (c.moveToFirst()) {
            mID = c.getInt(0);
            mCount = 100;
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
                start_check_activity();
                break;
            case CheckCode:
                start_details_activty();
                break;
        }
    }


    public void history_btn_clicked(View v) {

    }

}
