package pt.ipg.marcaoconsultas;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

public class DbTablePacientes implements BaseColumns {
   // private static final String FIELD_ID = "idPacientes";
    private static final String FIELD_NAME_Pac = "nome";
    private static final String FIELD_SEXO = "sexo";
    private static final String FIELD_MOVEL = "Telemovel";
    private static final String FIELD_ENDERECO_ELETRON = "email";
    public static final String TABLE_PACIENTES = "Pacientes";

    private SQLiteDatabase db;

    public DbTablePacientes(SQLiteDatabase db) {
        this.db = db;
    }


    public void create() {
        db.execSQL(
                "CREATE TABLE " + TABLE_PACIENTES + "("
                        + _ID + "INTEGER PRIMARY KEY AUTOINCREMENT," +
                       // FIELD_ID + " INTEGER," +
                        FIELD_NAME_Pac + "TEXT NOT NULL," +
                        FIELD_SEXO + "TEXT NOT NULL," +
                        FIELD_MOVEL + "INTEGER," +
                        FIELD_ENDERECO_ELETRON + "TEXT NOT NULL" +
                        ")"

        );
    }

    public static ContentValues getContentValues(Pacientes pacientes){
        ContentValues values = new ContentValues();

        values.put(FIELD_NAME_Pac, pacientes.getNome());
        values.put(_ID, pacientes.getIdPacinte());
        values.put(FIELD_ENDERECO_ELETRON, pacientes.getEmail());
        values.put(FIELD_MOVEL, pacientes.getTelemovel());
        values.put(FIELD_SEXO, pacientes.getSexo());

        return values;
    }

    public long insert(ContentValues values){
        return db.insert(TABLE_PACIENTES, null, values);
    }

}
