package ca.qc.cgmatane.fruitride.donnee;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class BaseDeDonnee extends SQLiteOpenHelper {

    private static BaseDeDonnee instance = null;

    public static BaseDeDonnee getInstance(Context contexte)
    {
        instance = new BaseDeDonnee(contexte);
        return instance;
    }

    public static BaseDeDonnee getInstance()
    {
        return instance;
    }

    public BaseDeDonnee(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public BaseDeDonnee(Context contexte) {
        super(contexte, "utilisateur", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "create table if not exists utilisateur(id_utilisateur INTEGER PRIMARY KEY, nom TEXT" +
                ", prenom TEXT, niveau INT, experience INT)";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {

    }
}
