package toronto;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;

public class Pedido {

    public int codigo;
    public Date data;
    public ArrayList<Produto> produtos;
    public Float valor;
    public Cliente cliente;

    private Connection conn;
    
    public Pedido(Connection c) {
        conn = c;
    }
    
    public void addicionaProduto(Produto produto) {
        produtos.add(produto);
        valor += produto.preco;
    }

    public Boolean salva() {
        return null;
    }

}
