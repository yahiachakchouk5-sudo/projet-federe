package com.school.app.controller;

import com.school.app.dao.*;
import com.school.app.model.*;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class ClasseController {
    @FXML private TextField nomClasseField;
    @FXML private ComboBox<Niveau> niveauCombo;
    @FXML private ComboBox<Inscription> inscriptionCombo;
    @FXML private ComboBox<Classe> classeCombo;
    @FXML private TableView<Classe> classeTable;
    @FXML private TableColumn<Classe, Integer> idCol, countCol, niveauCol;
    @FXML private TableColumn<Classe, String> nomCol;
    @FXML private HBox adminBar1, adminBar2;
    @FXML private Separator adminSep;
    @FXML private TableView<Inscription> studentTable;
    @FXML private TableColumn<Inscription, String> studentNameCol;
    @FXML private TableColumn<Inscription, Integer> studentIdCol;

    private ClasseDAO classeDAO = new ClasseDAO();
    private CommonDAO commonDAO = new CommonDAO();
    private InscriptionDAO insDAO = new InscriptionDAO();
    private AffectationDAO affDAO = new AffectationDAO();

    @FXML
    public void initialize() {
        idCol.setCellValueFactory(new PropertyValueFactory<>("idClasse"));
        nomCol.setCellValueFactory(new PropertyValueFactory<>("nomClasse"));
        niveauCol.setCellValueFactory(new PropertyValueFactory<>("idNiveau"));

        studentNameCol.setCellValueFactory(new PropertyValueFactory<>("nomEleve"));
        studentIdCol.setCellValueFactory(new PropertyValueFactory<>("idInscription"));
        
        // Load details when class is selected
        classeTable.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                loadStudents(newVal.getIdClasse());
            }
        });

        checkRole();
        loadData();
    }

    private void checkRole() {
        String role = com.school.app.util.Session.getCurrentRole();
        if ("TEACHER".equals(role)) {
            adminBar1.setVisible(false);
            adminBar1.setManaged(false);
            adminBar2.setVisible(false);
            adminBar2.setManaged(false);
            adminSep.setVisible(false);
            adminSep.setManaged(false);
        }
    }

    private void loadStudents(int idClasse) {
        try {
            studentTable.setItems(FXCollections.observableArrayList(insDAO.getByClasse(idClasse)));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadData() {
        try {
            String role = com.school.app.util.Session.getCurrentRole();
            List<Classe> classes;
            if ("TEACHER".equals(role)) {
                String teacherCode = com.school.app.util.Session.getCurrentUser();
                classes = classeDAO.getClassesByTeacher(teacherCode);
            } else {
                classes = classeDAO.getAll();
                niveauCombo.setItems(FXCollections.observableArrayList(commonDAO.getAllNiveaux()));
                inscriptionCombo.setItems(FXCollections.observableArrayList(insDAO.getAll()));
                classeCombo.setItems(FXCollections.observableArrayList(classes));
            }
            classeTable.setItems(FXCollections.observableArrayList(classes));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleCreateClasse() {
        Classe c = new Classe();
        c.setNomClasse(nomClasseField.getText());
        c.setIdNiveau(niveauCombo.getValue().getIdNiveau());
        c.setCapaciteMax(20);
        try {
            classeDAO.add(c);
            loadData();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleAffectation() {
        Classe selectedClasse = classeCombo.getValue();
        Inscription selectedIns = inscriptionCombo.getValue();
        
        try {
            int currentCount = classeDAO.countStudentsInClass(selectedClasse.getIdClasse());
            if (currentCount >= 20) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "La classe est pleine (Max 20 élèves) !");
                alert.show();
                return;
            }
            
            AffectationEleve aff = new AffectationEleve();
            aff.setIdInscription(selectedIns.getIdInscription());
            aff.setIdClasse(selectedClasse.getIdClasse());
            aff.setDateAffectation(LocalDate.now());
            
            affDAO.add(aff);
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Élève affecté avec succès !");
            alert.show();
            loadData();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
