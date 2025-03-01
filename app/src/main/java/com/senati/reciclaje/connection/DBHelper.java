package com.senati.reciclaje.connection;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.senati.reciclaje.repository.UserRepository;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "db_reciclaje";
    private static final int DB_VERSION = 1;

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        new UserRepository(db).createTable();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        new UserRepository(db).dropTable();
        onCreate(db);
    }
}
