package ca.qc.cgmatane.fruitride.vue;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import ca.qc.cgmatane.fruitride.R;
import ca.qc.cgmatane.fruitride.donnee.UtilisateurDAO;
import ca.qc.cgmatane.fruitride.modele.Utilisateur;

public class AjoutUtilisateur extends AppCompatActivity {

    protected EditText vueAjouterUtilisateurChampNom;
    protected EditText getVueAjouterUtilisateurChampPrenom;

    protected UtilisateurDAO accesseurUtilisateur;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vue_ajout_utilisateur);

        vueAjouterUtilisateurChampNom = (EditText) findViewById(R.id.vue_ajouter_utilisateur_champ_nom);
        getVueAjouterUtilisateurChampPrenom = (EditText) findViewById(R.id.vue_ajouter_utilisateur_champ_prenom);

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
                getVueAjouterUtilisateurChampPrenom.getText().toString(),
                0));

        naviguerRetourClub();
    }

    private void naviguerRetourClub() {
        Intent retourAccueil = new Intent(this, Accueil.class);
        startActivity(retourAccueil);
    }
}
