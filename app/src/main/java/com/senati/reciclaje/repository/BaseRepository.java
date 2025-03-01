package com.senati.reciclaje.repository;

import android.database.sqlite.SQLiteDatabase;

public abstract class BaseRepository {
    protected SQLiteDatabase db;

    public BaseRepository(SQLiteDatabase db) {
        this.db = db;
    }

    public abstract void createTable();
    public abstract void dropTable();
}
