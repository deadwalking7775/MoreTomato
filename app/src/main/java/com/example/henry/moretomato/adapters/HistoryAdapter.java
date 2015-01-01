package com.example.henry.moretomato.adapters;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import com.example.henry.moretomato.R;
import com.example.henry.moretomato.data.Task;

/**
 * Created by Henry on 2014/12/23.
 */
public class HistoryAdapter extends CursorAdapter implements AdapterView.OnItemClickListener {

    public HistoryAdapter(Context context, Cursor cursor, int flag) {
        super(context, cursor, flag);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v;
        TextView itemText;
        v = layoutInflater.inflate(R.layout.item_history_list, parent, false);
        itemText = (TextView)v.findViewById(R.id.text_item_history_content);
        itemText.setText(cursor.getString(cursor.getColumnIndex(Task.CONTENT)));
        return v;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v;
        if (!mCursor.moveToPosition(position)) {
            throw new IllegalStateException("couldn't move cursor to position "
                    + position);
        }
        if (convertView == null){
            v = newView(mContext, mCursor, parent);
        }
        else {
            v = convertView;
        }
        return v;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }
}
