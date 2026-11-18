package com.school.app.controller;

import com.school.app.dao.EnseignantDAO;
import com.school.app.dao.EleveDAO;
import com.school.app.model.Enseignant;
import com.school.app.model.Eleve;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class LoginController {

    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Label errorLabel;

    private EnseignantDAO enseignantDAO = new EnseignantDAO();
    private EleveDAO eleveDAO = new EleveDAO();

    @FXML
    private void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        try {
            // Check Teachers/Admin first
            Enseignant prof = enseignantDAO.authenticate(username, password);
            if (prof != null) {
                String role = prof.getRole();
                if (role == null) role = "TEACHER"; // Fallback
                
                com.school.app.util.Session.login(prof.getCode(), role, 0);
                switchToMain();
                return;
            }

            // Check Students
            Eleve stud = eleveDAO.authenticate(username, password);
            if (stud != null) {
                com.school.app.util.Session.login(username, "STUDENT", stud.getId());
                switchToMain();
                return;
            }

            errorLabel.setText("Identifiants incorrects.");
        } catch (SQLException | IOException e) {
            errorLabel.setText("Erreur: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void switchToMain() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/com/school/app/view/MainView.fxml"));
        Stage stage = (Stage) usernameField.getScene().getWindow();
        stage.setScene(new Scene(root, 1000, 700));
        stage.centerOnScreen();
    }

    @FXML
    private void handleGoToRegister() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/com/school/app/view/RegisterView.fxml"));
        Stage stage = (Stage) usernameField.getScene().getWindow();
        stage.setScene(new Scene(root, 1000, 700));
    }
}
