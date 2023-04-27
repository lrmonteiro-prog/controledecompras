package com.example.controledecompras.conexao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.sql.Connection;
import java.sql.SQLException;

public class Conexao extends SQLiteOpenHelper {

    private static final String name = "compraproduto.bd";
    private static final int version = 7;


    public Conexao(@Nullable Context context) {
        super(context, name,null,version);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        String sql_produto = "create table produto(id integer primary key autoincrement," +
                "codigobarra varchar(25)unique, nomeproduto varchar(50), quantidade float," +
                "foto blob, localcompra varchar(50), comprar varchar(3),valor float," +
                "datacompra varchar(10))";

        String sql_compra = "create table compra(id integer primary key autoincrement," +
                " codigobarra varchar(25),localcompra varchar(50)," +
                "datacompra varchar(10), valor float)";

        String sql_validade = "create table validade(id integer primary key autoincrement," +
               " codigobarra varchar(25),nomeproduto varchar(50),datavalidade varchar(10))";

        db.execSQL(sql_produto);
        db.execSQL(sql_compra);
        db.execSQL(sql_validade);
    }

    // desconectar
    public static void DesconnectDb() {
        Connection conn = null;
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {}
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
