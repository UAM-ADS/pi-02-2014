/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package toronto.gui;

import java.io.IOException;
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
import javafx.geometry.Rectangle2D;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Tab;

import java.sql.*;
import toronto.utils.Constants;
import toronto.utils.Erros.ErroMsg;

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
    private Tab clienteTab;
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
    
    private static Stage root;
    private static Connection conn;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Referência ao primaryStage
        root = (Stage) btnSair.getScene().getWindow();
        String erroMsg = null;
        try {
            // Abre conexão com banco de dados
            Class.forName(Constants.JDBC_DRIVER);
            conn = DriverManager.getConnection(Constants.DB_URL);
        } catch (ClassNotFoundException e) {
            // Define a mensagem de erro
            erroMsg = ErroMsg.msg(ErroMsg.FATAL_CLASSE_INEXISTENTE);
        } catch (SQLException e) {
            // Define a mensagem de erro
            erroMsg = ErroMsg.msg(ErroMsg.FATAL_ACESSO_SQL);
        } finally {
            if (erroMsg != null) {
                // Mostra a caixa de erro fatal
                try {
                    FXMLLoader loader = FXMLLoader.load(getClass().getResource("FXMLErroFatalModal.fxml"));
                    Scene fatalScene = new Scene((Parent)loader.load());
                    Stage fatal = new Stage();
                    fatal.initModality(Modality.WINDOW_MODAL);
                    fatal.initOwner(root);
                    fatal.setScene(fatalScene);
                    FXMLErroFatalModalController fatalController = loader.<FXMLErroFatalModalController>getController();
                    fatalController.setMensagem(erroMsg);
                    fatal.show();
                } catch (IOException ioE) {
                    System.out.println("Não foi possível encontrar o arquivo de definições da modal de erro fatal...");
                }
            }
        }
    }
    
    /**
     * Apresenta uma caixa de diálogo confirmando o encerramento do aplicativo
     * 
     * @param event
     * @throws Exception 
     */
    @FXML
    private void sairApp(ActionEvent event) throws Exception {
        // Carrega layout da caixa de diálogo
        Scene dialogScene = new Scene((Parent)FXMLLoader.load(getClass().getResource("FXMLMainSairModal.fxml")));
        // Inicia a cena da caixa de diálogo
        Stage dialog = new Stage();
        dialog.initModality(Modality.WINDOW_MODAL);
        dialog.initOwner(root);
        dialog.setScene(dialogScene);
        dialog.show();
        
        // Centraliza a janela na tela
        Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
        dialog.setX((primScreenBounds.getWidth() - dialog.getWidth()) / 2);
        dialog.setY((primScreenBounds.getHeight() - dialog.getHeight()) / 2);
    }

    /**
     * Insere um produto no banco de dados
     * 
     * @param event 
     */
    @FXML
    private void vendaInsereProd(ActionEvent event) {
    }

    /**
     * Finaliza a venda, gravando os dados da mesma no banco de dados e
     * e imprimindo o cupom fiscal
     * 
     * @param event 
     */
    @FXML
    private void vendaFinaliza(ActionEvent event) {
    }

    /**
     * Realiza uma busca no banco de dados por um CPF cadastrado
     * 
     * @param event
     * @throws SQLException 
     */
    @FXML
    private void vendaBuscaCPF(ActionEvent event) throws SQLException {
        // Limpa o campo de CPF, retirando pontos e traços
        String pattern = "[\\.-]";
        String cpf = clienteCPFTextField.getText().replaceAll(pattern, "");
        
        // Executa a busca no BD pelo CPF
        String sql = "SELECT * FROM cliente WHERE cpf=?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, cpf);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {    // Se houver o CPF no cadastro
            vendaCPFTextField.setText("");
            vendaNomeClienteLabel.setText(rs.getString("nome"));
        } else {            // Se o CPF não existir no cadastro
            
        }
    }

    /**
     * Valida algoritmicamente o CPF informado
     * 
     * @param event 
     */
    @FXML
    private void clienteValidaCPF(ActionEvent event) {
    }

    /**
     * Salva os dados do cliente no banco de dados
     * 
     * @param event 
     */
    @FXML
    private void clienteSalva(ActionEvent event) {
    }

    /**
     * Salva os dados do produto no banco de dados
     * 
     * @param event 
     */
    @FXML
    private void prodSalva(ActionEvent event) {
    }

    /**
     * Busca produtos no banco de dados que conferem com os dados informados
     * no campo de busca
     * 
     * @param event 
     */
    @FXML
    private void estoqueBusca(ActionEvent event) {
    }

    /**
     * Salva as alterações feitas nos dados de estoque no banco de dados
     * 
     * @param event 
     */
    @FXML
    private void estoqueSalva(ActionEvent event) {
    }

    /**
     * Salva os dados do novo funcionário no banco de dados
     * 
     * @param event 
     */
    @FXML
    private void funcSalva(ActionEvent event) {
    }

    /**
     * Gera o relatório gerencial
     * 
     * @param event 
     */
    @FXML
    private void adminGera(ActionEvent event) {
    }
    
}
