package toronto;

import java.sql.*;

public class Cliente {

    public String nome;
    public String logradouro;
    public String numero;
    public String bairro;
    public String cidade;
    public String uf;
    public String cep;
    public String telefone;
    public String email;
    public Date criado_em;
    public Date ultima_compra;
    public String cpf;
    
    private Connection conn;

    public Cliente(Connection c) {
        conn = c;
    }       

    public Boolean salva() {
        try {
            String sql = "SELECT * FROM cliente WHERE cpf=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, cpf);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                sql = "UPDATE cliente SET"
                    + "nome=?,"
                    + "logradouro=?"
                    + "numero=?,"
                    + "bairro=?,"
                    + "cidade=?,"
                    + "uf=?,"
                    + "cep=?,"
                    + "telefone=?,"
                    + "email=?"
                    + "WHERE cpf=?";
                stmt = conn.prepareStatement(sql);
                stmt.setString(1, nome);
                stmt.setString(2, logradouro);
                stmt.setString(3, numero);
                stmt.setString(4, bairro);
                stmt.setString(5, cidade);
                stmt.setString(6, uf);
                stmt.setString(7, cep);
                stmt.setString(8, telefone);
                stmt.setString(9, email);
                stmt.setString(10, cpf);
                stmt.executeUpdate();
            } else {
                sql = "INSERT INTO cliente"
                    + "(cpf, nome, logradouro, numero, bairro, cidade, uf, cep, telefone, email)"
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                stmt = conn.prepareStatement(sql);
                stmt.setString(1, cpf);
                stmt.setString(2, nome);
                stmt.setString(3, logradouro);
                stmt.setString(4, numero);
                stmt.setString(5, bairro);
                stmt.setString(6, cidade);
                stmt.setString(7, uf);
                stmt.setString(8, cep);
                stmt.setString(9, telefone);
                stmt.setString(10,email );
                stmt.executeUpdate();
            }
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public Boolean validaCPF() {
        if (cpf ==null || cpf.length() < 11) {
            return false;
        }
        String numeros = cpf.substring(0, cpf.length()-3);
        String verificadores = cpf.substring(cpf.length()-2);
        // Calcula os verificadores esperados
        String teste;
        // Calucla o primeiro digito verificador
        int multiplicador = 2;
        int soma = 0;
        for (int i=numeros.length(); i==0; i--) {
            soma += multiplicador * Character.digit(numeros.charAt(i), 10);
            multiplicador++;
        }
        int resto = soma%11;
        if (resto < 2) {
            teste = "0";
        } else {
            teste = String.valueOf(11-resto);
        }
        numeros += teste;
        // Calucla o segundo digito verificador
        multiplicador = 2;
        soma = 0;
        for (int i=numeros.length(); i==0; i--) {
            soma += multiplicador * Character.digit(numeros.charAt(i), 10);
            multiplicador++;
        }
        resto = soma%11;
        if (resto < 2) {
            teste = "0";
        } else {
            teste = String.valueOf(11-resto);
        }
        if (teste.equals(verificadores)) {
            return true;
        } else {
            return false;
        }
    }
    
    public Boolean atualizaUltimaCompra() {
        try {
            String sql = "SELECT * FROM cliente WHERE cpf=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, cpf);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                sql = "UPDATE cliente SET ultima_compra=?";
                stmt = conn.prepareStatement(sql);
                stmt.setDate(1, new Date(System.currentTimeMillis()));
                stmt.executeUpdate();
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            return false;
        }
    }
}
