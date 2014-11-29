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

import toronto.utils.Constants.TabIndex;

/**
 * FXML Controller class
 *
 * @author mgaldieri
 */
public class FXMLCPFInexistenteModalController implements Initializable {
    @FXML
    private Button cpfInexCancelaBtn;
    @FXML
    private Button cpfInexNovoBtn;
    
    private FXMLMainController mainController;
    private Stage dialogStage;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }
    
    public void initParams() {
        dialogStage = (Stage) cpfInexNovoBtn.getScene().getWindow();
    }

    @FXML
    private void cpfInexCancela(ActionEvent event) {
        dialogStage.close();
    }

    @FXML
    private void cpfInexNovo(ActionEvent event) {
        mainController.selecionaTab(TabIndex.CLIENTE.getIndex());
        dialogStage.close();
    }
    
    public void setMainController(FXMLMainController controller) {
        mainController = controller;
    }
    
}
