package com.shiming.hement.data.local;


import android.content.ContentValues;
import android.database.Cursor;
import com.shiming.hement.data.model.TodayBean;


/**
 * DB 数据库类
 */
public class DB {

    public DB() { }

    public abstract static class HementTable {
        public static final String TABLE_NAME = "hement";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_DATE = "date";
        public static final String COLUMN_DAY = "day";

        // 注意创建表的结构是否正确
        public static final String CREATE =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        COLUMN_TITLE + " TEXT NOT NULL, " +
                        COLUMN_DATE + " TEXT NOT NULL, " +
                        COLUMN_DAY + " TEXT NOT NULL" +
                " ); ";
        public static ContentValues toContentValues(TodayBean ribot) {
            ContentValues values = new ContentValues();
            values.put(COLUMN_TITLE, ribot.title);
            values.put(COLUMN_DATE, ribot.date);
            values.put(COLUMN_DAY, ribot.day);
            return values;
        }
        public static TodayBean parseCursor(Cursor cursor) {
            String title = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE));
            String date = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DATE));
            String day = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DAY));
            TodayBean bean = new TodayBean();
            bean.setDate(date);
            bean.setTitle(title);
            bean.setDay(day);
            return bean;
        }
    }
}
