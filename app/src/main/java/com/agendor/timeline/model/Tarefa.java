package com.agendor.timeline.model;

import android.view.View;
import android.widget.ImageButton;

import java.util.Date;

public class Tarefa {

    private Integer id;
    private String cliente;
    private String descricao;
    private String data;
    private Integer idTipoTarefa;

    public Tarefa(Integer id, String cliente, String descricao, String data, Integer idTipoTarefa){
        this.id = id;
        this.cliente = cliente;
        this.descricao = descricao;
        this.data = data;
        this.idTipoTarefa = idTipoTarefa;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getCliente() {
        return cliente;
    }

    public String setDescricao() {
        return descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    public String setData() {
        return data;
    }

    public String getData() {
        return data;
    }

    public Integer setIdTipoTarefa() {
        return idTipoTarefa;
    }

    public Integer getIdTipoTarefa() {
        return idTipoTarefa;
    }

}

