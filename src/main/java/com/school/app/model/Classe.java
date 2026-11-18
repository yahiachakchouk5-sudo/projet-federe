package com.school.app.model;

public class Classe {
    private int idClasse;
    private String nomClasse;
    private int capaciteMax;
    private int idNiveau;

    public Classe() {}
    public Classe(int idClasse, String nomClasse, int capaciteMax, int idNiveau) {
        this.idClasse = idClasse;
        this.nomClasse = nomClasse;
        this.capaciteMax = capaciteMax;
        this.idNiveau = idNiveau;
    }

    public int getIdClasse() { return idClasse; }
    public void setIdClasse(int idClasse) { this.idClasse = idClasse; }
    public String getNomClasse() { return nomClasse; }
    public void setNomClasse(String nomClasse) { this.nomClasse = nomClasse; }
    public int getCapaciteMax() { return capaciteMax; }
    public void setCapaciteMax(int capaciteMax) { this.capaciteMax = capaciteMax; }
    public int getIdNiveau() { return idNiveau; }
    public void setIdNiveau(int idNiveau) { this.idNiveau = idNiveau; }

    @Override
    public String toString() { return nomClasse; }
}
