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

        // criar e inserir dados (C) RUD
        long IdConsultas = insertConsultas(tableConsultas, consultas);

        // query and read C(R)UD
        consultas = ReadFirstConsultas(tableConsultas, "Saude Mental", IdConsultas);

        // update CR(U)D
        consultas.setTipoConsulta("Psicatria");
        int rowsAffected = tableConsultas.update(
                DbTableConsultas.getContentValues(consultas),
                DbTableConsultas._ID + "=?",
                new String[] {Long.toString(IdConsultas)}
        );
        assertEquals("Falha a atualizar a consulta",1, rowsAffected);

        ///////////// query and read C(R)UD
        consultas = ReadFirstConsultas(tableConsultas, "Psicatria", IdConsultas );

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

    @Test
    public void DadosCRUDtest(){

        DbConsultasOpenHelper dbConsultasOpenHelper = new DbConsultasOpenHelper(getContext());
        SQLiteDatabase db = dbConsultasOpenHelper.getWritableDatabase();

        DbTableConsultas tableConsultas = new DbTableConsultas(db);
        DbTableMeusDados tableMeusDados = new DbTableMeusDados(db);

        Consultas consultas = new Consultas();
        consultas.setTipoConsulta("Rotina");

        long idConsultas = insertConsultas(tableConsultas, consultas);

        // criar e inserir dados (C) RUD

        MeusDados meusDados = new MeusDados();
        meusDados.setNome("Fabio Junior");
        meusDados.setTelemovel(99342452);
        meusDados.setId((int) idConsultas);


        long id = tableMeusDados.insert(
                DbTableMeusDados.getContentValues(meusDados)
        );
        assertNotEquals("Falha a inserir os dados", -1, id );

        // query/read C(R)UD

        meusDados = ReadFirstDados(tableMeusDados, "Fabio Junior", 99342452, idConsultas, id);

        // update CR(U)D
        meusDados.setNome("Fabio Junior dos Santos");
        meusDados.setTelemovel(99344433);


        int ronwFffected = tableMeusDados.update(
                DbTableMeusDados.getContentValues(meusDados),
                DbTableMeusDados._ID +"=?",
                new String[]{Long.toString(id)}
        );
        assertEquals("Falha a atulizar os dados", 1, ronwFffected);

        // query/read C(R)UD

        meusDados = ReadFirstDados(tableMeusDados, "Fabio Vieira dos Santos",
                99344433, idConsultas, id);

        /// delete CRU (D)

        ronwFffected = tableMeusDados.delete(
                DbTableMeusDados._ID + "=?",
                new String[]{Long.toString(id)}
        );

        assertEquals("Falha a deletar os dados", 1, ronwFffected);

        Cursor cursor = tableMeusDados.query(DbTableMeusDados.All_CoLMNS, null, null,
                null,null, null);
        assertEquals("pacientes econtrados apos ser apagdos?", 0, cursor.getCount());


    }

    @Test
    public  void medicosCRUDtest(){

        DbConsultasOpenHelper dbConsultasOpenHelper = new DbConsultasOpenHelper(getContext());
        SQLiteDatabase db = dbConsultasOpenHelper.getWritableDatabase();

        //  DbTableConsultas tableConsultas = new DbTableConsultas(db);

        Consultas consultas = new Consultas();
        consultas.setTipoConsulta("Rotina");

        DbTableMedicos tableMedicos = new DbTableMedicos(db);

        //  // criar e inserir dados (C) RUD

        Medicos medicos = new Medicos();
        medicos.setNomeMed("Joao Maia");


        long idMed = tableMedicos.insert(DbTableMedicos.getContentValues(medicos));

        assertEquals("Falha a inserir medico", -1, idMed);

        // query/read C(R)UD
        medicos = ReadFirstMedicos(tableMedicos, "Joao Maia", idMed);

        // update CR(U)D
        medicos.setNomeMed("Joao Matos");


        int rowsAffected = tableMedicos.update(DbTableMedicos.getContentValues(medicos),
                DbTableMedicos._ID + "=?",
                new String[]{Long.toString(idMed)});
        assertEquals("Falha a atualizar o medico", 1, rowsAffected);

        // query/read C(R)UD

        medicos = ReadFirstMedicos(tableMedicos, "Joao Matos", idMed);


        // delete CRU(D)
        rowsAffected = tableMedicos.delete(DbTableMedicos._ID + "=?",
                new String[]{Long.toString(idMed)});
        assertEquals("Falha a deletar medico", 1, rowsAffected);

        Cursor cursor = tableMedicos.query(DbTableMedicos.ALL_COLUMNS_Med, null, null,
                null, null,null);
        assertEquals("medicos encontrados após a atualização de exclusão?", 0, cursor.getCount());
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



    private long insertConsultas (DbTableConsultas tableConsultas, Consultas consultas){
        long id = tableConsultas.insert(
                DbTableConsultas.getContentValues(consultas)
        );
        assertNotEquals("Falha a inserir consultas", -1, id);

        return id;
    }






    private MeusDados ReadFirstDados(DbTableMeusDados tableMeusDados,
                                        String expectedNome, long expectedTelemovel,
                                         long expectedId, long expectedIdConsulta){
        Cursor cursor = tableMeusDados.query(DbTableMeusDados.All_CoLMNS, null, null,
                null,null, null);
        assertEquals("Falha a ler os meus dados", 1, cursor.getCount());
        assertEquals("Falha a ler o primeiro dado", cursor.moveToNext());

        MeusDados meusDados = DbTableMeusDados.getCurrentMeusDadosBookFromCursor(cursor);

        assertEquals(" nome  incorreto", expectedNome, meusDados.getNome());
        assertEquals("telemovel  incorreto ", expectedTelemovel, meusDados.getTelemovel());
        assertEquals("id  incorreto", expectedId, meusDados.getId());
        assertEquals("id da consulta incorreto", expectedIdConsulta, meusDados.getIdconsultas());

        return meusDados;
    }




    private  Medicos ReadFirstMedicos(DbTableMedicos tableMedicos,
                                      String espectednomeMed, long espectedId){
       Cursor cursor = tableMedicos.query(DbTableMedicos.ALL_COLUMNS_Med, null, null,
               null, null,null);

       assertEquals("Falha a ler medicos", 1, cursor.getCount());
       assertTrue("Falaha a ler o primeiro medico", cursor.moveToNext());

        Medicos medicos = DbTableMedicos.getCurrentMedicosFromCursor(cursor);

        assertEquals("nome do medico incorreto", espectednomeMed, medicos.getNomeMed());
        assertEquals("id dp medico incoreto", espectedId, medicos.getId());

        return medicos;
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

    @NonNull
    private Consultas ReadFirstConsultas(DbTableConsultas tableConsultas, String expectedTipo, long expectedIdConsultas){
        Cursor cursor = tableConsultas.query(DbTableConsultas.All_COLUMNS, null, null,
                null,null,null);

        assertEquals("Falha a ler as consultas", 1, cursor.getCount());

        assertTrue("Falha a ler a primeira consulta", cursor.moveToNext());

        Consultas consultas = DbTableConsultas.getCurrentConsultasFromCursor(cursor);

        assertEquals("id da consulta incorretp", expectedIdConsultas, consultas.getIdConsultas());
        assertEquals("tipo de consulta incorreto", expectedTipo, consultas.getTipoConsulta());

        return consultas;
    }

    private Context getContext(){
        return InstrumentationRegistry.getTargetContext();
    }
}
