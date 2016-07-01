/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifnmg.merci.excessao;

/**
 *
 * @author wff
 */
public class CampoObrigatorioException extends RuntimeException {

    public CampoObrigatorioException() {
        super("Preencha os campos obrigatorio");
    }

    public CampoObrigatorioException(String message) {
        super(message);
    }
}
