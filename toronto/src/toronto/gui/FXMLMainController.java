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
import java.text.ParseException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.control.ComboBox;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.TabPane;
import javax.swing.text.MaskFormatter;
import toronto.utils.Constants;
import toronto.utils.Constants.Versao;
import toronto.utils.Constants.TabIndex;
import toronto.utils.Erros.ErroMsg;
import toronto.Usuario;
import toronto.Cliente;
import toronto.FrenteCaixa;
import toronto.GerenciadorEstoque;
import toronto.GerenciadorFinanceiro;

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
    private TabPane mainTabPane;
    @FXML
    private Tab clienteTab;
    @FXML
    private TextField clienteCPFTextField;
    @FXML
    private Button clienteValidaCPFBtn;
    @FXML
    private Label clienteCPFInvalidoLabel;
    @FXML
    private Label clienteCPFOkLabel;
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
    private ComboBox<String> clienteUFComboBox;
    @FXML
    private TextField clienteCEPTextField;
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
    @FXML
    private Label headerVersaoLabel;
    @FXML
    private Label headerOlaLabel;
    
    private static Stage root;
    private static Connection conn;
    private static Usuario user;
    private static Cliente clienteAtual;
    private static FrenteCaixa caixa;
    private static GerenciadorEstoque gerEstoque;
    private static GerenciadorFinanceiro gerFinanceiro;
    

    /**
     * Inicializa a classe controladora.
     * Inicializa variáveis que não dependem de inicialização prévia.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Referência aos singletons gerenciadores
        gerEstoque = GerenciadorEstoque.gerenciador;
        gerFinanceiro = GerenciadorFinanceiro.gerenciador;
    }
    
    public void initParams(Usuario u) {
        // Referência ao usuário logado no aplicativo
        if (user == null) {
            user = u;
        }
        
        // Referência ao primaryStage
        if (root == null) {
            root = (Stage) btnSair.getScene().getWindow();
        }
        
        // Se não houver uma conexão com o BD aberta...
        if (conn == null) {
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
        
        // Atualiza o nome do usuário logado
        headerOlaLabel.setText("Olá, "+user.nome+"!");
        
        // Atualiza o número da versão
        headerVersaoLabel.setText("versão "+Versao.full());
        
        // Atualiza o nome do cliente
        if (clienteAtual == null) {
            vendaNomeClienteLabel.setText("");
        } else {
            vendaNomeClienteLabel.setText(clienteAtual.nome);
        }
        
        // Insere a lista de estados
        if (clienteUFComboBox.getItems().size() == 0) {
            ObservableList obList = FXCollections.observableList(Constants.listaUF);
            clienteUFComboBox.getItems().clear();
            clienteUFComboBox.setItems(obList);
        }
        
        // Inicia a frente de caixa
        if (caixa == null) {
            caixa = FrenteCaixa.getCaixa(conn);
        }
        
        // Zera o valor total
        atualizaTotal(0.0f);
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
        dialog.centerOnScreen();
        dialog.show();
    }

    /**
     * Insere um produto no pedido
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
        clienteAtual = null;
    }

    /**
     * Realiza uma busca no banco de dados por um CPF cadastrado
     * 
     * @param event
     * @throws SQLException 
     */
    @FXML
    private void vendaBuscaCPF(ActionEvent event) throws SQLException, IOException {
        // Limpa o campo de CPF, retirando pontos e traços
        String pattern = "[\\.-]";
        String cpf = vendaCPFTextField.getText().replaceAll(pattern, "");
        
        // Executa a busca no BD pelo CPF
        String sql = "SELECT * FROM cliente WHERE cpf=?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, cpf);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {    // Se houver o CPF no cadastro
            vendaCPFTextField.setText("");
            vendaNomeClienteLabel.setText(rs.getString("nome"));
        } else {            // Se o CPF não existir no cadastro
            // Mostra o diálogo para opção de inserção de novo cliente
            URL location = getClass().getResource("FXMLCPFInexistenteModal.fxml");
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(location);
            loader.setBuilderFactory(new JavaFXBuilderFactory());
            Scene dialogScene = new Scene((Parent)loader.load(location.openStream()));
            // Inicia a cena da caixa de diálogo
            Stage dialog = new Stage();
            dialog.initModality(Modality.WINDOW_MODAL);
            dialog.initOwner(root);
            dialog.setScene(dialogScene);
            FXMLCPFInexistenteModalController controller = (FXMLCPFInexistenteModalController)loader.getController();
            controller.initParams();
            controller.setMainController(this);
            dialog.centerOnScreen();
            dialog.show();
        }
    }

    /**
     * Valida algoritmicamente o CPF informado
     * 
     * @param event 
     */
    @FXML
    private void clienteValidaCPF(ActionEvent event) throws SQLException, IOException, ParseException {
        // Primeiro verifica se o CPF está cadastrado
        // Limpa o campo de CPF, retirando pontos e traços
        String pattern = "[\\.-]";
        String cpf = clienteCPFTextField.getText().replaceAll(pattern, "");
        
        // Executa a busca no BD pelo CPF
        String sql = "SELECT * FROM cliente WHERE cpf=?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, cpf);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {    // Se houver o CPF no cadastro
            // Preenche os campos de cadastro
            clienteCPFTextField.setText(formataCPF(rs.getString("cpf")));
            clienteNomeTextField.setText(rs.getString("nome"));
            clienteEndTextField.setText(rs.getString("logradouro"));
            clienteEndNumTextField.setText(rs.getString("numero"));
            clienteBairroTextField.setText(rs.getString("bairro"));
            clienteCidadeTextField.setText(rs.getString("cidade"));
            if (rs.getString("uf") != null) {
                clienteUFComboBox.setValue(rs.getString("uf"));
            }
            clienteCEPTextField.setText(rs.getString("cep"));
            clienteEmailTextField.setText(rs.getString("email"));
            clienteTelTextField.setText(rs.getString("telefone"));
        } else {    // CPF não cadastrado
            // Valida algoritmicamente o CPF
            if (!Cliente.validaCPF(cpf)) {   // CPF inválido
                clienteCPFOkLabel.setVisible(false);
                // Apresenta a modal de CPF inválido
                URL location = getClass().getResource("FXMLCPFInvalidoModal.fxml");
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(location);
                loader.setBuilderFactory(new JavaFXBuilderFactory());
                Scene dialogScene = new Scene((Parent)loader.load(location.openStream()));
                // Inicia a cena da caixa de diálogo
                Stage dialog = new Stage();
                dialog.initModality(Modality.WINDOW_MODAL);
                dialog.initOwner(root);
                dialog.setScene(dialogScene);
                FXMLCPFInvalidoModalController controller = (FXMLCPFInvalidoModalController)loader.getController();
                controller.initParams();
                dialog.centerOnScreen();
                dialog.show();
            } else {
                clienteCPFOkLabel.setVisible(true);
            }
        }
    }

    /**
     * Salva os dados do cliente no banco de dados
     * 
     * @param event 
     */
    @FXML
    private void clienteSalva(ActionEvent event) {
        if (clienteAtual == null) {
            clienteAtual = new Cliente(conn);
        }
        clienteAtual.cpf = clienteCPFTextField.getText();
        clienteAtual.nome = clienteNomeTextField.getText();
        clienteAtual.logradouro = clienteEndTextField.getText();
        clienteAtual.numero = clienteEndNumTextField.getText();
        clienteAtual.bairro = clienteBairroTextField.getText();
        clienteAtual.cidade = clienteCidadeTextField.getText();
        clienteAtual.uf = clienteUFComboBox.getValue();
        clienteAtual.cep = clienteCEPTextField.getText();
        clienteAtual.email = clienteEmailTextField.getText();
        clienteAtual.telefone = clienteTelTextField.getText();
        clienteAtual.salva();
        selecionaTab(TabIndex.VENDA.getIndex());
        clienteLimpaDados();
        initParams(user);
    }
    
    public void clienteLimpaDados() {
        clienteCPFTextField.setText("");
        clienteNomeTextField.setText("");
        clienteEndTextField.setText("");
        clienteEndNumTextField.setText("");
        clienteBairroTextField.setText("");
        clienteCidadeTextField.setText("");
        clienteUFComboBox.setValue("");
        clienteCEPTextField.setText("");
        clienteEmailTextField.setText("");
        clienteTelTextField.setText("");
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
    
    private String formataCPF(String cpf) throws ParseException {
        MaskFormatter mask = new MaskFormatter("###.###.###-##");
        mask.setValueContainsLiteralCharacters(false);
        return mask.valueToString(cpf);
    }
    
    public void selecionaTab(int index) {
        SingleSelectionModel<Tab> selectionModel = mainTabPane.getSelectionModel();
        selectionModel.select(index);
    }
    
    public void setClienteAtual(Cliente c) {
        clienteAtual = c;
    }
    
    public void atualizaTotal(float valor) {
        vendaTotalLabel.setText(String.format("R$ %.2f", valor));
    }
}
