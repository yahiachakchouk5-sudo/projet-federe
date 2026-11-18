package com.school.app.dao;

import com.school.app.model.Matiere;
import com.school.app.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MatiereDAO {
    public List<Matiere> getAll() throws SQLException {
        List<Matiere> list = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM Matiere")) {
            while (rs.next()) {
                list.add(new Matiere(rs.getInt("idMatiere"), rs.getString("nomMatiere"), rs.getInt("idNiveau")));
            }
        }
        return list;
    }

    public void add(Matiere m) throws SQLException {
        String query = "INSERT INTO Matiere (nomMatiere, idNiveau) VALUES (?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, m.getNom());
            pstmt.setInt(2, m.getIdNiveau());
            pstmt.executeUpdate();
        }
    }
}
