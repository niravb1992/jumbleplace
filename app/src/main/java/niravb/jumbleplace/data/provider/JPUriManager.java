package niravb.jumbleplace.data.provider;

import android.content.UriMatcher;
import android.net.Uri;

import niravb.jumbleplace.data.sql.tables.CachedCountriesTable;
import niravb.jumbleplace.data.sql.tables.ScoresTable;

public class JPUriManager {

    public static final Uri BASE_URI = Uri.parse("content://" + JPContentProvider.AUTHORITY_NAME);

    public static final String URI_PATH_SCORES = ScoresTable.TABLE_NAME;
    public static final String URI_PATH_SCORE_WITH_ID = ScoresTable.TABLE_NAME + "/#";
    public static final String URI_PATH_CACHED_COUNTRIES = CachedCountriesTable.TABLE_NAME;
    public static final String URI_PATH_CACHED_COUNTRY_WITH_ID = CachedCountriesTable.TABLE_NAME + "/#";

    public static final int URI_SCORES = 1;
    public static final int URI_CACHED_COUNTRIES = 2;

    public static UriMatcher getUriMatcher() {
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

        uriMatcher.addURI(
                JPContentProvider.AUTHORITY_NAME,
                URI_PATH_SCORES,
                URI_SCORES
        );
        uriMatcher.addURI(
                JPContentProvider.AUTHORITY_NAME,
                URI_PATH_CACHED_COUNTRIES,
                URI_CACHED_COUNTRIES
        );

        return uriMatcher;
    }

}
