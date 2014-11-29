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
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author mgaldieri
 */
public class FXMLAlertaModalController implements Initializable {
    @FXML
    private Button cpfInvalidoOkBtn;
    private Stage dialogStage;
    @FXML
    private Label avisoMsgLabel;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    
    public void initParams(String msg) {
        dialogStage = (Stage) cpfInvalidoOkBtn.getScene().getWindow();
        avisoMsgLabel.setText(msg);
    }

    @FXML
    private void cpfInvalidoOk(ActionEvent event) {
        dialogStage.close();
    }
    
}
