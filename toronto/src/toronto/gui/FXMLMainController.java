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
import java.util.Scanner;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.control.ComboBox;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyEvent;
import javafx.util.converter.FloatStringConverter;
import javafx.util.converter.IntegerStringConverter;
import javax.swing.text.MaskFormatter;
import toronto.utils.Constants;
import toronto.utils.Constants.Versao;
import toronto.utils.Constants.TabIndex;
import toronto.utils.Constants.ProdutoAttr;
import toronto.utils.Erros.ErroMsg;
import toronto.Usuario;
import toronto.Cliente;
import toronto.FrenteCaixa;
import toronto.GerenciadorEstoque;
import toronto.GerenciadorFinanceiro;
import toronto.NotaFiscal;
import toronto.Produto;
import toronto.exceptions.*;
import toronto.gui.ProdutoRow;

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
    private TableView<ProdutoRow> vendaProdTableView;
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
    private TableView<ProdutoRow> estoqueTableView;
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
    @FXML
    private TabPane mainTabPane;
    @FXML
    private Tab tabVenda;
    @FXML
    private Tab tabCliente;
    @FXML
    private Tab tabProduto;
    @FXML
    private Tab tabEstoque;
    @FXML
    private Tab tabFuncionario;
    @FXML
    private Tab tabAdmin;
    @FXML
    private Tab tabSobre;
    @FXML
    private TableColumn<ProdutoRow, Integer> vendaCodColumn;
    @FXML
    private TableColumn<ProdutoRow, String> vendaNomeColumn;
    @FXML
    private TableColumn<ProdutoRow, Integer> vendaQtdColumn;
    @FXML
    private TableColumn<ProdutoRow, Float> vendaPrecoColumn;
    @FXML
    private TableColumn<ProdutoRow, Integer> estoqueCodColumn;
    @FXML
    private TableColumn<ProdutoRow, String> estoqueNomeColumn;
    @FXML
    private TableColumn<ProdutoRow, String> estoqueDescColumn;
    @FXML
    private TableColumn<ProdutoRow, Integer> estoqueQtdColumn;
    @FXML
    private TableColumn<ProdutoRow, Float> estoquePrecoColumn;
    
    private static Stage root;
    private static Connection conn;
    private static Usuario user;
    private static Cliente clienteAtual;
    private static FrenteCaixa caixa;
    private static GerenciadorEstoque gerEstoque;
    private static GerenciadorFinanceiro gerFinanceiro;
    private static ObservableList<ProdutoRow> vendaProdutoList;
    private static ObservableList<ProdutoRow> estoqueProdutoList;
    

    /**
     * Inicializa a classe controladora.
     * Inicializa variáveis que não dependem de inicialização prévia.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Configura tabela de produtos na venda
        vendaCodColumn.setCellValueFactory(
                new PropertyValueFactory<ProdutoRow, Integer>("codigo")
        );
        vendaNomeColumn.setCellValueFactory(
                new PropertyValueFactory<ProdutoRow, String>("nome")
        );
        vendaQtdColumn.setCellValueFactory(
                new PropertyValueFactory<ProdutoRow, Integer>("quantidade")
        );
        vendaPrecoColumn.setCellValueFactory(
                new PropertyValueFactory<ProdutoRow, Float>("preco")
        );
        vendaProdutoList = FXCollections.observableArrayList();
        vendaProdTableView.setItems(vendaProdutoList);
        
        // Configura tabela de produtos em estoque
        estoqueTableView.setEditable(true);
        
        estoqueCodColumn.setCellValueFactory(
                new PropertyValueFactory<ProdutoRow, Integer>("codigo")
        );
        
        estoqueNomeColumn.setCellValueFactory(
                new PropertyValueFactory<ProdutoRow, String>("nome")
        );
        estoqueNomeColumn.setCellFactory(TextFieldTableCell.<ProdutoRow>forTableColumn());
        estoqueNomeColumn.setOnEditCommit(
                new EventHandler<CellEditEvent<ProdutoRow, String>>() {
                    @Override
                    public void handle(CellEditEvent<ProdutoRow, String> t) {
                        ((ProdutoRow) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())
                            ).setNome(t.getNewValue());
                    }
                }
        );
        
        estoqueDescColumn.setCellValueFactory(
                new PropertyValueFactory<ProdutoRow, String>("descricao")
        );
        estoqueDescColumn.setCellFactory(TextFieldTableCell.<ProdutoRow>forTableColumn());
        estoqueDescColumn.setOnEditCommit(
                new EventHandler<CellEditEvent<ProdutoRow, String>>() {
                    @Override
                    public void handle(CellEditEvent<ProdutoRow, String> t) {
                        ((ProdutoRow) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())
                            ).setDescricao(t.getNewValue());
                    }
                }
        );
        
        estoqueQtdColumn.setCellValueFactory(
                new PropertyValueFactory<ProdutoRow, Integer>("quantidade")
        );
        estoqueQtdColumn.setCellFactory(TextFieldTableCell.<ProdutoRow, Integer>forTableColumn(new IntegerStringConverter()));
        estoqueQtdColumn.setOnEditCommit(
                new EventHandler<CellEditEvent<ProdutoRow, Integer>>() {
                    @Override
                    public void handle(CellEditEvent<ProdutoRow, Integer> t) {
                        ((ProdutoRow) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())
                            ).setQuantidade(t.getNewValue());
                    }
                }
        );
        
        estoquePrecoColumn.setCellValueFactory(
                new PropertyValueFactory<ProdutoRow, Float>("preco")
        );
        estoquePrecoColumn.setCellFactory(TextFieldTableCell.<ProdutoRow, Float>forTableColumn(new FloatStringConverter()));
        estoquePrecoColumn.setOnEditCommit(
                new EventHandler<CellEditEvent<ProdutoRow, Float>>() {
                    @Override
                    public void handle(CellEditEvent<ProdutoRow, Float> t) {
                        ((ProdutoRow) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())
                            ).setPreco(t.getNewValue());
                    }
                }
        );
        
        estoqueProdutoList = FXCollections.observableArrayList();
        estoqueTableView.setItems(estoqueProdutoList);
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
        // Referência aos singletons gerenciadores
        gerEstoque = GerenciadorEstoque.gerenciador(conn);
        gerFinanceiro = GerenciadorFinanceiro.gerenciador;
        
        // Verifica as permissões do usuário
        if (!user.admin) {
            // Desabilita as funcionalidades de administrador
            mainTabPane.getTabs().remove(tabProduto);
            mainTabPane.getTabs().remove(tabEstoque);
            mainTabPane.getTabs().remove(tabFuncionario);
            mainTabPane.getTabs().remove(tabAdmin);
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
        caixa.setCliente(clienteAtual);
        
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
    private void vendaInsereProd(ActionEvent event) throws SQLException, IOException {
        // Busca o produto no banco de dados
        try {
            int codigo = Integer.parseInt(vendaCodProdTextField.getText());
            int quantidade = Integer.parseInt(vendaQtdProdTextField.getText());
            Produto produto = caixa.verificaProduto(codigo, quantidade);
            if (produto != null) {
                caixa.adicionaProdutoPedido(produto, quantidade);
                // Atualiza valor da compra
                atualizaTotal(caixa.getValorPedido());
                // Atualiza tabela de produtos
                vendaProdutoList.add(new ProdutoRow(produto.codigo,
                                            produto.nome,
                                            produto.descricao,
                                            Integer.parseInt(vendaQtdProdTextField.getText()),
                                            produto.preco));
                vendaCodProdTextField.clear();
                vendaQtdProdTextField.setText("1");
            }
        } catch (ProdutoInexistenteException e) {
            // Exibe alerta avisando que o produto não está cadastrado
            mostraAlerta(ErroMsg.msg(ErroMsg.ALERTA_PRODUTO_INEXISTENTE));
        } catch (ProdutoForaDeEstoqueException e) {
            // Exibe alerta avisando que o produto está fora de estoque
            mostraAlerta(ErroMsg.msg(ErroMsg.ALERTA_PRODUTO_FORA_ESTOQUE));
        }
        
    }

    /**
     * Finaliza a venda, gravando os dados da mesma no banco de dados e
     * e imprimindo o cupom fiscal
     * 
     * @param event 
     */
    @FXML
    private void vendaFinaliza(ActionEvent event) throws IOException {
        if (vendaProdutoList.size() > 0) {  // Se tiver produto na lista
            String html = new Scanner(this.getClass().getResourceAsStream("nf.html"), "UTF-8").useDelimiter("\\A").next();
            NotaFiscal nf = caixa.fechaCompra();
            // Substitui tags no html
            String pattNumNF = "(\\{\\{\\s*(num_nf)\\s*\\}\\})";
            String pattProds = "(\\{\\{\\s*(prods)\\s*\\}\\})";
            String pattImposto = "(\\{\\{\\s*(imp_total)\\s*\\}\\})";
            String pattImpPerc = "(\\{\\{\\s*(imp_perc)\\s*\\}\\})";
            String pattVersao = "(\\{\\{\\s*(versao)\\s*\\}\\})";
            String prods = "";
            System.out.println(nf);
            for (Produto p : nf.pedido.produtos) {
                prods += String.format("<tr><th>%d</th><th>%s</th><th>%d</th><th>%.2f</th></tr>\n",
                        p.codigo,
                        p.nome,
                        nf.pedido.quantidades.get(p.codigo),
                        p.preco*nf.pedido.quantidades.get(p.codigo));
            }
            html = html.replaceAll(pattNumNF, String.format("%06d", nf.numero));
            html = html.replaceAll(pattProds, prods);
            
            html = html.replaceAll(pattImposto, String.format("%.2f", nf.valor*Constants.IMPOSTOS));
            html = html.replaceAll(pattImpPerc, String.format("%.2f", 100*Constants.IMPOSTOS));
            html = html.replaceAll(pattVersao, Versao.full());
            mostraNF(html);
            clienteAtual = null;
            vendaProdutoList.clear();
            initParams(user);
        } else {    // Lista de compra vazia
            // Mostra diálogo alertando sobre lista vazia
            mostraAlerta(ErroMsg.msg(ErroMsg.ALERTA_CARRINHO_VAZIO));
        }
        
    }
    
    private Boolean mostraNF(String url) {
        try {
            URL location = getClass().getResource("FXMLCupomFiscal.fxml");
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(location);
            loader.setBuilderFactory(new JavaFXBuilderFactory());
            Scene dialogScene = new Scene((Parent)loader.load(location.openStream()));
            // Inicia a cena da caixa de diálogo
            Stage dialog = new Stage();
            dialog.initModality(Modality.WINDOW_MODAL);
            dialog.initOwner(root);
            dialog.setScene(dialogScene);
            FXMLCupomFiscalController controller = (FXMLCupomFiscalController)loader.getController();
            controller.initParams(url);
            dialog.centerOnScreen();
            dialog.show();
        } catch (IOException e) {
            return false;
        }
        return true;
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
                mostraAlerta(ErroMsg.msg(ErroMsg.ALERTA_CPF_INVALIDO));
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
    private void prodSalva(ActionEvent event) throws IOException {
        // Cria o produto
        float preco = Float.parseFloat(prodPrecoTextField.getText().replaceAll("\\w+\\$\\s?", "").replace(',', '.'));
        Produto produto = new Produto(conn);
        produto.nome = prodNomeTextField.getText();
        produto.descricao = prodDescTextArea.getText();
        produto.preco = preco;
        // Salva no banco de dados
        if (!produto.salva()) {
            mostraAlerta(ErroMsg.msg(ErroMsg.ALERTA_ACESSO_SQL));
        } else {
            // Limpa os campos
            prodNomeTextField.setText("");
            prodDescTextArea.setText("");
            prodPrecoTextField.setText("");
        }
    }

    /**
     * Busca produtos no banco de dados que conferem com os dados informados
     * no campo de busca
     * 
     * @param event 
     */
    @FXML
    private void estoqueBusca(ActionEvent event) throws SQLException {
        // Apaga a lista
        estoqueProdutoList.clear();
        // Busca no banco de dados
        String sql =
                "SELECT * FROM (\n" +
                "    SELECT p.cod_produto, p.nome, p.descricao, p.preco, e.quantidade FROM produto p\n" +
                "        INNER JOIN estoque e ON p.cod_produto=e.cod_produto ORDER BY p.cod_produto\n" +
                ") WHERE nome LIKE ?\n" +
                "UNION\n" +
                "SELECT * FROM (\n" +
                "    SELECT p.cod_produto, p.nome, p.descricao, p.preco, e.quantidade FROM produto p\n" +
                "        INNER JOIN estoque e ON p.cod_produto=e.cod_produto ORDER BY p.cod_produto\n" +
                ") WHERE descricao LIKE ?\n" +
                "UNION\n" +
                "SELECT * FROM (\n" +
                "    SELECT p.cod_produto, p.nome, p.descricao, p.preco, e.quantidade FROM produto p\n" +
                "        INNER JOIN estoque e ON p.cod_produto=e.cod_produto ORDER BY p.cod_produto\n" +
                ") WHERE cod_produto LIKE ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, "%"+estoqueBuscaTextField.getText()+"%");
        stmt.setString(2, "%"+estoqueBuscaTextField.getText()+"%");
        stmt.setString(3, "%"+estoqueBuscaTextField.getText()+"%");
        ResultSet rs = stmt.executeQuery();
        // Insere os resultados na tabela
        while (rs.next()) {
            estoqueProdutoList.add(new ProdutoRow(rs.getInt("cod_produto"),
                                                  rs.getString("nome"),
                                                  rs.getString("descricao"),
                                                  rs.getInt("quantidade"),
                                                  rs.getFloat("preco")));
        }
        estoqueBuscaTextField.clear();
    }

    /**
     * Salva as alterações feitas nos dados de estoque no banco de dados
     * 
     * @param event 
     */
    @FXML
    private void estoqueSalva(ActionEvent event) throws SQLException {
        for (ProdutoRow pRow : estoqueProdutoList) {
            // Atualiza quantidade
            gerEstoque.atualizaProdutoEstoque(pRow.getCodigo(), pRow.getQuantidade());
            // Atualiza produto
            Produto produto = new Produto(conn);
            produto.codigo = pRow.getCodigo();
            produto.nome = pRow.getNome();
            produto.descricao = pRow.getDescricao();
            produto.preco = pRow.getPreco();
            produto.atualiza();
        }
        // Apaga a lista
        estoqueProdutoList.clear();
    }
    
    /**
     * Verifica se as senhas conferem
     * 
     * @param event 
     */
    @FXML
    private void funcSenhaKeyPressed(KeyEvent event) {
        if (funcSenhaTextField.getText().equals(funcSenhaConfTextBox.getText())) {
            funcSenhaAlertaLabel.setVisible(false);
        } else {
            funcSenhaAlertaLabel.setVisible(true);
        }
    }

    /**
     * Salva os dados do novo funcionário no banco de dados
     * 
     * @param event 
     */
    @FXML
    private void funcSalva(ActionEvent event) throws IOException {
        // Checa se as senhas conferem
        if (funcSenhaTextField.getText().equals(funcSenhaConfTextBox.getText())) { // Senhas conferem
            // Cria o funcionário
            float salario = Float.parseFloat(funcSalarioTextField.getText().replaceAll("\\w+\\$\\s?", "").replace(',', '.'));
            Usuario funcionario = new Usuario(conn);
            funcionario.nome = funcNomeTextField.getText();
            funcionario.login = funcLoginTextField.getText();
            funcionario.senha = funcSenhaTextField.getText();
            funcionario.admin = funcAdminCheckBox.selectedProperty().getValue();
            funcionario.salario = salario;
            // Salva no banco de dados
            if (!funcionario.salva()) {
                mostraAlerta(ErroMsg.msg(ErroMsg.ALERTA_ACESSO_SQL));
            } else {
                // Limpa os campos
                funcNomeTextField.setText("");
                funcLoginTextField.setText("");
                funcSenhaTextField.setText("");
                funcSenhaConfTextBox.setText("");
                funcAdminCheckBox.selectedProperty().setValue(Boolean.FALSE);
            }
        } else { // Senhas não conferem
            mostraAlerta(ErroMsg.msg(ErroMsg.ALERTA_SENHA_NAO_CONFERE));
        }
    }

    /**
     * Gera o relatório gerencial
     * 
     * @param event 
     */
    @FXML
    private void adminGera(ActionEvent event) {
    }
    
    private void mostraAlerta(String msg) throws IOException {
        URL location = getClass().getResource("FXMLAlertaModal.fxml");
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(location);
        loader.setBuilderFactory(new JavaFXBuilderFactory());
        Scene dialogScene = new Scene((Parent)loader.load(location.openStream()));
        // Inicia a cena da caixa de diálogo
        Stage dialog = new Stage();
        dialog.initModality(Modality.WINDOW_MODAL);
        dialog.initOwner(root);
        dialog.setScene(dialogScene);
        FXMLAlertaModalController controller = (FXMLAlertaModalController)loader.getController();
        controller.initParams(msg);
        dialog.centerOnScreen();
        dialog.show();
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
