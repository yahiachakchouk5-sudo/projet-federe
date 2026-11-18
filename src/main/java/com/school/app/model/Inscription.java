package com.school.app.model;

public class Inscription {
    private int idInscription;
    private String niveauEtude;
    private int idEleve;
    private String annee;
    private int idNiveau;
    private String nomEleve; // Added for display

    public Inscription() {}

    public int getIdInscription() { return idInscription; }
    public void setIdInscription(int idInscription) { this.idInscription = idInscription; }
    public String getNiveauEtude() { return niveauEtude; }
    public void setNiveauEtude(String niveauEtude) { this.niveauEtude = niveauEtude; }
    public int getIdEleve() { return idEleve; }
    public void setIdEleve(int idEleve) { this.idEleve = idEleve; }
    public String getAnnee() { return annee; }
    public void setAnnee(String annee) { this.annee = annee; }
    public int getIdNiveau() { return idNiveau; }
    public void setIdNiveau(int idNiveau) { this.idNiveau = idNiveau; }

    public String getNomEleve() { return nomEleve; }
    public void setNomEleve(String nomEleve) { this.nomEleve = nomEleve; }

    @Override
    public String toString() {
        return (nomEleve != null ? nomEleve : "Élève #" + idEleve) + " [" + niveauEtude + "]";
    }
}
