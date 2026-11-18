package com.school.app.controller;

import com.school.app.dao.EleveDAO;
import com.school.app.model.Eleve;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.SQLException;
import java.time.LocalDate;

public class EleveController {
    @FXML private TextField nomField;
    @FXML private TextField prenomField;
    @FXML private DatePicker datePicker;
    @FXML private TableView<Eleve> eleveTable;
    @FXML private TableColumn<Eleve, Integer> idCol;
    @FXML private TableColumn<Eleve, String> nomCol;
    @FXML private TableColumn<Eleve, String> prenomCol;
    @FXML private TableColumn<Eleve, LocalDate> dateCol;

    private EleveDAO eleveDAO = new EleveDAO();
    private ObservableList<Eleve> eleveList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        nomCol.setCellValueFactory(new PropertyValueFactory<>("nom"));
        prenomCol.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        dateCol.setCellValueFactory(new PropertyValueFactory<>("dateNaissance"));
        
        eleveTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                nomField.setText(newSelection.getNom());
                prenomField.setText(newSelection.getPrenom());
                datePicker.setValue(newSelection.getDateNaissance());
            }
        });

        loadEleves();
    }

    private void loadEleves() {
        try {
            eleveList.setAll(eleveDAO.getAll());
            eleveTable.setItems(eleveList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleAddEleve() {
        Eleve e = new Eleve();
        e.setNom(nomField.getText());
        e.setPrenom(prenomField.getText());
        e.setDateNaissance(datePicker.getValue());
        try {
            eleveDAO.add(e);
            loadEleves();
            clearFields();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void handleUpdateEleve() {
        Eleve selected = eleveTable.getSelectionModel().getSelectedItem();
        if (selected == null) return;
        
        selected.setNom(nomField.getText());
        selected.setPrenom(prenomField.getText());
        selected.setDateNaissance(datePicker.getValue());
        
        try {
            eleveDAO.update(selected);
            loadEleves();
            clearFields();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void handleDeleteEleve() {
        Eleve selected = eleveTable.getSelectionModel().getSelectedItem();
        if (selected == null) return;
        
        try {
            eleveDAO.delete(selected.getId());
            loadEleves();
            clearFields();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void clearFields() {
        nomField.clear(); prenomField.clear(); datePicker.setValue(null);
    }
}
