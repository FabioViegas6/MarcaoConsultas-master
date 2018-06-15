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
        consultas.setTipoConsulta("Saúde Mental");

        // criar e inserir dados (C) RUD
        Long id = insertConsultas(tableConsultas, consultas);



    }
    private long insertConsultas (DbTableConsultas tableConsultas, Consultas consultas){
        long id = tableConsultas.insert(
                DbTableConsultas.getContentValues(consultas)
        );
        assertNotEquals("Falha a inserir consultas", -1, id);

        return id;
    }

    @NonNull
    private Consultas ReadFirstConsultas (DbTableConsultas tableConsultas, String expectedTipo, long expecteIdConsultas,
                                          String expectedData, String expectedMedico, String expectedPacientes){
       Cursor cursor = tableConsultas.query(DbTableConsultas.All_COLUMNS, null, null,
               null,null,null);

       assertEquals("Falha a ler as consultas", 1, cursor.getCount());

       assertTrue("Falha a ler a primeira consulta", cursor.moveToNext());

        Consultas consultas = DbTableConsultas.getCurrentConsultasFromCursor(cursor);

        assertEquals("id da consulta incorretp", expecteIdConsultas, consultas.getIdConsultas());
        assertEquals("paciente incorreto", expectedPacientes, consultas.getPacintes());
        assertEquals("data da consulta incorreta ", expectedData, consultas.getData());
        assertEquals("medico da consulta incorreto", expectedMedico, consultas.getMedico());
        assertEquals("tipo de consulta incorreto", expectedTipo, consultas.getTipoConsulta());

        return consultas;
    }
}
