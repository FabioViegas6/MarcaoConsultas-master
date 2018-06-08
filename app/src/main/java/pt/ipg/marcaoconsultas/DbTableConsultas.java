package pt.ipg.marcaoconsultas;

import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;



public class DbTableConsultas implements BaseColumns {

    private static final String TABLE_NAME = "consultas";
    private static final String FIELD_MEDICO = "medico";
    private static final String FIELD_PACIENTE = "paciente";
    public static final String FIELD_DATA = "data";

    private SQLiteDatabase db;

    public DbTableConsultas(SQLiteDatabase db) {
        this.db = db;
    }

    public void create(){
        db.execSQL(
                "CREATE TABLE Consultas (" + _ID + "INTEGER PRIMARY KEY AUTOINCREMENT," +
                        FIELD_DATA + " TEXT NOT NULL," +
                        TABLE_NAME + "TEXT NOT NULL," +
                        FIELD_MEDICO + "TEXT NOT NULL," +
                        FIELD_PACIENTE + "TEXT NOT NULL," +

                        "FOREIGN KEY (" + FIELD_MEDICO + FIELD_PACIENTE + ") REFERENCES " +
                        DbTableMedicos.MEDICOS_NAME + DbTablePacientes.TABLE_PACIENTES +
                        "(" + DbTableMedicos._ID + DbTablePacientes._ID + ")" +
                        ")"
        );
    }
}
