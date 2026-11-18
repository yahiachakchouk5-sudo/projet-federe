package com.school.app.model;

import java.time.LocalDate;

public class AffectationEleve {
    private int idAffectation;
    private int idInscription;
    private int idClasse;
    private LocalDate dateAffectation;

    public AffectationEleve() {}

    public int getIdAffectation() { return idAffectation; }
    public void setIdAffectation(int idAffectation) { this.idAffectation = idAffectation; }
    public int getIdInscription() { return idInscription; }
    public void setIdInscription(int idInscription) { this.idInscription = idInscription; }
    public int getIdClasse() { return idClasse; }
    public void setIdClasse(int idClasse) { this.idClasse = idClasse; }
    public LocalDate getDateAffectation() { return dateAffectation; }
    public void setDateAffectation(LocalDate dateAffectation) { this.dateAffectation = dateAffectation; }
}
