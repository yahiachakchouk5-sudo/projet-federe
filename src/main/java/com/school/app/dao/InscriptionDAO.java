package com.school.app.dao;

import com.school.app.model.Inscription;
import com.school.app.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InscriptionDAO {
    public List<Inscription> getAll() throws SQLException {
        List<Inscription> list = new ArrayList<>();
        String query = "SELECT i.*, e.nom, e.prenom FROM Inscription i JOIN Eleve e ON i.idEleve = e.idEleve";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                Inscription ins = new Inscription();
                ins.setIdInscription(rs.getInt("idInscription"));
                ins.setIdEleve(rs.getInt("idEleve"));
                ins.setAnnee(rs.getString("annee"));
                ins.setNiveauEtude(rs.getString("niveauEtude"));
                ins.setIdNiveau(rs.getInt("idNiveau"));
                ins.setNomEleve(rs.getString("prenom") + " " + rs.getString("nom"));
                list.add(ins);
            }
        }
        return list;
    }

    public int add(Inscription ins) throws SQLException {
        String query = "INSERT INTO Inscription (idEleve, annee, niveauEtude, idNiveau) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setInt(1, ins.getIdEleve());
            pstmt.setString(2, ins.getAnnee());
            pstmt.setString(3, ins.getNiveauEtude());
            pstmt.setInt(4, ins.getIdNiveau());
            pstmt.executeUpdate();
            
            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) return rs.getInt(1);
            }
        }
        return -1;
    }

    public List<Inscription> getByClasse(int idClasse) throws SQLException {
        List<Inscription> list = new ArrayList<>();
        String query = "SELECT i.*, e.nom, e.prenom FROM Inscription i " +
                       "JOIN Eleve e ON i.idEleve = e.idEleve " +
                       "JOIN AffectationEleve a ON i.idInscription = a.idInscription " +
                       "WHERE a.idClasse = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, idClasse);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Inscription ins = new Inscription();
                    ins.setIdInscription(rs.getInt("idInscription"));
                    ins.setIdEleve(rs.getInt("idEleve"));
                    ins.setAnnee(rs.getString("annee"));
                    ins.setNiveauEtude(rs.getString("niveauEtude"));
                    ins.setIdNiveau(rs.getInt("idNiveau"));
                    ins.setNomEleve(rs.getString("prenom") + " " + rs.getString("nom"));
                    list.add(ins);
                }
            }
        }
        return list;
    }

    public Inscription getInscriptionByEleve(int idEleve) throws SQLException {
        String query = "SELECT i.*, e.nom, e.prenom FROM Inscription i JOIN Eleve e ON i.idEleve = e.idEleve WHERE i.idEleve = ? LIMIT 1";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, idEleve);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Inscription ins = new Inscription();
                    ins.setIdInscription(rs.getInt("idInscription"));
                    ins.setIdEleve(rs.getInt("idEleve"));
                    ins.setAnnee(rs.getString("annee"));
                    ins.setNiveauEtude(rs.getString("niveauEtude"));
                    ins.setIdNiveau(rs.getInt("idNiveau"));
                    ins.setNomEleve(rs.getString("prenom") + " " + rs.getString("nom"));
                    return ins;
                }
            }
        }
        return null;
    }
}
