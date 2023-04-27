package com.example.controledecompras.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.controledecompras.R;
import com.example.controledecompras.bean.ProdutoBEAN;
import com.example.controledecompras.bean.ValidadeBEAN;
import com.example.controledecompras.dao.ProdutoDAO;
import com.example.controledecompras.dao.ValidadeDAO;
import com.example.controledecompras.lista.ListaActivityListaProduto;
import com.example.controledecompras.lista.ListaActivityProduto;
import com.example.controledecompras.lista.ListaActivityValidade;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Timer;


public class MainActivity extends AppCompatActivity {

    private ProdutoDAO produtoDAO;
    private ValidadeDAO validadeDAO;
    private List<ValidadeBEAN> validadeBEAN;
    private List<ValidadeBEAN> validadeBEANS = new ArrayList<>();
    private Timer t;
    private int TimerCounter = 0;
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
    private String dataCorrente;
    private Button btnValidade;
    private Button btnCodigoBarra;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        produtoDAO = new ProdutoDAO(this);

        validadeDAO = new ValidadeDAO(this);
        validadeBEAN = validadeDAO.obterTodos();
        validadeBEANS.addAll(validadeBEAN);

        dataCorrente = sdf.format(new Date());

        btnCodigoBarra = findViewById(R.id.btnCodigoBarra);
        btnValidade = findViewById(R.id.btnValidade);

        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA},0);
        }

        btnCodigoBarra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Animation animation= AnimationUtils.loadAnimation(MainActivity.this,R.anim.zoomin);
               // btnCodigoBarra.startAnimation(animation);

                IntentIntegrator integrator = new IntentIntegrator(MainActivity.this);
                integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
                integrator.setPrompt("Leitor Código Barras");
                integrator.setCameraId(0);
                integrator.setBeepEnabled(true);
                integrator.setBarcodeImageEnabled(true);
                integrator.initiateScan();
            }
        });

        btnValidade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verificarValidade();
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
        if(result != null){
            if(result.getContents()==null){
                Toast.makeText(this, "Leitura cancelada!", Toast.LENGTH_SHORT).show();
            }else{
                List <ProdutoBEAN>produtoBEAN = produtoDAO.procurar(result.getContents());
                Intent intent = new Intent(MainActivity.this, MainActivityProduto.class);
                intent.putExtra("codigo",produtoBEAN.size());
                intent.putExtra("codigobarra",result.getContents());
                for(int i=0;i<produtoBEAN.size();i++){
                    intent.putExtra("idproduto",produtoBEAN.get(i).getId());
                    intent.putExtra("nomeproduto",produtoBEAN.get(i).getNomeproduto());
                    intent.putExtra("quantidade",produtoBEAN.get(i).getQuantidade());
                    intent.putExtra("foto",produtoBEAN.get(i).getFoto());
                    intent.putExtra("localcompra",produtoBEAN.get(i).getLocalcompra());
                    intent.putExtra("comprar",produtoBEAN.get(i).getComprar());
                    intent.putExtra("valor",produtoBEAN.get(i).getValor());
                    intent.putExtra("datacompra",produtoBEAN.get(i).getDatacompra());
                }
                startActivity(intent);
            }
        }else{
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater i = getMenuInflater();
        i.inflate(R.menu.menu_principal,menu);
        return true;
    }

    public void produto (MenuItem item){
        Intent it = new Intent(this, ListaActivityProduto.class);
        it.putExtra("codigo",0);
        startActivity(it);
    }

    public void listaProduto (MenuItem item){
        Intent it = new Intent(this, ListaActivityListaProduto.class);
        startActivity(it);
    }

    public void incluir(MenuItem item) {
        Intent intent = new Intent(MainActivity.this, MainActivityProduto.class);
        intent.putExtra("codigo",0);
        startActivity(intent);
    }

    public void validade(MenuItem item) {
        Intent intent = new Intent(MainActivity.this, ListaActivityValidade.class);
      //  intent.putExtra("codigo",0);
        startActivity(intent);
    }

    public void verificarValidade(){
        if(validadeBEANS.size()!=0) {
            for (int i = 0; i < validadeBEANS.size(); i++) {
                if (!validadeBEANS.get(i).getDatavalidade().equals("")) {
                    // início: Calcula quantos dias da validade do produto
                    Calendar data1 = Calendar.getInstance();
                    Calendar data2 = Calendar.getInstance();
                    try {
                        data1.setTime(sdf.parse(dataCorrente));
                        data2.setTime(sdf.parse(String.valueOf(validadeBEANS.get(i).getDatavalidade())));
                    } catch (java.text.ParseException e) {
                    }
                    int diaValidade = data2.get(Calendar.DAY_OF_YEAR) -
                            data1.get(Calendar.DAY_OF_YEAR);
                    // fim: Calcula quantos dias da validade do produto
                    if (diaValidade < 0) {
                        Toast.makeText(MainActivity.this, "O produto " +
                                validadeBEANS.get(i).getNomeproduto() +
                                " está vencido há " + diaValidade * -1 + " dia(s)", Toast.LENGTH_LONG).show();
                    } else if (diaValidade == 0) {
                        Toast.makeText(MainActivity.this, "O produto " +
                                validadeBEANS.get(i).getNomeproduto() +
                                " vence hoje!", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(MainActivity.this, "O produto " +
                                validadeBEANS.get(i).getNomeproduto() +
                                " vencerá em " + diaValidade + " dia(s)", Toast.LENGTH_LONG).show();
                    }
                }
            }
            Toast.makeText(MainActivity.this, "Entre na opção **Validade do Produto** Para desabilitar a notificação do produto", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(MainActivity.this, "Nenhum produto cadastrado", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        validadeBEAN = validadeDAO.obterTodos();
        validadeBEANS.clear();
        validadeBEANS.addAll(validadeBEAN);
    }


}