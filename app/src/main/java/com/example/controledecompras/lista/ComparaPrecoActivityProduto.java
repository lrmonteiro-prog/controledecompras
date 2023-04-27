package com.example.controledecompras.lista;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import com.example.controledecompras.R;
import com.example.controledecompras.adapter.ComparaPrecoAdapter;
import com.example.controledecompras.bean.CompraBEAN;
import com.example.controledecompras.bean.ProdutoBEAN;
import com.example.controledecompras.conexao.Conexao;
import com.example.controledecompras.dao.CompraDAO;
import com.example.controledecompras.dao.ProdutoDAO;

import java.util.ArrayList;
import java.util.List;

public class ComparaPrecoActivityProduto extends AppCompatActivity {

    private ListView listviewComparaPreco;
    private ProdutoDAO produtoDAO;
    private CompraDAO compraDAO;
    private List<ProdutoBEAN> produtoBEAN;
    private List<ProdutoBEAN> produtoBEANS = new ArrayList<>();
    private List<CompraBEAN> compraBEAN;
    private List<CompraBEAN> compraBEANS = new ArrayList<>();

    private Conexao conexao;
    private SQLiteDatabase compraproduto;

    private TextView txtNomeProduto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.compara_preco_activity_produto);

        txtNomeProduto = findViewById(R.id.txtNomeProduto);

        Intent intent = getIntent();
        Integer codigo = (Integer) intent.getSerializableExtra("codigo");
        String codigobarra = (String) intent.getSerializableExtra("codigobarra");
        String nomeproduto = (String) intent.getSerializableExtra("nomeproduto");
        byte[] foto = (byte[]) intent.getSerializableExtra("foto");

        txtNomeProduto.setText(nomeproduto);

        conexao = new Conexao(this);
        compraproduto = conexao.getWritableDatabase();

        listviewComparaPreco = findViewById(R.id.listviewComparaPreco);
        produtoDAO = new ProdutoDAO(this);
        produtoBEAN = produtoDAO.obterTodos();
        produtoBEANS.addAll(produtoBEAN);

        compraDAO = new CompraDAO(this);
        compraBEAN = compraDAO.obterTodos();
        compraBEANS.addAll(compraBEAN);

        String sql_compra = "SELECT * from compra WHERE compra.codigobarra ="+"'"+codigobarra+"'"+
                "ORDER BY compra.valor DESC";

        Cursor cursor = compraproduto.rawQuery(sql_compra,null);
        compraBEANS.clear();
        while(cursor.moveToNext()){
            CompraBEAN c = new CompraBEAN();
            c.setId(cursor.getInt(cursor.getColumnIndex("id")));
            c.setCodigobarra(cursor.getString(cursor.getColumnIndex("codigobarra")));
            c.setLocalcompra(cursor.getString(cursor.getColumnIndex("localcompra")));
            c.setDatacompra(cursor.getString(cursor.getColumnIndex("datacompra")));
            c.setValor(cursor.getFloat(cursor.getColumnIndex("valor")));
            compraBEANS.add(c);
        }
        ComparaPrecoAdapter adaptador = new ComparaPrecoAdapter(this, compraBEANS);
        listviewComparaPreco.setAdapter(adaptador);
        registerForContextMenu(listviewComparaPreco);
    }
}