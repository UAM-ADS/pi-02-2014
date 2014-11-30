package toronto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import toronto.exceptions.*;
import toronto.Produto;
import toronto.Cliente;

public class FrenteCaixa {
    private static FrenteCaixa caixa = null;
    private static Connection conn;
    private static GerenciadorEstoque gerEstoque;
    
    private Pedido pedido;
    private Cliente cliente;
    
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
        gerEstoque = GerenciadorEstoque.gerenciador(conn);
        return caixa;
    }
    
    public void setCliente(Cliente cl) {
        cliente = cl;
    }

    public ArrayList RemoveProduto;
    public ArrayList GeraNotaFiscal;
    public ArrayList GeraPedido;
    
    private void geraPedido() {
        pedido = new Pedido(conn);
        pedido.data = new Date();
        pedido.valor = 0.0f;
        pedido.cliente = cliente;
    }
    
    public Produto verificaProduto(int id) throws SQLException, ProdutoInexistenteException, ProdutoForaDeEstoqueException {
        Produto produto;
        String sql = "SELECT * FROM produto WHERE cod_produto=?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {    // Produto encontrado no BD
            // Verifica se o produto está em estoque
            if (gerEstoque.quantidadeProduto(id) > 0) {
                produto = new Produto(conn);
                produto.codigo = rs.getInt("cod_produto");
                produto.nome = rs.getString("nome");
                produto.descricao = rs.getString("descricao");
                produto.preco = rs.getFloat("preco");
            } else {
                throw new ProdutoForaDeEstoqueException("Produto fora de estoque");
            }
        } else {    // Produto não encontrado
            throw new ProdutoInexistenteException("Produto não encontrado no banco de dados");
        }
        return produto;
    }
    
    public void adicionaProdutoPedido(Produto produto) {
        if (pedido == null) {
            geraPedido();
        }
        pedido.addicionaProduto(produto);
    }
    
    public ArrayList<Produto> getProdutosPedido() {
        return pedido.produtos;
    }
    
    public float getValorPedido() {
        return pedido.valor;
    }

    public Boolean fechaCompra() {
        pedido = null;
        cliente = null;
        return true;
    }

    public Boolean emiteNotaFiscal() {
        return null;
    }
    
    public void setConnection(Connection c) {
        conn = c;
    }

}
