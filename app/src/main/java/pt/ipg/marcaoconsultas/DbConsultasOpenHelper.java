package pt.ipg.marcaoconsultas;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;



public class DbConsultasOpenHelper extends SQLiteOpenHelper {

    private static final boolean PRODUTION = false;

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

        if (!PRODUTION){
            seed(db);
        }

    }

    private void seed (SQLiteDatabase db){
        DbTableConsultas dbTableConsultas = new DbTableConsultas(db);

        Consultas consultas = new Consultas();
        consultas.setTipoConsulta("Rotina");
        int idConsultaRotina = (int) dbTableConsultas.insert(DbTableConsultas.getContentValues(consultas));

         consultas = new Consultas();
        consultas.setTipoConsulta("Visão");
        int idConsultaVisao = (int) dbTableConsultas.insert(DbTableConsultas.getContentValues(consultas));



        DbTableMeusDados dbTableMeusDados = new DbTableMeusDados(db);

        MeusDados meusDados = new MeusDados();
        meusDados.setNome("Fabio Marques");
        meusDados.setIdconsultas(idConsultaRotina);
        meusDados.setTelemovel(966628464);
        dbTableMeusDados.insert(DbTableMeusDados.getContentValues(meusDados));

         meusDados = new MeusDados();
        meusDados.setNome("Fabio Viegas Marques");
        meusDados.setIdconsultas(idConsultaVisao);
        meusDados.setTelemovel(966628469);
        dbTableMeusDados.insert(DbTableMeusDados.getContentValues(meusDados));


        DbTableDistritos dbTableDistritos = new DbTableDistritos(db);

        Distritos distritos = new Distritos();
        distritos.setNomeDis("Guarda");
        dbTableDistritos.insert(DbTableDistritos.getContentValues(distritos));

         distritos = new Distritos();
        distritos.setNomeDis("Viseu");
        dbTableDistritos.insert(DbTableDistritos.getContentValues(distritos));


        DbTableMedicos dbTableMedicos = new DbTableMedicos(db);

        Medicos medicos = new Medicos();
        medicos.setNomeMed("João Maia");
        medicos.setId(idConsultaVisao);
        dbTableMedicos.insert(DbTableMedicos.getContentValues(medicos));

        medicos = new Medicos();
        medicos.setNomeMed("Fernanda Martins");
        medicos.setId(idConsultaVisao);
        dbTableMedicos.insert(DbTableMedicos.getContentValues(medicos));


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
