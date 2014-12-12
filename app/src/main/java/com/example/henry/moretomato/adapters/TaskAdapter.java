package com.example.henry.moretomato.adapters;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView;
import android.widget.EditText;
import android.support.v4.widget.CursorAdapter;
import android.view.View;

import com.example.henry.moretomato.R;
import com.example.henry.moretomato.data.TaskDB;
import com.example.henry.moretomato.data.TaskProvider;

/**
 * Created by Henry on 2014/10/27.
 */

public class TaskAdapter extends CursorAdapter implements AdapterView.OnItemClickListener,
        AdapterView.OnItemLongClickListener, TextView.OnEditorActionListener, View.OnClickListener{

    public TaskAdapter(Context context, Cursor cursor, int flag){
        super(context, cursor, flag);
        mContext.getContentResolver().registerContentObserver(
                TaskProvider.TASK_URI, false,
                new UpdateObserver(mUpdateHandler));
    }

    private Handler mUpdateHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            notifyDataSetChanged();
        };
    };

    class UpdateObserver extends ContentObserver {
        private Handler mHandler;

        public UpdateObserver(Handler handler) {
            super(handler);
            mHandler = handler;
        }

        @Override
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);
            mHandler.sendEmptyMessage(0);
        }
    }

    private static class RowCountHolder{
        public int mRowCnt;
    }

    @Override
    public boolean onEditorAction(android.widget.TextView textView, int actionId, android.view.KeyEvent keyEvent){
        if (actionId == EditorInfo.IME_ACTION_DONE){
            ContentValues values = new ContentValues();
            values.put("_id", mCursor.getColumnCount());
            values.put("content", textView.getText().toString());
            values.put("parent_id", "");
            values.put("createdtime", "");
            values.put("updatedtime", "");
            values.put("endtime", "");
            values.put("level", 0);
            Uri uri = mContext.getContentResolver().insert(TaskProvider.TASK_URI, values);
        }
        return true;
    }

    @Override
    public void onClick(android.view.View view){
        switch (view.getId()){
            case R.id.imageView_item_todo_delete:
                RowCountHolder holder = (RowCountHolder)view.getTag();
                mContext.getContentResolver().delete(ContentUris.withAppendedId(TaskProvider.TASK_URI, holder.mRowCnt), null, null);
                break;
            default:
                break;
        }
    }

    @Override
    public void onItemClick(android.widget.AdapterView<?> adapterView, android.view.View view, int i, long l){
        Toast.makeText(mContext,"Click item " + i,Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onItemLongClick(android.widget.AdapterView<?> adapterView, android.view.View view, int i, long l){
        Toast.makeText(mContext,"Edit text " + i,Toast.LENGTH_SHORT).show();
        return true;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor){

    }

    @Override
    public View newView (Context context, Cursor cursor, ViewGroup parent){
        LayoutInflater layoutInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v;

        if (cursor.getInt(cursor.getColumnIndex("_id")) == 0){
            v = layoutInflater.inflate(R.layout.item_create_new_todo, parent, false);
            EditText newTaskText = (EditText)v.findViewById(R.id.editText_new_task);
            newTaskText.setOnEditorActionListener(this);
            return v;
        }
        else {
            v = layoutInflater.inflate(R.layout.item_todo_list, parent, false);
            TextView text_todo_content = (TextView)v.findViewById(R.id.textView_item_todo_content);
            text_todo_content.setText(cursor.getString(cursor.getColumnIndex("content")));

            RowCountHolder holder = new RowCountHolder();
            holder.mRowCnt = cursor.getInt(cursor.getColumnIndex("_id"));
            v.findViewById(R.id.imageView_item_todo_delete).setTag(holder);
            v.findViewById(R.id.imageView_item_todo_delete).setOnClickListener(this);
            v.findViewById(R.id.imageView_item_todo_done).setTag(holder);
            v.findViewById(R.id.imageView_item_todo_done).setOnClickListener(this);
            v.findViewById(R.id.imageView_item_todo_star).setTag(holder);
            v.findViewById(R.id.imageView_item_todo_star).setOnClickListener(this);
            return  v;
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