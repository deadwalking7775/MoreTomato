package com.example.henry.moretomato.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

import java.io.File;

public class TaskProvider extends ContentProvider {

	public static final int ALL_MEMOS_WITH_DELETED = 0;
	public static final int TASK = 1;
	public static final int TITLE = 2;

	private static final String AUTHORITY = "com.example.henry.moretomato.data.TaskContentProvider";
	public static final Uri TASK_URI = Uri.parse("content://" + AUTHORITY + "/"
            + TaskDB.TASK_TABLE_NAME);
    private static final UriMatcher sURIMatcher = new UriMatcher(UriMatcher.NO_MATCH);
	static {
		sURIMatcher.addURI(AUTHORITY, TaskDB.TASK_TABLE_NAME, TASK);
		sURIMatcher.addURI(AUTHORITY, TaskDB.TASK_TABLE_NAME + "/#", TITLE);
	}

    private TaskDB mTaskDB;

	@Override
	public boolean onCreate() {
        mTaskDB = new TaskDB(getContext());
		return true;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
		queryBuilder.setTables(TaskDB.TASK_TABLE_NAME);
		Cursor cursor = queryBuilder.query(mTaskDB.getReadableDatabase(),
                projection, selection, selectionArgs, null, null, sortOrder);
		cursor.setNotificationUri(getContext().getContentResolver(), uri);
		return cursor;
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		int uriType = sURIMatcher.match(uri);
		SQLiteDatabase database = mTaskDB.getWritableDatabase();
		int rowAffected = 0;
		ContentValues values = new ContentValues();
		switch (uriType) {
		case TASK:
			rowAffected = database.delete(TaskDB.TASK_TABLE_NAME, selection,
					selectionArgs);
			break;
		case TITLE:
			String id = uri.getLastPathSegment();
            rowAffected = database.delete(TaskDB.TASK_TABLE_NAME,
                    Task.ID + " = " + id, selectionArgs);
		default:
			break;
		}
		getContext().getContentResolver().notifyChange(uri, null);
		return rowAffected;
	}

	@Override
	public String getType(Uri uri) {
		return null;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		if (values.containsKey(Task.ID)) {
			values.remove(Task.ID);
		}
		int uriType = sURIMatcher.match(uri);
		SQLiteDatabase database = mTaskDB.getWritableDatabase();
		Uri itemUri = null;
		switch (uriType) {
		case TASK:
			long newID = database.insert(TaskDB.TASK_TABLE_NAME, null, values);
			if (newID > 0) {
				itemUri = ContentUris.withAppendedId(uri, newID);
				getContext().getContentResolver().notifyChange(itemUri, null);
			}
		default:
			break;
		}
		return itemUri;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		int updateCount = 0;
		switch (sURIMatcher.match(uri)) {
		case TASK:
			updateCount = mTaskDB.getWritableDatabase().update(
					TaskDB.TASK_TABLE_NAME, values, selection, selectionArgs);
			break;
		case TITLE:
			String where = "";
			if (!TextUtils.isEmpty(selection)) {
				where += " and " + selection;
			}
			updateCount = mTaskDB.getWritableDatabase().update(
					TaskDB.TASK_TABLE_NAME, values,
					Task.ID + "=" + uri.getLastPathSegment() + where,
					selectionArgs);
			break;

		default:
			break;
		}
		if (updateCount > 0) {
			getContext().getContentResolver().notifyChange(uri, null);
		}
		return updateCount;
	}
}
