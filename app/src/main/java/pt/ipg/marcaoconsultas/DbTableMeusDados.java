package pt.ipg.marcaoconsultas;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

public class DbTableMeusDados implements BaseColumns {
    public static final String TABLE_NAME = "MeusDados";
    public static final String FIELD_NAME = "nome";
    public static final String FIELD_MOVEL = "telemovel";
    public static final String FIELD_ID_CONSULTAS = "IdConsultas";

    public static final String [] All_CoLMNS = new String[]{
            _ID, FIELD_NAME, FIELD_ID_CONSULTAS, FIELD_MOVEL
    };

    private SQLiteDatabase db;

    public DbTableMeusDados(SQLiteDatabase db) {
        this.db = db;
    }


    public void create() {
        db.execSQL(
                "CREATE TABLE " + TABLE_NAME + "("
                        + _ID + "INTEGER PRIMARY KEY AUTOINCREMENT," +
                        FIELD_NAME + "TEXT NOT NULL," +
                        FIELD_MOVEL + "INTEGER," +
                        FIELD_ID_CONSULTAS + "INTEGER," +
                        "FOREIGN KEY (" + FIELD_ID_CONSULTAS + ") REFERENCES " +
                        DbTableConsultas.TABLE_CONSULTAS +
                        "(" + DbTableConsultas._ID +")" +
                        ")"

        );
    }

    public static ContentValues getContentValues(MeusDados meusDados){
        ContentValues values = new ContentValues();

        values.put(FIELD_NAME, meusDados.getNome());
        values.put(_ID, meusDados.getId());
        values.put(FIELD_ID_CONSULTAS, meusDados.getIdconsultas());
        values.put(FIELD_MOVEL, meusDados.getTelemovel());

        return values;
    }

    public long insert(ContentValues values){
        return db.insert(TABLE_NAME, null, values);
    }

    public static MeusDados getCurrentDadosBookFromCursor(Cursor cursor){

        final int posId = cursor.getColumnIndex(_ID);
        final int posName = cursor.getColumnIndex(FIELD_NAME);
        final int posIdCons = cursor.getColumnIndex(FIELD_ID_CONSULTAS);
        final int posMovel = cursor.getColumnIndex(FIELD_MOVEL);

        MeusDados meusDados = new MeusDados();

        meusDados.setId(cursor.getInt(posId));
        meusDados.setNome(cursor.getString(posName));
        meusDados.setTelemovel(cursor.getInt(posMovel));
        meusDados.setIdconsultas(cursor.getInt(posIdCons));



        return meusDados;
    }

    public int update(ContentValues values, String whereClause, String[] whereArgs) {
        return db.update(TABLE_NAME, values, whereClause, whereArgs);
    }

    public int delete(String whereClause, String[] whereArgs) {
        return db.delete(TABLE_NAME, whereClause, whereArgs);
    }



    public Cursor query (String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy) {
        return db.query(TABLE_NAME, columns, selection, selectionArgs, groupBy, having, orderBy);
    }

}
