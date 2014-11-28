package toronto;

import java.sql.*;
import java.util.Scanner;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javax.annotation.Resources;

import toronto.Constants.Versao;
import toronto.Constants;
import toronto.Utils;

/**
 *
 * EDU01072 - Projeto Interdisciplinar II (ON.0) - 201420.04583
 * Sistema Toronto de PDV
 * @version 0.1.0
 * 
 * @custom.equipe Canadá
 * @author Mauricio de Camargo Galdieri
 * @custom.ra 20458437
 * @author Lorenna Borges
 * @custom.ra XXXXXXXX
 * @author Andressa Pinheiro Guerra
 * @custom.ra XXXXXXXX
 * @author Thiago Ribeiro Baptista
 * @custom.ra XXXXXXXX
 */
public class Toronto extends Application {
    // Credenciais do usuário inicial
    private static final String name = "Administrador";
    private static final String user = "admin";
    private static final String pass = "admin123";
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        // Inicializa o banco de dados local
        Class.forName(Constants.JDBC_DRIVER);
        Connection conn = DriverManager.getConnection(Constants.DB_URL);
        
        // Cria as tabelas
        String sql = new Scanner(this.getClass().getResourceAsStream("initdb.sql"), "UTF-8").useDelimiter("\\A").next();
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.execute();
        
        // Verifica se o usuário inicial existe
        sql = "SELECT * FROM usuario WHERE login=?";
        stmt = conn.prepareStatement(sql);
        stmt.setString(1, user);
        // Se o usuário não existe
        if (!stmt.executeQuery().next()) {
            // Cria o usuário inicial
            sql = "INSERT INTO usuario (nome, login, senha, admin) VALUES (?, ?, ?, ?)";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, name);
            stmt.setString(2, user);
            stmt.setString(3, Utils.md5String(pass));
            stmt.setBoolean(4, true);
            stmt.executeUpdate();
        }
        
        conn.close();
        
        Parent root = FXMLLoader.load(getClass().getResource("FXMLLogin.fxml"));
        
        Scene scene = new Scene(root);
        
        primaryStage.setTitle("Systema Toronto v"+Versao.full());
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
