package pt.ipg.marcaoconsultas;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

public class DbTablePacientes implements BaseColumns {
    // private static final String FIELD_ID = "idPacientes";
    public static final String TABLE_PACIENTES = "Pacientes";
    public static final String FIELD_NAME_Pac = "nome";
    public static final String FIELD_SEXO = "sexo";
    public static final String FIELD_MOVEL = "telemovel";
    public static final String FIELD_ENDERECO_ELETRON = "email";

    public static final String [] All_CoLMNS = new String[]{
            _ID, FIELD_NAME_Pac, FIELD_SEXO, FIELD_ENDERECO_ELETRON, FIELD_MOVEL
    };

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

    public static Pacientes getCurrentPacientesBookFromCursor(Cursor cursor){

        final int posIdPac = cursor.getColumnIndex(_ID);
        final int posNamePac = cursor.getColumnIndex(FIELD_NAME_Pac);
        final int posEnderEletrPaci = cursor.getColumnIndex(FIELD_ENDERECO_ELETRON);
        final int posMovelPac = cursor.getColumnIndex(FIELD_MOVEL);
        final int posSexo = cursor.getColumnIndex(FIELD_SEXO);

        Pacientes pacientes = new Pacientes();

        pacientes.setIdPacinte(cursor.getInt(posIdPac));
        pacientes.setEmail(cursor.getString(posEnderEletrPaci));
        pacientes.setNome(cursor.getString(posNamePac));
        pacientes.setSexo(cursor.getString(posSexo));
        pacientes.setTelemovel(cursor.getInt(posMovelPac));


        return pacientes;
    }

    public int update(ContentValues values, String whereClause, String[] whereArgs) {
        return db.update(TABLE_PACIENTES, values, whereClause, whereArgs);
    }

    public int delete(String whereClause, String[] whereArgs) {
        return db.delete(TABLE_PACIENTES, whereClause, whereArgs);
    }

}
