package com.senati.reciclaje.context;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * UserContext guarda contexto generado por el usuario al iniciar sesion,
 * esto nos permitirá saber información util como el ID del usuario
 * para interactuar con otras entidades desde los activities obteniendo el contexto
 * y eliminarlo al cerrar sesión
 */
public class UserContext {
    private static final String PREF_NAME = "user_prefs";
    private static final String KEY_USER_ID = "user_id";
    private SharedPreferences sharedPreferences;

    public UserContext(Context context) {
        this.sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public void saveUserId(int userId) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(KEY_USER_ID, userId);
        editor.apply();
    }

    public int getUserId() {
        return sharedPreferences.getInt(KEY_USER_ID, -1);
    }

    public void clearUser() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(KEY_USER_ID);
        editor.apply();
    }
}
