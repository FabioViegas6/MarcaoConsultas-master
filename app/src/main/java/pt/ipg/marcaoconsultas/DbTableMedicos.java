package pt.ipg.marcaoconsultas;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import android.database.Cursor;



public class DbTableMedicos implements BaseColumns {
    // private static final String FIELD_ID_Medi = "idMedicos";
    private static final String FIELD_NAME_MED = "medicos";
    private static final String FIELD_ENDERECO_ELETRON_MED = "email";
    public static final String MEDICOS_NAME = "nome";
    private static final String FIELD_MOVEL = "telemovel";

    public static final String [] ALL_COLUMNS = new String[] {
            _ID, FIELD_NAME_MED, FIELD_ENDERECO_ELETRON_MED, FIELD_MOVEL
    };


    private SQLiteDatabase db;

    public DbTableMedicos(SQLiteDatabase db) {
        this.db = db;
    }


    public void create() {
        db.execSQL(
                "CREATE TABLE " + MEDICOS_NAME +
                        " (" + _ID + "INTEGER PRIMARY KEY AUTOINCREMENT," +
                        //  FIELD_ID_Medi + " INTEGER," +
                        FIELD_NAME_MED + "TEXT NOT NULL," +
                        FIELD_MOVEL + "INTEGER," +
                        FIELD_ENDERECO_ELETRON_MED + "TEXT NOT NULL" +
                        ")"

        );
    }

    public static ContentValues getContentValues(Medicos medicos) {
        ContentValues values = new ContentValues();

        values.put(FIELD_NAME_MED, medicos.getNomeMed());
        values.put(_ID, medicos.getContribuinteMed());
        values.put(FIELD_ENDERECO_ELETRON_MED, medicos.getEmailMed());
        values.put(FIELD_MOVEL, medicos.getTelemovelmed());

        return values;
    }


    public static Medicos getCurrentMedicosFromCursor(Cursor cursor) {
        final int posIdMedico = cursor.getColumnIndex(_ID);
        final int posNameMedico = cursor.getColumnIndex(FIELD_NAME_MED);
        final int posEnderecoEletro = cursor.getColumnIndex(FIELD_ENDERECO_ELETRON_MED);
        final int posMovelMed = cursor.getColumnIndex(FIELD_MOVEL);

        Medicos medicos = new Medicos();

        medicos.setContribuinteMed(cursor.getInt(posIdMedico));
        medicos.setEmailMed(cursor.getString(posEnderecoEletro));
        medicos.setNomeMed(cursor.getString(posNameMedico));
        medicos.setTelemovelmed(cursor.getInt(posMovelMed));

        return medicos;
    }

    /**
     * Convenience method for inserting a row into the categories table.
     *
     * @param values this map contains the initial column values for the
     *               row. The keys should be the column names and the values the
     *               column values
     * @return the row ID of the newly inserted row, or -1 if an error occurred
     */

    public long insert(ContentValues values) {
        return db.insert(MEDICOS_NAME, null, values);

    }
    /**
     * Convenience method for updating rows in the categories table.
     *
     * @param values a map from column names to new column values. null is a
     *            valid value that will be translated to NULL.
     * @param whereClause the optional WHERE clause to apply when updating.
     *            Passing null will update all rows.
     * @param whereArgs You may include ?s in the where clause, which
     *            will be replaced by the values from whereArgs. The values
     *            will be bound as Strings.
     * @return the number of rows affected
     */
    public int update(ContentValues values, String whereClause, String[] whereArgs) {
        return db.update(MEDICOS_NAME, values, whereClause, whereArgs);
    }


    public int delete(String whereClause, String[] whereArgs) {
        return db.delete(MEDICOS_NAME, whereClause, whereArgs);
    }


    public Cursor query (String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy) {
        return db.query(MEDICOS_NAME, columns, selection, selectionArgs, groupBy, having, orderBy);
    }
}
