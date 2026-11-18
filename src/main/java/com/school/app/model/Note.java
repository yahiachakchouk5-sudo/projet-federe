package com.school.app.model;

public class Note {
    private int idNote;
    private double valeur;
    private int trimestre;
    private int coefficient;
    private int idInscription;
    private int idMatiere;
    private String codeEnseignant;
    private String nomEleve;
    private String nomMatiere;

    public Note() {}

    public int getIdNote() { return idNote; }
    public void setIdNote(int idNote) { this.idNote = idNote; }
    public double getValeur() { return valeur; }
    public void setValeur(double valeur) { this.valeur = valeur; }
    public int getTrimestre() { return trimestre; }
    public void setTrimestre(int trimestre) { this.trimestre = trimestre; }
    public int getCoefficient() { return coefficient; }
    public void setCoefficient(int coefficient) { this.coefficient = coefficient; }
    public int getIdInscription() { return idInscription; }
    public void setIdInscription(int idInscription) { this.idInscription = idInscription; }
    public int getIdMatiere() { return idMatiere; }
    public void setIdMatiere(int idMatiere) { this.idMatiere = idMatiere; }
    public String getCodeEnseignant() { return codeEnseignant; }
    public void setCodeEnseignant(String codeEnseignant) { this.codeEnseignant = codeEnseignant; }
    public String getNomEleve() { return nomEleve; }
    public void setNomEleve(String nomEleve) { this.nomEleve = nomEleve; }
    public String getNomMatiere() { return nomMatiere; }
    public void setNomMatiere(String nomMatiere) { this.nomMatiere = nomMatiere; }
}
