package toronto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import toronto.exceptions.ProdutoInexistenteException;

public class GerenciadorEstoque {
    private static GerenciadorEstoque gerenciador;
    private static Connection conn;
    
    private GerenciadorEstoque(Connection c) {
        this.conn = c;
    }
    
    public static GerenciadorEstoque gerenciador(Connection c) {
        if (gerenciador == null) {
            gerenciador = new GerenciadorEstoque(c);
        }
        return gerenciador;
    }

    public Boolean acrescentaProduto(Produto produto) {
        return null;
    }

    public Boolean removeProduto(Produto produto) {
        return null;
    }
    
    public void atualizaProdutoEstoque(int cod_produto, int quantidade) throws SQLException {
        System.out.println("Quantidade: "+quantidade);
        String sql = "UPDATE estoque SET quantidade=? WHERE cod_produto=?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, quantidade);
        stmt.setInt(2, cod_produto);
        stmt.executeUpdate();
    }

    public int quantidadeProduto(int id) throws SQLException, ProdutoInexistenteException {
        String sql = "SELECT quantidade FROM estoque WHERE cod_produto=?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            return rs.getInt("quantidade");
        } else {
            throw new ProdutoInexistenteException("Produto não encontrado no banco de dados");
        }
    }

}
