package pt.ipg.marcaoconsultas;

//import android.app.LoaderManager;
//import android.content.CursorLoader;
import android.content.Intent;
//import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>{

    private static final int CONSULTA_CURSOR_LOADER_ID = 0;


    private ConsultasCursorAdapter consultasCursorAdapter;
    public static final String CONSULTA_ID = "CONSULTA_ID";
    private RecyclerView recyclerViewConsultas;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerViewConsultas = (RecyclerView) findViewById(R.id.recyclerViewConsultas);

        recyclerViewConsultas.setLayoutManager(new LinearLayoutManager(this));
        consultasCursorAdapter = new ConsultasCursorAdapter(this);
        recyclerViewConsultas.setAdapter(consultasCursorAdapter);


        //consultasCursorAdapter.setViewHolderClickListener( new View.);

     getSupportLoaderManager().initLoader(CONSULTA_CURSOR_LOADER_ID, null, this);
    }
    private void editMeusDados(){
        int id = consultasCursorAdapter.getLastConcsultasClicked();

        Intent intent = new Intent(this, EditConsultaActivity.class);
        intent.putExtra(CONSULTA_ID, id);

        startActivity(intent);
    }

    @Override
    protected void onResume(){
        super.onResume();
       getSupportLoaderManager().restartLoader(CONSULTA_CURSOR_LOADER_ID, null, this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Instantiate and return a new Loader for the given ID.
     *
     * @param id The ID whose loader is to be created.
     * @param args Any arguments supplied by the caller.
     * @return Return a new Loader instance that is ready to start loading.
     */


    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        if (id == CONSULTA_CURSOR_LOADER_ID){
            return new CursorLoader(this, ConsultaContentProvider.MEUS_DADOS_URI, DbTableMeusDados.All_CoLMNS, null,null, null);
        }
        return null;
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        consultasCursorAdapter.refreshData(data);

    }

    /**
     * Called when a previously created loader is being reset, and thus
     * making its data unavailable.  The application should at this point
     * remove any references it has to the Loader's data.
     *
     * @param loader The Loader that is being reset.
     */

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

        consultasCursorAdapter.refreshData(null);

    }
}
