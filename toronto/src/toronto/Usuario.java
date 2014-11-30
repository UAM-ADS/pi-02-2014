package toronto;

import java.util.ArrayList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import toronto.utils.Crypto;

public class Usuario {

    public String nome;
    public String login;
    public String senha;
    public Boolean admin;
    public Float salario;

    public ArrayList CadastraProduto;
    public ArrayList CadastraCliente;
    public ArrayList AcrescentaProduto;
    public ArrayList CriaPedido;
    public ArrayList CadastraFuncionário;
    public ArrayList ImprimeRelatório;
    
    private Connection conn;

    public Usuario(Connection c) {
        conn = c;
    }

    public Boolean salva() {
        try {
            String sql = "INSERT INTO usuario (nome, login, senha, admin, salario)"
                    + "VALUES (?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, nome);
            stmt.setString(2, login);
            stmt.setString(3, Crypto.md5String(senha));
            stmt.setBoolean(4, admin);
            stmt.setFloat(5, salario);
            stmt.executeUpdate();
        } catch (SQLException e) {
            return false;
        }
        return true;
    }

    public Boolean autentica() {
        return null;
    }

    public Boolean sair() {
        return null;
    }

}
