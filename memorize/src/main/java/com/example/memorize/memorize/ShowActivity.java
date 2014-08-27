package com.example.memorize.memorize;

import android.app.Activity;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.Random;

public class ShowActivity extends Activity {
    private int mCount = 100;
    private int[] mNumbers = null;
    private int[] mTime = null;
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
            mTime[mIndex-1] = (int)(System.currentTimeMillis() - mLastTime);
            mLastTime = System.currentTimeMillis();

            next();
        }
        else {
            mTime[mIndex-1] = (int)(System.currentTimeMillis() - mLastTime);

            stop();
        }

    }

    private void start() {
        mNumbers = new int[getCount()];
        mTime = new int[getCount()];
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
        byte time_byte[] = new byte[mCount * 4];
        byte ref_byte[] = new byte[mCount * 4];
        MemorizeDBOpenHelper.convertIntArrayToByteArray(mNumbers, ref_byte);
        MemorizeDBOpenHelper.convertIntArrayToByteArray(mTime, time_byte);
        cv.put("ref", ref_byte);
        cv.put("time", time_byte);
        db.update(MemorizeDBOpenHelper.TABLE_NAME, cv, "_id = ?", new String[] {Integer.toString(mID)});

        setResult(this.RESULT_OK);
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

    public int[] getTime() {
        return mTime;
    }
}
