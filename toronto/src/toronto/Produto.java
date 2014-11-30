package toronto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Produto {

    public int codigo;
    public String nome;
    public Float preco;
    public String descricao;
    
    private Connection conn;

    public Produto(Connection c) {
        conn = c;
    }

    public Boolean salva() {
        try {
            String sql = "INSERT INTO produto (nome, descricao, preco) VALUES (?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, nome);
            stmt.setString(2, descricao);
            stmt.setFloat(3, preco);
            stmt.executeUpdate();
        } catch (SQLException e) {
            return false;
        }
        return true;
    }

}
