package niravb.jumbleplace.data.sql.queries;

import niravb.jumbleplace.data.sql.tables.CachedCountriesTable;

public class CreateCachedCountriesTable {

    public static String query() {

        return "CREATE TABLE " + CachedCountriesTable.TABLE_NAME + " (" +
                CachedCountriesTable._ID + " INTEGER PRIMARY KEY," +
                CachedCountriesTable.ColumnOriginalName.NAME + " " +
                CachedCountriesTable.ColumnOriginalName.DATA_TYPE + " NOT NULL " +
                " );";

    }

}
