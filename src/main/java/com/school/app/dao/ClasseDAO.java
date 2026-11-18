package com.school.app.dao;

import com.school.app.model.Classe;
import com.school.app.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClasseDAO {
    public List<Classe> getAll() throws SQLException {
        List<Classe> list = new ArrayList<>();
        String query = "SELECT * FROM Classe";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                list.add(new Classe(
                    rs.getInt("idClasse"),
                    rs.getString("nomClasse"),
                    rs.getInt("capaciteMax"),
                    rs.getInt("idNiveau")
                ));
            }
        }
        return list;
    }

    public void add(Classe c) throws SQLException {
        String query = "INSERT INTO Classe (nomClasse, capaciteMax, idNiveau) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, c.getNomClasse());
            pstmt.setInt(2, c.getCapaciteMax());
            pstmt.setInt(3, c.getIdNiveau());
            pstmt.executeUpdate();
        }
    }
    
    public int countStudentsInClass(int idClasse) throws SQLException {
        String query = "SELECT COUNT(*) FROM AffectationEleve WHERE idClasse = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, idClasse);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) return rs.getInt(1);
            }
        }
        return 0;
    }

    public String getClassNameByInscription(int idInscription) throws SQLException {
        String query = "SELECT c.nomClasse FROM Classe c JOIN AffectationEleve a ON c.idClasse = a.idClasse WHERE a.idInscription = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, idInscription);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) return rs.getString("nomClasse");
            }
        }
        return "Non affecté";
    }

    public List<Classe> getClassesByNiveau(int idNiveau) throws SQLException {
        List<Classe> list = new ArrayList<>();
        String query = "SELECT * FROM Classe WHERE idNiveau = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, idNiveau);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    list.add(new Classe(
                        rs.getInt("idClasse"),
                        rs.getString("nomClasse"),
                        rs.getInt("capaciteMax"),
                        rs.getInt("idNiveau")
                    ));
                }
            }
        }
        return list;
    }

    public List<Classe> getClassesByTeacher(String codeEnseignant) throws SQLException {
        List<Classe> list = new ArrayList<>();
        String query = "SELECT c.* FROM Classe c " +
                       "JOIN AffectationEnseignant ae ON c.idClasse = ae.idClasse " +
                       "WHERE ae.codeEnseignant = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, codeEnseignant);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    list.add(new Classe(
                        rs.getInt("idClasse"),
                        rs.getString("nomClasse"),
                        rs.getInt("capaciteMax"),
                        rs.getInt("idNiveau")
                    ));
                }
            }
        }
        return list;
    }
}
