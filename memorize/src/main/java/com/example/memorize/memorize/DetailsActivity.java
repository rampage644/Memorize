package com.example.memorize.memorize;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.nio.ByteBuffer;

public class DetailsActivity extends Activity {
    private MemorizeAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        int ID = getIntent().getIntExtra(RootActivity.ID_KEY, -1);
        int count = getIntent().getIntExtra(RootActivity.COUNT_KEY, 100);


        MemorizeDBOpenHelper helper = new MemorizeDBOpenHelper(this);
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor c = db.query(MemorizeDBOpenHelper.TABLE_NAME,
            new String[] {"ref,act,time"}, "id = ?", new String[] {Integer.toString(ID)}, null, null, null);

        if (c.moveToFirst()) {
            int[] ref = ByteBuffer.wrap(c.getBlob(0)).asIntBuffer().array();
            int[] act = ByteBuffer.wrap(c.getBlob(1)).asIntBuffer().array();
            int[] time = ByteBuffer.wrap(c.getBlob(2)).asIntBuffer().array();
            adapter = new MemorizeAdapter(this, ref, act, time);
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.details, menu);
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
}
