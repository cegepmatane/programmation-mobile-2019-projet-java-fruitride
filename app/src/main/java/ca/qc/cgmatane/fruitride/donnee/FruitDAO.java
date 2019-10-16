package ca.qc.cgmatane.fruitride.donnee;

import java.util.ArrayList;
import java.util.List;

import ca.qc.cgmatane.fruitride.modele.Fruit;
import ca.qc.cgmatane.fruitride.reseau.RecupererJsonFruit;

public class FruitDAO {
    private static FruitDAO instance = null;
    private List<Fruit> listeFruit;

    public static FruitDAO getInstance() {
        if (instance == null) {
            instance = new FruitDAO();
        }
        instance.chargerListeFruit();
        return instance;
    }

    public FruitDAO() {
        listeFruit = new ArrayList<>();
    }

    private void chargerListeFruit() {

        RecupererJsonFruit recupererJsonFruit = new RecupererJsonFruit();
        recupererJsonFruit.execute();
        while (recupererJsonFruit.getId().size() == 0) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        List<String> id = recupererJsonFruit.getId();
        List<String> latitude = recupererJsonFruit.getLatitude();
        List<String> longitude = recupererJsonFruit.getLongitude();
        List<String> type = recupererJsonFruit.getType();

        for (int i = 0; i < id.size(); i++) {
            listeFruit.add(new Fruit(Integer.parseInt(id.get(i)), Double.parseDouble(latitude.get(i)), Double.parseDouble(longitude.get(i)), type.get(i)));
        }
    }

    public void ajouterFruit(Fruit fruit) {
        listeFruit.add(fruit);
    }

    public List<Fruit> recupererListeFruit() {
        return listeFruit;
    }

    public Fruit chercherFruitParId(int id_fruit) {
        for (Fruit fruitRecherche : this.listeFruit) {
            if (fruitRecherche.getIdFruit() == id_fruit) {
                return fruitRecherche;
            }
        }
        return null;
    }
}
