package pt.ipg.marcaoconsultas;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;



public class DbTableDistritos implements BaseColumns {


        public static final String TABLE_DIS = "distritos";
        private static final String FIELD_NAME_DIS = "nome";

        public  static final  String[] All_CLUMNS_DIS = new String[]{_ID, FIELD_NAME_DIS};


        private SQLiteDatabase db;
        public DbTableDistritos(SQLiteDatabase db) {
            this.db = db;
        }

        public void create() {
            db.execSQL(
                    "CREATE TABLE " + TABLE_DIS + "(" +
                            _ID + "INTEGER PRIMARY KEY AUTOINCREMENT," +
                            FIELD_NAME_DIS + "TEXT NOT NULL" +
                            ")"
            );

        }

        public static ContentValues getContentValues(Distritos distrito){
            ContentValues values = new ContentValues();
            values.put(_ID, distrito.getIdDis());
            values.put(FIELD_NAME_DIS, distrito.getNomeDis());

            return values;
        }

        public static Distritos getCurrentDistritosFromCursor(Cursor cursor){

            final int posIdDis = cursor.getColumnIndex(_ID);
            final int posNameDis = cursor.getColumnIndex(FIELD_NAME_DIS);

            Distritos distrito = new Distritos();

            distrito.setIdDis(cursor.getInt(posIdDis));
            distrito.setNomeDis((cursor.getString(posNameDis)));

            return distrito;
        }

        public long insert(ContentValues values){
            return db.insert(TABLE_DIS, null, values);
        }

    /**
     * Convenience method for updating rows in the categories table.
     *
     * @param values a map from column names to new column values. null is a
     *            valid value that will be translated to NULL.
     * @param whereClause the optional WHERE clause to apply when updating.
     *            Passing null will update all rows.
     * @param whereArgs You may include ?s in the where clause, which
     *            will be replaced by the values from whereArgs. The values
     *            will be bound as Strings.
     * @return the number of rows affected
     */


        public int update(ContentValues values, String whereClause, String[] whereArgs ){
            return db.update(TABLE_DIS, values, whereClause, whereArgs);
        }

    /**
     * Convenience method for deleting rows in the categories table.
     *
     * @param whereClause the optional WHERE clause to apply when deleting.
     *            Passing null will delete all rows.
     * @param whereArgs You may include ?s in the where clause, which
     *            will be replaced by the values from whereArgs. The values
     *            will be bound as Strings.
     * @return the number of rows affected if a whereClause is passed in, 0
     *         otherwise. To remove all rows and get a count pass "1" as the
     *         whereClause.
     */


        public  int delete (String whereClause, String[] whereArgs){
            return  db.delete(TABLE_DIS, whereClause, whereArgs);
        }


        public Cursor query(String[] columns, String selection, String[] selectionArgs,
                            String groupBy, String having, String orderBy){


            return db.query(TABLE_DIS, columns, selection, selectionArgs, groupBy, having,
                    orderBy);
        }



}
