package ca.qc.cgmatane.fruitride.modele;

import java.util.HashMap;

public class Utilisateur {

    protected String nom;
    protected String prenom;
    protected int niveau;
    protected float experience;

    public Utilisateur(String nom, String prenom, int niveau, float experience) {
        this.nom = nom;
        this.prenom = prenom;
        this.niveau = niveau;
        this.experience = experience;
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

    public HashMap<String, String> obtenirUtilisateurPourAdapteur() {
        HashMap<String,String> utilisateurPourAdapteur = new HashMap<String, String>();
        utilisateurPourAdapteur.put("nom", this.nom);
        utilisateurPourAdapteur.put("prenom", this.prenom);
        utilisateurPourAdapteur.put("niveau", this.niveau + "");
        utilisateurPourAdapteur.put("experience", this.experience + "");
        return utilisateurPourAdapteur;
    }
}
