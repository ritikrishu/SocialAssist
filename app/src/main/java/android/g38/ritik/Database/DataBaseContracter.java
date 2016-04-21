package android.g38.ritik.Database;

import android.provider.BaseColumns;

/**
 * Created by ritik on 3/17/2016.
 */
public class DataBaseContracter{
    public static abstract class EvenEntry implements BaseColumns{
        public static final String TABLE_NAME = "evenentry";
        public static final String COLUMN_TIME = "time";
        public static final String COLUMN_ACTION = "action";
        public static final String COLUMN_TRIGGER = "trigger";
        public static final String COLUMN_EVENT = "event";
        public static final String COLUMN_SHORTDES = "shortdes";
    }
    public static abstract class OddEntry implements BaseColumns{
        public static final String TABLE_NAME = "oddentry";
        public static final String COLUMN_DETAIL = "detaildes";
    }
}
