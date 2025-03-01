package com.senati.reciclaje.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.senati.reciclaje.connection.DatabaseManager;
import com.senati.reciclaje.model.User;

public class UserRepository extends BaseRepository {

    private static final String TABLE_NAME = "users";

    public UserRepository(SQLiteDatabase db) {
        super(db);
    }

    public UserRepository(Context context) {
        super(DatabaseManager.getInstance(context).getDatabase());
    }

    @Override
    public void createTable() {
        String createTableQuery = "CREATE TABLE " + TABLE_NAME + " (" +
                "ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "APELLIDOS TEXT NOT NULL, " +
                "NOMBRES TEXT NOT NULL, " +
                "USERNAME TEXT NOT NULL UNIQUE, " +
                "PASS_USER TEXT NOT NULL)";
        db.execSQL(createTableQuery);
    }

    @Override
    public void dropTable() {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
    }

    public boolean registerUser(User user) {
        ContentValues values = new ContentValues();
        values.put("APELLIDOS", user.getApellidos());
        values.put("NOMBRES", user.getNombres());
        values.put("USERNAME", user.getUsername());
        values.put("PASS_USER", user.getPassword());

        long result = db.insert(TABLE_NAME, null, values);
        return result != -1;
    }

    public boolean loginUser(String username, String password) {
        Cursor cursor = db.rawQuery("SELECT ID FROM " + TABLE_NAME + " WHERE USERNAME=? AND PASS_USER=?", new String[]{username, password});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }
}
