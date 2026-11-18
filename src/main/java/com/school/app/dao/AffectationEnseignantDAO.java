package com.school.app.dao;

import com.school.app.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AffectationEnseignantDAO {
    
    public void ensureTableExists() {
        String sql = "CREATE TABLE IF NOT EXISTS AffectationEnseignant (" +
                     "codeEnseignant VARCHAR(50) NOT NULL, " +
                     "idClasse INT NOT NULL, " +
                     "PRIMARY KEY (codeEnseignant, idClasse), " +
                     "FOREIGN KEY (codeEnseignant) REFERENCES Enseignant(code) ON DELETE CASCADE, " +
                     "FOREIGN KEY (idClasse) REFERENCES Classe(idClasse) ON DELETE CASCADE" +
                     ") ENGINE=InnoDB;";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void add(String codeEnseignant, int idClasse) throws SQLException {
        ensureTableExists();
        String query = "INSERT IGNORE INTO AffectationEnseignant (codeEnseignant, idClasse) VALUES (?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, codeEnseignant);
            pstmt.setInt(2, idClasse);
            pstmt.executeUpdate();
        }
    }
}
