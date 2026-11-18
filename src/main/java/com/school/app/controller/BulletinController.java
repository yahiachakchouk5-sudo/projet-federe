package com.school.app.controller;

import com.school.app.dao.BulletinDAO;
import com.school.app.dao.InscriptionDAO;
import com.school.app.dao.NoteDAO;
import com.school.app.model.Inscription;
import com.school.app.model.Note;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;

import java.sql.SQLException;

public class BulletinController {
    @FXML private ComboBox<com.school.app.model.Classe> classeCombo;
    @FXML private ComboBox<Inscription> inscriptionCombo;
    @FXML private HBox controlBox;
    @FXML private VBox bulletinContainer;
    @FXML private Label eleveLabel, classeLabel, anneeLabel, moyenneLabel, rangLabel;
    @FXML private TableView<Note> notesTable;
    @FXML private TableColumn<Note, String> matiereCol;
    @FXML private TableColumn<Note, Double> noteCol;
    @FXML private TableColumn<Note, Integer> coefCol;
    @FXML private TextArea appreciationArea;

    private InscriptionDAO insDAO = new InscriptionDAO();
    private com.school.app.dao.ClasseDAO classeDAO = new com.school.app.dao.ClasseDAO();
    private BulletinDAO bulletinDAO = new BulletinDAO();

    @FXML
    public void initialize() {
        matiereCol.setCellValueFactory(new PropertyValueFactory<>("idMatiere"));
        noteCol.setCellValueFactory(new PropertyValueFactory<>("valeur"));
        coefCol.setCellValueFactory(new PropertyValueFactory<>("coefficient"));
        
        loadBasicData();

        // Role-based logic
        String role = com.school.app.util.Session.getCurrentRole();
        if ("STUDENT".equals(role)) {
            // Hide whole control bar for students
            controlBox.setVisible(false);
            controlBox.setManaged(false);
            
            // Auto-load student data
            try {
                int studentId = com.school.app.util.Session.getCurrentUserId();
                Inscription myIns = insDAO.getInscriptionByEleve(studentId);
                if (myIns != null) {
                    inscriptionCombo.setValue(myIns);
                    handleGenerate();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            // Filter listener for Admin/Teacher
            classeCombo.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
                if (newVal != null) {
                    try {
                        inscriptionCombo.setItems(FXCollections.observableArrayList(insDAO.getByClasse(newVal.getIdClasse())));
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    private void loadBasicData() {
        try {
            String role = com.school.app.util.Session.getCurrentRole();
            if ("TEACHER".equals(role)) {
                String teacherCode = com.school.app.util.Session.getCurrentUser();
                classeCombo.setItems(FXCollections.observableArrayList(classeDAO.getClassesByTeacher(teacherCode)));
            } else {
                classeCombo.setItems(FXCollections.observableArrayList(classeDAO.getAll()));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private NoteDAO noteDAO = new NoteDAO();

    @FXML
    private void handleGenerate() {
        Inscription ins = inscriptionCombo.getValue();
        if (ins == null) return;

        try {
            double moyenne = bulletinDAO.calculateMoyenne(ins.getIdInscription());
            int rang = bulletinDAO.calculateRank(ins.getIdInscription(), 1);

            eleveLabel.setText("Nom: " + ins.getNomEleve());
            classeLabel.setText("Niveau: " + ins.getNiveauEtude());
            anneeLabel.setText(ins.getAnnee());
            moyenneLabel.setText(String.format("%.2f / 20", moyenne));
            rangLabel.setText(rang + " / " + 20);

            // Populate Table
            notesTable.setItems(FXCollections.observableArrayList(noteDAO.getNotesByInscription(ins.getIdInscription())));

            bulletinContainer.setVisible(true);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
