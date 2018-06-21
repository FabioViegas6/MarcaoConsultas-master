package pt.ipg.marcaoconsultas;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;



public class DbTableConsultas implements BaseColumns {



    public static final String TABLE_CONSULTAS = "consulta";
    public static final String FIELD_TIPO = "tipodeconsulta";

    public static final String [] All_COLUMNS = new String[] {
            _ID,  FIELD_TIPO};

    private SQLiteDatabase db;

    public DbTableConsultas(SQLiteDatabase db) {
        this.db = db;
    }

    public void create(){
        db.execSQL(
                "CREATE TABLE " + TABLE_CONSULTAS + " (" +
                        _ID + "INTEGER PRIMARY KEY AUTOINCREMENT," +
                        FIELD_TIPO + "TEXT NOT NULL"+")");
    }
    public static ContentValues getContentValues(Consultas consultas){
        ContentValues values = new ContentValues();

        values.put(_ID, consultas.getIdConsultas());
        values.put(FIELD_TIPO, consultas.getTipoConsulta());

        return values;
    }

    public static Consultas getCurrentConsultasFromCursor(Cursor cursor){

       final int posIdCon = cursor.getColumnIndex(_ID);
       final int posTipo = cursor.getColumnIndex(FIELD_TIPO);

        Consultas consultas = new Consultas();

        consultas.setIdConsultas(cursor.getInt(posIdCon));
        consultas.setTipoConsulta(cursor.getString(posTipo));

        return consultas;
    }




    public long insert(ContentValues values) {
        return insert( values);
    }




    public int update(ContentValues values, String whereClause, String[] whereArgs) {
        return db.update(TABLE_CONSULTAS, values, whereClause, whereArgs);
    }

    public int delete(String whereClause, String[] whereArgs) {
        return db.delete(TABLE_CONSULTAS, whereClause, whereArgs);
    }


    public Cursor query (String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy) {
        return db.query(TABLE_CONSULTAS, columns, selection, selectionArgs, groupBy, having, orderBy);
    }

}
