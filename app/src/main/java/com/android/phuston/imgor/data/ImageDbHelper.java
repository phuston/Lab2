package com.android.phuston.imgor.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Facilitates the creation/upgrading/deletion of SQLite database
 */

public class ImageDbHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Images.db";

    public ImageDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        final String TEXT_TYPE = " TEXT";
        final String SQL_CREATE_ENTRIES =
                "CREATE TABLE " + ImageContract.ImageEntry.TABLE_NAME + " (" +
                        ImageContract.ImageEntry._ID + " INTEGER PRIMARY KEY," +
                        ImageContract.ImageEntry.COLUMN_NAME_IMAGE + TEXT_TYPE + " )";

        db.execSQL(SQL_CREATE_ENTRIES);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        final String SQL_DELETE_ENTRIES =
                "DROP TABLE IF EXISTS " + ImageContract.ImageEntry.TABLE_NAME;

        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
