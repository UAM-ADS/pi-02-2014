package toronto.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author mgaldieri
 */
public class Constants {
    public enum Versao {
        MAJOR('0'),
        MINOR('9'),
        FIX('0');
        private char valor;
        
        private Versao(char valor) {
            this.valor = valor;
        }
                
        public static String full() {
            return ""+Versao.MAJOR.valor+'.'+Versao.MINOR.valor+'.'+Versao.FIX.valor;
        }
    }
    
    public enum TabIndex {
        VENDA(0),
        CLIENTE(1),
        PRODUTO(2),
        ESTOQUE(3),
        FUNCIONARIO(4),
        ADMIN(5),
        SOBRE(6);
        private int index;
        
        private TabIndex(int index) {
            this.index = index;
        }
        
        public int getIndex() {
            return index;
        }
    }
    
    // Configurações do banco de dados local
    public static final String JDBC_DRIVER = "org.h2.Driver";
    public static final String DB_URL = "jdbc:h2:~/.toronto/toronto.db";
    
    public static final float IMPOSTOS = 0.3146f;
    
    public enum ProdutoAttr {
        NOME,
        DESCRICAO,
        QUANTIDADE,
        PRECO;
    }
    
    // Lista de estados brasileiros
    public static final List<String> listaUF = Arrays.asList(
        "AC",
        "AL",
        "AP",
        "AM",
        "BA",
        "CE",
        "DF",
        "ES",
        "GO",
        "MA",
        "MT",
        "MS",
        "MG",
        "PA",
        "PB",
        "PR",
        "PE",
        "PI",
        "RJ",
        "RN",
        "RS",
        "RO",
        "RR",
        "SC",
        "SP",
        "SE",
        "TO");
}
