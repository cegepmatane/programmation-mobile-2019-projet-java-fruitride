package ca.qc.cgmatane.fruitride.vue;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import ca.qc.cgmatane.fruitride.R;
import ca.qc.cgmatane.fruitride.donnee.BaseDeDonnee;
import ca.qc.cgmatane.fruitride.donnee.UtilisateurDAO;
import ca.qc.cgmatane.fruitride.modele.Utilisateur;

public class AjoutUtilisateur extends AppCompatActivity {

    protected EditText vueAjouterUtilisateurChampNom;
    protected EditText vueAjouterUtilisateurChampPrenom;

    protected UtilisateurDAO accesseurUtilisateur;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.vue_ajout_utilisateur);

        BaseDeDonnee.getInstance(getApplicationContext());

        // VARIABLE QUI RENVOIS TRUE SI C'EST LA PREMIERE FOIS QUE L'APPLICATION EST LANCEE
        Boolean premiereUtilisation = getSharedPreferences("PREFERENCE", MODE_PRIVATE)
                .getBoolean("isFirstRun", true);

        if (premiereUtilisation) {
            //Checker si utilisateur dans BDD plutot que si premier démarrage
        } else {
            startActivity(new Intent(this, Accueil.class));
            this.finish();
        }

        getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit()
                .putBoolean("isFirstRun", false).commit();

        vueAjouterUtilisateurChampNom = (EditText) findViewById(R.id.vue_ajouter_utilisateur_champ_nom);
        vueAjouterUtilisateurChampPrenom = (EditText) findViewById(R.id.vue_ajouter_utilisateur_champ_prenom);

        Button vueAjouterJoueurActionEnregistrer =
                (Button) findViewById(R.id.vue_ajouter_utilisateur_action_enregistrer);

        vueAjouterJoueurActionEnregistrer.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        enregistrerUtilisateur();
                    }
                }
        );
    }

    private void enregistrerUtilisateur() {

        accesseurUtilisateur = UtilisateurDAO.getInstance();

        accesseurUtilisateur.ajouterUtilisateur(new Utilisateur(vueAjouterUtilisateurChampNom.getText().toString(),
                vueAjouterUtilisateurChampPrenom.getText().toString(),
                0));

        naviguerRetourClub();
    }

    private void naviguerRetourClub() {
        Intent retourAccueil = new Intent(this, Accueil.class);
        startActivity(retourAccueil);
        this.finish();
    }
}
