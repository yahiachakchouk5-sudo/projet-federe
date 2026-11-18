package com.school.app.model;

public class Matiere {
    private int id;
    private String nom;
    private int idNiveau;

    public Matiere() {}
    public Matiere(int id, String nom, int idNiveau) {
        this.id = id;
        this.nom = nom;
        this.idNiveau = idNiveau;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    public int getIdNiveau() { return idNiveau; }
    public void setIdNiveau(int idNiveau) { this.idNiveau = idNiveau; }

    @Override
    public String toString() { return nom; }
}
