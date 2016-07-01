/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifnmg.merci.negocio;

import br.edu.ifnmg.merci.entidade.Usuario;
import br.edu.ifnmg.merci.excessao.ArgumentoInvalidoException;
import br.edu.ifnmg.merci.excessao.UsuarioLoginExistenteException;
import br.edu.ifnmg.merci.persistencia.UsuarioDAO;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author wff
 */
public class UsuarioBO {
    public void criar(Usuario usuario) throws SQLException{
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        verificarUsuarioLogin(usuario.getLogin());
        usuarioDAO.criar(usuario);
    }
    public Usuario buscarLoginAndSenha(String login, String senha) throws SQLException{
        Usuario usuarioExistente = null;
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        usuarioExistente = usuarioDAO.buscarByLoginAndSenha(login, senha);
        if(usuarioExistente != null){
            return usuarioExistente;
        }else{
            throw new ArgumentoInvalidoException("Login ou Senha inválidos.\n "
                    + "Tente novamento efetuar o login.");
        }
    }
    public void verificarUsuarioLogin(String login) throws SQLException{
        Usuario usuarioExistente = null;
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        usuarioExistente = usuarioDAO.buscarByLogin(login);
        if(usuarioExistente != null){
            throw new UsuarioLoginExistenteException("Login ja existe!\n escolha outro login!");
        }
    }
    public void alterarSenha(Usuario usuario) throws SQLException{
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        usuarioDAO.alterarSenha(usuario);
    }
    public Usuario buscarLogin(String login) throws SQLException{
        Usuario usuarioExistente = new Usuario();
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        usuarioExistente = usuarioDAO.buscarByLogin(login);
        if(usuarioExistente != null){
            return usuarioExistente;
        }else{
            throw new ArgumentoInvalidoException("Login inexistente \n Verifique se o login está correto");
        }
    }
    public List<Usuario> buscarTodos() throws SQLException{
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        return usuarioDAO.buscarTodos();
    }
    public void atualizarDados(Usuario usuario) throws SQLException {
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        usuarioDAO.atualizarDados(usuario);
    }

    public void excluirUsuario(Usuario usuario) throws SQLException {
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        usuarioDAO.excluirUsuario(usuario);
    }
}
