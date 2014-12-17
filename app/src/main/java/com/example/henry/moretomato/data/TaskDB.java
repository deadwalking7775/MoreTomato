package com.example.henry.moretomato.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class TaskDB extends SQLiteOpenHelper {
    public final static String ID = "_id";
    public final static String STATUS = "status";
    public final static String ENID = "enid";
    public final static String CONTENT = "content";
    public final static String SYNCSTATUS = "syncstatus";
    public final static String UPDATEDTIME = "updatedtime";
    public final static String CREATEDTIME = "createdtime";
    public final static String NAME = "MoreTomato";
    public final static int VERSION = 1;
    public final static String TASK_TABLE_NAME = "Task";

	private final String CREATE_TASK_TABLE = "create table Task(`_id` integer primary key autoincrement,"
			+ "`content` text,"
            + "`parent_id` text,"
            + "`level` int,"
            + "`completed` int,"
            + "`urgency` int,"
			+ "`createdtime` text,"
			+ "`updatedtime` text,"
			+ "`endtime` text" + ");";

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
