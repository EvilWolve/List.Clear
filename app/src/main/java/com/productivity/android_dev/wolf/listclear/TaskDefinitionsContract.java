package com.productivity.android_dev.wolf.listclear;

import android.provider.BaseColumns;

/**
 * Created by Wolf on 21/02/2017.
 */

public final class TaskDefinitionsContract {
    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private.
    private TaskDefinitionsContract() {}

    /* Inner class that defines the table contents */
    public static class TasksDefinitionEntry implements BaseColumns {
        public static final String TABLE_NAME = "TASK_DEFINITIONS";
        public static final String NAME = "TASK_NAME";
        public static final String IMPORTANCE = "TASK_IMPORTANCE";
        public static final String TYPE = "TASK_TYPE";
        public static final String DATE = "TASK_DATE";
        public static final String PATTERN = "TASK_PATTERN";
        public static final String DURATION = "TASK_DURATION";
        public static final String AMOUNT = "TASK_AMOUNT";
    }

    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TasksDefinitionEntry.TABLE_NAME + " (" +
                    TasksDefinitionEntry._ID + " INTEGER PRIMARY KEY," +
                    TasksDefinitionEntry.NAME + " TEXT," +
                    TasksDefinitionEntry.IMPORTANCE + " INTEGER," +
                    TasksDefinitionEntry.TYPE + " INTEGER," +
                    TasksDefinitionEntry.DATE + " TEXT," +
                    TasksDefinitionEntry.PATTERN + " INTEGER," +
                    TasksDefinitionEntry.DURATION + " REAL," +
                    TasksDefinitionEntry.AMOUNT + " INTEGER" +
                    ")";

    public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + TasksDefinitionEntry.TABLE_NAME;
}

