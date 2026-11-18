package com.school.app.controller;

import com.school.app.dao.*;
import com.school.app.model.Inscription;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.SQLException;

public class DashboardController {
    @FXML private Label studentCount, teacherCount, classCount;
    @FXML private TableView<Inscription> recentTable;
    @FXML private TableColumn<Inscription, Integer> idCol;
    @FXML private TableColumn<Inscription, String> eleveCol, niveauCol, anneeCol;

    private EleveDAO eleveDAO = new EleveDAO();
    private EnseignantDAO profDAO = new EnseignantDAO();
    private ClasseDAO classeDAO = new ClasseDAO();
    private InscriptionDAO insDAO = new InscriptionDAO();

    @FXML
    public void initialize() {
        idCol.setCellValueFactory(new PropertyValueFactory<>("idInscription"));
        eleveCol.setCellValueFactory(new PropertyValueFactory<>("nomEleve"));
        niveauCol.setCellValueFactory(new PropertyValueFactory<>("niveauEtude"));
        anneeCol.setCellValueFactory(new PropertyValueFactory<>("annee"));

        refresh();
    }

    public void refresh() {
        try {
            studentCount.setText(String.valueOf(eleveDAO.getAll().size()));
            teacherCount.setText(String.valueOf(profDAO.getAll().size()));
            classCount.setText(String.valueOf(classeDAO.getAll().size()));
            
            recentTable.setItems(FXCollections.observableArrayList(insDAO.getAll()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
