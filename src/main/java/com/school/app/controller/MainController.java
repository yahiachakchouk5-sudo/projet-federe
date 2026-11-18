package com.school.app.controller;

import com.school.app.dao.EleveDAO;
import com.school.app.model.Eleve;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.time.LocalDate;

public class MainController {

    @FXML private TabPane tabPane;
    @FXML private Tab tabDashboard, tabEleve, tabInscription, tabClasse, tabMatiere, tabNote, tabBulletin, tabProf;
    @FXML private Label statusLabel;

    // Controllers injection
    @FXML private DashboardController dashboardContentController;
    @FXML private EleveController eleveContentController;
    @FXML private NoteController noteContentController;

    public void initialize() {
        String role = com.school.app.util.Session.getCurrentRole();
        statusLabel.setText("Connecté en tant que: " + role);

        if ("TEACHER".equals(role)) {
            tabPane.getTabs().removeAll(tabDashboard, tabEleve, tabProf, tabMatiere, tabInscription, tabBulletin);
        } else if ("STUDENT".equals(role)) {
            tabPane.getTabs().removeAll(tabDashboard, tabEleve, tabInscription, tabClasse, tabMatiere, tabNote, tabProf);
            
            // Fetch class info for student
            try {
                com.school.app.dao.InscriptionDAO iDAO = new com.school.app.dao.InscriptionDAO();
                com.school.app.dao.ClasseDAO cDAO = new com.school.app.dao.ClasseDAO();
                com.school.app.model.Inscription ins = iDAO.getInscriptionByEleve(com.school.app.util.Session.getCurrentUserId());
                if (ins != null) {
                    String className = cDAO.getClassNameByInscription(ins.getIdInscription());
                    statusLabel.setText(statusLabel.getText() + " | Classe: " + className);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        // ADMIN keeps everything
        
        // Auto-refresh logic when switching tabs
        tabPane.getSelectionModel().selectedItemProperty().addListener((obs, oldTab, newTab) -> {
            if (newTab != null) {
                if (newTab == tabDashboard && dashboardContentController != null) {
                    dashboardContentController.refresh();
                } else if (newTab == tabNote && noteContentController != null) {
                    noteContentController.refresh();
                }
            }
        });
    }

    @FXML
    private void handleLogout() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/com/school/app/view/LoginView.fxml"));
            Stage stage = (Stage) tabPane.getScene().getWindow();
            stage.setScene(new Scene(root, 1000, 700));
            stage.centerOnScreen();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleExit() {
        System.exit(0);
    }
    
    public void setStatus(String message) {
        statusLabel.setText(message);
    }
}
