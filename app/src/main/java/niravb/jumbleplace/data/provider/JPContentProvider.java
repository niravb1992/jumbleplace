package niravb.jumbleplace.data.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

import niravb.jumbleplace.data.sql.JPSqlDatabaseHelper;
import niravb.jumbleplace.data.sql.tables.CachedCountriesTable;
import niravb.jumbleplace.data.sql.tables.ScoresTable;

public class JPContentProvider extends ContentProvider {

    public static final String AUTHORITY_NAME = "niravb.jumbleplace.provider.jpcontentprovider";

    private JPSqlDatabaseHelper jpSqlDatabaseHelper;
    private static final UriMatcher uriMatcher = JPUriManager.getUriMatcher();

    @Override
    public boolean onCreate() {
        jpSqlDatabaseHelper = new JPSqlDatabaseHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {

        Cursor cursor;
        switch (uriMatcher.match(uri)) {
            case JPUriManager.URI_SCORES:
                cursor = jpSqlDatabaseHelper.getReadableDatabase().query(
                        ScoresTable.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            case JPUriManager.URI_CACHED_COUNTRIES:
                cursor = jpSqlDatabaseHelper.getReadableDatabase().query(
                        CachedCountriesTable.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            default:
                throw new UnsupportedOperationException("The URI " + uri + " could not be found.");
        }

        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;

    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {

        Uri retUri;

        try {

            switch (uriMatcher.match(uri)) {
                case JPUriManager.URI_SCORES:
                    long insertedScoreId = jpSqlDatabaseHelper.getWritableDatabase().
                            insert(ScoresTable.TABLE_NAME, null, values);
                    if (insertedScoreId > 0)
                        retUri = ContentUris.withAppendedId(
                                JPUriManager.
                                        BASE_URI.
                                        buildUpon().
                                        appendPath(JPUriManager.URI_PATH_SCORE_WITH_ID).build(),
                                insertedScoreId
                        );
                    else
                        throw new android.database.SQLException("Unknown failure while inserting " +
                                "row into " + uri);
                    break;
                case JPUriManager.URI_CACHED_COUNTRIES:
                    long insertedCountryId = jpSqlDatabaseHelper.getWritableDatabase().
                            insert(CachedCountriesTable.TABLE_NAME, null, values);
                    if (insertedCountryId > 0)
                        retUri = ContentUris.withAppendedId(
                                JPUriManager.
                                        BASE_URI.
                                        buildUpon().
                                        appendPath(JPUriManager.URI_PATH_CACHED_COUNTRY_WITH_ID).build(),
                                insertedCountryId
                        );
                    else
                        throw new android.database.SQLException("Unknown failure while inserting " +
                                "row into " + uri);
                    break;
                default:
                    throw new UnsupportedOperationException("The URI " + uri + " could not be found.");
            }

            getContext().getContentResolver().notifyChange(retUri, null);
            return retUri;

        } catch (Exception e) {
            return null;
        }

    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {

        int rowsDeletedCount;
        switch (uriMatcher.match(uri)) {
            // case JPUriManager.URI_SCORE:
            case JPUriManager.URI_SCORES:
                rowsDeletedCount = jpSqlDatabaseHelper.getWritableDatabase().
                        delete(ScoresTable.TABLE_NAME, selection, selectionArgs);
                if (rowsDeletedCount > 0)
                    getContext().getContentResolver().notifyChange(uri, null);
                break;
            case JPUriManager.URI_CACHED_COUNTRIES:
                // NOTE: We do not want to notify data change here because after we fetch the cached
                // countries for the current game, those exact countries are deleted in the
                // background, which causes a weird behavior, specifically, an infinite loop of
                // data being created and deleted.
                rowsDeletedCount = jpSqlDatabaseHelper.getWritableDatabase().
                        delete(CachedCountriesTable.TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("The URI " + uri + " could not be found.");
        }

        return rowsDeletedCount;

    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }

}
