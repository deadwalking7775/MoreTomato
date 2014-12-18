package com.example.henry.moretomato.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

import java.sql.Time;

public class TaskDB extends SQLiteOpenHelper {
    public final static String NAME = "MoreTomato";
    public final static int VERSION = 1;//12/18/14
    public final static String TASK_TABLE_NAME = "Task";

	private final String CREATE_TASK_TABLE = "create table Task(`_id` integer primary key autoincrement,"
			+ "`content` text,"
            + "`tag` text,"
            + "`urgency` int,"
            + "`is_done` int,"
            + "`created_time` int,"
            + "`done_time` int,"
            + "`used_time` int" + ");";


	public TaskDB(Context context, String name, CursorFactory factory, int version) {
		super(context, name, factory, version);
	}

	public TaskDB(Context context) {
		this(context, NAME, null, VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_TASK_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	}
}
