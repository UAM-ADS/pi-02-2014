/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package toronto.gui;

import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author mgaldieri
 */
public class ProdutoRow {
    private final SimpleIntegerProperty codigo;
    private final SimpleStringProperty nome;
    private final SimpleStringProperty descricao;
    private final SimpleIntegerProperty quantidade;
    private final SimpleFloatProperty preco;
    
    public ProdutoRow(int codigo, String nome, String descricao, int quantidade, float preco) {
        this.codigo = new SimpleIntegerProperty(codigo);
        this.nome = new SimpleStringProperty(nome);
        this.descricao = new SimpleStringProperty(descricao);
        this.quantidade = new SimpleIntegerProperty(quantidade);
        this.preco = new SimpleFloatProperty(preco);
    }
    
    public int getCodigo() {
        return this.codigo.get();
    }
    
    public void setCodigo(int codigo) {
        this.codigo.set(codigo);
    }
    
    public String getNome() {
        return this.nome.get();
    }
    
    public void setNome(String nome) {
        this.nome.set(nome);
    }
    
    public String getDescricao() {
        return this.descricao.get();
    }
    
    public void setDescricao(String descricao) {
        this.descricao.set(descricao);
    }
    
    public int getQuantidade() {
        return this.quantidade.get();
    }
    
    public void setQuantidade(int quantidade) {
        this.quantidade.set(quantidade);
    }
    
    public float getPreco() {
        return this.preco.get();
    }
    
    public void setPreco(float preco) {
        this.preco.set(preco);
    }
}
