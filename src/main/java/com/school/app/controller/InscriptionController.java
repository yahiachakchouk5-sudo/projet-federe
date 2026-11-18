package com.school.app.controller;

import com.school.app.dao.CommonDAO;
import com.school.app.dao.EleveDAO;
import com.school.app.dao.InscriptionDAO;
import com.school.app.model.Eleve;
import com.school.app.model.Inscription;
import com.school.app.model.Niveau;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.SQLException;

public class InscriptionController {
    @FXML private ComboBox<Eleve> eleveCombo;
    @FXML private ComboBox<String> anneeCombo;
    @FXML private ComboBox<Niveau> niveauCombo;
    @FXML private TextField niveauEtudeField;
    @FXML private TableView<Inscription> inscriptionTable;
    @FXML private TableColumn<Inscription, Integer> idCol;
    @FXML private TableColumn<Inscription, Integer> eleveCol;
    @FXML private TableColumn<Inscription, String> anneeCol;
    @FXML private TableColumn<Inscription, Integer> niveauCol;

    private InscriptionDAO insDAO = new InscriptionDAO();
    private EleveDAO eleveDAO = new EleveDAO();
    private CommonDAO commonDAO = new CommonDAO();

    @FXML
    public void initialize() {
        idCol.setCellValueFactory(new PropertyValueFactory<>("idInscription"));
        eleveCol.setCellValueFactory(new PropertyValueFactory<>("idEleve"));
        anneeCol.setCellValueFactory(new PropertyValueFactory<>("annee"));
        niveauCol.setCellValueFactory(new PropertyValueFactory<>("idNiveau"));

        loadData();
    }

    private void loadData() {
        try {
            eleveCombo.setItems(FXCollections.observableArrayList(eleveDAO.getAll()));
            anneeCombo.setItems(FXCollections.observableArrayList(commonDAO.getAllAnnees()));
            niveauCombo.setItems(FXCollections.observableArrayList(commonDAO.getAllNiveaux()));
            inscriptionTable.setItems(FXCollections.observableArrayList(insDAO.getAll()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleInscription() {
        Inscription i = new Inscription();
        i.setIdEleve(eleveCombo.getValue().getId());
        i.setAnnee(anneeCombo.getValue());
        i.setIdNiveau(niveauCombo.getValue().getIdNiveau());
        i.setNiveauEtude(niveauEtudeField.getText());
        try {
            insDAO.add(i);
            loadData();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
