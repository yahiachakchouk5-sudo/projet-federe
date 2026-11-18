package com.school.app.controller;

import com.school.app.dao.EleveDAO;
import com.school.app.dao.InscriptionDAO;
import com.school.app.dao.NiveauDAO;
import com.school.app.model.Eleve;
import com.school.app.model.Inscription;
import com.school.app.model.Niveau;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class RegisterController {

    @FXML private TextField nomField, prenomField, adresseField, phoneField, usernameField;
    @FXML private DatePicker dobPicker;
    @FXML private PasswordField passwordField;
    @FXML private ComboBox<Niveau> niveauCombo;
    @FXML private Label statusLabel;

    private EleveDAO eleveDAO = new EleveDAO();
    private InscriptionDAO insDAO = new InscriptionDAO();
    private NiveauDAO niveauDAO = new NiveauDAO();

    @FXML
    public void initialize() {
        try {
            niveauCombo.setItems(FXCollections.observableArrayList(niveauDAO.getAll()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleRegister() {
        if (dobPicker.getValue() == null || niveauCombo.getValue() == null) {
            statusLabel.setText("Veuillez remplir tous les champs (Date et Niveau).");
            return;
        }

        Eleve e = new Eleve();
        e.setNom(nomField.getText());
        e.setPrenom(prenomField.getText());
        e.setDateNaissance(dobPicker.getValue());
        e.setAdresse(adresseField.getText());
        e.setTelephoneParent(phoneField.getText());
        e.setNomUtilisateur(usernameField.getText());
        e.setMotDePasse(passwordField.getText());

        try {
            int idEleve = eleveDAO.add(e);
            
            // Auto-inscription du nouvel étudiant
            Inscription ins = new Inscription();
            ins.setIdEleve(idEleve);
            ins.setIdNiveau(niveauCombo.getValue().getIdNiveau());
            ins.setAnnee("2025-2026"); 
            ins.setNiveauEtude(niveauCombo.getValue().getNomNiveau());
            
            int idIns = insDAO.add(ins);

            // Auto-affectation à la première classe disponible avec de l'espace
            com.school.app.dao.ClasseDAO classeDAO = new com.school.app.dao.ClasseDAO();
            java.util.List<com.school.app.model.Classe> classes = classeDAO.getClassesByNiveau(ins.getIdNiveau());
            
            boolean affected = false;
            for (com.school.app.model.Classe c : classes) {
                int currentCount = classeDAO.countStudentsInClass(c.getIdClasse());
                if (currentCount < c.getCapaciteMax()) {
                    com.school.app.dao.AffectationDAO affDAO = new com.school.app.dao.AffectationDAO();
                    com.school.app.model.AffectationEleve aff = new com.school.app.model.AffectationEleve();
                    aff.setIdInscription(idIns);
                    aff.setIdClasse(c.getIdClasse());
                    aff.setDateAffectation(java.time.LocalDate.now());
                    affDAO.add(aff);
                    affected = true;
                    break;
                }
            }

            if (affected) {
                statusLabel.setText("Compte créé et affecté avec succès !");
            } else {
                statusLabel.setText("Compte créé ! (Classes complètes, affectation manuelle requise)");
            }
            handleBack();
        } catch (SQLException | IOException ex) {
            statusLabel.setText("Erreur: " + ex.getMessage());
        }
    }

    @FXML
    private void handleBack() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/com/school/app/view/LoginView.fxml"));
        Stage stage = (Stage) statusLabel.getScene().getWindow();
        stage.setScene(new Scene(root, 1000, 700));
    }
}
