package com.senati.reciclaje.connection;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Implementación del patrón Singleton
 */
public class DatabaseManager {
    private static DatabaseManager instance;
    private final SQLiteDatabase database;

    private DatabaseManager(Context context) {
        DBHelper dbHelper = new DBHelper(context);
        this.database = dbHelper.getWritableDatabase();
    }

    public static synchronized DatabaseManager getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseManager(context);
        }
        return instance;
    }

    public SQLiteDatabase getDatabase() {
        return database;
    }
}

