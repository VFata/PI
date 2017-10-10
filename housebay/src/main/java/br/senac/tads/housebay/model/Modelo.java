/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.senac.tads.housebay.model;

/**
 *
 * @author Diego
 */
public class Modelo {
    private Long _id;
    private boolean ativo;

    public Modelo() {
        this._id = -1l;
        this.ativo = true;
    }
    
    public Modelo(Long _id, boolean ativo) {
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
    
    
    
}
