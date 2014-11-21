package com.example.henry.moretomato.adapters;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.view.View;
import android.widget.CursorAdapter;

import com.example.henry.moretomato.R;

/**
 * Created by Henry on 2014/10/27.
 */

public class TaskAdapter extends CursorAdapter implements AdapterView.OnItemClickListener{

    public TaskAdapter(Context context, Cursor cursor, int flag){
        super(context, cursor, flag);
    }

    @Override
    public void onItemClick(android.widget.AdapterView<?> adapterView, android.view.View view, int i, long l){

    }

    @Override
    public void bindView(View view, Context context, Cursor cursor){

    }

    @Override
    public View newView (Context context, Cursor cursor, ViewGroup parent){
        LayoutInflater layoutInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        return  layoutInflater.inflate(R.layout.item_to_dolist, parent, false);
    }


}