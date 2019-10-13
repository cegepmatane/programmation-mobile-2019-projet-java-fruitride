package ca.qc.cgmatane.fruitride.donnee;

import java.util.ArrayList;
import java.util.List;

import ca.qc.cgmatane.fruitride.modele.Fruit;

public class FruitDAO {
    private static FruitDAO instance = null;
    private List<Fruit> listeFruit;

    public static FruitDAO getInstance() {
        if (instance == null) {
            instance = new FruitDAO();
        }
        instance.preparerListeFruit();
        return instance;
    }

    public FruitDAO() {
        listeFruit = new ArrayList<>();
    }

    private void preparerListeFruit() {
        listeFruit.add(new Fruit(48.840981, -67.497192, "apple"));
        listeFruit.add(new Fruit(48.840984, -67.500000, "pear"));
        listeFruit.add(new Fruit(48.841000, -67.497492, "pineapple"));
        listeFruit.get(0).setIdFruit(1);
        listeFruit.get(1).setIdFruit(2);
        listeFruit.get(2).setIdFruit(3);
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
