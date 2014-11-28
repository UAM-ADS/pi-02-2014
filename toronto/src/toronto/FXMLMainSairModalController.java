/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package toronto;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
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
public class FXMLMainSairModalController implements Initializable {
    @FXML
    private Button btnSairCancelar;
    @FXML
    private Button btnSairConfirma;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void sairCancelaApp(ActionEvent event) {
        Stage dialogStage= (Stage) btnSairCancelar.getScene().getWindow();
        dialogStage.close();
    }

    @FXML
    private void sairConfirmaApp(ActionEvent event) {
        Platform.exit();
    }

    
}
