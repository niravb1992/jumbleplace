package niravb.jumbleplace.data.models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;

import java.text.SimpleDateFormat;
import java.util.Date;

import niravb.jumbleplace.Utilities;
import niravb.jumbleplace.data.provider.JPUriManager;
import niravb.jumbleplace.data.sql.tables.ScoresTable;

public class ScoreModel {

    //region API
    public ScoreModel(Context context, int scoredPoints) {
        this.context = context;
        this.scoredPoints = scoredPoints;
        this.totalPoints = Utilities.getNumberOfCountriesPreferenceValue(context);
    }

    public static Loader<Cursor> getAll(Context context) {
        return new CursorLoader(context,
                JPUriManager.BASE_URI.buildUpon().appendPath(JPUriManager.URI_PATH_SCORES).build(),
                null, null, null, null);
    }

    public boolean create() {
        ContentValues scoreValues = new ContentValues();

        SimpleDateFormat sdf = new SimpleDateFormat(Utilities.CREATED_AT_FORMAT,
                context.getResources().getConfiguration().locale);
        Date nowDateTime = new Date();

        scoreValues.put(ScoresTable.ColumnCreatedAt.NAME, sdf.format(nowDateTime));
        scoreValues.put(ScoresTable.ColumnScoredPoints.NAME, scoredPoints);
        scoreValues.put(ScoresTable.ColumnTotalPoints.NAME, totalPoints);

        Uri uri = context.getContentResolver().insert(
                JPUriManager.BASE_URI.buildUpon()
                        .appendPath(JPUriManager.URI_PATH_SCORES).build(),
                scoreValues);
        return uri != null;
    }

    public static void deleteAll(Context context) {

        context.getContentResolver().delete(JPUriManager.BASE_URI.buildUpon()
                .appendPath(JPUriManager.URI_PATH_SCORES).build(), null, null);


    }

    public static void delete(Context context, int id) {

        context.getContentResolver().
                delete(
                        JPUriManager.
                                BASE_URI.buildUpon().
                                appendPath(JPUriManager.URI_PATH_SCORES).build(),
                        ScoresTable._ID + "=?",
                        new String[]{String.valueOf(id)}
                );

    }

    //endregion

    //region Internals
    private final int scoredPoints;

    private final int totalPoints;

    private final Context context;

    //endregion

}
