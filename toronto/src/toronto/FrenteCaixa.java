package toronto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Date;
import java.util.HashMap;
import java.util.Map;
import toronto.exceptions.*;
import toronto.Produto;
import toronto.Cliente;
import toronto.utils.Constants;

public class FrenteCaixa {
    private static FrenteCaixa caixa = null;
    private static Connection conn;
    private static GerenciadorEstoque gerEstoque;
    
    private Pedido pedido;
    private Cliente cliente;
//    private Map<Integer, Integer> quantidades;
    
    private FrenteCaixa() {
//        quantidades = new HashMap<>();
    }
    
    public static FrenteCaixa getCaixa(Connection c) {
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
        pedido.data = new Date(System.currentTimeMillis());
        pedido.valor = 0.0f;
        pedido.cliente = cliente;
    }
    
    public Produto verificaProduto(int id, int quantidade) throws SQLException, ProdutoInexistenteException, ProdutoForaDeEstoqueException {
        if (pedido == null) {
            geraPedido();
        }
        Produto produto;
        String sql = "SELECT * FROM produto WHERE cod_produto=?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {    // Produto encontrado no BD
            // Verifica se o produto está em estoque
            int qtdTotal = (pedido.quantidades.containsKey(id)?pedido.quantidades.get(id):0) + quantidade;
            if (gerEstoque.quantidadeProduto(id) >= qtdTotal) {
                produto = new Produto(conn);
                produto.codigo = rs.getInt("cod_produto");
                produto.nome = rs.getString("nome");
                produto.descricao = rs.getString("descricao");
                produto.preco = rs.getFloat("preco");
                pedido.quantidades.put(id, (pedido.quantidades.containsKey(id)?pedido.quantidades.get(id):0)+quantidade);
            } else {
                throw new ProdutoForaDeEstoqueException("Produto fora de estoque");
            }
        } else {    // Produto não encontrado
            throw new ProdutoInexistenteException("Produto não encontrado no banco de dados");
        }
        return produto;
    }
    
    public void adicionaProdutoPedido(Produto produto, int quantidade) {
        if (pedido == null) {
            geraPedido();
        }
        pedido.addicionaProduto(produto, quantidade);
    }
    
    public ArrayList<Produto> getProdutosPedido() {
        return pedido.produtos;
    }
    
    public float getValorPedido() {
        return pedido.valor;
    }

    public NotaFiscal fechaCompra() {
        pedido.salva();
        gerEstoque.consolidaEstoque(pedido);
        return emiteNotaFiscal();
    }

    private NotaFiscal emiteNotaFiscal() {
        NotaFiscal nf = new NotaFiscal(conn);
        nf.pedido = pedido;
        nf.valor = pedido.valor;
        nf.imposto = Constants.IMPOSTOS;
        try {
            nf.salva();
        } catch (SQLException e) {
            nf = null;
        }
        pedido = null;
        cliente = null;
        return nf;
    }
    
    public void setConnection(Connection c) {
        conn = c;
    }

}
