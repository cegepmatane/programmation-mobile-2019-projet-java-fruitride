package ca.qc.cgmatane.fruitride.vue;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import ca.qc.cgmatane.fruitride.R;
import ca.qc.cgmatane.fruitride.controleur.ControleurAjouterUtilisateur;
import ca.qc.cgmatane.fruitride.donnee.BaseDeDonnee;
import ca.qc.cgmatane.fruitride.donnee.UtilisateurDAO;
import ca.qc.cgmatane.fruitride.modele.Utilisateur;

public class AjoutUtilisateur extends AppCompatActivity implements VueAjoutUtilisateur{

    protected EditText vueAjouterUtilisateurChampNom;
    protected EditText vueAjouterUtilisateurChampPrenom;

    protected ControleurAjouterUtilisateur controleurAjouterUtilisateur = new ControleurAjouterUtilisateur(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.vue_ajout_utilisateur);

        testSiPremiereUtilisation();

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

        controleurAjouterUtilisateur.onCreate(getApplicationContext());
    }

    public void enregistrerUtilisateur() {

        Utilisateur utilisateur = new Utilisateur(vueAjouterUtilisateurChampNom.getText().toString(),
                vueAjouterUtilisateurChampPrenom.getText().toString(),
                0);

        controleurAjouterUtilisateur.actionEnregistrerUtilisateur(utilisateur);
    }

    public void naviguerRetourClub() {
        Intent retourAccueil = new Intent(this, Accueil.class);
        startActivity(retourAccueil);
        this.finish();
    }

    public void testSiPremiereUtilisation() {
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
    }
}
