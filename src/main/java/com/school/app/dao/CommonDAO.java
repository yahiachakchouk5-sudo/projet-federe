package com.school.app.dao;

import com.school.app.model.Niveau;
import com.school.app.util.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CommonDAO {
    public List<Niveau> getAllNiveaux() throws SQLException {
        List<Niveau> list = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM Niveau")) {
            while (rs.next()) {
                list.add(new Niveau(rs.getInt("idNiveau"), rs.getString("nomNiveau")));
            }
        }
        return list;
    }

    public List<String> getAllAnnees() throws SQLException {
        List<String> list = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT annee FROM AnneeScolaire")) {
            while (rs.next()) {
                list.add(rs.getString("annee"));
            }
        }
        return list;
    }
}
