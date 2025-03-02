package com.senati.reciclaje.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

import com.senati.reciclaje.R;
import com.senati.reciclaje.context.UserContext;
import com.senati.reciclaje.repository.UserRepository;
import com.senati.reciclaje.utils.ToastUtils;

public class Login extends AppCompatActivity {

    private EditText et_username, et_password;
    private UserRepository userRepository;
    private UserContext userContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loadUI();
        userRepository = new UserRepository(this);
        userContext = new UserContext(this);
    }

    public void login(View view) {
        if (!validateFields()) return;

        String username = et_username.getText().toString();
        String password = et_password.getText().toString();

        Integer userId = userRepository.loginUser(username, password);

        if (userId != null) {
            userContext.saveUserId(userId);
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            clearFields();
            ToastUtils.showToastShort(this, "Inicio de sesión exitoso");
            finish();
        } else {
            ToastUtils.showToastShort(this, "Credenciales incorrectas");
        }
    }

    public void redirectRegister(View view){
        Intent intent = new Intent(getApplicationContext(), Signup.class);
        startActivity(intent);
    }

    // metodos
    public void loadUI(){
        et_username = findViewById(R.id.et_username);
        et_password = findViewById(R.id.et_password);
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