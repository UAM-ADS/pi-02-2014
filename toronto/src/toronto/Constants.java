package toronto;

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
}
