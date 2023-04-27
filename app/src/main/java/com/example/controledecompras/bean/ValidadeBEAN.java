package com.example.controledecompras.bean;

import java.io.Serializable;

public class ValidadeBEAN implements Serializable {

    public Integer id;
    public String codigobarra;
    public String nomeproduto;
    public String datavalidade;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCodigobarra() {
        return codigobarra;
    }

    public void setCodigobarra(String codigobarra) {
        this.codigobarra = codigobarra;
    }

    public String getNomeproduto() {
        return nomeproduto;
    }

    public void setNomeproduto(String nomeproduto) {
        this.nomeproduto = nomeproduto;
    }

    public String getDatavalidade() {
        return datavalidade;
    }

    public void setDatavalidade(String datavalidade) {
        this.datavalidade = datavalidade;
    }
}
