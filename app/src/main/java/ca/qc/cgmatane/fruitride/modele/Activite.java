package ca.qc.cgmatane.fruitride.modele;

import java.util.Date;

public class Activite {
    protected int idActivite;
    protected Date date;
    protected int nombreDePas;
    protected int nombreDeFruitsRamasses;
    protected int idUtilisateur;

    public int getIdActivite() {
        return idActivite;
    }

    public void setIdActivite(int idActivite) {
        this.idActivite = idActivite;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getNombreDePas() {
        return nombreDePas;
    }

    public void setNombreDePas(int nombreDePas) {
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
