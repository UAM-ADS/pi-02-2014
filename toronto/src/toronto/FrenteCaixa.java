package toronto;

import java.sql.*;
import java.util.ArrayList;

public class FrenteCaixa {
    private static FrenteCaixa caixa = null;
    private static Connection conn;
    private static GerenciadorEstoque gerEstoque;
    
    private Pedido pedido;
    
    private FrenteCaixa() {
    }
    
    public static FrenteCaixa getCaixa(Connection c) {
        System.out.print(c);
        if (c == null) {
            throw new IllegalArgumentException("Conexão com banco de dados inválida.");
        }
        if (caixa == null) {
            caixa = new FrenteCaixa();
        }
        conn = c;
        gerEstoque = GerenciadorEstoque.gerenciador;
        return caixa;
    }

    public ArrayList RemoveProduto;
    public ArrayList GeraNotaFiscal;
    public ArrayList GeraPedido;
    
    public void geraPedido() {
        pedido = new Pedido(conn);
    }
    
    public void adicionaProdutoPedido(Produto produto) {
        
    }

    public Boolean fechaCompra() {
        pedido = null;
        return null;
    }

    public Boolean emiteNotaFiscal() {
        return null;
    }
    
    public void setConnection(Connection c) {
        conn = c;
    }

}
