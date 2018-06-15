package pt.ipg.marcaoconsultas;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;



public class DbTableConsultas implements BaseColumns {

    private static final String FIELD_MEDICO = "medico";
    private static final String FIELD_PACIENTE = "paciente";
    private static final String FIELD_DATA = "data";
    public static final String TABLE_CONSULTAS = "consulta";
    public static final String FIELD_TIPO = "tipodeconsulta";

    private SQLiteDatabase db;

    public DbTableConsultas(SQLiteDatabase db) {
        this.db = db;
    }

    public void create(){
        db.execSQL(
                "CREATE TABLE " + TABLE_CONSULTAS + " (" +
                        _ID + "INTEGER PRIMARY KEY AUTOINCREMENT," +
                        FIELD_DATA + " TEXT NOT NULL," +
                        FIELD_MEDICO + "TEXT NOT NULL," +
                        FIELD_PACIENTE + "TEXT NOT NULL," +
                        FIELD_TIPO + "TEXT NOT NULL," +

                        "FOREIGN KEY (" + FIELD_MEDICO + FIELD_PACIENTE + ") REFERENCES " +
                        DbTableMedicos.MEDICOS_NAME + DbTablePacientes.TABLE_PACIENTES +
                        "(" + DbTableMedicos._ID + DbTablePacientes._ID + ")" +
                        ")"
        );
    }
    public static ContentValues getContentValues(Consultas consultas){
        ContentValues values = new ContentValues();

        values.put(_ID, consultas.getIdConsultas());
        values.put(FIELD_PACIENTE, consultas.getPacintes());
        values.put(FIELD_DATA, consultas.getData());
        values.put(FIELD_MEDICO, consultas.getMedico());
        values.put(FIELD_TIPO, consultas.getTipoConsulta());

        return values;
    }

    public static Consultas getCurrentConsultasFromCursor(Cursor cursor){

       final int posIdCon = cursor.getColumnIndex(_ID);
       final int posPaciente = cursor.getColumnIndex(FIELD_PACIENTE);
       final int posData = cursor.getColumnIndex(FIELD_DATA);
       final int posMedico = cursor.getColumnIndex(FIELD_MEDICO);
       final int posTipo = cursor.getColumnIndex(FIELD_TIPO);

        Consultas consultas = new Consultas();

        consultas.setIdConsultas(cursor.getInt(posIdCon));
       consultas.setMedico(cursor.getString(posMedico));
       consultas.setData(cursor.getString(posData));
        consultas.setPacintes(cursor.getString(posPaciente));
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
