package niravb.jumbleplace.data.sql;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import niravb.jumbleplace.data.sql.queries.CreateCachedCountriesTable;
import niravb.jumbleplace.data.sql.queries.CreateScoresTable;
import niravb.jumbleplace.data.sql.queries.DropCachedCountriesTable;
import niravb.jumbleplace.data.sql.queries.DropScoresTable;

public class JPSqlDatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "jumbleplace.db";

    public JPSqlDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CreateScoresTable.query());
        db.execSQL(CreateCachedCountriesTable.query());

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL(DropScoresTable.query());
        db.execSQL(DropCachedCountriesTable.query());

    }

}