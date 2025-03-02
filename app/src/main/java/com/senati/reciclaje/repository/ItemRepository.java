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

    public Item getItemsById(int itemId) {
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE ITEM_ID=?", new String[]{String.valueOf(itemId)});
        if (cursor.moveToFirst()) {
            Item item = new Item(
                    cursor.getInt(0),
                    cursor.getInt(1),
                    cursor.getInt(2),
                    cursor.getInt(3),
                    cursor.getInt(4),
                    cursor.getInt(5)
            );
            cursor.close();
            return item;
        }
        cursor.close();
        return null;
    }

    public List<Item> getItemsByUserId(int userId) {
        List<Item> items = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE USER_ID=?", new String[]{String.valueOf(userId)});
        while (cursor.moveToNext()) {
            items.add(new Item(
                    cursor.getInt(0),
                    cursor.getInt(1),
                    cursor.getInt(2),
                    cursor.getInt(3),
                    cursor.getInt(4),
                    cursor.getInt(5)
            ));
        }
        cursor.close();
        return items;
    }

    public boolean updateItems(Item item) {
        ContentValues values = new ContentValues();
        values.put("USER_ID", item.getUserId());
        values.put("NUM_PLASTICO", item.getNumPlastico());
        values.put("NUM_CARTON", item.getNumCarton());
        values.put("NUM_METAL", item.getNumMetal());
        values.put("NUM_ELECTRONICO", item.getNumElectronico());

        int rowsAffected = db.update(TABLE_NAME, values, "ITEM_ID=?", new String[]{String.valueOf(item.getItemId())});
        return rowsAffected > 0;
    }
}
