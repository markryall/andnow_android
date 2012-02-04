package me.piv.data;

import android.provider.BaseColumns;

public interface SessionColumns extends BaseColumns {
    public static final String TABLE_NAME = "sessions";
    public static final String DESCRIPTION = "description";
    public static final String START_TIME = "start_time";
    public static final String END_TIME = "end_time";
}