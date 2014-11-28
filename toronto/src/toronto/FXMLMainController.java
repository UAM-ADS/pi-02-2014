/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package toronto;

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
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.StageStyle;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author mgaldieri
 */
public class FXMLMainController implements Initializable {
    @FXML
    private Button btnSair;
    @FXML
    private TextField vendaCodProdTextField;
    @FXML
    private TextField vendaQtdProdTextField;
    @FXML
    private Button vendaInserirProdBtn;
    @FXML
    private TableView<?> vendaProdTableView;
    @FXML
    private Button vendaFinalizarBtn;
    @FXML
    private TextArea vendaNFTextView;
    @FXML
    private TextField vendaCPFTextField;
    @FXML
    private Button vendaCPFBuscaBtn;
    @FXML
    private Label vendaNomeClienteLabel;
    @FXML
    private Label vendaTotalLabel;
    @FXML
    private TextField clienteCPFTextField;
    @FXML
    private Button clienteValidaCPFBtn;
    @FXML
    private TextField clienteNomeTextField;
    @FXML
    private TextField clienteEndTextField;
    @FXML
    private TextField clienteEndNumTextField;
    @FXML
    private TextField clienteBairroTextField;
    @FXML
    private TextField clienteCidadeTextField;
    @FXML
    private TextField clienteCEPTextField;
    @FXML
    private ChoiceBox<?> clienteUFChoiceBox;
    @FXML
    private TextField clienteEmailTextField;
    @FXML
    private TextField clienteTelTextField;
    @FXML
    private Button clienteSalvaBtn;
    @FXML
    private TextField prodNomeTextField;
    @FXML
    private TextField prodPrecoTextField;
    @FXML
    private TextArea prodDescTextArea;
    @FXML
    private Button prodSalvaBtn;
    @FXML
    private TextField estoqueBuscaTextField;
    @FXML
    private Button estoqueBuscaBtn;
    @FXML
    private TableView<?> estoqueTableView;
    @FXML
    private Button estoqueSalvaBtn;
    @FXML
    private TextField funcNomeTextField;
    @FXML
    private TextField funcLoginTextField;
    @FXML
    private PasswordField funcSenhaTextField;
    @FXML
    private TextField funcSalarioTextField;
    @FXML
    private PasswordField funcSenhaConfTextBox;
    @FXML
    private CheckBox funcAdminCheckBox;
    @FXML
    private Label funcSenhaAlertaLabel;
    @FXML
    private Button funcSalvaBtn;
    @FXML
    private Button adminGeraBtn;
    @FXML
    private TextField adminDataInicioTextField;
    @FXML
    private TextField adminDataFimTextField;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void sairApp(ActionEvent event) throws Exception {
        // Referência ao primaryStage
        Stage root = (Stage) btnSair.getScene().getWindow();
        // Carrega layout da caixa de diálogo
        Scene dialogScene = new Scene((Parent)FXMLLoader.load(getClass().getResource("FXMLMainSairModal.fxml")));
        // Inicia a cena da caixa de diálogo
        Stage dialog = new Stage();
        dialog.initModality(Modality.WINDOW_MODAL);
        dialog.initOwner(root);
        dialog.setScene(dialogScene);
        dialog.show();
        
        Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
        dialog.setX((primScreenBounds.getWidth() - dialog.getWidth()) / 2);
        dialog.setY((primScreenBounds.getHeight() - dialog.getHeight()) / 2);
    }

    @FXML
    private void vendaInsereProd(ActionEvent event) {
    }

    @FXML
    private void vendaFinaliza(ActionEvent event) {
    }

    @FXML
    private void vendaBuscaCPF(ActionEvent event) {
    }

    @FXML
    private void clienteValidaCPF(ActionEvent event) {
    }

    @FXML
    private void clienteSalva(ActionEvent event) {
    }

    @FXML
    private void prodSalva(ActionEvent event) {
    }

    @FXML
    private void estoqueBusca(ActionEvent event) {
    }

    @FXML
    private void estoqueSalva(ActionEvent event) {
    }

    @FXML
    private void funcSalva(ActionEvent event) {
    }

    @FXML
    private void adminGera(ActionEvent event) {
    }
    
}
