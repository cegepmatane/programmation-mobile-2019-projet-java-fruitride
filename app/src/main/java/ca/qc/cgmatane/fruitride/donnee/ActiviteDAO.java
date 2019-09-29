package ca.qc.cgmatane.fruitride.donnee;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import ca.qc.cgmatane.fruitride.modele.Activite;
import ca.qc.cgmatane.fruitride.modele.Utilisateur;

public class ActiviteDAO {

    private static ActiviteDAO instance = null;
    private List<Activite> listeActivite;

    private Activite activiteDuJour;

    private BaseDeDonnee accesseurBaseDeDonnees;

    public static ActiviteDAO getInstance() {
        if (instance == null)
            instance = new ActiviteDAO();
        return instance;
    }

    public ActiviteDAO() {
        this.accesseurBaseDeDonnees = BaseDeDonnee.getInstance();
        listeActivite = new ArrayList<>();
        activiteDuJour = new Activite(0);
    }

    public Activite recupererActivite() {

        String LISTER_ACTIVITE = "SELECT * FROM activite WHERE date = '" + dateDuJour() + "'";

        Cursor curseur = accesseurBaseDeDonnees.getReadableDatabase().rawQuery(LISTER_ACTIVITE, null);

        int indexId_nbPas = curseur.getColumnIndex("nb_pas");

        for (curseur.moveToFirst();!curseur.isAfterLast();curseur.moveToNext()) {
            int nb_pas = curseur.getInt(indexId_nbPas);
            activiteDuJour.setNombreDePas(nb_pas);
            return activiteDuJour;
        }
        return new Activite(0);
    }

    public void ajouterActivite(Activite activite) {
        SQLiteDatabase db = accesseurBaseDeDonnees.getWritableDatabase();
        SQLiteStatement query = db.compileStatement("INSERT INTO activite(id_activite" +
                ", date, nb_pas, nb_fruit, id_utilisateur) VALUES(null,?,?,?,?)");
        query.bindString(1, dateDuJour());
        query.bindString(2, activite.getNombreDePas() + "");
        query.bindString(3, activite.getNombreDeFruitsRamasses() + "");
        query.bindString(4, activite.getIdUtilisateur() + "");
        query.execute();
    }

    public void isActiviteAjourdhui(Activite activite) {
        String LISTER_ACTIVITE = "SELECT * FROM activite WHERE date ='" + dateDuJour() + "'";
        Cursor curseur = accesseurBaseDeDonnees.getReadableDatabase().rawQuery(
                LISTER_ACTIVITE, null);
        System.out.println(dateDuJour());
        System.out.println(curseur.getCount());
        if (!(curseur.getCount() > 0)) {
            activiteDuJour = new Activite(activite.getNombreDePas());
            ajouterActivite(activite);
        }
    }

    public void enregistrerNombreDePas(float nbPas) {
        SQLiteDatabase db = accesseurBaseDeDonnees.getWritableDatabase();
        SQLiteStatement query = db.compileStatement("UPDATE activite SET nb_pas = " + nbPas
                + " WHERE date = '" + dateDuJour() + "'");
        query.execute();
    }

    public Activite chercherActiviteParIdActivite(int idActivite) {
        for (Activite activiteRecherche :
                this.listeActivite) {
            if (activiteRecherche.getIdActivite() == idActivite) return activiteRecherche;
        }
        return null;
    }

    public String dateDuJour() {
        int annee = Calendar.getInstance().get(Calendar.YEAR);
        String mois = Integer.toString(Calendar.getInstance().get(Calendar.MONTH) + 1);
        if (mois.length() < 2) {
            mois = "0" + mois;
        }
        int jour = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);

        String date = annee + "-" + mois + "-" + jour;

        return date;
    }
}
