package toronto;

import java.sql.*;
import java.util.ArrayList;
import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

public class Pedido {

    public int codigo;
    public Date data;
    public ArrayList<Produto> produtos;
    public Float valor;
    public Cliente cliente;
    public Map<Integer, Integer> quantidades;

    private Connection conn;
    
    public Pedido(Connection c) {
        conn = c;
        produtos = new ArrayList<>();
        quantidades = new HashMap<>();
    }
    
    public void addicionaProduto(Produto produto, int quantidade) {
        produtos.add(produto);
        valor += quantidade * produto.preco;
    }

    public Boolean salva() {
        try {
            String sql = "INSERT INTO pedido (data, valor, cliente_id) VALUES (?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setDate(1, new Date(System.currentTimeMillis()));
            stmt.setFloat(2, valor);
            stmt.setString(3, cliente!=null?cliente.cpf:null);
            stmt.executeUpdate();
            // Guarda o id do pedido inserido
            ResultSet rsKeys = stmt.getGeneratedKeys();
            rsKeys.next();
            codigo = rsKeys.getInt(1);
            for (Produto p : produtos) {
                sql = "INSERT INTO produtos (cod_pedido, cod_produto, quantidade) VALUES (?, ?, ?)";
                stmt = conn.prepareStatement(sql);
                stmt.setInt(1, codigo);
                stmt.setInt(2, p.codigo);
                stmt.setInt(3, quantidades.containsKey(p)?quantidades.get(p):0);
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            return false;
        }
        return null;
    }

}
