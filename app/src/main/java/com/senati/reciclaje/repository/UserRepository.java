package com.senati.reciclaje.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.senati.reciclaje.model.User;

public class UserRepository extends SQLiteOpenHelper {

    private static final String DB_NAME = "db_reciclaje";
    private static final int DB_VERSION = 1;
    private static final String USER_TABLE = "users";

    public UserRepository(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + USER_TABLE + " (" +
                "ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "APELLIDOS TEXT NOT NULL, " +
                "NOMBRES TEXT NOT NULL, " +
                "USERNAME TEXT NOT NULL, " +
                "PASS_USER TEXT NOT NULL)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + USER_TABLE);
        onCreate(db);
    }

    public boolean registerUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("APELLIDOS", user.getApellidos());
        values.put("NOMBRES", user.getNombres());
        values.put("USERNAME", user.getUsername());
        values.put("PASS_USER", user.getPassword());

        long result = db.insert(USER_TABLE, null, values);
        db.close();
        return result != -1;
    }

    public boolean loginUser(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT ID FROM " + USER_TABLE + " WHERE USERNAME=? AND PASS_USER=?", new String[]{username, password});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        db.close();
        return exists;
    }
}
