package com.school.app.controller;

import com.school.app.dao.CommonDAO;
import com.school.app.dao.MatiereDAO;
import com.school.app.model.Matiere;
import com.school.app.model.Niveau;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.SQLException;

public class MatiereController {
    @FXML private TextField nomMatiereField;
    @FXML private ComboBox<Niveau> niveauCombo;
    @FXML private TableView<Matiere> matiereTable;
    @FXML private TableColumn<Matiere, Integer> idCol, niveauCol;
    @FXML private TableColumn<Matiere, String> nomCol;

    private MatiereDAO matiereDAO = new MatiereDAO();
    private CommonDAO commonDAO = new CommonDAO();

    @FXML
    public void initialize() {
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        nomCol.setCellValueFactory(new PropertyValueFactory<>("nom"));
        niveauCol.setCellValueFactory(new PropertyValueFactory<>("idNiveau"));
        loadData();
    }

    private void loadData() {
        try {
            niveauCombo.setItems(FXCollections.observableArrayList(commonDAO.getAllNiveaux()));
            matiereTable.setItems(FXCollections.observableArrayList(matiereDAO.getAll()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleAddMatiere() {
        Matiere m = new Matiere();
        m.setNom(nomMatiereField.getText());
        m.setIdNiveau(niveauCombo.getValue().getIdNiveau());
        try {
            matiereDAO.add(m);
            loadData();
            nomMatiereField.clear();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
