package pt.ipg.marcaoconsultas;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;



public class ConsultaContentProvider extends ContentProvider {

    private static final String AUTHORITY = "pt.ipg.marcaoconsultas";

    public static final Uri BASE_URI = Uri.parse("conten://" + AUTHORITY);
    public static final Uri CONSULTA_URI = Uri.withAppendedPath(BASE_URI, DbTableConsultas.TABLE_CONSULTAS);
    public static final  Uri MEUS_DADOS_URI = Uri.withAppendedPath(BASE_URI,
            DbTableMeusDados.TABLE_NAME);
    public static final Uri MEDICOS_URI = Uri.withAppendedPath(BASE_URI,
            DbTableMeusDados.TABLE_NAME);
    public static final  Uri DISTRITOS_URI = Uri.withAppendedPath(BASE_URI,
            DbTableDistritos.TABLE_DIS);

    private static final int CONSULTA = 100;
    private static final int ID_CONSULTA = 101;
    private static final int MEUS_DADOS = 200;
    private static final int ID_DADOS = 201;
    private static final int MEDICO = 300;
    private static final int ID_MEDICO = 301;
    private static final int DISTRITO = 400;
    private static final int ID_DISTRITO = 401;

    private static final String MULTIPLE_ITENS = "vnd.android.cursor.dir" ;
    private static final String SINGLE_ITEM = "vnd.android.cursor.item";

    DbConsultasOpenHelper consultasOpenHelper;


    private static UriMatcher getConsultasUriMatcher(){

        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

        uriMatcher.addURI(AUTHORITY, "consultas", CONSULTA);
        uriMatcher.addURI(AUTHORITY, "consultas/#", ID_CONSULTA);
        uriMatcher.addURI(AUTHORITY, "meus dados", MEUS_DADOS);
        uriMatcher.addURI(AUTHORITY, "meus dados/#", ID_DADOS);
        uriMatcher.addURI(AUTHORITY, "medico", MEDICO);
        uriMatcher.addURI(AUTHORITY, "medico/#", ID_MEDICO);
        uriMatcher.addURI(AUTHORITY, "distrito", DISTRITO);
        uriMatcher.addURI(AUTHORITY, "distrito/#", ID_DISTRITO);

        return uriMatcher;
    }


    /**
     * Implement this to initialize your content provider on startup.
     * This method is called for all registered content providers on the
     * application main thread at application launch time.  It must not perform
     * lengthy operations, or application startup will be delayed.
     *
     * <p>You should defer nontrivial initialization (such as opening,
     * upgrading, and scanning databases) until the content provider is used
     * (via {@link #query}, {@link #insert}, etc).  Deferred initialization
     * keeps application startup fast, avoids unnecessary work if the provider
     * turns out not to be needed, and stops database errors (such as a full
     * disk) from halting application launch.
     *
     * <p>If you use SQLite, {@link android.database.sqlite.SQLiteOpenHelper}
     * is a helpful utility class that makes it easy to manage databases,
     * and will automatically defer opening until first use.  If you do use
     * SQLiteOpenHelper, make sure to avoid calling
     * {@link android.database.sqlite.SQLiteOpenHelper#getReadableDatabase} or
     * {@link android.database.sqlite.SQLiteOpenHelper#getWritableDatabase}
     * from this method.  (Instead, override
     * {@link android.database.sqlite.SQLiteOpenHelper#onOpen} to initialize the
     * database when it is first opened.)
     *
     * @return true if the provider was successfully loaded, false otherwise
     */

    @Override
    public boolean onCreate() {
        consultasOpenHelper = new DbConsultasOpenHelper(getContext());

        return true;
    }

    /**
     * Implement this to handle query requests from clients.
     *
     * <p>Apps targeting {@link android.os.Build.VERSION_CODES#O} or higher should override
     * {@link #query(Uri, String[], Bundle, CancellationSignal)} and provide a stub
     * implementation of this method.
     *
     * <p>This method can be called from multiple threads, as described in
     * <a href="{@docRoot}guide/topics/fundamentals/processes-and-threads.html#Threads">Processes
     * and Threads</a>.
     * <p>
     * Example client call:<p>
     * <pre>// Request a specific record.
     * Cursor managedCursor = managedQuery(
     ContentUris.withAppendedId(Contacts.People.CONTENT_URI, 2),
     projection,    // Which columns to return.
     null,          // WHERE clause.
     null,          // WHERE clause value substitution
     People.NAME + " ASC");   // Sort order.</pre>
     * Example implementation:<p>
     * <pre>// SQLiteQueryBuilder is a helper class that creates the
     // proper SQL syntax for us.
     SQLiteQueryBuilder qBuilder = new SQLiteQueryBuilder();

     // Set the table we're querying.
     qBuilder.setTables(DATABASE_TABLE_NAME);

     // If the query ends in a specific record number, we're
     // being asked for a specific record, so set the
     // WHERE clause in our query.
     if((URI_MATCHER.match(uri)) == SPECIFIC_MESSAGE){
     qBuilder.appendWhere("_id=" + uri.getPathLeafId());
     }

     // Make the query.
     Cursor c = qBuilder.query(mDb,
     projection,
     selection,
     selectionArgs,
     groupBy,
     having,
     sortOrder);
     c.setNotificationUri(getContext().getContentResolver(), uri);
     return c;</pre>
     *
     * @param uri The URI to query. This will be the full URI sent by the client;
     *      if the client is requesting a specific record, the URI will end in a record number
     *      that the implementation should parse and add to a WHERE or HAVING clause, specifying
     *      that _id value.
     * @param projection The list of columns to put into the cursor. If
     *      {@code null} all columns are included.
     * @param selection A selection criteria to apply when filtering rows.
     *      If {@code null} then all rows are included.
     * @param selectionArgs You may include ?s in selection, which will be replaced by
     *      the values from selectionArgs, in order that they appear in the selection.
     *      The values will be bound as Strings.
     * @param sortOrder How the rows in the cursor should be sorted.
     *      If {@code null} then the provider is free to define the sort order.
     * @return a Cursor or {@code null}.
     */

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection,
                        @Nullable String selection, @Nullable String[] selectionArgs,
                        @Nullable String sortOrder) {

        SQLiteDatabase db = consultasOpenHelper.getReadableDatabase();

        String id = uri.getLastPathSegment();
        UriMatcher matcher = getConsultasUriMatcher();

        switch (matcher.match(uri)){
            case CONSULTA:
                return new DbTableConsultas(db).query(projection, selection, selectionArgs,
                        null, null, sortOrder);
            case ID_CONSULTA:
                return new DbTableConsultas(db).query(projection, DbTableConsultas._ID + "=?",
                        new String[] {id}, null, null, null);
            case MEUS_DADOS:
                return new DbTableMeusDados(db).query(projection, selection, selectionArgs,
                        null, null, null);

            case MEDICO:
                return new DbTableMedicos(db).query(projection, selection, selectionArgs,
                        null, null, null);

            case DISTRITO:
                return new DbTableMeusDados(db).query(projection, selection, selectionArgs,
                        null, null, null);

            case ID_MEDICO:
                return new DbTableMedicos(db).query(projection,
                        DbTableMedicos._ID + "=?",
                        new String[] {id}, null, null, null);

            case ID_DADOS:
                return new DbTableMeusDados(db).query(projection,
                        DbTableMeusDados._ID + "=?",
                        new String[] {id}, null, null, null);

            case ID_DISTRITO:
                return new DbTableDistritos(db).query(projection,
                        DbTableDistritos._ID + "=?",
                        new String[] {id}, null, null, null);

            default:
                throw new UnsupportedOperationException("Uri Invalido:" + uri);
        }

    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {

        UriMatcher matcher = getConsultasUriMatcher();

        switch (matcher.match(uri)){
            case CONSULTA:
                return MULTIPLE_ITENS + "/" + AUTHORITY + "/" +
                        DbTableConsultas.TABLE_CONSULTAS;
            case ID_CONSULTA:
                return SINGLE_ITEM + "/" + AUTHORITY + "/" +
                        DbTableConsultas.TABLE_CONSULTAS;


            case MEUS_DADOS:
                return MULTIPLE_ITENS + "/" + AUTHORITY + "/" +
                        DbTableMeusDados.TABLE_NAME;
            case ID_DADOS:
                return SINGLE_ITEM + "/" + AUTHORITY + "/" +
                        DbTableMeusDados.TABLE_NAME;


            case MEDICO:
                return MULTIPLE_ITENS + "/" + AUTHORITY + "/" +
                        DbTableMedicos.MEDICOS_NAME;
            case ID_MEDICO:
                return SINGLE_ITEM + "/" + AUTHORITY + "/" +
                        DbTableMedicos.MEDICOS_NAME;


            case DISTRITO:
                return MULTIPLE_ITENS + "/" + AUTHORITY + "/" +
                        DbTableDistritos.TABLE_DIS;
            case ID_DISTRITO:
                return SINGLE_ITEM + "/" + AUTHORITY + "/" +
                        DbTableDistritos.TABLE_DIS;

            default:
                throw new UnsupportedOperationException("Unknown URI: " + uri);
        }

    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {

        SQLiteDatabase db = consultasOpenHelper.getWritableDatabase();
        UriMatcher matcher = getConsultasUriMatcher();

        long id = -1;

        switch (matcher.match(uri)){
            case CONSULTA:
                id = new DbTableConsultas(db).insert(values);
                break;
            case MEDICO:
                id = new DbTableMedicos(db).insert(values);
                break;
            case MEUS_DADOS:
                id = new DbTableMeusDados(db).insert(values);
                break;
            case DISTRITO:
                id = new DbTableDistritos(db).insert(values);
                break;


            default:
                throw new UnsupportedOperationException("Unknown URI: " + uri);
        }
        if (id > 0) {
            notifyChanges(uri);
            return Uri.withAppendedPath(uri, Long.toString(id));
        }else {
            throw new SQLException(" não foi possivel inserir registo");
        }

    }

    private void notifyChanges(@NonNull Uri uri){
        getContext().getContentResolver().notifyChange(uri, null);
    }


    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {

        SQLiteDatabase db = consultasOpenHelper.getWritableDatabase();

        UriMatcher matcher = getConsultasUriMatcher();
        String id = uri.getLastPathSegment();

        int linhas = 0;

        switch (matcher.match(uri)){

            case ID_CONSULTA:
                linhas = new DbTableConsultas(db).delete( DbTableConsultas._ID + "=?",
                        new String[] { id });

            case ID_MEDICO:
                linhas = new DbTableMedicos(db).delete(
                        DbTableMedicos._ID + "=?",
                        new String[] {id});
                break;

            case ID_DADOS:
                linhas = new DbTableMeusDados(db).delete(
                        DbTableMeusDados._ID + "=?",
                        new String[] {id});
                break;

            case ID_DISTRITO:
                linhas = new DbTableDistritos(db).delete(
                        DbTableDistritos._ID + "=?",
                        new String[] {id});
                break;

            default:
                throw new UnsupportedOperationException("Uri Invalido:" + uri);

        }

        if(linhas > 0) notifyChanges(uri);
        return linhas;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {

       SQLiteDatabase db = consultasOpenHelper.getWritableDatabase();
       UriMatcher matcher = getConsultasUriMatcher();
       String id = uri.getLastPathSegment();

       int linhas;

        switch (matcher.match(uri)){

            case ID_CONSULTA:
                linhas = new DbTableConsultas(db).update(values, DbTableConsultas._ID + "=?",
                        new String[] { id });

            case ID_MEDICO:
                linhas = new DbTableMedicos(db).update(values,
                        DbTableMedicos._ID + "=?",
                        new String[] {id});
                break;

            case ID_DADOS:
                linhas = new DbTableMeusDados(db).update(values,
                        DbTableMeusDados._ID + "=?",
                        new String[] {id});
                break;

            case ID_DISTRITO:
                linhas = new DbTableDistritos(db).update(values,
                        DbTableDistritos._ID + "=?",
                        new String[] {id});
                break;

            default:
                throw new UnsupportedOperationException("Uri Invalido:" + uri);

        }

        if(linhas > 0) notifyChanges(uri);
        return linhas;

    }
}
