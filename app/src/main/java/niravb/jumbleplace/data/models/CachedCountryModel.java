package niravb.jumbleplace.data.models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;

import niravb.jumbleplace.data.provider.JPUriManager;
import niravb.jumbleplace.data.sql.tables.CachedCountriesTable;

public class CachedCountryModel {

    //region API
    public CachedCountryModel(Context context, String originalName) {
        this.context = context;
        this.originalName = originalName;
    }

    public static Loader<Cursor> getAll(Context context) {
        return new CursorLoader(context,
                JPUriManager.BASE_URI.buildUpon().appendPath(JPUriManager.URI_PATH_CACHED_COUNTRIES).build(),
                null, null, null, null);
    }

    public void create() {
        ContentValues scoreValues = new ContentValues();

        scoreValues.put(CachedCountriesTable.ColumnOriginalName.NAME, originalName);

        context.getContentResolver().insert(
                JPUriManager.BASE_URI.buildUpon()
                        .appendPath(JPUriManager.URI_PATH_CACHED_COUNTRIES).build(),
                scoreValues);

    }

    public static void deleteAll(Context context) {

        context.getContentResolver().delete(JPUriManager.BASE_URI.buildUpon()
                .appendPath(JPUriManager.URI_PATH_CACHED_COUNTRIES).build(), null, null);


    }

    public static int getCount(Context context) {

        Cursor cursor = context.getContentResolver().query(JPUriManager.BASE_URI.buildUpon()
                        .appendPath(JPUriManager.URI_PATH_CACHED_COUNTRIES).build(),
                new String[]{"count(*)"}, null, null, null);
        cursor.moveToFirst();
        int countValue = cursor.getInt(0);
        cursor.close();
        return countValue;

    }

    //endregion

    //region Internals

    private final String originalName;

    private final Context context;

    //endregion

}
