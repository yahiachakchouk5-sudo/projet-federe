package com.school.app.model;

public class Bulletin {
    private int idBulletin;
    private double moyenneGenerale;
    private int rangClasse;
    private String appreciation;
    private int idInscription;

    public Bulletin() {}

    public int getIdBulletin() { return idBulletin; }
    public void setIdBulletin(int idBulletin) { this.idBulletin = idBulletin; }
    public double getMoyenneGenerale() { return moyenneGenerale; }
    public void setMoyenneGenerale(double moyenneGenerale) { this.moyenneGenerale = moyenneGenerale; }
    public int getRangClasse() { return rangClasse; }
    public void setRangClasse(int rangClasse) { this.rangClasse = rangClasse; }
    public String getAppreciation() { return appreciation; }
    public void setAppreciation(String appreciation) { this.appreciation = appreciation; }
    public int getIdInscription() { return idInscription; }
    public void setIdInscription(int idInscription) { this.idInscription = idInscription; }
}
