package pt.ipg.marcaoconsultas;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;



public class DbTableMedicos implements BaseColumns {
    // private static final String FIELD_ID_Medi = "idMedicos";
    private static final String FIELD_NAME_MED = "nome";
    private static final String FIELD_MOVEL = "Telemovel";
    private static final String FIELD_ENDERECO_ELETRON_MED = "email";
    public static final String MEDICOS_NAME = "medicos";

    private SQLiteDatabase db;

    public DbTableMedicos(SQLiteDatabase db) {
        this.db = db;
    }



    public void create(){
        db.execSQL(
                "CREATE TABLE " + MEDICOS_NAME +
                        " (" + _ID + "INTEGER PRIMARY KEY AUTOINCREMENT," +
                        //  FIELD_ID_Medi + " INTEGER," +
                        FIELD_NAME_MED + "TEXT NOT NULL," +
                        // FIELD_MOVEL + "INTEGER," +
                        FIELD_ENDERECO_ELETRON_MED + "TEXT NOT NULL" +
                        ")"

        );
    }

    public static ContentValues getContentValues(Medicos medicos){
        ContentValues values = new ContentValues();

        values.put(FIELD_NAME_MED, medicos.getNomeMed());
        values.put(_ID, medicos.getContribuinteMed());
        values.put(FIELD_ENDERECO_ELETRON_MED, medicos.getEmailMed());

        return values;
    }

    public long insert(ContentValues values){
        return db.insert(MEDICOS_NAME, null, values);
    }



}
