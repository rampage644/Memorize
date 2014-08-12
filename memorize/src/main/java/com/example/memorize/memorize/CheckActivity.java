package com.example.memorize.memorize;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridView;

import com.example.memorize.memorize.R;

public class CheckActivity extends Activity implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {
    public int[] getNumbers() {
        return mNumbers;
    }

    public int getIndex() {
        return mIndex;
    }

    private int[] mNumbers = null;
    private MemorizeAdapter adapter = null;
    private int mIndex = 0;
    private int mCount = 100;
    public static final int INVALID_NUMBER = -1;
    private EditText input = null;
    private GridView grid = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check);

        this.mNumbers = new int[mCount];
        for (int i=0;i<mNumbers.length;i++) {
            mNumbers[i] = INVALID_NUMBER;
        }

        input = (EditText) findViewById(R.id.number_input);
        grid = (GridView) findViewById(R.id.grid);
        adapter = new MemorizeAdapter(this,
                mNumbers, null, null);
        grid.setAdapter(adapter);
        grid.setOnItemClickListener(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.check, menu);
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

    public void next_btn_click(View v) {
        process_input();
        if (mIndex < mCount - 1)
            mIndex++;
        process_data();
    }

    public void prev_btn_click(View v) {
        process_input();
        if (mIndex > 0)
            mIndex--;
        process_data();
    }

    private void process_input() {
        if (mIndex >= 0 && mIndex < mCount) {
            try {
                mNumbers[mIndex] = Integer.parseInt(input.getText().toString());
            }
            catch (NumberFormatException e) {}
        }
    }

    private void process_data() {
        if (mNumbers[mIndex] != INVALID_NUMBER)
            input.setText(Integer.toString(mNumbers[mIndex]));
        else
            input.setText("");
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        if (i >= 0 && i < mCount) {
            mIndex = i;
            process_data();
        }

    }

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
        if (i >= 0 && i < mCount) {
            int value = mNumbers[i];
            mNumbers[i] = INVALID_NUMBER;
            for (int j=i+1;j<mNumbers.length;++j) {
                int tmp = mNumbers[j];
                mNumbers[j] = value;
                value = tmp;
            }
            adapter.notifyDataSetChanged();
            return true;
        }
        return false;
    }
}
