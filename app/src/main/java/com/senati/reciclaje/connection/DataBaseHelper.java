package com.senati.reciclaje.connection;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class DataBaseHelper extends SQLiteOpenHelper {

    private static final String DB = "db_reciclaje";
    private static final int DB_VERSION = 1;
    private static final String USER_TABLE = "users";
    private static final String ID = "ID";
    private static final String USERNAME_COLUMN = "USERNAME";
    private static final String PASSWORD_COLUMN = "PASS_USER";

    public DataBaseHelper(Context context) {
        super(context, DB, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("Database", "Creando la base de datos...");
        db.execSQL(
                "CREATE TABLE "+ USER_TABLE + "(" +
                        "ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "APELLIDOS TEXT NOT NULL," +
                        "NOMBRES TEXT NOT NULL," +
                        "USERNAME TEXT NOT NULL," +
                        "PASS_USER TEXT NOT NULL" +
                        ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE EXIST"+USER_TABLE);
        onCreate(db);
    }

    public boolean RegisterUser(
            String apellidos,
            String nombres,
            String username,
            String pass_user
    ) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("APELLIDOS", apellidos);
        cv.put("NOMBRES", nombres);
        cv.put("USERNAME", username);
        cv.put("PASS_USER", pass_user);

        long result = db.insert(USER_TABLE, null, cv);

        if (result == -1) {
            Log.e("Database", "Error al insertar usuario en la base de datos.");
        } else {
            Log.d("Database", "Usuario registrado correctamente con ID: " + result);
        }

        return result != -1;
    }


    public boolean LoginUser(
            String email,
            String password
    ){
        SQLiteDatabase db = this.getReadableDatabase();
        String[] id = {ID};
        String selections = USERNAME_COLUMN + "=?" + " AND "+ PASSWORD_COLUMN + "=?";
        String[] selectionArgs = {email, password};
        Cursor result = db.query(USER_TABLE, id, selections, selectionArgs, null, null,null);
        int rowCount = result.getCount();

        if(rowCount>0) {
            return true;
        }
      return false;
    }
}
