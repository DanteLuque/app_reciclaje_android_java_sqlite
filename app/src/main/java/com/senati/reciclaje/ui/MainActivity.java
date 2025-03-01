package com.senati.reciclaje.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.senati.reciclaje.R;

public class MainActivity extends AppCompatActivity {

    private ImageButton ib_plastico, ib_carton, ib_metal, ib_eletronico;
    private ImageView iv_item_seleccionado;
    private EditText et_cantidad_ingresada;
    private TextView tv_score_plastico, tv_score_carton, tv_score_metal, tv_score_electronica;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadUI();
    }

    //eventos
    public void logout(View view){
        Intent intent = new Intent(getApplicationContext(), Login.class);
        startActivity(intent);
    }

    //metodos
    public void loadUI(){
        ib_plastico = (ImageButton) findViewById(R.id.ib_plastico);
        ib_carton = (ImageButton) findViewById(R.id.ib_carton);
        ib_metal = (ImageButton) findViewById(R.id.ib_metal);
        ib_eletronico = (ImageButton) findViewById(R.id.ib_eletronico);
        iv_item_seleccionado = (ImageView) findViewById(R.id.iv_item_seleccionado);
        et_cantidad_ingresada = (EditText) findViewById(R.id.et_cantidad_ingresada);
        tv_score_plastico = (TextView) findViewById(R.id.tv_score_plastico);
        tv_score_carton = (TextView) findViewById(R.id.tv_score_carton);
        tv_score_metal = (TextView) findViewById(R.id.tv_score_metal);
        tv_score_electronica = (TextView) findViewById(R.id.tv_score_electronica);
    }
}