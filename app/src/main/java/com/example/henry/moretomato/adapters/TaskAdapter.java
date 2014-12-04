package com.example.henry.moretomato.adapters;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;
import android.widget.AdapterView;
import android.widget.Button;
import android.support.v4.widget.CursorAdapter;
import android.view.View;

import com.example.henry.moretomato.R;

/**
 * Created by Henry on 2014/10/27.
 */

public class TaskAdapter extends CursorAdapter implements AdapterView.OnItemClickListener,
        View.OnClickListener{

    public TaskAdapter(Context context, Cursor cursor, int flag){
        super(context, cursor, flag);
    }

    @Override
    public void onItemClick(android.widget.AdapterView<?> adapterView, android.view.View view, int i, long l){
        Toast.makeText(mContext,"You've got an event from " + i,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(android.view.View view){
        if (view.getId() == R.id.button_new_task){
            Toast.makeText(mContext,"You've got an new task ",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor){

    }

    @Override
    public View newView (Context context, Cursor cursor, ViewGroup parent){
        LayoutInflater layoutInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (cursor.getInt(cursor.getColumnIndex("_id")) == 0){
            View v = layoutInflater.inflate(R.layout.item_create_new_todo, parent, false);
            v.findViewById(R.id.button_new_task).setOnClickListener(this);
            return v;
        }
        else {
            return layoutInflater.inflate(R.layout.item_todo_list, parent, false);
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (!mCursor.moveToPosition(position)){
            throw new IllegalStateException("couldn't move cursor to position "
                    + position);
        }
        return newView(mContext, mCursor, parent);
    }
}