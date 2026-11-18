package com.school.app.controller;

import com.school.app.dao.EnseignantDAO;
import com.school.app.dao.ClasseDAO;
import com.school.app.dao.AffectationEnseignantDAO;
import com.school.app.model.Enseignant;
import com.school.app.model.Classe;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.SQLException;

public class EnseignantController {
    @FXML private TextField codeField, nomField, prenomField, phoneField, userField;
    @FXML private PasswordField passField;
    @FXML private ComboBox<Enseignant> enseignantCombo;
    @FXML private ComboBox<Classe> classeCombo;
    @FXML private TableView<Enseignant> enseignantTable;
    @FXML private TableColumn<Enseignant, String> codeCol, nomCol, prenomCol;

    private EnseignantDAO dao = new EnseignantDAO();
    private ClasseDAO classeDAO = new ClasseDAO();
    private AffectationEnseignantDAO affDAO = new AffectationEnseignantDAO();

    @FXML
    public void initialize() {
        if (codeCol != null) {
            codeCol.setCellValueFactory(new PropertyValueFactory<>("code"));
            nomCol.setCellValueFactory(new PropertyValueFactory<>("nom"));
            prenomCol.setCellValueFactory(new PropertyValueFactory<>("prenom"));
            loadData();
        }
    }

    private void loadData() {
        try {
            java.util.List<Enseignant> profs = dao.getAll();
            enseignantTable.setItems(FXCollections.observableArrayList(profs));
            enseignantCombo.setItems(FXCollections.observableArrayList(profs));
            classeCombo.setItems(FXCollections.observableArrayList(classeDAO.getAll()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleAddEnseignant() {
        Enseignant e = new Enseignant();
        e.setCode(codeField.getText());
        e.setNom(nomField.getText());
        e.setPrenom(prenomField.getText());
        e.setTelephone(phoneField.getText());
        e.setNomUtilisateur(userField.getText());
        e.setMotDePasse(passField.getText());
        try {
            dao.add(e);
            loadData();
            clearFields();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void handleAffectation() {
        Enseignant prof = enseignantCombo.getValue();
        Classe cl = classeCombo.getValue();
        
        if (prof == null || cl == null) {
            new Alert(Alert.AlertType.WARNING, "Veuillez choisir un prof et une classe").show();
            return;
        }

        try {
            affDAO.add(prof.getCode(), cl.getIdClasse());
            new Alert(Alert.AlertType.INFORMATION, "Professeur affecté à la classe avec succès !").show();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Erreur lors de l'affectation: " + e.getMessage()).show();
        }
    }

    private void clearFields() {
        codeField.clear(); nomField.clear(); prenomField.clear();
        phoneField.clear(); userField.clear(); passField.clear();
    }
}
