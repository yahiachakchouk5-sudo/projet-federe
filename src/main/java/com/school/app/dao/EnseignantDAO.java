package com.school.app.dao;

import com.school.app.model.Enseignant;
import com.school.app.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EnseignantDAO {
    // Already has authenticate and add from previous turns, let's add getAll
    public List<Enseignant> getAll() throws SQLException {
        List<Enseignant> list = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM Enseignant")) {
            while (rs.next()) {
                Enseignant e = new Enseignant();
                e.setCode(rs.getString("code"));
                e.setNom(rs.getString("nom"));
                e.setPrenom(rs.getString("prenom"));
                e.setTelephone(rs.getString("telephone"));
                e.setNomUtilisateur(rs.getString("nomUtilisateur"));
                list.add(e);
            }
        }
        return list;
    }

    public Enseignant authenticate(String username, String password) throws SQLException {
        String query = "SELECT * FROM Enseignant WHERE nomUtilisateur = ? AND motDePasse = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);
             try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Enseignant e = new Enseignant();
                    e.setCode(rs.getString("code"));
                    e.setNom(rs.getString("nom"));
                    e.setPrenom(rs.getString("prenom"));
                    e.setNomUtilisateur(rs.getString("nomUtilisateur"));
                    e.setRole(rs.getString("role"));
                    return e;
                }
            }
        }
        return null;
    }

    public void add(Enseignant e) throws SQLException {
        String query = "INSERT INTO Enseignant (code, nom, prenom, telephone, nomUtilisateur, motDePasse) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, e.getCode());
            pstmt.setString(2, e.getNom());
            pstmt.setString(3, e.getPrenom());
            pstmt.setString(4, e.getTelephone());
            pstmt.setString(5, e.getNomUtilisateur());
            pstmt.setString(6, e.getMotDePasse());
            pstmt.executeUpdate();
        }
    }
}
