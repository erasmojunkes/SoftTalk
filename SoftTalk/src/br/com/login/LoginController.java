/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.login;

import br.com.Utils.Functions;
import br.com.softtalk.SoftTalk;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 *
 * @author luis_
 */
public class LoginController {

    @FXML
    private TextField usuario;
    @FXML
    private PasswordField senha;

    @FXML
    protected void LoginAction(ActionEvent event) throws SQLException, IOException {
        validaLogin();
    }

    private void validaLogin() throws SQLException, IOException {
        String where;
        Statement st;
        ResultSet rs;
        Functions functions = new Functions();

        try {
            where = "idusuario = " + Integer.parseInt(usuario.getText());

        } catch (NumberFormatException ex) {
            where = "upper(login) =  upper('" + usuario.getText() + "')";
        }

        st = SoftTalk.conexao.createStatement();
        rs = st.executeQuery("Select idusuario, login, senha, flagativo from usuario where " + where);

        if (rs.next()) {
            if (rs.getString("flagativo").equals("T")) {
                if (rs.getString("senha").equals(functions.encript(senha.getText()))) {
                    Parent fxmlLoader = FXMLLoader.load(SoftTalk.class.getResource("SoftTalk.fxml"));

                    SoftTalk.stage.toFront();
                    SoftTalk.stage.setScene(new Scene(fxmlLoader));
                    SoftTalk.stage.showAndWait();
                } else {
                    // usuario ou senha incorretos
                }
            } else {
                //mensagem usuario inativo
            }
        }

    }

    //botao para acessar tela de cadastro
    @FXML
    private void cadastroAction(ActionEvent event) {
        openCadastro();
    }

    private void openCadastro() {
        Parent root;
        try {
            root = FXMLLoader.load(Login.class.getResource("CadastroUsuario.fxml"));
            Scene scene = new Scene(root);

            SoftTalk.stage.setScene(scene);
            SoftTalk.stage.show();
        } catch (IOException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
