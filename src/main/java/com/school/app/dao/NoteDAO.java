package com.school.app.dao;

import com.school.app.model.Note;
import com.school.app.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NoteDAO {
    public void add(Note note) throws SQLException {
        String query = "INSERT INTO Note (valeur, trimestre, coefficient, idInscription, idMatiere, codeEnseignant) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setDouble(1, note.getValeur());
            pstmt.setInt(2, note.getTrimestre());
            pstmt.setInt(3, note.getCoefficient());
            pstmt.setInt(4, note.getIdInscription());
            pstmt.setInt(5, note.getIdMatiere());
            pstmt.setString(6, note.getCodeEnseignant());
            pstmt.executeUpdate();
        }
    }

    private Note mapRow(ResultSet rs) throws SQLException {
        Note n = new Note();
        n.setIdNote(rs.getInt("idNote"));
        n.setValeur(rs.getDouble("valeur"));
        n.setTrimestre(rs.getInt("trimestre"));
        n.setCoefficient(rs.getInt("coefficient"));
        n.setIdInscription(rs.getInt("idInscription"));
        n.setIdMatiere(rs.getInt("idMatiere"));
        n.setCodeEnseignant(rs.getString("codeEnseignant"));
        n.setNomEleve(rs.getString("prenom") + " " + rs.getString("nom"));
        n.setNomMatiere(rs.getString("nomMatiere"));
        return n;
    }

    public List<Note> getAll() throws SQLException {
        List<Note> notes = new ArrayList<>();
        String query = "SELECT nt.*, e.nom, e.prenom, m.nomMatiere FROM Note nt " +
                       "JOIN Inscription i ON nt.idInscription = i.idInscription " +
                       "JOIN Eleve e ON i.idEleve = e.idEleve " +
                       "JOIN Matiere m ON nt.idMatiere = m.idMatiere";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                notes.add(mapRow(rs));
            }
        }
        return notes;
    }

    public List<Note> getNotesByInscription(int idInscription) throws SQLException {
        List<Note> notes = new ArrayList<>();
        String query = "SELECT nt.*, e.nom, e.prenom, m.nomMatiere FROM Note nt " +
                       "JOIN Inscription i ON nt.idInscription = i.idInscription " +
                       "JOIN Eleve e ON i.idEleve = e.idEleve " +
                       "JOIN Matiere m ON nt.idMatiere = m.idMatiere " +
                       "WHERE nt.idInscription = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, idInscription);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    notes.add(mapRow(rs));
                }
            }
        }
        return notes;
    }

    public List<Note> getNotesByTeacher(String codeEnseignant) throws SQLException {
        List<Note> notes = new ArrayList<>();
        String query = "SELECT nt.*, e.nom, e.prenom, m.nomMatiere FROM Note nt " +
                       "JOIN Inscription i ON nt.idInscription = i.idInscription " +
                       "JOIN Eleve e ON i.idEleve = e.idEleve " +
                       "JOIN Matiere m ON nt.idMatiere = m.idMatiere " +
                       "WHERE nt.codeEnseignant = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, codeEnseignant);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    notes.add(mapRow(rs));
                }
            }
        }
        return notes;
    }

    public void update(Note note) throws SQLException {
        String query = "UPDATE Note SET valeur = ?, trimestre = ?, coefficient = ?, idInscription = ?, idMatiere = ? WHERE idNote = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setDouble(1, note.getValeur());
            pstmt.setInt(2, note.getTrimestre());
            pstmt.setInt(3, note.getCoefficient());
            pstmt.setInt(4, note.getIdInscription());
            pstmt.setInt(5, note.getIdMatiere());
            pstmt.setInt(6, note.getIdNote());
            pstmt.executeUpdate();
        }
    }
}
