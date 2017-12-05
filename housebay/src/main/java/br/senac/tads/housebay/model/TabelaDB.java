/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.senac.tads.housebay.model;

import java.util.GregorianCalendar;

/**
 *
 * @author Diego
 */
public abstract class TabelaDB {
    public static final String ID = "id";
    public final static String ATIVO = "ativo";
    
    private Long _id;
    private boolean ativo;
    private GregorianCalendar criado;
    private GregorianCalendar modificado;

    public TabelaDB() {
        this._id = -1l;
        this.ativo = true;
    }
    
    public TabelaDB(Long _id, boolean ativo) {
        this._id = _id;
        this.ativo = ativo;
    }

    public Long getId() {
        return _id;
    }
    public void setId(Long _id) {
        this._id = _id;
    }

    public boolean isAtivo() {
        return ativo;
    }
    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public GregorianCalendar getCriado() {
        return criado;
    }
    public void setCriado(GregorianCalendar criado) {
        this.criado = criado;
    }
    public void setCriado(long timeInMillis) {
        this.criado = new GregorianCalendar();
        this.criado.setTimeInMillis(timeInMillis);
    }

    public GregorianCalendar getModificado() {
        return modificado;
    }
    public void setModificado(GregorianCalendar modificado) {
        this.modificado = modificado;
    }
    public void setModificado(long timeInMillis) {
        this.modificado = new GregorianCalendar();
        this.modificado.setTimeInMillis(timeInMillis);
    }
    
}
