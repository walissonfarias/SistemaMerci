/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifnmg.merci.persistencia;

import br.edu.ifnmg.merci.entidade.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author wff
 */
public class UsuarioDAO {
    private static final String SQL_INSERT = "INSERT INTO USUARIO (NOME, LOGIN, SENHA, GRUPO) VALUES (?, ?, ?, ?)";
    private static final String SQL_BUSCAR_USUARIO_SENHA = "SELECT NOME, LOGIN, SENHA, GRUPO FROM USUARIO WHERE LOGIN = ? AND SENHA =?";
    private static final String SQL_BUSCAR_BY_LOGIN = "SELECT NOME, LOGIN, SENHA, GRUPO FROM USUARIO WHERE  LOGIN = ? ";
    private static final String SQL_BUSCAR_TODOS = "SELECT NOME, LOGIN, SENHA, GRUPO FROM USUARIO";
    private static final String SQL_UPDATE_DADOS = "UPDATE USUARIO SET NOME = ?, SENHA= ?, GRUPO=? WHERE LOGIN = ? ";
    private static final String SQL_EXCLUIR_POR_LOGIN = "DELETE FROM USUARIO WHERE LOGIN = ? ";
    private static final String SQL_UPDATE_ALTERAR_SENHA = "UPDATE USUARIO SET SENHA = ? WHERE LOGIN = ?";
    public void criar(Usuario usuario) throws SQLException{
        Connection conexao = null;
        PreparedStatement comando = null;
        
        try{
            conexao = BancoDadosUtil.getConnection();
            comando = conexao.prepareStatement(SQL_INSERT);
            comando.setString(1, usuario.getNome());
            comando.setString(2, usuario.getLogin());
            comando.setString(3, usuario.getSenha());
            comando.setString(4, usuario.getGrupo());
            comando.execute();
            conexao.commit();
        }catch(Exception e){
            if(conexao!= null){
                conexao.rollback();
            }
            throw e;
        }finally{
            BancoDadosUtil.fecharChamadasBancoDados(conexao, comando);
        }
        
    }
    public Usuario buscarByLoginAndSenha(String login, String senha) throws SQLException{
        Usuario usuario = null;
        Connection conexao = null;
        PreparedStatement comando = null;
        ResultSet resultado = null;
        try{
            conexao = BancoDadosUtil.getConnection();
            comando = conexao.prepareStatement(SQL_BUSCAR_USUARIO_SENHA);
            comando.setString(1, login);
            comando.setString(2, senha);
            resultado = comando.executeQuery();
            
            while(resultado.next()){
                usuario = this.extrairLinhaResultado(resultado);
            }    
        }catch(Exception e){
            if(conexao!= null){
                conexao.rollback();
            }
            throw new RuntimeException();
        }finally{
            BancoDadosUtil.fecharChamadasBancoDados(conexao, comando, resultado);
        }
        return usuario;
    }    
    
    public Usuario buscarByLogin(String login) throws SQLException{
        Usuario usuario = null;
        Connection conexao = null;
        PreparedStatement comando = null;
        ResultSet resultado = null;
        try{
            conexao = BancoDadosUtil.getConnection();
            comando = conexao.prepareStatement(SQL_BUSCAR_BY_LOGIN);
            comando.setString(1, login);
            resultado = comando.executeQuery();
            
            while(resultado.next()){
                usuario = this.extrairLinhaResultado(resultado);
            }    
        }catch(Exception e){
            if(conexao!= null){
                conexao.rollback();
            }
            throw new RuntimeException();
        }finally{
            BancoDadosUtil.fecharChamadasBancoDados(conexao, comando, resultado);
        }
        return usuario;
    }
    public Usuario buscarByNome(String nome) throws SQLException{
        Usuario usuario = null;
        Connection conexao = null;
        PreparedStatement comando = null;
        ResultSet resultado = null;
        try{
            conexao = BancoDadosUtil.getConnection();
            comando = conexao.prepareStatement(SQL_BUSCAR_BY_LOGIN);
            comando.setString(1, nome);
            resultado = comando.executeQuery();
            
            while(resultado.next()){
                usuario = this.extrairLinhaResultado(resultado);
            }    
        }catch(Exception e){
            if(conexao!= null){
                conexao.rollback();
            }
            throw new RuntimeException();
        }finally{
            BancoDadosUtil.fecharChamadasBancoDados(conexao, comando, resultado);
        }
        return usuario;
    }
    public List<Usuario> buscarTodos() throws SQLException {
        Connection conexao = null;
        PreparedStatement comando = null;
        ResultSet resultado = null;

        List<Usuario> listaUsuarios = new ArrayList();

        try {
            //Recupera a conexão
            conexao = BancoDadosUtil.getConnection();
            //Cria o comando de consulta dos dados
            comando = conexao.prepareStatement(SQL_BUSCAR_TODOS);
            //Executa o comando e obtém o resultado da consulta
            resultado = comando.executeQuery();
            //O método next retornar boolean informando se existe um próximo
            //elemento para iterar
            while (resultado.next()) {
                Usuario usuario = this.extrairLinhaResultado(resultado);
                //Adiciona um item à lista que será retornada
                listaUsuarios.add(usuario);
            }
        } finally {
            //Todo objeto que referencie o banco de dados deve ser fechado
            BancoDadosUtil.fecharChamadasBancoDados(conexao, comando, resultado);
        }
        return listaUsuarios;
    }    
      
    private Usuario extrairLinhaResultado(ResultSet resultado) throws SQLException {
        //Instancia um novo objeto e atribui os valores vindo do BD
        //(Note que no BD o index inicia por 1)
        Usuario usuario = new Usuario();        
        usuario.setNome(resultado.getString(1));
        usuario.setLogin(resultado.getString(2));
        usuario.setSenha(resultado.getString(3));
        usuario.setGrupo(resultado.getString(4));                                       
        return usuario;
    }  
    public void alterarSenha(Usuario usuario) throws SQLException{
        PreparedStatement comando = null;
        Connection conexao = null;    
        try{
            conexao = BancoDadosUtil.getConnection();
            comando = conexao.prepareStatement(SQL_UPDATE_ALTERAR_SENHA);
            comando.setString(1, usuario.getSenha());            
            comando.setString(2, usuario.getLogin());
            comando.execute();
            conexao.commit();
        } catch (Exception e) {
            if (conexao != null) {
                conexao.rollback();
            }
            throw new RuntimeException();
        } finally {
            if (comando != null && !comando.isClosed()) {
                comando.close();
            }
            if (conexao != null && !conexao.isClosed()) {
                conexao.close();
            }
        }
    }
    public void atualizarDados(Usuario usuario) throws SQLException{
        PreparedStatement comando = null;
        Connection conexao = null;    
        try{
            conexao = BancoDadosUtil.getConnection();
            comando = conexao.prepareStatement(SQL_UPDATE_DADOS);
            comando.setString(1, usuario.getNome());
            comando.setString(2, usuario.getSenha());
            comando.setString(3, usuario.getGrupo());
            comando.setString(4, usuario.getLogin());
            comando.execute();
            conexao.commit();
        } catch (Exception e) {
            if (conexao != null) {
                conexao.rollback();
            }
            throw new RuntimeException();
        } finally {
            if (comando != null && !comando.isClosed()) {
                comando.close();
            }
            if (conexao != null && !conexao.isClosed()) {
                conexao.close();
            }
        }
    }
    
    public void excluirUsuario(Usuario usuario) throws SQLException{
        Connection conexao = null;
        PreparedStatement comando = null;
        
        try {
            conexao = BancoDadosUtil.getConnection();
            comando = conexao.prepareStatement(SQL_EXCLUIR_POR_LOGIN);
            comando.setString(1, usuario.getLogin());
            comando.execute();
            conexao.commit();
        } catch (Exception e) {
            if (conexao != null) {
                conexao.rollback();
            }
            throw new RuntimeException(e);
        } finally {
            if (comando != null && !comando.isClosed()) {
                comando.close();
            }
            if (conexao != null && !conexao.isClosed()) {
                conexao.close();
            }
        }
    }
}

