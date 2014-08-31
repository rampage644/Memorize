package com.example.memorize.memorize;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.GridView;

public class DetailsActivity extends Activity {
    private MemorizeAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        int ID = getIntent().getIntExtra(RootActivity.ID_KEY, -1);

        MemorizeDBOpenHelper helper = new MemorizeDBOpenHelper(this);
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor c = db.query(MemorizeDBOpenHelper.TABLE_NAME,
            new String[] {"ref,act,time,count"}, "_id = ?", new String[] {Integer.toString(ID)}, null, null, null);

        if (c.moveToFirst()) {
            int[] ref = new int[c.getInt(3)];
            MemorizeDBOpenHelper.convertByteArrayToIntArray(c.getBlob(0), ref);
            int[] act = new int[c.getInt(3)];
            MemorizeDBOpenHelper.convertByteArrayToIntArray(c.getBlob(1), act);
            int[] time = new int[c.getInt(3)];
            MemorizeDBOpenHelper.convertByteArrayToIntArray(c.getBlob(2), time);
            adapter = new MemorizeAdapter(this, ref, act, time);
            GridView gv = (GridView) findViewById(R.id.details_grid);
            gv.setAdapter(adapter);
        }

    }
}
