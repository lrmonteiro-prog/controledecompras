package com.example.controledecompras.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.controledecompras.bean.ValidadeBEAN;
import com.example.controledecompras.conexao.Conexao;

import java.util.ArrayList;
import java.util.List;

public class ValidadeDAO {

    private Conexao conexao;
    private SQLiteDatabase compraproduto;

    public ValidadeDAO(Context context){
        conexao = new Conexao(context);
        compraproduto = conexao.getWritableDatabase();
    }

    public long inserir (ValidadeBEAN validadeBEAN){
        ContentValues values = new ContentValues();
        values.put("codigobarra", validadeBEAN.getCodigobarra());
        values.put("nomeproduto", validadeBEAN.getNomeproduto());
        values.put("datavalidade", validadeBEAN.getDatavalidade());
        return compraproduto.insert("validade",null, values);
    }

    public List<ValidadeBEAN> obterTodos(){
        List<ValidadeBEAN> validadeBEAN = new ArrayList<>();
        Cursor cursor = compraproduto.query("validade", new String[]{"id","codigobarra",
                       "nomeproduto","datavalidade"},null,null,null,
                null,null);
        while (cursor.moveToNext()){
            ValidadeBEAN e = new ValidadeBEAN();
            e.setId(cursor.getInt(0));
            e.setCodigobarra(cursor.getString(1));
            e.setNomeproduto(cursor.getString(2));
            e.setDatavalidade(cursor.getString(3));
            validadeBEAN.add(e);
        }
        cursor.close();
        return validadeBEAN;
    }

    public void excluir(ValidadeBEAN validadeBEAN){
        compraproduto.delete("validade","id=?",new String[]{validadeBEAN.getId().toString()});
    }

    public void atualizar(ValidadeBEAN validadeBEAN){
        ContentValues values = new ContentValues();
        values.put("codigobarra", validadeBEAN.getCodigobarra());
        values.put("nomeproduto", validadeBEAN.getNomeproduto());
        values.put("datavalidade", validadeBEAN.getDatavalidade());
        compraproduto.update("validade",values,"id=?",new String[]{validadeBEAN.getId().toString()});
    }

    private List procurar(String codigobarra) {

        String sqlProcuraCompra = "SELECT * FROM estoque WHERE nomeproduto = "+"'"+codigobarra+"'";

        Cursor cursor = compraproduto.rawQuery(sqlProcuraCompra,null);

        List<ValidadeBEAN> validadeBEANS = new ArrayList<>();

        while (cursor.moveToNext()){
            if(codigobarra.equals(cursor.getString(cursor.getColumnIndex(codigobarra)))){
                ValidadeBEAN validadeBEAN = new ValidadeBEAN();
                validadeBEAN.setCodigobarra(cursor.getString(cursor.getColumnIndex("codigobarra")));
                validadeBEAN.setNomeproduto(cursor.getString(cursor.getColumnIndex("nomeproduto")));
                validadeBEAN.setDatavalidade(cursor.getString(cursor.getColumnIndex("datavalidade")));
                validadeBEANS.add(validadeBEAN);
            }
        }
        cursor.close();
        return validadeBEANS;
    }
}
