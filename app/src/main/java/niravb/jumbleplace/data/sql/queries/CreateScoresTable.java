package niravb.jumbleplace.data.sql.queries;

import niravb.jumbleplace.data.sql.tables.ScoresTable;

public class CreateScoresTable {

    public static String query() {

        return "CREATE TABLE " + ScoresTable.TABLE_NAME + " (" +
                ScoresTable._ID + " INTEGER PRIMARY KEY," +
                ScoresTable.ColumnCreatedAt.NAME + " " +
                ScoresTable.ColumnCreatedAt.DATA_TYPE + " NOT NULL, " +
                ScoresTable.ColumnScoredPoints.NAME + " " +
                ScoresTable.ColumnScoredPoints.DATA_TYPE + " NOT NULL, " +
                ScoresTable.ColumnTotalPoints.NAME + " " +
                ScoresTable.ColumnTotalPoints.DATA_TYPE + " NOT NULL " +
                " );";

    }

}
