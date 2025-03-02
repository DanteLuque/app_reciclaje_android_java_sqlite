package com.senati.reciclaje.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.senati.reciclaje.R;
import com.senati.reciclaje.model.Item;
import com.senati.reciclaje.model.User;
import com.senati.reciclaje.repository.ItemRepository;
import com.senati.reciclaje.repository.UserRepository;
import com.senati.reciclaje.utils.ToastUtils;

public class Signup extends AppCompatActivity {

    private EditText et_apellidos, et_nombres, et_username, et_password, et_confirm_password;
    private UserRepository userRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        loadUI();
        userRepository = new UserRepository(this);
    }

    // eventos
    public void registerUser(View view){
        if (!validateFields()) return;
        if (!validateUsername()) return;
        if (!validatePassword()) return;
        if (!validatePasswordMatch()) return;

        if (userRepository.userExists(et_username.getText().toString())) {
            ToastUtils.showToastShort(this, "El nombre de usuario ya existe");
            return;
        }

        User user = new User(
                null,
                et_apellidos.getText().toString(),
                et_nombres.getText().toString(),
                et_username.getText().toString(),
                et_password.getText().toString()
        );

        Integer userId = userRepository.registerUser(user);

        if (userId != null) {
            ItemRepository itemRepository = new ItemRepository(this);
            Item newItem = new Item(null, userId, 0, 0, 0, 0);
            itemRepository.saveItems(newItem);

            ToastUtils.showToastShort(this, "Usuario registrado exitosamente");
            clearFields();

            Intent intent = new Intent(this, Login.class);
            startActivity(intent);
            finish();
        } else {
            ToastUtils.showToastShort(this, "Error al registrar usuario");
        }
    }


    public void redirectLogin(View view){
        Intent intent = new Intent(getApplicationContext(), Login.class);
        startActivity(intent);
    }

    // metodos
    public void loadUI(){
        et_apellidos = findViewById(R.id.et_apellidos);
        et_nombres = findViewById(R.id.et_nombres);
        et_username = findViewById(R.id.et_username);
        et_password = findViewById(R.id.et_password);
        et_confirm_password = findViewById(R.id.et_confirm_password);
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