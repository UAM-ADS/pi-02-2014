/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package toronto.gui;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.Screen;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.sql.*;
import toronto.utils.Constants;
import toronto.utils.Crypto;

/**
 * FXML Controller class
 *
 * @author mgaldieri
 */
public class FXMLLoginController implements Initializable {
    @FXML
    private TextField loginEmail;
    @FXML
    private PasswordField loginSenha;
    @FXML
    private Button loginOk;
    @FXML
    private Label loginErro;


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void verificaLogin(ActionEvent event) throws Exception {
        Class.forName(Constants.JDBC_DRIVER);
        Connection conn = DriverManager.getConnection(Constants.DB_URL);
        
        String sql = "SELECT * FROM usuario WHERE login=?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, loginEmail.getText());
        ResultSet rs = stmt.executeQuery();
        conn.close();
        if (!rs.next() || !rs.getString("senha").equals(Crypto.md5String(loginSenha.getText()))) {
            loginErro.setVisible(true);
        } else {
            Scene scene = new Scene((Parent)FXMLLoader.load(getClass().getResource("FXMLMain.fxml")));
            Stage stage = (Stage) loginOk.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
            
            Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
            stage.setX((primScreenBounds.getWidth() - stage.getWidth()) / 2);
            stage.setY((primScreenBounds.getHeight() - stage.getHeight()) / 2);
        }
    }
    
}
