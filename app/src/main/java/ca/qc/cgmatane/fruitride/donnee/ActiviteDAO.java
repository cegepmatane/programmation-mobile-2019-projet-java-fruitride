package ca.qc.cgmatane.fruitride.donnee;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import ca.qc.cgmatane.fruitride.modele.Activite;
import ca.qc.cgmatane.fruitride.modele.Utilisateur;

public class ActiviteDAO {

    private static ActiviteDAO instance = null;
    private List<Activite> listeActivite;

    private BaseDeDonnee accesseurBaseDeDonnees;

    public static ActiviteDAO getInstance() {
        if (instance == null)
            instance = new ActiviteDAO();
        return instance;
    }

    public ActiviteDAO() {
        this.accesseurBaseDeDonnees = BaseDeDonnee.getInstance();
        listeActivite = new ArrayList<>();
    }

    //public void preparerListeActivite() {
    //    listeActivite.add(new Activite(Calendar.getInstance(), 1234, 2, 1));
    //    listeActivite.add(new Activite(Calendar.getInstance(), 9765, 4, 1));
    //    listeActivite.add(new Activite(Calendar.getInstance(), 1002, 0, 1));
    //}

    public Activite recupererActivite() {

        String LISTER_ACTIVITE = "SELECT * FROM activite WHERE date = date('now')";

        Cursor curseur = accesseurBaseDeDonnees.getReadableDatabase().rawQuery(LISTER_ACTIVITE, null);

        int indexId_nbPas = curseur.getColumnIndex("nb_pas");

        for (curseur.moveToFirst();!curseur.isAfterLast();curseur.moveToNext()) {
            int nb_pas = curseur.getInt(indexId_nbPas);
            Activite activite = new Activite(nb_pas);
            return activite;
        }
        return new Activite(0);
    }

    public void ajouterActivite(Activite activite) {

        SQLiteDatabase db = accesseurBaseDeDonnees.getWritableDatabase();
        SQLiteStatement query = db.compileStatement("INSERT INTO activite(id_activite" +
                ", date, nb_pas, nb_fruit, id_utilisateur) VALUES(null,?,?,?,?,?)");
        query.bindString(1, activite.getIdActivite() + "");
        query.bindString(2, activite.getDate() + "");
        query.bindString(3, activite.getNombreDePas() + "");
        query.bindString(4, activite.getNombreDeFruitsRamasses() + "");
        query.bindString(5, activite.getIdUtilisateur() + "");
        query.execute();
    }

    public void isActiviteAjourdhui(Activite activite) {
        String LISTER_ACTIVITE = "SELECT * FROM activite WHERE date = date('now')";
        Cursor curseur = accesseurBaseDeDonnees.getReadableDatabase().rawQuery(LISTER_ACTIVITE, null);

        if (curseur == null) {
            SQLiteDatabase db = accesseurBaseDeDonnees.getWritableDatabase();
            SQLiteStatement query = db.compileStatement("INSERT INTO activite(id_activite" +
                    ", date, nb_pas, nb_fruit, id_utilisateur) VALUES(null,?,?,?,?,?)");
            query.bindString(1, activite.getIdActivite() + "");
            query.bindString(2, activite.getDate() + "");
            query.bindString(3, activite.getNombreDePas() + "");
            query.bindString(4, activite.getNombreDeFruitsRamasses() + "");
            query.bindString(5, activite.getIdUtilisateur() + "");
            query.execute();
        }
    }

    public Activite chercherActiviteParIdActivite(int idActivite) {
        for (Activite activiteRecherche :
                this.listeActivite) {
            if (activiteRecherche.getIdActivite() == idActivite) return activiteRecherche;
        }
        return null;
    }
}
