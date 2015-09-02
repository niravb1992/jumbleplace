package niravb.jumbleplace.data.sql.queries;

import niravb.jumbleplace.data.sql.tables.CachedCountriesTable;

public class DropCachedCountriesTable {

    public static String query() {
        return "DROP TABLE IF EXISTS " + CachedCountriesTable.TABLE_NAME;
    }

}
