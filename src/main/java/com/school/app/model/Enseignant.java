package com.school.app.model;

public class Enseignant {
    private String code;
    private String nom;
    private String prenom;
    private String telephone;
    private String nomUtilisateur;
    private String motDePasse;
    private String role; // ADMIN or TEACHER

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public Enseignant() {}

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    public String getPrenom() { return prenom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }
    public String getTelephone() { return telephone; }
    public void setTelephone(String telephone) { this.telephone = telephone; }
    public String getNomUtilisateur() { return nomUtilisateur; }
    public void setNomUtilisateur(String nomUtilisateur) { this.nomUtilisateur = nomUtilisateur; }
    public String getMotDePasse() { return motDePasse; }
    public void setMotDePasse(String motDePasse) { this.motDePasse = motDePasse; }

    @Override
    public String toString() { return prenom + " " + nom; }
}
