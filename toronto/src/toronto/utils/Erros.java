/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package toronto.utils;

/**
 *
 * @author mgaldieri
 */
public class Erros {
    // Mensagens de erro
    public enum ErroMsg {
        FATAL_CLASSE_INEXISTENTE("O banco de dados apresenta inconsistência. Por favor reinicie o aplicativo."),
        FATAL_ACESSO_SQL("Houve um erro ao acessar o banco de dados. Por favor reinicie o aplicativo.");
        
        private String valor;
        
        private ErroMsg(String valor) {
            this.valor = valor;
        }
        
        public static String msg(ErroMsg erro) {
            return erro.valor;
        }
    }
    
}
