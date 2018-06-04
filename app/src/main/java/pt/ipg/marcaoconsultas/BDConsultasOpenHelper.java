package pt.ipg.marcaoconsultas;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BDConsultasOpenHelper extends SQLiteOpenHelper {


    public static final String DATABASE_NAME = "consultas.db";
    public static final char DATABASE_VERSION = '1';


    public BDConsultasOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME,null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
      DbTableMedicos dbTableMedicos = new DbTableMedicos(db);
        dbTableMedicos.create();


        DbTablePacientes dbTablePacientes = new DbTablePacientes(db);
        dbTablePacientes.create();



    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
