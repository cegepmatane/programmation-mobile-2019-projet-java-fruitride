package ca.qc.cgmatane.fruitride.modele;

import java.util.Calendar;

public class Activite {
    protected int idActivite;
    protected Calendar date;
    protected float nombreDePas;
    protected int nombreDeFruitsRamasses;
    protected int idUtilisateur;

    public Activite(Calendar date, float nombreDePas, int nombreDeFruitsRamasses, int idUtilisateur) {
        this.date = date;
        this.nombreDePas = nombreDePas;
        this.nombreDeFruitsRamasses = nombreDeFruitsRamasses;
        this.idUtilisateur = idUtilisateur;
    }

    public Activite(float nombreDePas) {
        this.nombreDePas = nombreDePas;
    }

    public int getIdActivite() {
        return idActivite;
    }

    public void setIdActivite(int idActivite) {
        this.idActivite = idActivite;
    }

    public Calendar getDate() {
        return date;
    }

    public void setDate(Calendar date) {
        this.date = date;
    }

    public float getNombreDePas() {
        return nombreDePas;
    }

    public void setNombreDePas(float nombreDePas) {
        this.nombreDePas = nombreDePas;
    }

    public int getNombreDeFruitsRamasses() {
        return nombreDeFruitsRamasses;
    }

    public void setNombreDeFruitsRamasses(int nombreDeFruitsRamasses) {
        this.nombreDeFruitsRamasses = nombreDeFruitsRamasses;
    }

    public int getIdUtilisateur() {
        return idUtilisateur;
    }

    public void setIdUtilisateur(int idUtilisateur) {
        this.idUtilisateur = idUtilisateur;
    }
}
