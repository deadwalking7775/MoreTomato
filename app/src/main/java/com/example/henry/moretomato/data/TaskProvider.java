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

public class TaskProvider extends ContentProvider {

	public static final int ALL_MEMOS_WITH_DELETED = 0;
	public static final int TASK = 1;
	public static final int TITLE = 2;

	private static final String AUTHORITY = "com.example.henry.moretomato.data.MemoContentProvider";
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
		int uriType = sURIMatcher.match(uri);
		switch (uriType) {
		case ALL_MEMOS_WITH_DELETED:
			break;
		case TASK:
			break;
		case TITLE:
			queryBuilder.appendWhere(TaskDB.ID + "=" + uri.getLastPathSegment());
			break;

		default:
			throw new IllegalArgumentException("Unknown URI");
		}
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
//		values.put(TaskDB.STATUS, Task.STATUS_DELETE);
//		values.put(TaskDB.SYNCSTATUS, Task.NEED_SYNC_DELETE);
		switch (uriType) {
		case TASK:
			rowAffected = database.delete(TaskDB.TASK_TABLE_NAME, selection,
					selectionArgs);
			break;
		case TITLE:
			String id = uri.getLastPathSegment();
			if (TextUtils.isEmpty(selection)) {
				rowAffected = database.update(TaskDB.TASK_TABLE_NAME, values,
						TaskDB.ID + "=" + id, null);
			} else {
				rowAffected = database.update(TaskDB.TASK_TABLE_NAME, values,
						selection + " and " + TaskDB.ID + "=" + id,
						selectionArgs);
			}
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
		if (values.containsKey(TaskDB.ID)) {
			values.remove(TaskDB.ID);
		}
//		Task task = Task.build(values);
//		if (task.getContent().trim().length() == 0) {
//			return null;
//		}
		int uriType = sURIMatcher.match(uri);
		SQLiteDatabase database = mTaskDB.getWritableDatabase();
		Uri itemUri = null;
		switch (uriType) {
		case TASK:
			long newID = database.insert(TaskDB.TASK_TABLE_NAME, null, values);
			if (newID > 0) {
				itemUri = ContentUris.withAppendedId(uri, newID);
				getContext().getContentResolver().notifyChange(itemUri, null);
//				task.setId((int) newID);
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
					TaskDB.ID + "=" + uri.getLastPathSegment() + where,
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
