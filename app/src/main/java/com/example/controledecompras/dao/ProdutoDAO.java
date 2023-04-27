package com.example.controledecompras.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.example.controledecompras.bean.ProdutoBEAN;
import com.example.controledecompras.conexao.Conexao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ProdutoDAO {

    private Conexao conexao;
    private SQLiteDatabase compraproduto;

    public ProdutoDAO (Context context){
        conexao = new Conexao(context);
        compraproduto = conexao.getWritableDatabase();
    }

    public long inserir (ProdutoBEAN produtoBEAN){
        ContentValues values = new ContentValues();
        values.put("codigobarra", produtoBEAN.getCodigobarra());
        values.put("nomeproduto", produtoBEAN.getNomeproduto());
        values.put("quantidade", produtoBEAN.getQuantidade());
        values.put("foto", produtoBEAN.getFoto());
        values.put("localcompra", produtoBEAN.getLocalcompra());
        values.put("comprar", produtoBEAN.getComprar());
        values.put("valor", produtoBEAN.getValor());
        values.put("datacompra", produtoBEAN.getDatacompra());
        return compraproduto.insert("produto",null, values);
    }

    public List<ProdutoBEAN> obterTodos(){
        List<ProdutoBEAN> produtoBEAN = new ArrayList<>();
        Cursor cursor = compraproduto.query("produto", new String[]{"id","codigobarra",
                        "nomeproduto","quantidade","foto","localcompra","comprar","valor","datacompra"},
                       null,null,null,null,null);
        while (cursor.moveToNext()){
            ProdutoBEAN p = new ProdutoBEAN();
            p.setId(cursor.getInt(0));
            p.setCodigobarra(cursor.getString(1));
            p.setNomeproduto(cursor.getString(2));
            p.setQuantidade(cursor.getFloat(3));
            p.setFoto(cursor.getBlob(4));
            p.setLocalcompra(cursor.getString(5));
            p.setComprar(cursor.getString(6));
            p.setValor(cursor.getFloat(7));
            p.setDatacompra(cursor.getString(8));
            produtoBEAN.add(p);
        }
        cursor.close();
       // compraproduto.close();
        return produtoBEAN;
    }

    public List<ProdutoBEAN> obterListaCompra(){
        List<ProdutoBEAN> produtoBEAN = new ArrayList<>();
        String sql = "SELECT * FROM produto WHERE produto.comprar='sim'";
        Cursor cursor = compraproduto.rawQuery(sql, null);
        while (cursor.moveToNext()){
            ProdutoBEAN p = new ProdutoBEAN();
            p.setId(cursor.getInt(0));
            p.setCodigobarra(cursor.getString(1));
            p.setNomeproduto(cursor.getString(2));
            p.setQuantidade(cursor.getFloat(3));
            p.setFoto(cursor.getBlob(4));
            p.setLocalcompra(cursor.getString(5));
            p.setComprar(cursor.getString(6));
            p.setValor(cursor.getFloat(7));
            p.setDatacompra(cursor.getString(8));
            produtoBEAN.add(p);
        }
        cursor.close();
        return produtoBEAN;
    }

    public void excluir(ProdutoBEAN produtoBEAN){
        compraproduto.delete("produto","id=?",new String[]{produtoBEAN.getId().toString()});
        compraproduto.close();
    }

    public void atualizar(ProdutoBEAN produtoBEAN){
        ContentValues values = new ContentValues();
        values.put("id", produtoBEAN.getId());
        values.put("codigobarra", produtoBEAN.getCodigobarra());
        values.put("nomeproduto", produtoBEAN.getNomeproduto());
        values.put("quantidade", produtoBEAN.getQuantidade());
        values.put("foto", produtoBEAN.getFoto());
        values.put("localcompra", produtoBEAN.getLocalcompra());
        values.put("comprar", produtoBEAN.getComprar());
        values.put("valor", produtoBEAN.getValor());
        values.put("datacompra", produtoBEAN.getDatacompra());
        String[] args = {String.valueOf(produtoBEAN.getId())};
         compraproduto.update("produto",values,"id=?",new String[]{produtoBEAN.getId().toString()});
         compraproduto.close();
    }

    public List procurar(String codproduto) {

        String sqlProcuraProduto = "SELECT * FROM produto WHERE codigobarra = "+"'"+codproduto+"'";

        Cursor cursor = compraproduto.rawQuery(sqlProcuraProduto,null);

        List<ProdutoBEAN> produtoBEANs = new ArrayList<>();

        while (cursor.moveToNext()){
            if(codproduto.equals(cursor.getString(cursor.getColumnIndex("codigobarra")))){
                ProdutoBEAN produtoBEAN = new ProdutoBEAN();
                produtoBEAN.setId(cursor.getInt(cursor.getColumnIndex("id")));
                produtoBEAN.setCodigobarra(cursor.getString(cursor.getColumnIndex("codigobarra")));
                produtoBEAN.setNomeproduto(cursor.getString(cursor.getColumnIndex("nomeproduto")));
                produtoBEAN.setQuantidade(Float.valueOf(cursor.getString(cursor.getColumnIndex("quantidade"))));
                produtoBEAN.setFoto(cursor.getBlob(cursor.getColumnIndex("foto")));
                produtoBEAN.setLocalcompra(cursor.getString(cursor.getColumnIndex("localcompra")));
                produtoBEAN.setComprar(cursor.getString(cursor.getColumnIndex("comprar")));
                produtoBEAN.setValor(cursor.getFloat(cursor.getColumnIndex("valor")));
                produtoBEAN.setDatacompra(cursor.getString(cursor.getColumnIndex("datacompra")));
                produtoBEANs.add(produtoBEAN);
            }
        }
        cursor.close();
       // compraproduto.close();
        return produtoBEANs;
    }
}
