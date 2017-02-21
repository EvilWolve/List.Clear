package com.productivity.android_dev.wolf.listclear;

import android.provider.BaseColumns;

/**
 * Created by Wolf on 21/02/2017.
 */

public class TasksContract {
    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private.
    private TasksContract() {}

    /* Inner class that defines the table contents */
    public static class TasksEntry implements BaseColumns {
        public static final String TABLE_NAME = "TASKS";
        public static final String DEFINITION = "TASK_DEFINITION";
        public static final String PROGRESS = "TASK_PROGRESS";
    }

    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TasksContract.TasksEntry.TABLE_NAME + " (" +
                    TasksContract.TasksEntry._ID + " INTEGER PRIMARY KEY," +
                    TasksEntry.DEFINITION + " INTEGER," +
                    TasksEntry.PROGRESS + " REAL" +
                    ")";

    public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + TaskDefinitionsContract.TasksDefinitionEntry.TABLE_NAME;
}
