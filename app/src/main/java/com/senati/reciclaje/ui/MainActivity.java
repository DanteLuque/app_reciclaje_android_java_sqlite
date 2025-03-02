package com.senati.reciclaje.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.senati.reciclaje.R;
import com.senati.reciclaje.context.UserContext;
import com.senati.reciclaje.model.Item;
import com.senati.reciclaje.repository.ItemRepository;
import com.senati.reciclaje.utils.ToastUtils;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ImageButton ib_plastico, ib_carton, ib_metal, ib_eletronico;
    private ImageView iv_item_seleccionado;
    private EditText et_cantidad_ingresada;
    private TextView tv_score_plastico, tv_score_carton, tv_score_metal, tv_score_electronica;
    private int selectedMaterial = -1;
    private int userId;
    private ItemRepository itemRepository;
    private UserContext userContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        userContext = new UserContext(this);
        userId = userContext.getUserId();

        if (userId == -1) {
            Toast.makeText(this, "Error: Usuario no autenticado", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(), Login.class);
            startActivity(intent);
            finish();
            return;
        }

        itemRepository = new ItemRepository(this);
        loadUI();
        updateScores();
    }

    //eventos
    public void selectItem(View view) {
        int id = view.getId();

        if (id == R.id.ib_plastico) {
            iv_item_seleccionado.setImageResource(R.drawable.plastico);
            highlightSelectedButton(ib_plastico);
            selectedMaterial = 1;
        } else if (id == R.id.ib_carton) {
            iv_item_seleccionado.setImageResource(R.drawable.carton);
            highlightSelectedButton(ib_carton);
            selectedMaterial = 2;
        } else if (id == R.id.ib_metal) {
            iv_item_seleccionado.setImageResource(R.drawable.metales);
            highlightSelectedButton(ib_metal);
            selectedMaterial = 3;
        } else if (id == R.id.ib_eletronico) {
            iv_item_seleccionado.setImageResource(R.drawable.electronica);
            highlightSelectedButton(ib_eletronico);
            selectedMaterial = 4;
        }
    }

    public void saveItem(View view) {
        if (selectedMaterial == -1) {
            ToastUtils.showToastShort(this, "Selecciona un material antes de guardar");
            return;
        }

        String cantidadStr = et_cantidad_ingresada.getText().toString().trim();

        try {
            double cantidadKg = Double.parseDouble(cantidadStr);

            if (cantidadKg == 0) {
                ToastUtils.showToastShort(this, "La cantidad no puede ser 0");
                return;
            }

            int cantidadGramos = (int) (cantidadKg * 1000);

            boolean success = itemRepository.updateItems(userId, cantidadGramos, selectedMaterial);
            if (success) {
                ToastUtils.showToastShort(this, "Cantidad actualizada correctamente");
                et_cantidad_ingresada.setText("");
                updateScores();
            } else {
                ToastUtils.showToastShort(this, "Error al actualizar");
            }
        } catch (NumberFormatException e) {
            ToastUtils.showToastShort(this, "Por favor, ingresa un número válido");
        }
    }


    public void logout(View view) {
        userContext.clearUser();
        Intent intent = new Intent(getApplicationContext(), Login.class);
        startActivity(intent);
        finish();
    }

    //metodos
    private void updateScores() {
        List<Item> items = itemRepository.getItemsByUserId(userId);
        if (!items.isEmpty()) {
            Item userItem = items.get(0);
            tv_score_plastico.setText(String.valueOf(userItem.getNumPlastico()));
            tv_score_carton.setText(String.valueOf(userItem.getNumCarton()));
            tv_score_metal.setText(String.valueOf(userItem.getNumMetal()));
            tv_score_electronica.setText(String.valueOf(userItem.getNumElectronico()));
        }
    }
    public void loadUI(){
        ib_plastico = findViewById(R.id.ib_plastico);
        ib_carton = findViewById(R.id.ib_carton);
        ib_metal = findViewById(R.id.ib_metal);
        ib_eletronico = findViewById(R.id.ib_eletronico);
        iv_item_seleccionado = findViewById(R.id.iv_item_seleccionado);
        et_cantidad_ingresada = findViewById(R.id.et_cantidad_ingresada);
        tv_score_plastico =  findViewById(R.id.tv_score_plastico);
        tv_score_carton =  findViewById(R.id.tv_score_carton);
        tv_score_metal = findViewById(R.id.tv_score_metal);
        tv_score_electronica =  findViewById(R.id.tv_score_electronica);
    }

    private void highlightSelectedButton(ImageButton selectedButton) {
        ib_plastico.setBackgroundResource(android.R.color.transparent);
        ib_carton.setBackgroundResource(android.R.color.transparent);
        ib_metal.setBackgroundResource(android.R.color.transparent);
        ib_eletronico.setBackgroundResource(android.R.color.transparent);

        selectedButton.setBackgroundResource(R.drawable.bg_selected_square);
    }
}