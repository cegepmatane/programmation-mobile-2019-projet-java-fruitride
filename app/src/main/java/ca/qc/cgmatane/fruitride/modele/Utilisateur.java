package ca.qc.cgmatane.fruitride.modele;

import java.util.HashMap;

public class Utilisateur {

    protected String nom;
    protected String prenom;
    protected int niveau;
    protected float experience;
    protected int id_utilisateur;

    public Utilisateur(String nom, String prenom, int niveau, float experience, int id_utilisateur) {
        this.nom = nom;
        this.prenom = prenom;
        this.niveau = niveau;
        this.experience = experience;
        this.id_utilisateur = id_utilisateur;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public int getNiveau() {
        return niveau;
    }

    public void setNiveau(int niveau) {
        this.niveau = niveau;
    }

    public float getExperience() {
        return experience;
    }

    public void setExperience(float experience) {
        this.experience = experience;
    }

    public int getId_utilisateur() {
        return id_utilisateur;
    }

    public void setId_utilisateur(int id_utilisateur) {
        this.id_utilisateur = id_utilisateur;
    }

    public HashMap<String, String> obtenirUtilisateurPourAdapteur() {
        HashMap<String,String> utilisateurPourAdapteur = new HashMap<String, String>();
        utilisateurPourAdapteur.put("nom", this.nom);
        utilisateurPourAdapteur.put("prenom", this.prenom);
        utilisateurPourAdapteur.put("niveau", this.niveau + "");
        utilisateurPourAdapteur.put("experience", this.experience + "");
        utilisateurPourAdapteur.put("id_utilisateur", this.id_utilisateur + "");
        return utilisateurPourAdapteur;
    }
}
