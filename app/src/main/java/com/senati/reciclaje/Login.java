package com.senati.reciclaje;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.senati.reciclaje.connection.DataBaseHelper;

public class Login extends AppCompatActivity {

    private EditText et_username, et_password;
    private DataBaseHelper dataBaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loadUI();
        dataBaseHelper = new DataBaseHelper(this);
    }

    public void login(View view){
        if (!validateFields()) return;

        String username = et_username.getText().toString();
        String password = et_password.getText().toString();
        boolean inserted = dataBaseHelper.LoginUser(username, password);

        if(inserted){
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            ToastShort("Inicio de sesión exitoso");
            finish();
        }

        clearFields();
    }

    public void redirectRegister(View view){
        Intent intent = new Intent(getApplicationContext(), Signup.class);
        startActivity(intent);
    }

    // metodos
    public void loadUI(){
        et_username = (EditText) findViewById(R.id.et_username);
        et_password = (EditText) findViewById(R.id.et_password);
    }
    public void ToastShort(String msg){
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }

    private boolean validateFields() {
        if (isEmpty(et_username)) return false;
        if (isEmpty(et_password)) return false;

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

    private void clearFields() {
        et_username.setText("");
        et_password.setText("");
    }

}