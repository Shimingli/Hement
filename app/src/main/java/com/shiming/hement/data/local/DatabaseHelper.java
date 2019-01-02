package com.shiming.hement.data.local;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import com.shiming.hement.data.model.TodayBean;
import com.squareup.sqlbrite2.BriteDatabase;
import com.squareup.sqlbrite2.SqlBrite;

import java.util.List;


import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Scheduler;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * <p>
 *
 * </p>
 *
 * @author shiming
 * @version v1.0
 * @since 2018/11/29 10:58
 */

public class DatabaseHelper {

    private final BriteDatabase mDb;

    /**
     * 这样子就没有很好的解耦，在方法里面 new 一个对象 ！
     * @param context
     */
    public DatabaseHelper(Context context) {
        this(new DbOpenHelper(context), Schedulers.io());
    }

    public DatabaseHelper(DbOpenHelper dbOpenHelper, Scheduler scheduler) {
        SqlBrite.Builder briteBuilder = new SqlBrite.Builder();
        mDb = briteBuilder.build().wrapDatabaseHelper(dbOpenHelper, scheduler);
    }
    public  BriteDatabase getDb() {
        return mDb;
    }

    /**
     * 保存数据库中的数据
     * @param newRibots
     * @return
     */
    public Observable<TodayBean> setDBData(final List<TodayBean> newRibots) {
        return Observable.create(new ObservableOnSubscribe<TodayBean>() {
            @Override
            public void subscribe(ObservableEmitter<TodayBean> emitter) throws Exception {
                if (emitter.isDisposed()) return;
                BriteDatabase.Transaction transaction = mDb.newTransaction();
                try {
                    mDb.delete(DB.HementTable.TABLE_NAME, null);
                    for (TodayBean ribot : newRibots) {
                        long result = mDb.insert(DB.HementTable.TABLE_NAME,
                                DB.HementTable.toContentValues(ribot),
                                SQLiteDatabase.CONFLICT_REPLACE);
                        if (result >= 0) emitter.onNext(ribot);
                    }
                    transaction.markSuccessful();
                    emitter.onComplete();
                } finally {
                    transaction.end();
                }
            }
        });
    }

    /**
     * 获取数据库中的数据
     * @return
     */
    public Observable<List<TodayBean>> getDBData() {
        return mDb.createQuery(DB.HementTable.TABLE_NAME,
                "SELECT * FROM " + DB.HementTable.TABLE_NAME)
                .mapToList(new Function<Cursor, TodayBean>() {
                    @Override
                    public TodayBean apply(@NonNull Cursor cursor) throws Exception {
                        return DB.HementTable.parseCursor(cursor);
                    }
                });
    }
}
