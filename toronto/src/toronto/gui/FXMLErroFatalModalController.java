/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package toronto.gui;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

/**
 * FXML Controller class
 *
 * @author mgaldieri
 */
public class FXMLErroFatalModalController implements Initializable {
    @FXML
    private Button fatalSairBtn;
    @FXML
    private Label fatalMsgTextField;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    
    public void setMensagem(String msg) {
        this.fatalMsgTextField.setText(msg);
    }

    @FXML
    private void fatalSair(ActionEvent event) {
        Platform.exit();
    }
    
}
