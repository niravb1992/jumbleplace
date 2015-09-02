package niravb.jumbleplace.data.sql.queries;

import niravb.jumbleplace.data.sql.tables.ScoresTable;

public class DropScoresTable {

    public static String query() {
        return "DROP TABLE IF EXISTS " + ScoresTable.TABLE_NAME;
    }

}
