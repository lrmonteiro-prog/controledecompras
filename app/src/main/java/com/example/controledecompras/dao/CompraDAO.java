package com.example.controledecompras.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.controledecompras.bean.CompraBEAN;
import com.example.controledecompras.conexao.Conexao;

import java.util.ArrayList;
import java.util.List;

public class CompraDAO {

    private Conexao conexao;
    private SQLiteDatabase compraproduto;

    public CompraDAO(Context context){
        conexao = new Conexao(context);
        compraproduto = conexao.getWritableDatabase();
    }

    public long inserir (CompraBEAN compraBEAN){
        ContentValues values = new ContentValues();
        values.put("codigobarra", compraBEAN.getCodigobarra());
        values.put("localcompra", compraBEAN.getLocalcompra());
        values.put("datacompra", compraBEAN.getDatacompra());
        values.put("valor", compraBEAN.getValor());
        return compraproduto.insert("compra",null, values);
    }

    public List<CompraBEAN> obterTodos(){
        List<CompraBEAN> compraBEAN = new ArrayList<>();
        Cursor cursor = compraproduto.query("compra", new String[]{"id","codigobarra",
                        "localcompra","datacompra","valor"},null,null,null,
                null,null);
        while (cursor.moveToNext()){
            CompraBEAN c = new CompraBEAN();
            c.setId(cursor.getInt(0));
            c.setCodigobarra(cursor.getString(1));
            c.setLocalcompra(cursor.getString(2));
            c.setDatacompra(cursor.getString(3));
            c.setValor(cursor.getFloat(4));
            compraBEAN.add(c);
        }
        cursor.close();
        compraproduto.close();
        return compraBEAN;
    }

    public void excluir(CompraBEAN compraBEAN){
        compraproduto.delete("compra","id=?",new String[]{compraBEAN.getId().toString()});
        compraproduto.close();
    }

    public void atualizar(CompraBEAN compraBEAN){
        ContentValues values = new ContentValues();
        values.put("codigobarra", compraBEAN.getCodigobarra());
        values.put("localcompra", compraBEAN.getLocalcompra());
        values.put("datacompra", compraBEAN.getDatacompra());
        values.put("valor", compraBEAN.getValor());
        compraproduto.update("compra",values,"id=?",new String[]{compraBEAN.getId().toString()});
        compraproduto.close();
    }

    private List procurar(String codigobarra) {

        String sqlProcuraCompra = "SELECT * FROM compra WHERE nomeproduto = "+"'"+codigobarra+"'";

        Cursor cursor = compraproduto.rawQuery(sqlProcuraCompra,null);

        List<CompraBEAN> compraBEANs = new ArrayList<>();

        while (cursor.moveToNext()){
            if(codigobarra.equals(cursor.getString(cursor.getColumnIndex(codigobarra)))){
                CompraBEAN compraBEAN = new CompraBEAN();
                compraBEAN.setCodigobarra(cursor.getString(cursor.getColumnIndex("codigobarra")));
                compraBEAN.setLocalcompra(cursor.getString(cursor.getColumnIndex("localcompra")));
                compraBEAN.setDatacompra(cursor.getString(cursor.getColumnIndex("datacompra")));
                compraBEAN.setValor(cursor.getFloat(cursor.getColumnIndex("valor")));
                compraBEANs.add(compraBEAN);
            }
        }
        cursor.close();
        compraproduto.close();
        return compraBEANs;
    }
}
