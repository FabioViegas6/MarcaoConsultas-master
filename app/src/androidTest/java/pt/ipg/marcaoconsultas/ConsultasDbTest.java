package pt.ipg.marcaoconsultas;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ConsultasDbTest {

    private Context getContext(){
        return InstrumentationRegistry.getTargetContext();
    }

    @Before
    public void setUp(){
        getContext().deleteDatabase(DbConsultasOpenHelper.DATA_BASE_NAME);
    }

    @Test
    public void OpenConsultasDbTest() {
        // Context of the app under test.

        Context appContext = getContext();

        DbConsultasOpenHelper dbConsultasOpenHelper = new DbConsultasOpenHelper(appContext);

        SQLiteDatabase db = dbConsultasOpenHelper.getReadableDatabase();
        assertTrue("Não foi possivel abrir ou criar o banco de dados ",db.isOpen());
        db.close();
    }

    @Test
    public void consultasCRUDtest(){

        DbConsultasOpenHelper dbConsultasOpenHelper = new DbConsultasOpenHelper(getContext());
        SQLiteDatabase db = dbConsultasOpenHelper.getWritableDatabase();

        DbTableConsultas tableConsultas = new DbTableConsultas(db);

        Consultas consultas = new Consultas();
        consultas.setTipoConsulta("Saude Mental");
        consultas.setMedico("Fernando Neves");
        consultas.setData("23/6/2018");
        consultas.setPacintes("=?");

        // criar e inserir dados (C) RUD
        long IdConsultas = insertConsultas(tableConsultas, consultas);

        // query and read C(R)UD
        consultas = ReadFirstConsultas(tableConsultas, "Saude Mental",
                "23/6/2018", "Fernando Neves",
                "=?", IdConsultas );

        // update CR(U)D
        consultas.setTipoConsulta("Psicatria");
        consultas.setMedico("Fernanda Almeida");
        consultas.setData("23/07/2018");
        consultas.setPacintes("=?");
        int rowsAffected = tableConsultas.update(
                DbTableConsultas.getContentValues(consultas),
                DbTableConsultas._ID + "=?",
                new String[] {Long.toString(IdConsultas)}
        );
        assertEquals("Falha a atualizar a consulta",1, rowsAffected);

        ///////////// query and read C(R)UD
        consultas = ReadFirstConsultas(tableConsultas, "Saude Mental",
                "23/6/2018", "Fernando Neves",
                "=?", IdConsultas );

        /////////////// delete CRU(D)

        rowsAffected = tableConsultas.delete(
                DbTableConsultas._ID + "=?",
                new String[] {Long.toString(IdConsultas)}
        );
        assertEquals("Falha a deletar consulta",1, rowsAffected);

        Cursor cursor = tableConsultas.query(DbTableConsultas.All_COLUMNS,
                null, null, null, null, null);
        assertEquals("consultas encontradas após serem apagadas? ", 0, cursor.getCount());


    }
    private long insertConsultas (DbTableConsultas tableConsultas, Consultas consultas){
        long id = tableConsultas.insert(
                DbTableConsultas.getContentValues(consultas)
        );
        assertNotEquals("Falha a inserir consultas", -1, id);

        return id;
    }

    @NonNull
    private Consultas ReadFirstConsultas(DbTableConsultas tableConsultas, String expectedTipo,
                                          String expectedData, String expectedMedico,
                                         String expectedPacientes, long expectedIdConsultas){
       Cursor cursor = tableConsultas.query(DbTableConsultas.All_COLUMNS, null, null,
               null,null,null);

       assertEquals("Falha a ler as consultas", 1, cursor.getCount());

       assertTrue("Falha a ler a primeira consulta", cursor.moveToNext());

        Consultas consultas = DbTableConsultas.getCurrentConsultasFromCursor(cursor);

        assertEquals("id da consulta incorretp", expectedIdConsultas, consultas.getIdConsultas());
        assertEquals("paciente incorreto", expectedPacientes, consultas.getPacintes());
        assertEquals("data da consulta incorreta ", expectedData, consultas.getData());
        assertEquals("medico da consulta incorreto", expectedMedico, consultas.getMedico());
        assertEquals("tipo de consulta incorreto", expectedTipo, consultas.getTipoConsulta());

        return consultas;
    }
    @Test
    public void PacientesCRUDtest(){

        DbConsultasOpenHelper dbConsultasOpenHelper = new DbConsultasOpenHelper(getContext());
        SQLiteDatabase db = dbConsultasOpenHelper.getWritableDatabase();

        DbTableConsultas tableConsultas = new DbTableConsultas(db);
        DbTablePacientes tablePacientes = new DbTablePacientes(db);

        Consultas consultas = new Consultas();
        consultas.setTipoConsulta("Rotina");



        // criar e inserir dados (C) RUD

        Pacientes pacientes = new Pacientes();
        pacientes.setNome("Fabia Vieira");
        pacientes.setTelemovel(99342452);
        pacientes.setSexo("Feminino");
        pacientes.setEmail("fabia@gmail.com");


        long IdPacintes = tablePacientes.insert(
                DbTablePacientes.getContentValues(pacientes)
        );
        assertNotEquals("Falha a inserir pacintes", -1, IdPacintes );

        // query/read C(R)UD

        pacientes = ReadFirstPaciente(tablePacientes, "Fabio Vieira", 99342452,
                "Feminino", "fabia@gmail.com", IdPacintes);

        // update CR(U)D
        pacientes.setNome("Fabia Vieira dos Santos");
        pacientes.setTelemovel(99344433);
        pacientes.setSexo("Feminino");
        pacientes.setEmail("fabiaV@gmail.com");

        int ronwFffected = tablePacientes.update(
                DbTablePacientes.getContentValues(pacientes),
                DbTablePacientes._ID +"=?",
                new String[]{Long.toString(IdPacintes)}
        );
        assertEquals("Falha a atulizar pacientes", 1, ronwFffected);

        // query/read C(R)UD

        pacientes = ReadFirstPaciente(tablePacientes, "Fabio Vieira dos Santos",
                99344433, "Feminino", "fabiaV@gmail.com", IdPacintes);

        /// delete CRU (D)

        ronwFffected = tablePacientes.delete(
                DbTablePacientes._ID + "=?",
                new String[]{Long.toString(IdPacintes)}
        );

        assertEquals("Falha a deletar paciente", 1, ronwFffected);

        Cursor cursor = tablePacientes.query(DbTablePacientes.All_CoLMNS, null, null,
                null,null, null);
        assertEquals("pacientes econtrados apos ser apagdos?", 0, cursor.getCount());


    }

    private Pacientes ReadFirstPaciente(DbTablePacientes tablePacientes,
                                        String expectedNome, long expectedTelemovel,
                                        String expectedSexo, String expectedEmail, long expectedIdPac){
        Cursor cursor = tablePacientes.query(DbTablePacientes.All_CoLMNS, null, null,
                null,null, null);
        assertEquals("Falha a ler pacintes", 1, cursor.getCount());
        assertEquals("Falha a ler o primeiro pacinte", cursor.moveToNext());

        Pacientes pacientes = DbTablePacientes.getCurrentPacientesBookFromCursor(cursor);

        assertEquals(" nome do paciente incorreto", expectedNome, pacientes.getNome());
        assertEquals("telemovel do pacinte incorreto ", expectedTelemovel, pacientes.getTelemovel());
        assertEquals("sexo do paciente incorreto", expectedSexo, pacientes.getSexo());
        assertEquals("email do pacinte incorreto", expectedEmail, pacientes.getEmail());
        assertEquals("id do pacinte incorreto", expectedIdPac, pacientes.getIdPacinte());

        return pacientes;
    }

    @Test
    public  void medicosCRUDtest(){

        DbConsultasOpenHelper dbConsultasOpenHelper = new DbConsultasOpenHelper(getContext());
        SQLiteDatabase db = dbConsultasOpenHelper.getWritableDatabase();

        DbTableConsultas tableConsultas = new DbTableConsultas(db);

        Consultas consultas = new Consultas();
        consultas.setTipoConsulta("Rotina");

        DbTableMedicos tableMedicos = new DbTableMedicos(db);

        //  // criar e inserir dados (C) RUD

        Medicos medicos = new Medicos();
         medicos.setNomeMed("Joao Maia");
         medicos.setTelemovelmed(909090901);
         medicos.setEmailMed("maia2@gmail.com");

         long idMed = tableMedicos.insert(DbTableMedicos.getContentValues(medicos));

         assertEquals("Falha a inserir medico", -1, idMed);

        // query/read C(R)UD
        medicos = ReadFirstMedicos(tableMedicos, "Joao Maia", "maia2@gmail.com", 909090901, idMed);

        // update CR(U)D
        medicos.setNomeMed("Joao Matos");
        medicos.setEmailMed("matos1@gmail.com");
        medicos.setTelemovelmed(929292921);

        int rowsAffected = tableMedicos.update(DbTableMedicos.getContentValues(medicos),
                DbTableMedicos._ID + "=?",
                new String[]{Long.toString(idMed)});
        assertEquals("Falha a atualizar o medico", 1, rowsAffected);

        // query/read C(R)UD

        medicos = ReadFirstMedicos(tableMedicos, "Joao Matos",
                "matos1@gmail.com", 929292921, idMed);


        // delete CRU(D)
        rowsAffected = tableMedicos.delete(DbTableMedicos._ID + "=?",
                new String[]{Long.toString(idMed)});
        assertEquals("Falha a deletar medico", 1, rowsAffected);

        Cursor cursor = tableMedicos.query(DbTableMedicos.ALL_COLUMNS_Med, null, null,
                null, null,null);
        assertEquals("medicos encontrados após a atualização de exclusão?", 0, cursor.getCount());
    }




    private  Medicos ReadFirstMedicos(DbTableMedicos tableMedicos, String espectednomeMed,
                                      String espectedEmail, long espectedMovel, long espectedId){
       Cursor cursor = tableMedicos.query(DbTableMedicos.ALL_COLUMNS_Med, null, null,
               null, null,null);

       assertEquals("Falha a ler medicos", 1, cursor.getCount());
       assertTrue("Falaha a ler o primeiro medico", cursor.moveToNext());

        Medicos medicos = DbTableMedicos.getCurrentMedicosFromCursor(cursor);

        assertEquals("nome do medico incorreto", espectednomeMed, medicos.getNomeMed());
        assertEquals("telemovel do medico incorreto", espectedMovel, medicos.getTelemovelmed());
        assertEquals("id dp medico incoreto", espectedId, medicos.getContribuinteMed());
        assertEquals("email do medico incorreto", espectedEmail, medicos.getEmailMed());

        return medicos;
    }

    @Test
    public void distritoCRUDtest(){

        DbConsultasOpenHelper dbConsultasOpenHelper = new DbConsultasOpenHelper(getContext());
        SQLiteDatabase db = dbConsultasOpenHelper.getWritableDatabase();

        DbTableDistritos tableDistritos = new DbTableDistritos(db);

        //  // criar e inserir dados (C) RUD
        Distritos distritos = new Distritos();

        distritos.setNomeDis("Guarda");
        long idDis = tableDistritos.insert(DbTableDistritos.getContentValues(distritos));
        assertNotEquals("Falha a inserir distrito", -1, idDis);

        // query/read C(R)UD
        distritos = ReadFirstDistritos(tableDistritos, "Guarda", idDis);

        // update CR(U)D
        distritos.setNomeDis("Viseu");

        int rowsAffected = tableDistritos.update( DbTableDistritos.getContentValues(distritos),
                DbTableDistritos._ID + "=?", new String[]{Long.toString(idDis)});
        assertNotEquals("Falha a atualizar distrito", 1, rowsAffected);

        // criar e inserir dados (C) RUD
        distritos = ReadFirstDistritos(tableDistritos, "Viseu", idDis);

        // delete CRU(D)

        rowsAffected = tableDistritos.delete(DbTableDistritos._ID + "=?",
                new String[]{Long.toString(idDis)});

        assertEquals("Falha a deletar distrito", 1, rowsAffected);

        Cursor cursor = tableDistritos.query(DbTableDistritos.All_CLUMNS_DIS, null, null,
                null, null, null);
        assertEquals("distritos encontrados após a atualização de exclusão", 0, cursor.getCount());

    }

    private Distritos ReadFirstDistritos(DbTableDistritos tableDistritos,
                                         String espectedNomedis, long expectedIdDis){
        Cursor cursor = tableDistritos.query(DbTableDistritos.All_CLUMNS_DIS, null, null,
                null, null, null);
        assertEquals("Falha a ler Distritod", 1, cursor.getCount());
        assertTrue("Falaha a ler o primeiro distrito", cursor.moveToNext());

        Distritos distritos = DbTableDistritos.getCurrentDistritosFromCursor(cursor);
        assertEquals("Nome do dostrito incorreto", espectedNomedis, distritos.getNomeDis());
        assertEquals("id do dostrito incorreto", expectedIdDis, distritos.getIdDis());

        return distritos;
    }

}
