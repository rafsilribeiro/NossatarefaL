package com.rafael.nossatarefal;

public class TarefaModel {
    private int id;
    private String descricao;

    public TarefaModel(int id, String descricao) {
        this.id = id;
        this.descricao = descricao;
    }

    public int getId() {
        return id;
    }

    public String getDescricao() {
        return descricao;
    }
}
