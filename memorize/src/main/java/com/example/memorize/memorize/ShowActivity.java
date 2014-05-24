package com.example.memorize.memorize;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import java.util.Random;
import android.util.Log;

public class ShowActivity extends Activity {
    private int mCount = 100;
    private int[] mNumbers = null;
    private long[] mTime = null;
    private int mIndex = -1;
    private TextView mNumberView = null;
    private long mLastTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);

        mNumberView = (TextView) findViewById(R.id.textView);

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
