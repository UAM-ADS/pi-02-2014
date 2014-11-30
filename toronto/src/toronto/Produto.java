package toronto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Produto {

    public int codigo;
    public String nome;
    public float preco;
    public String descricao;
    
    private Connection conn;

    public Produto(Connection c) {
        conn = c;
    }
    
    public Boolean atualiza() {
        try {
            String sql = "UPDATE produto SET"
                       + "nome=?,"
                       + "descricao=?"
                       + "preco=?"
                       + "WHERE cod_produto=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, nome);
            stmt.setString(2, descricao);
            stmt.setFloat(3, preco);
            stmt.setInt(4, codigo);
            stmt.executeUpdate();
        } catch (SQLException e) {
            return false;
        }
        return true;
    }

    public Boolean salva() {
        try {
            // Insere produto na tabela de produtos
            String sql = "INSERT INTO produto (nome, descricao, preco) VALUES (?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, nome);
            stmt.setString(2, descricao);
            stmt.setFloat(3, preco);
            stmt.executeUpdate();
            // Guarda o id do produto inserido
            ResultSet rsKeys = stmt.getGeneratedKeys();
            rsKeys.next();
            int prodId = rsKeys.getInt(1);
            // Insere o produto na tabela de estoque com zero itens
            sql = "INSERT INTO estoque (cod_produto, quantidade) VALUES (?, 0)";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, prodId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            return false;
        }
        return true;
    }

}
