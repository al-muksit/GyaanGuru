package com.gyaanguru.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class UserDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "UserProfile.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "user_profile";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_UID = "uid";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_LOCAL_IMAGE_PATH = "local_image_path";
    private static final String COLUMN_IMAGE_URL = "image_url";

    public UserDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_UID + " TEXT UNIQUE, " +
                COLUMN_NAME + " TEXT, " +
                COLUMN_EMAIL + " TEXT, " +
                COLUMN_LOCAL_IMAGE_PATH + " TEXT, " +
                COLUMN_IMAGE_URL + " TEXT)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void saveUserProfile(String uid, String name, String email, String localPath, String imageUrl) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_UID, uid);
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_EMAIL, email);
        if (localPath != null) values.put(COLUMN_LOCAL_IMAGE_PATH, localPath);
        if (imageUrl != null) values.put(COLUMN_IMAGE_URL, imageUrl);

        db.insertWithOnConflict(TABLE_NAME, null, values, SQLiteDatabase.CONFLICT_REPLACE);
        db.close();
    }

    public String getLocalImagePath(String uid) {
        SQLiteDatabase db = this.getReadableDatabase();
        String path = null;
        try (Cursor cursor = db.query(TABLE_NAME, new String[]{COLUMN_LOCAL_IMAGE_PATH},
                COLUMN_UID + "=?", new String[]{uid}, null, null, null)) {
            if (cursor != null && cursor.moveToFirst()) {
                path = cursor.getString(0);
            }
        }
        db.close();
        return path;
    }
}
