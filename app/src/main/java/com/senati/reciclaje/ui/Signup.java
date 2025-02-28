package com.senati.reciclaje.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.senati.reciclaje.R;
import com.senati.reciclaje.connection.DataBaseHelper;

public class Signup extends AppCompatActivity {

    private EditText et_apellidos, et_nombres, et_username, et_password, et_confirm_password;
    private DataBaseHelper dataBaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        loadUI();
    }

    // eventos
    public void registerUser(View view){
        if (!validateFields()) return;
        if (!validateUsername()) return;
        if (!validatePassword()) return;
        if (!validatePasswordMatch()) return;

        String apellidos = et_apellidos.getText().toString();
        String nombres = et_nombres.getText().toString();
        String username = et_username.getText().toString();
        String password = et_password.getText().toString();

        dataBaseHelper = new DataBaseHelper(this);
        boolean isRegistered = dataBaseHelper.RegisterUser(apellidos, nombres, username, password);

        if (isRegistered) {
            ToastShort("Usuario registrado exitosamente");
            clearFields();

            Intent intent = new Intent(this, Login.class);
            startActivity(intent);
            finish();
        } else {
            ToastShort("Error al registrar usuario");
        }
    }


    public void redirectLogin(View view){
        Intent intent = new Intent(getApplicationContext(), Login.class);
        startActivity(intent);
    }

    // metodos
    public void loadUI(){
        et_apellidos = (EditText) findViewById(R.id.et_apellidos);
        et_nombres = (EditText) findViewById(R.id.et_nombres);
        et_username = (EditText) findViewById(R.id.et_username);
        et_password = (EditText) findViewById(R.id.et_password);
        et_confirm_password = (EditText) findViewById(R.id.et_confirm_password);
    }
    public void ToastShort(String msg){
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }

    private boolean validateFields() {
        if (isEmpty(et_apellidos)) return false;
        if (isEmpty(et_nombres)) return false;
        if (isEmpty(et_username)) return false;
        if (isEmpty(et_password)) return false;
        if (isEmpty(et_confirm_password)) return false;

        return true;
    }

    private boolean isEmpty(EditText editText) {
        if (editText.getText().toString().trim().isEmpty()) {
            editText.setError("Este campo es obligatorio");
            editText.requestFocus();
            return true;
        }
        return false;
    }

    private boolean validateUsername() {
        String username = et_username.getText().toString();
        if (username.length() < 8) {
            et_username.setError("El nombre de usuario debe tener al menos 8 caracteres");
            et_username.requestFocus();
            return false;
        }
        return true;
    }

    private boolean validatePassword() {
        String password = et_password.getText().toString();
        String passwordPattern = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\W).{8,}$";

        if (!password.matches(passwordPattern)) {
            et_password.setError("La contraseña debe tener al menos 8 caracteres, una mayúscula, una minúscula y un carácter especial");
            et_password.requestFocus();
            return false;
        }
        return true;
    }

    private boolean validatePasswordMatch() {
        String password = et_password.getText().toString();
        String confirmPassword = et_confirm_password.getText().toString();

        if (!password.equals(confirmPassword)) {
            et_confirm_password.setError("Las contraseñas no coinciden");
            et_confirm_password.requestFocus();
            return false;
        }
        return true;
    }

    private void clearFields() {
        et_apellidos.setText("");
        et_nombres.setText("");
        et_username.setText("");
        et_password.setText("");
        et_confirm_password.setText("");

        et_apellidos.requestFocus();
    }

}