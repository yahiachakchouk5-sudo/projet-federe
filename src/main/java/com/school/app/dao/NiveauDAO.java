package com.school.app.dao;

import com.school.app.model.Niveau;
import com.school.app.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NiveauDAO {
    public List<Niveau> getAll() throws SQLException {
        List<Niveau> list = new ArrayList<>();
        String query = "SELECT * FROM Niveau";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                Niveau n = new Niveau();
                n.setIdNiveau(rs.getInt("idNiveau"));
                n.setNomNiveau(rs.getString("nomNiveau"));
                list.add(n);
            }
        }
        return list;
    }
}
