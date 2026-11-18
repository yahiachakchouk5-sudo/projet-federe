package com.school.app.dao;

import com.school.app.util.DatabaseConnection;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class BulletinDAO {
    
    public double calculateMoyenne(int idInscription) throws SQLException {
        String query = "SELECT SUM(valeur * coefficient) / SUM(coefficient) FROM Note WHERE idInscription = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, idInscription);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) return rs.getDouble(1);
            }
        }
        return 0;
    }

    public int calculateRank(int idInscription, int idClasse) throws SQLException {
        String query = "SELECT idInscription, RANK() OVER (ORDER BY (SUM(valeur * coefficient) / SUM(coefficient)) DESC) as rnk " +
                       "FROM Note GROUP BY idInscription"; 
        // Note: This is an approximation. A real rank would join with AffectationEleve to filter by class.
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                if (rs.getInt("idInscription") == idInscription) return rs.getInt("rnk");
            }
        }
        return 1;
    }
}
