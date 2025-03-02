package com.senati.reciclaje.repository;

import android.database.sqlite.SQLiteDatabase;

/**
 * BaseRepository es una clase abstracta que sirve como platilla
 * para los repositorios que se van a instanciar en {@link com.senati.reciclaje.connection.DBHelper}
 */
public abstract class BaseRepository {
    protected SQLiteDatabase db;

    public BaseRepository(SQLiteDatabase db) {
        this.db = db;
    }

    public abstract void createTable();
    public abstract void dropTable();
}
