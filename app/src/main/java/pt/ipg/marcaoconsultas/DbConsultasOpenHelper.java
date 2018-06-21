package pt.ipg.marcaoconsultas;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;



public class DbConsultasOpenHelper extends SQLiteOpenHelper {


    public static final String DATA_BASE_NAME = "consultas.db";
    private static final char DATA_BASE_VERSION = 1;

    public DbConsultasOpenHelper(Context context) {
        super(context, DATA_BASE_NAME, null, DATA_BASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        DbTableMedicos dbTableMedicos = new DbTableMedicos(db);
        dbTableMedicos.create();


        DbTableMeusDados dbTablePacientes = new DbTableMeusDados(db);
        dbTablePacientes.create();

        DbTableConsultas dbTableConsultas = new DbTableConsultas(db);
        dbTableConsultas.create();

        DbTableDistritos dbTableDistritos = new DbTableDistritos(db);
        dbTableDistritos.create();


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
