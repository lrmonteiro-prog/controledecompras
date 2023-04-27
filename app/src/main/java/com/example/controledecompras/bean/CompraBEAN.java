package com.example.controledecompras.bean;

import java.io.Serializable;

public class CompraBEAN implements Serializable {

    public Integer id;
    public String codigobarra;
    public String localcompra;
    public String datacompra;
    public Float valor;

    public CompraBEAN() {

    }

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

    public String getLocalcompra() {
        return localcompra;
    }

    public void setLocalcompra(String localcompra) {
        this.localcompra = localcompra;
    }

    public String getDatacompra() {
        return datacompra;
    }

    public void setDatacompra(String datacompra) {
        this.datacompra = datacompra;
    }

    public Float getValor() {
        return valor;
    }

    public void setValor(Float valor) {
        this.valor = valor;
    }

    public CompraBEAN(String localcompra) {
        this.localcompra = localcompra;
    }
}
