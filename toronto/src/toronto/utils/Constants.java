package toronto.utils;

/**
 *
 * @author mgaldieri
 */
public class Constants {
    public enum Versao {
        MAJOR('0'),
        MINOR('1'),
        FIX('0');
        private char valor;
        
        private Versao(char valor) {
            this.valor = valor;
        }
                
        public static String full() {
            return ""+Versao.MAJOR.valor+'.'+Versao.MINOR.valor+'.'+Versao.FIX.valor;
        }
    }
    
    // Configurações do banco de dados local
    public static final String JDBC_DRIVER = "org.h2.Driver";
    public static final String DB_URL = "jdbc:h2:~/.toronto/toronto.db";
    
}
