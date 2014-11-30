/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package toronto.exceptions;

/**
 *
 * @author mgaldieri
 */
public class ProdutoForaDeEstoqueException extends Exception {
    public ProdutoForaDeEstoqueException(String msg) {
        super(msg);
    }
}
