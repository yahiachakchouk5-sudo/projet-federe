package com.school.app.controller;

import com.school.app.dao.*;
import com.school.app.model.*;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.SQLException;
import java.util.List;

public class NoteController {
    @FXML private ComboBox<Classe> classeCombo;
    @FXML private ComboBox<Matiere> matiereCombo;
    @FXML private ComboBox<Integer> trimestreCombo;
    @FXML private ComboBox<Inscription> inscriptionCombo;
    @FXML private TextField valeurField;
    @FXML private TableView<Note> noteTable;
    @FXML private TableColumn<Note, Integer> eleveCol, matiereCol, noteCol, trimestreCol;

    private ClasseDAO classeDAO = new ClasseDAO();
    private MatiereDAO matiereDAO = new MatiereDAO();
    private NoteDAO noteDAO = new NoteDAO();
    private InscriptionDAO insDAO = new InscriptionDAO();

    private Note selectedNote;

    @FXML
    public void initialize() {
        eleveCol.setCellValueFactory(new PropertyValueFactory<>("nomEleve"));
        matiereCol.setCellValueFactory(new PropertyValueFactory<>("nomMatiere"));
        noteCol.setCellValueFactory(new PropertyValueFactory<>("valeur"));
        trimestreCol.setCellValueFactory(new PropertyValueFactory<>("trimestre"));

        trimestreCombo.setItems(FXCollections.observableArrayList(1, 2, 3));
        
        loadBasicData();
        loadNotes();

        // Add filter listener
        classeCombo.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                try {
                    inscriptionCombo.setItems(FXCollections.observableArrayList(insDAO.getByClasse(newVal.getIdClasse())));
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });

        // Selection listener for update
        noteTable.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                selectedNote = newVal;
                valeurField.setText(String.valueOf(newVal.getValeur()));
                trimestreCombo.setValue(newVal.getTrimestre());
                // For combos, we search the corresponding objects
                // For simplicity, we just set the values if they are loaded
            }
        });
    }

    public void refresh() {
        loadBasicData();
        loadNotes();
    }

    private void loadNotes() {
        try {
            String role = com.school.app.util.Session.getCurrentRole();
            if ("TEACHER".equals(role)) {
                String teacherCode = com.school.app.util.Session.getCurrentUser();
                noteTable.setItems(FXCollections.observableArrayList(noteDAO.getNotesByTeacher(teacherCode)));
            } else {
                noteTable.setItems(FXCollections.observableArrayList(noteDAO.getAll()));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadBasicData() {
        try {
            String role = com.school.app.util.Session.getCurrentRole();
            if ("TEACHER".equals(role)) {
                String teacherCode = com.school.app.util.Session.getCurrentUser();
                classeCombo.setItems(FXCollections.observableArrayList(classeDAO.getClassesByTeacher(teacherCode)));
                matiereCombo.setItems(FXCollections.observableArrayList(matiereDAO.getAll()));
            } else {
                classeCombo.setItems(FXCollections.observableArrayList(classeDAO.getAll()));
                matiereCombo.setItems(FXCollections.observableArrayList(matiereDAO.getAll()));
                inscriptionCombo.setItems(FXCollections.observableArrayList(insDAO.getAll()));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleSaveNote() {
        try {
            if (selectedNote == null) {
                // Add new
                Note n = new Note();
                n.setValeur(Double.parseDouble(valeurField.getText()));
                n.setTrimestre(trimestreCombo.getValue());
                n.setIdInscription(inscriptionCombo.getValue().getIdInscription());
                n.setIdMatiere(matiereCombo.getValue().getId());
                n.setCodeEnseignant(com.school.app.util.Session.getCurrentUser());
                n.setCoefficient(1);
                noteDAO.add(n);
                new Alert(Alert.AlertType.INFORMATION, "Note enregistrée !").show();
            } else {
                // Update existing
                selectedNote.setValeur(Double.parseDouble(valeurField.getText()));
                selectedNote.setTrimestre(trimestreCombo.getValue());
                // We could also update matiere/inscription if selected
                if (inscriptionCombo.getValue() != null) selectedNote.setIdInscription(inscriptionCombo.getValue().getIdInscription());
                if (matiereCombo.getValue() != null) selectedNote.setIdMatiere(matiereCombo.getValue().getId());
                
                noteDAO.update(selectedNote);
                new Alert(Alert.AlertType.INFORMATION, "Note mise à jour !").show();
                selectedNote = null;
            }
            
            valeurField.clear();
            loadNotes();
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Erreur: " + e.getMessage()).show();
        }
    }

    @FXML
    private void handleClear() {
        selectedNote = null;
        valeurField.clear();
        trimestreCombo.getSelectionModel().clearSelection();
        inscriptionCombo.getSelectionModel().clearSelection();
        matiereCombo.getSelectionModel().clearSelection();
    }
}
