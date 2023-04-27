package com.example.controledecompras.bean;

import java.io.Serializable;

public class ProdutoBEAN implements Serializable {

    public Integer id;
    public String codigobarra;
    public String nomeproduto;
    public Float quantidade;
    public byte[] foto;
    public String localcompra;
    public String comprar;
    public Float valor;
    public String datacompra;

    public String getDatacompra() {
        return datacompra;
    }

    public void setDatacompra(String datacompra) {
        this.datacompra = datacompra;
    }

    public Float getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Float quantidade) {
        this.quantidade = quantidade;
    }

    public byte[] getFoto() {
        return foto;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }

    public String getLocalcompra() {
        return localcompra;
    }

    public void setLocalcompra(String localcompra) {
        this.localcompra = localcompra;
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

    public String getNomeproduto() {
        return nomeproduto;
    }

    public void setNomeproduto(String nomeproduto) {
        this.nomeproduto = nomeproduto;
    }

    public String getComprar() {
        return comprar;
    }

    public void setComprar(String comprar) {
        this.comprar = comprar;
    }

    public Float getValor() {
        return valor;
    }

    public void setValor(Float valor) {
        this.valor = valor;
    }

    public ProdutoBEAN() {
        this.nomeproduto = nomeproduto;
    }
}
