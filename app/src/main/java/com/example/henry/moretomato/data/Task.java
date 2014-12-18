package com.example.henry.moretomato.data;

import android.content.ContentValues;
import android.database.Cursor;

import java.io.Serializable;

public class Task implements Serializable {

    public final static int UNDONE = 0;
    public final static int DONE = 1;

    public final static String ID = "_id";
    public final static String CONTENT = "content";
    public final static String TAG = "tag";
    public final static String IS_DONE = "is_done";
    public final static String URGENCY = "urgency";
    public final static String CREATED_TIME = "created_time";
    public final static String DONE_TIME = "completed_time";
    public final static String USED_TIME = "used_time";

    public ContentValues buildNewTask(String content, String tag){
        ContentValues values = new ContentValues();
        values.put(CONTENT, content);
        values.put(TAG, tag);
        values.put(URGENCY, 0);
        values.put(IS_DONE, UNDONE);
        values.put(CREATED_TIME, System.currentTimeMillis());
        values.put(DONE_TIME, 0);
        values.put(USED_TIME, 0);
        return values;
    }

    public Task(Cursor cursor) {
    }
}


