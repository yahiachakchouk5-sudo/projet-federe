package com.school.app.dao;

import com.school.app.model.AffectationEleve;
import com.school.app.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AffectationDAO {
    public void add(AffectationEleve aff) throws SQLException {
        String query = "INSERT INTO AffectationEleve (idInscription, idClasse, dateAffectation) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, aff.getIdInscription());
            pstmt.setInt(2, aff.getIdClasse());
            pstmt.setDate(3, java.sql.Date.valueOf(aff.getDateAffectation()));
            pstmt.executeUpdate();
        }
    }
}
