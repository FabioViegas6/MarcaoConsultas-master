package pt.ipg.marcaoconsultas;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;



public class ConsultaContentProvider extends ContentProvider {

    private static final String AUTHORITY = "pt.ipg.marcaoconsultas";

    private static int CONSULTA = 100;
    private static int ID_CONSULTA = 101;
    private static int MEUS_DADOS = 200;
    private static int ID_DADOS = 201;
    private static int MEDICO = 300;
    private static int ID_MEDICO = 301;
    private static int DISTRITO = 400;
    private static int ID_DISTRITO = 401;

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
        return false;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        return null;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}
