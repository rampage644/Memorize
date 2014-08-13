package com.example.memorize.memorize;

import android.app.Activity;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.Random;
import android.util.Log;

public class ShowActivity extends Activity {
    private int mCount = 100;
    private int[] mNumbers = null;
    private long[] mTime = null;
    private int mIndex = -1;
    private TextView mNumberView = null;
    private long mLastTime = 0;

    private int mID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);

        mNumberView = (TextView) findViewById(R.id.textView);

        mID = getIntent().getIntExtra(RootActivity.ID_KEY, -1);
        mCount = getIntent().getIntExtra(RootActivity.COUNT_KEY, 100);
    }

    public void onClick(View v) {
        if (mIndex == -1) {
            start();

            mLastTime = System.currentTimeMillis();
        }
        else if (mIndex >= 0 && mIndex < getCount()) {
            mTime[mIndex-1] = System.currentTimeMillis() - mLastTime;
            mLastTime = System.currentTimeMillis();

            next();
        }
        else {
            mTime[mIndex-1] = System.currentTimeMillis() - mLastTime;

            stop();
        }

    }

    private void start() {
        mNumbers = new int[getCount()];
        mTime = new long[getCount()];
        Random r = new Random();
        for (int i=0; i< getCount(); ++i) {
            mNumbers[i] = r.nextInt(101);
        }

        mIndex = 0;
        mNumberView.setTextSize(128);
        next();
    }

    private void stop() {
        mIndex = -1;
        mNumberView.setText(R.string.show_start_text);
        mNumberView.setTextSize(32);

        MemorizeDBOpenHelper helper = new MemorizeDBOpenHelper(this);
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        ByteBuffer bb = ByteBuffer.allocate(4 * mNumbers.length);
        bb.asIntBuffer().put(IntBuffer.wrap(mNumbers));
        byte[] buffer = new byte[4 * mNumbers.length];
        bb.get(buffer);
        cv.put("ref", buffer);
        db.update(MemorizeDBOpenHelper.TABLE_NAME, cv, "id = ?", new String[] {Integer.toString(mID)});

        finish();
    }

    private void next() {
        mNumberView.setText(Integer.toString(mNumbers[mIndex]));
        if (mIndex % 2 == 0) {
            mNumberView.setTypeface(null, Typeface.BOLD);
        } else {
            mNumberView.setTypeface(null, Typeface.NORMAL);
        }

        mIndex++;
    }

    public int getCount() {
        return mCount;
    }

    public void setCount(int mCount) {
        this.mCount = mCount;
    }

    public int[] getNumbers() {
        return mNumbers;
    }

    public long[] getTime() {
        return mTime;
    }
}
