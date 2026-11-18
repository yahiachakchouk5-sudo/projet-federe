package com.school.app.dao;

import com.school.app.model.Eleve;
import com.school.app.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EleveDAO {
    public List<Eleve> getAll() throws SQLException {
        List<Eleve> eleves = new ArrayList<>();
        String query = "SELECT * FROM Eleve";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                eleves.add(new Eleve(
                    rs.getInt("idEleve"),
                    rs.getString("nom"),
                    rs.getString("prenom"),
                    rs.getDate("dateNaissance").toLocalDate(),
                    rs.getString("adresse"),
                    rs.getString("telephoneParent")
                ));
            }
        }
        return eleves;
    }

    public int add(Eleve e) throws SQLException {
        String query = "INSERT INTO Eleve (nom, prenom, dateNaissance, adresse, telephoneParent, nomUtilisateur, motDePasse) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, e.getNom());
            pstmt.setString(2, e.getPrenom());
            pstmt.setDate(3, java.sql.Date.valueOf(e.getDateNaissance()));
            pstmt.setString(4, e.getAdresse());
            pstmt.setString(5, e.getTelephoneParent());
            pstmt.setString(6, e.getNomUtilisateur());
            pstmt.setString(7, e.getMotDePasse());
            pstmt.executeUpdate();
            
            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) return rs.getInt(1);
            }
        }
        return -1;
    }

    public void update(Eleve e) throws SQLException {
        String query = "UPDATE Eleve SET nom=?, prenom=?, dateNaissance=?, adresse=?, telephoneParent=?, nomUtilisateur=?, motDePasse=? WHERE idEleve=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, e.getNom());
            pstmt.setString(2, e.getPrenom());
            pstmt.setDate(3, java.sql.Date.valueOf(e.getDateNaissance()));
            pstmt.setString(4, e.getAdresse());
            pstmt.setString(5, e.getTelephoneParent());
            pstmt.setString(6, e.getNomUtilisateur());
            pstmt.setString(7, e.getMotDePasse());
            pstmt.setInt(8, e.getId());
            pstmt.executeUpdate();
        }
    }

    public void delete(int id) throws SQLException {
        String query = "DELETE FROM Eleve WHERE idEleve = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        }
    }

    public Eleve authenticate(String username, String password) throws SQLException {
        String query = "SELECT * FROM Eleve WHERE nomUtilisateur = ? AND motDePasse = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);
             try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Eleve e = new Eleve();
                    e.setId(rs.getInt("idEleve"));
                    e.setNom(rs.getString("nom"));
                    e.setPrenom(rs.getString("prenom"));
                    e.setNomUtilisateur(rs.getString("nomUtilisateur"));
                    return e;
                }
            }
        }
        return null;
    }
}
