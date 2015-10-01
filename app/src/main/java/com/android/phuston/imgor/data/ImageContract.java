package com.android.phuston.imgor.data;

import android.provider.BaseColumns;

/**
 * Defines contract for SQLite
 */
public class ImageContract {

    public ImageContract () {
    }

    public static abstract class ImageEntry implements BaseColumns {
        public static final String TABLE_NAME = "images";
        public static final String COLUMN_NAME_IMAGE = "image_entry";
    }
}
