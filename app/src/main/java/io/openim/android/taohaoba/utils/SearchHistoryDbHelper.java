package io.openim.android.taohaoba.utils;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 历史搜索记录
 */
public class SearchHistoryDbHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "search_history.db";
    private static final int DB_VERSION = 2;
    public static final String TABLE_NAME = "history";
    public static final String COL_ID = "_id";
    public static final String COL_CONTENT = "content";
    public static final String COL_TIMESTAMP = "timestamp";

    // 单例模式[8](@ref)
    private static SearchHistoryDbHelper instance;

    public static synchronized SearchHistoryDbHelper getInstance(Context context) {
        if (instance == null) {
            instance = new SearchHistoryDbHelper(context.getApplicationContext());
        }
        return instance;
    }

    private SearchHistoryDbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + "("
                + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COL_CONTENT + " TEXT UNIQUE,"  // 唯一约束去重[1](@ref)
                + COL_TIMESTAMP + " DATETIME DEFAULT CURRENT_TIMESTAMP)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    // 插入搜索记录[7](@ref)
    public void insertRecord(String query) {
        if (TextUtils.isEmpty(query)) return;

        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_CONTENT, query);

        db.insertWithOnConflict(TABLE_NAME, null, values, SQLiteDatabase.CONFLICT_REPLACE);
    }

    // 获取最近10条记录[3](@ref)
    @SuppressLint("Range")
    public List<String> getRecentRecords() {
        List<String> records = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME,
                new String[]{COL_CONTENT},
                null, null,
                null, null,
                COL_TIMESTAMP + " DESC",
                "10");

        while (cursor.moveToNext()) {
            records.add(cursor.getString(cursor.getColumnIndex(COL_CONTENT)));
        }
        cursor.close();
        return records;
    }

    // 清除历史记录[2](@ref)
    public void clearHistory() {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_NAME, null, null);
    }
}
