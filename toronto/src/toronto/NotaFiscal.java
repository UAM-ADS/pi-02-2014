package toronto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class NotaFiscal {

    public int numero;
    public Pedido pedido;
    public Float imposto;
    public Float valor;
    
    private Connection conn;

    public NotaFiscal(Connection c) {
        conn = c;
    }

    public void salva() throws SQLException {
        String sql = "INSERT INTO notafiscal (cod_pedido, impostos, valor) VALUES (?, ?, ?)";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, pedido.codigo);
        stmt.setFloat(2, imposto);
        stmt.setFloat(3, valor);
        stmt.executeUpdate();
        // Guarda o id do produto inserido
        ResultSet rsKeys = stmt.getGeneratedKeys();
        rsKeys.next();
        numero = rsKeys.getInt(1);
    }

}
