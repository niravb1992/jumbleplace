package niravb.jumbleplace.data.sql.tables;

import android.provider.BaseColumns;

public class CachedCountriesTable implements BaseColumns {

    public static final String TABLE_NAME = "cached_countries";

    public static class ColumnOriginalName {

        public static final String NAME = "original_name";
        public static final int COLUMN_INDEX = 1;
        public static final String DATA_TYPE = "TEXT";

    }

}
