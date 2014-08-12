package com.example.memorize.memorize;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.content.Context;

/**
 * Created by ramp on 8/12/14.
 */
public class MemorizeAdapter extends BaseAdapter {
    private Context mCtx;
    private int[] mReference;
    private int[] mActual;
    private int[] mTimes;
    private LayoutInflater inflater;

    public MemorizeAdapter(Context ctx, int[] act, int[] ref, int[] time) {
        mCtx = ctx;
        inflater = (LayoutInflater) mCtx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        mReference = ref;
        mActual = act;
        mTimes = time;
    }


    @Override
    public int getCount() {
        return mActual.length;
    }

    @Override
    public Object getItem(int i) {
        return mActual[i];
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.grid_item, parent, false);
        }

        TextView view = (TextView) convertView.findViewById(R.id.grid_item_cell);
        StringBuilder bld = new StringBuilder();
        if (mActual != null)
            bld.append(Integer.toString(mActual[i]));
        else
            bld.append(" - ");
        bld.append('\n');
        if (mReference != null) {
            bld.append(Integer.toString(mReference[i]));

            if (mReference[i] == mActual[i])
                view.setBackground(mCtx.getResources().getDrawable(R.drawable.grid_cell_green));
            else
                view.setBackground(mCtx.getResources().getDrawable(R.drawable.grid_cell_red));
        }
        else
            bld.append(" - ");
        bld.append('\n');

        if (mTimes != null)
            bld.append(Double.toString(mTimes[i]*0.001));
        else
            bld.append(" - ");

        view.setText(bld.toString());
        return convertView;
    }
}
