package com.example.controledecompras.main;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;

import com.example.controledecompras.R;

public class MainActivityValidade extends AppCompatActivity {

    public EditText Editcodigobarra;
    public EditText EditNomeproduto;
    public EditText EditDatavalidade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity_validade);

    }
}