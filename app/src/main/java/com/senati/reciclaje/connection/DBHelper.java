package com.senati.reciclaje.connection;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.senati.reciclaje.repository.ItemRepository;
import com.senati.reciclaje.repository.UserRepository;

/**
 * DBHelper es una clase que sirve para inicializar los repositorios
 * permitiendo que sus respectivas tablas se generen en la base de datos.
 * la clase DBHelper se conectará a la DB por medio de {@link DatabaseManager}
 */
public class DBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "db_reciclaje";
    private static final int DB_VERSION = 1;

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        new UserRepository(db).createTable();
        new ItemRepository(db).createTable();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        new UserRepository(db).dropTable();
        new ItemRepository(db).dropTable();
        onCreate(db);
    }
}
