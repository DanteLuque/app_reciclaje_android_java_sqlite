package com.senati.reciclaje.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.senati.reciclaje.connection.DatabaseManager;
import com.senati.reciclaje.model.Item;

import java.util.ArrayList;
import java.util.List;

public class ItemRepository extends BaseRepository {

    private static final String TABLE_NAME = "items";

    public ItemRepository(SQLiteDatabase db) {
        super(db);
    }

    public ItemRepository(Context context) {
        super(DatabaseManager.getInstance(context).getDatabase());
    }

    @Override
    public void createTable() {
        String createTableQuery = "CREATE TABLE " + TABLE_NAME + " (" +
                "ITEM_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "USER_ID INTEGER NOT NULL, " +
                "NUM_PLASTICO INTEGER DEFAULT 0, " +
                "NUM_CARTON INTEGER DEFAULT 0, " +
                "NUM_METAL INTEGER DEFAULT 0, " +
                "NUM_ELECTRONICO INTEGER DEFAULT 0)";
        db.execSQL(createTableQuery);
    }

    @Override
    public void dropTable() {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
    }

    public boolean saveItems(Item item) {
        ContentValues values = new ContentValues();
        values.put("USER_ID", item.getUserId());
        values.put("NUM_PLASTICO", item.getNumPlastico());
        values.put("NUM_CARTON", item.getNumCarton());
        values.put("NUM_METAL", item.getNumMetal());
        values.put("NUM_ELECTRONICO", item.getNumElectronico());

        long result = db.insert(TABLE_NAME, null, values);
        return result != -1;
    }


    public List<Item> getItemsByUserId(int userId) {
        List<Item> items = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE USER_ID=?", new String[]{String.valueOf(userId)});

        while (cursor.moveToNext()) {
            items.add(new Item(
                    cursor.getInt(0),
                    cursor.getInt(1),
                    cursor.getInt(2) / 1000.0,
                    cursor.getInt(3) / 1000.0,
                    cursor.getInt(4) / 1000.0,
                    cursor.getInt(5) / 1000.0
            ));
        }
        cursor.close();
        return items;
    }


    public boolean updateItems(int userId, int cantidadGramos, int tipoMaterial) {
        int cantidadActual = 0;
        String columnName = null;

        switch (tipoMaterial) {
            case 1:
                columnName = "NUM_PLASTICO";
                break;
            case 2:
                columnName = "NUM_CARTON";
                break;
            case 3:
                columnName = "NUM_METAL";
                break;
            case 4:
                columnName = "NUM_ELECTRONICO";
                break;
            default:
                return false;
        }
        if (columnName == null) return false;

        Cursor cursor = db.rawQuery("SELECT " + columnName + " FROM " + TABLE_NAME + " WHERE USER_ID=?", new String[]{String.valueOf(userId)});

        if (cursor.moveToFirst()) cantidadActual = cursor.getInt(0);

        cursor.close();

        int nuevaCantidad = cantidadActual + cantidadGramos;

        ContentValues values = new ContentValues();
        values.put(columnName, nuevaCantidad);

        int rowsAffected = db.update(TABLE_NAME, values, "USER_ID=?", new String[]{String.valueOf(userId)});
        return rowsAffected > 0;
    }
}
