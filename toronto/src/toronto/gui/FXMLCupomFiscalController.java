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
import javafx.scene.text.Font;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

/**
 * FXML Controller class
 *
 * @author mgaldieri
 */
public class FXMLCupomFiscalController implements Initializable {
    @FXML
    private WebView nfWebView;
    @FXML
    private Button nfImprimeBtn;
    
    private WebEngine webEngine;
    private Font font;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void nfImprime(ActionEvent event) {
    }
    
    public void initParams(String html) {
        webEngine = nfWebView.getEngine();
        webEngine.loadContent(html);
    }
    
}
