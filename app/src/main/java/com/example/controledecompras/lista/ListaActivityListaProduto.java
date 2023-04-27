package com.example.controledecompras.lista;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.controledecompras.R;
import com.example.controledecompras.adapter.ProdutoAdapter;
import com.example.controledecompras.bean.ProdutoBEAN;
import com.example.controledecompras.dao.ProdutoDAO;
import com.example.controledecompras.main.MainActivityProduto;

import java.util.ArrayList;
import java.util.List;

public class ListaActivityListaProduto extends AppCompatActivity {

    private ListView listviewProduto;
    private ProdutoDAO produtoDAO;
    private List<ProdutoBEAN> produtoBEAN;
    private List<ProdutoBEAN> produtoBEANS = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lista_activity_produto);

        listviewProduto = findViewById(R.id.listviewProduto);
        produtoDAO = new ProdutoDAO(this);
        produtoBEAN = produtoDAO.obterListaCompra();
        produtoBEANS.addAll(produtoBEAN);

        ProdutoAdapter adaptador = new ProdutoAdapter(this, produtoBEANS);
        listviewProduto.setAdapter(adaptador);
        registerForContextMenu(listviewProduto);

        listviewProduto.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final ProdutoBEAN p = produtoBEANS.get(position);

                Intent intent = new Intent(ListaActivityListaProduto.this, MainActivityProduto.class);

                intent.putExtra("codigo",1);
                intent.putExtra("codigobarra",p.getCodigobarra());
                intent.putExtra("idproduto",p.getId());
                intent.putExtra("nomeproduto",p.getNomeproduto());
                intent.putExtra("quantidade",p.getQuantidade());
                intent.putExtra("foto",p.getFoto());
                intent.putExtra("localcompra",p.getLocalcompra());
                intent.putExtra("comprar",p.getComprar());
                intent.putExtra("valor",p.getValor());
                intent.putExtra("datacompra",p.getDatacompra());
                startActivity(intent);
            }
        });
    }

    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo){
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater i = getMenuInflater();
        i.inflate(R.menu.menu_contexto_produto,menu);
    }

    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater i = getMenuInflater();
        i.inflate(R.menu.menu_produto,menu);
        SearchView sv = (SearchView) menu.findItem(R.id.app_bar_search).getActionView();
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                procuraProduto(s);
                return false;
            }
        });
        return true;
    }

    public void procuraProduto(String nome){
        produtoBEANS.clear();
        for(ProdutoBEAN a : produtoBEAN){
            if(a.getNomeproduto().toLowerCase().contains((nome.toLowerCase()))){
                produtoBEANS.add(a);
            }
        }
        listviewProduto.invalidateViews();
    }

    public void excluir(MenuItem item){
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        final ProdutoBEAN produtoBEANExclui = produtoBEANS.get(menuInfo.position);
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Atenção!")
                .setMessage("Realmente deseja excluir esse dado:")
                .setNegativeButton("Não",null)
                .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        produtoBEANS.remove(produtoBEANExclui);
                        produtoBEAN.remove(produtoBEANExclui);
                        produtoDAO.excluir(produtoBEANExclui);
                        listviewProduto.invalidateViews();
                    }
                }).create();
        dialog.show();
    }

    public void compraPreco(MenuItem item) {
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        final ProdutoBEAN p = produtoBEAN.get(menuInfo.position);
        Intent intent = new Intent(ListaActivityListaProduto.this, ComparaPrecoActivityProduto.class);
        intent.putExtra("codigo",0);
        intent.putExtra("codigobarra",p.getCodigobarra());
        intent.putExtra("nomeproduto",p.getNomeproduto());
        intent.putExtra("foto",p.getFoto());
        startActivity(intent);
    }

    public void incluir(MenuItem item) {
        Intent intent = new Intent(ListaActivityListaProduto.this, MainActivityProduto.class);
        intent.putExtra("codigo",0);
        startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
        produtoBEAN = produtoDAO.obterListaCompra();
        produtoBEANS.clear();
        produtoBEANS.addAll(produtoBEAN);
        listviewProduto.invalidateViews();
    }
}