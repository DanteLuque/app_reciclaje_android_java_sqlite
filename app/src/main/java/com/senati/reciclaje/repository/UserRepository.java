package com.senati.reciclaje.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.senati.reciclaje.connection.DatabaseManager;
import com.senati.reciclaje.model.User;
import org.mindrot.jbcrypt.BCrypt;

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

    public Integer registerUser(User user) {
        String hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());

        ContentValues values = new ContentValues();
        values.put("APELLIDOS", user.getApellidos());
        values.put("NOMBRES", user.getNombres());
        values.put("USERNAME", user.getUsername());
        values.put("PASS_USER", hashedPassword);

        long result = db.insert(TABLE_NAME, null, values);

        return result == -1 ? null : (int) result;
    }

    public Integer loginUser(String username, String password) {
        Cursor cursor = db.rawQuery(
                "SELECT ID, PASS_USER FROM " + TABLE_NAME + " WHERE USERNAME=?",
                new String[]{username}
        );

        Integer userId = null;
        if (cursor.moveToFirst()) {
            String storedHashedPassword = cursor.getString(1);
            if (BCrypt.checkpw(password, storedHashedPassword)) {
                userId = cursor.getInt(0);
            }
        }
        cursor.close();
        return userId;
    }

    public boolean userExists(String username) {
        Cursor cursor = db.rawQuery(
                "SELECT 1 FROM " + TABLE_NAME + " WHERE USERNAME=?",
                new String[]{username}
        );
        boolean exists = cursor.moveToFirst();
        cursor.close();
        return exists;
    }
}
