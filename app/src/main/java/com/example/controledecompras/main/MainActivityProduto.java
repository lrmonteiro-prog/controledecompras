package com.example.controledecompras.main;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.controledecompras.R;
import com.example.controledecompras.bean.CompraBEAN;
import com.example.controledecompras.bean.ProdutoBEAN;
import com.example.controledecompras.bean.ValidadeBEAN;
import com.example.controledecompras.dao.CompraDAO;
import com.example.controledecompras.dao.ProdutoDAO;
import com.example.controledecompras.dao.ValidadeDAO;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MainActivityProduto extends AppCompatActivity {

    public EditText editCodigpbarra;
    public EditText editNomeproduto;
    public EditText editQuantidade;
    public ImageView imgFoto;
    public EditText editLocalcompra;
    public EditText editComprar;
    public EditText editValor;
    public EditText editDatacompra;
    public TextView txtValidade;

    public Button btnCadastrar;
    public Button btnSalvar;
    public Button btnTirarfoto;

    private ProdutoDAO produtoDAO;
    private CompraDAO compraDAO;
    private ValidadeDAO validadeDAO;

    private Bitmap imagem;

    private Calendar calendar;
    DatePickerDialog datePickerDialog;

    private Switch switchComprar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity_produto);

        produtoDAO = new ProdutoDAO(this);
        compraDAO = new CompraDAO(this);
        validadeDAO = new ValidadeDAO(this);
        calendar = Calendar.getInstance();

        switchComprar = findViewById(R.id.switchComprar);

        editCodigpbarra = findViewById(R.id.editCodigpbarra);
        editNomeproduto = findViewById(R.id.editNomeproduto);
        editQuantidade  = findViewById(R.id.editQuantidade);
        imgFoto         = findViewById(R.id.imgFoto);
        editLocalcompra = findViewById(R.id.editLocalcompra);
        editComprar     = findViewById(R.id.editComprar);
        editValor       = findViewById(R.id.editValor);
        editDatacompra  = findViewById(R.id.editDatacompra);
        txtValidade     = findViewById(R.id.txtValidade);

        btnCadastrar = findViewById(R.id.btnCadastrar);
        btnSalvar = findViewById(R.id.btnSalvar);
        btnTirarfoto = findViewById(R.id.btnTirarfoto);

        Intent intent = getIntent();
        Integer idproduto = (Integer) intent.getSerializableExtra("idproduto");
        Integer codigo = (Integer) intent.getSerializableExtra("codigo");
        String codigobarra = (String) intent.getSerializableExtra("codigobarra");
        String nomeproduto = (String) intent.getSerializableExtra("nomeproduto");
        Float quantidade = (Float) intent.getSerializableExtra("quantidade");
        byte[] foto = (byte[]) intent.getSerializableExtra("foto");
        String localcompra = (String) intent.getSerializableExtra("localcompra");
        String comprar = (String) intent.getSerializableExtra("comprar");
        Float valor = (Float) intent.getSerializableExtra("valor");
        String datacompra = (String) intent.getSerializableExtra("datacompra");

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String dataCorrente = sdf.format(new Date());

        switchComprar.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                editComprar.setText(converter(isChecked));
            }
        });
        editComprar.setEnabled(false);

        if(codigo>0){
            btnCadastrar.setEnabled(false);
            btnCadastrar.setVisibility(View.INVISIBLE);
            btnSalvar.setEnabled(true);
            editCodigpbarra.setEnabled(false);
            editCodigpbarra.setText(codigobarra);
            editNomeproduto.setText(nomeproduto);
            editQuantidade.setText(String.valueOf(quantidade));
            Bitmap bitmap = BitmapFactory.decodeByteArray(foto, 0, foto.length);
            imgFoto.setImageBitmap(bitmap);
            editLocalcompra.setText(localcompra);
            if(comprar.equals("sim")){
                switchComprar.setChecked(true);
            }else{
                switchComprar.setChecked(false);
            }
            editComprar.setText(comprar);
            editValor.setText(String.valueOf(valor));
            editDatacompra.setText(dataCorrente);
        }else{
            btnCadastrar.setEnabled(true);
            btnSalvar.setEnabled(false);
            btnSalvar.setVisibility(View.INVISIBLE);
            editCodigpbarra.setText(codigobarra);
            editDatacompra.setText(dataCorrente);
            editComprar.setText("não");
            switchComprar.setChecked(false);
            imgFoto.setImageResource(android.R.drawable.ic_menu_gallery);
        }

        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProdutoBEAN produtoBEAN = new ProdutoBEAN();
                produtoBEAN.setCodigobarra(editCodigpbarra.getText().toString());
                produtoBEAN.setNomeproduto(editNomeproduto.getText().toString());
                produtoBEAN.setQuantidade(Float.valueOf(editQuantidade.getText().toString()));
                produtoBEAN.setFoto(imageViewToByte(imgFoto));
                produtoBEAN.setLocalcompra(editLocalcompra.getText().toString());
                produtoBEAN.setComprar(editComprar.getText().toString());
                produtoBEAN.setValor(Float.valueOf(String.valueOf(editValor.getText())));
                produtoBEAN.setDatacompra(editDatacompra.getText().toString());

                CompraBEAN compraBEAN = new CompraBEAN();
                compraBEAN.setCodigobarra(editCodigpbarra.getText().toString());
                compraBEAN.setLocalcompra(editLocalcompra.getText().toString());
                compraBEAN.setDatacompra(editDatacompra.getText().toString());
                compraBEAN.setValor(Float.valueOf(String.valueOf(editValor.getText())));
                compraDAO.inserir(compraBEAN);

                // pode ser assim também: if(txtValidade.getText().length()!=0)
                if(!txtValidade.getText().toString().equals("")){
                    ValidadeBEAN validadeBEAN = new ValidadeBEAN();
                    validadeBEAN.setCodigobarra(editCodigpbarra.getText().toString());
                    validadeBEAN.setNomeproduto(editNomeproduto.getText().toString());
                    validadeBEAN.setDatavalidade(txtValidade.getText().toString());
                    validadeDAO.inserir(validadeBEAN);
                }

                long id = produtoDAO.inserir(produtoBEAN);
                if(id>0) {
                    Toast.makeText(MainActivityProduto.this, "Dado inserido com id: " + id, Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(MainActivityProduto.this, "Não INCLUÍDO, Dado existente!!!" , Toast.LENGTH_LONG).show();
                }
            }
        });

        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProdutoBEAN produtoBEAN = new ProdutoBEAN();
                produtoBEAN.setId(idproduto);
                produtoBEAN.setCodigobarra(editCodigpbarra.getText().toString());
                produtoBEAN.setNomeproduto(editNomeproduto.getText().toString());
                produtoBEAN.setQuantidade(Float.valueOf(editQuantidade.getText().toString()));
                produtoBEAN.setFoto(imageViewToByte(imgFoto));
                produtoBEAN.setLocalcompra(editLocalcompra.getText().toString());
                produtoBEAN.setComprar(editComprar.getText().toString());
                produtoBEAN.setValor(Float.valueOf(String.valueOf(editValor.getText())));
                produtoBEAN.setDatacompra(editDatacompra.getText().toString());

                CompraBEAN compraBEAN = new CompraBEAN();
                compraBEAN.setCodigobarra(editCodigpbarra.getText().toString());
                compraBEAN.setLocalcompra(editLocalcompra.getText().toString());
                compraBEAN.setDatacompra(editDatacompra.getText().toString());
                compraBEAN.setValor(Float.valueOf(String.valueOf(editValor.getText())));
                compraDAO.inserir(compraBEAN);

                if(!txtValidade.getText().toString().equals("")){
                    ValidadeBEAN validadeBEAN = new ValidadeBEAN();
                    validadeBEAN.setCodigobarra(editCodigpbarra.getText().toString());
                    validadeBEAN.setNomeproduto(editNomeproduto.getText().toString());
                    validadeBEAN.setDatavalidade(txtValidade.getText().toString());
                    validadeDAO.inserir(validadeBEAN);
                }

                produtoDAO.atualizar(produtoBEAN);
                Toast.makeText(MainActivityProduto.this, "Dado atualizado com sucesso ", Toast.LENGTH_SHORT).show();
            }
        });

        btnTirarfoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tirarFoto();
            }
        });

        // início Calendário de Data de Vencimento
        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Calendar dataSelecionada = Calendar.getInstance();
                dataSelecionada.set(year, month,dayOfMonth);
                SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                txtValidade.setText(format.format(dataSelecionada.getTime()));
            }
        },calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));
        txtValidade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog.show();
            }
        });
        // fim Calendário de Data de Vencimento

    }

    private void tirarFoto() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent,1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode==1 && resultCode==RESULT_OK){
            Bundle extras = data.getExtras();
            imagem = (Bitmap) extras.get("data");
            imgFoto.setImageBitmap(imagem);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public static byte[] imageViewToByte(ImageView image) {
        Bitmap bitmap = ((BitmapDrawable)image.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 70, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }

    private String converter(boolean isChecked) {
        String textoExibir = "";
        if(isChecked){
            textoExibir = "sim";
        }else{
            textoExibir = "não";
        }
        return textoExibir;
    }


}