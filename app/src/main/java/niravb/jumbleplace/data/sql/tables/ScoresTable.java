package niravb.jumbleplace.data.sql.tables;

import android.provider.BaseColumns;

public class ScoresTable implements BaseColumns {

    public static final String TABLE_NAME = "scores";

    public static class ColumnCreatedAt {

        public static final String NAME = "created_at";
        public static final int COLUMN_INDEX = 1;
        public static final String DATA_TYPE = "TEXT";

    }

    public static class ColumnScoredPoints {

        public static final String NAME = "scored_points";
        public static final int COLUMN_INDEX = 2;
        public static final String DATA_TYPE = "INTEGER";

    }

    public static class ColumnTotalPoints {

        public static final String NAME = "total_points";
        public static final int COLUMN_INDEX = 3;
        public static final String DATA_TYPE = "INTEGER";

    }

}
