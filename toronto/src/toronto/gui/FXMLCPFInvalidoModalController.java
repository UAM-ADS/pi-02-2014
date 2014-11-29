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
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author mgaldieri
 */
public class FXMLCPFInvalidoModalController implements Initializable {
    @FXML
    private Button cpfInvalidoOkBtn;
    private Stage dialogStage;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    
    public void initParams() {
        dialogStage = (Stage) cpfInvalidoOkBtn.getScene().getWindow();
    }

    @FXML
    private void cpfInvalidoOk(ActionEvent event) {
        dialogStage.close();
    }
    
}
