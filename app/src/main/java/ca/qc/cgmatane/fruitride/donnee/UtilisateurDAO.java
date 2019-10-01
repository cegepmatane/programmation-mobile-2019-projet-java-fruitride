package ca.qc.cgmatane.fruitride.donnee;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import java.util.ArrayList;
import java.util.List;

import ca.qc.cgmatane.fruitride.modele.Utilisateur;
public class UtilisateurDAO {

    private static UtilisateurDAO instance = null;
    protected List<Utilisateur> listeUtilisateur;

    private BaseDeDonnee accesseurBaseDeDonnees;

    public UtilisateurDAO() {
        this.accesseurBaseDeDonnees = BaseDeDonnee.getInstance();
        listeUtilisateur = new ArrayList<>();
    }

    public Utilisateur chercherUtilisateurParId(int id_utilisateur) {
        for (Utilisateur utilisateurRecherche :
                this.listeUtilisateur) {
            if (utilisateurRecherche.getId_utilisateur() == id_utilisateur)
                return utilisateurRecherche;
        }
        return null;
    }

    public void ajouterUtilisateur(Utilisateur utilisateur) {
        SQLiteDatabase db = accesseurBaseDeDonnees.getWritableDatabase();
        SQLiteStatement query = db.compileStatement("INSERT INTO utilisateur(id_utilisateur" +
                ", nom, prenom, niveau, experience) VALUES(null,?,?,?,?)");
        query.bindString(1, utilisateur.getNom());
        query.bindString(2, utilisateur.getPrenom());
        query.bindString(3, "0");
        query.bindString(4, "0");
        query.execute();
    }

    public void modifierUtilisateur(Utilisateur utilisateur) {
        for (Utilisateur utilisateurRecherche :
                this.listeUtilisateur) {
            if (utilisateurRecherche.getId_utilisateur() == utilisateur.getId_utilisateur()) {
                utilisateurRecherche = utilisateur;
            }
        }
    }

    public Utilisateur recupererUtilisateur() {

        String LISTER_UTILISATEUR = "SELECT * FROM utilisateur";

        Cursor curseur = accesseurBaseDeDonnees.getReadableDatabase().rawQuery(LISTER_UTILISATEUR, null);

        int indexId_utilisateur = curseur.getColumnIndex("id_utilisateur");
        int indexNom = curseur.getColumnIndex("nom");
        int indexPrenom = curseur.getColumnIndex("prenom");

        for (curseur.moveToFirst();!curseur.isAfterLast();curseur.moveToNext()) {
            int id_utilisateur = curseur.getInt(indexId_utilisateur);
            String nom = curseur.getString(indexNom);
            String prenom = curseur.getString(indexPrenom);
            Utilisateur utilisateur = new Utilisateur(nom, prenom, id_utilisateur);
            return new Utilisateur(nom, prenom, id_utilisateur);
        }
        return new Utilisateur("toto", "test", 0);
    }

    public static UtilisateurDAO getInstance() {
        if (null == instance)
            instance = new UtilisateurDAO();
        return instance;
    }
}
