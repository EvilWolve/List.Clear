package com.productivity.android_dev.wolf.listclear;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Wolf on 21/02/2017.
 */

public class TasksDatabaseHelper extends SQLiteOpenHelper {

    // BEGIN Singleton implementation
    private static TasksDatabaseHelper _instance;

    public static synchronized TasksDatabaseHelper getInstance(Context context) {
        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        // See this article for more information: http://bit.ly/6LRzfx
        if (_instance == null) {
            _instance = new TasksDatabaseHelper(context.getApplicationContext());
        }
        return _instance;
    }

    /**
     * Constructor should be private to prevent direct instantiation.
     * Make a call to the static method "getInstance()" instead.
     */
    private TasksDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // END Singleton implementation

    // Database Info
    private static final String DATABASE_NAME = "tasksDatabase.db";
    private static final int DATABASE_VERSION = 1;

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TaskDefinitionsContract.SQL_CREATE_ENTRIES);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(TaskDefinitionsContract.SQL_DELETE_ENTRIES);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    // TODO: Add task based on task definition
    // TODO: Update task definition

    // TODO: Update task progress

    // TODO: Look for tasks and task definitions based on ID

    // Insert a taskDefinition into the database
    public void addTaskDefinition(TaskDefinition taskDefinition) {
        // Create and/or open the database for writing
        SQLiteDatabase db = getWritableDatabase();

        // It's a good idea to wrap our insert in a transaction. This helps with performance and ensures
        // consistency of the database.
        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            values.put(TaskDefinitionsContract.TasksDefinitionEntry.NAME, taskDefinition.name);
            values.put(TaskDefinitionsContract.TasksDefinitionEntry.TYPE, taskDefinition.type);
            values.put(TaskDefinitionsContract.TasksDefinitionEntry.IMPORTANCE, taskDefinition.status);

            // Notice how we haven't specified the primary key. SQLite auto increments the primary key column.
            taskDefinition.ID = db.insertOrThrow(TaskDefinitionsContract.TasksDefinitionEntry.TABLE_NAME, null, values);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            ; // TODO: Log, or something?
        } finally {
            db.endTransaction();
        }
    }

    // Replace a taskDefinition into the database
    public void replaceTask(TaskDefinition taskDefinition) {
        // Create and/or open the database for writing
        SQLiteDatabase db = getWritableDatabase();

        // It's a good idea to wrap our insert in a transaction. This helps with performance and ensures
        // consistency of the database.
        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            values.put(TaskDefinitionsContract.TasksDefinitionEntry.NAME, taskDefinition.name);
            values.put(TaskDefinitionsContract.TasksDefinitionEntry.TYPE, taskDefinition.type);
            values.put(TaskDefinitionsContract.TasksDefinitionEntry.IMPORTANCE, taskDefinition.status);

            db.replace(TaskDefinitionsContract.TasksDefinitionEntry.TABLE_NAME, null, values);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            ; // TODO: Log, or something?
        } finally {
            db.endTransaction();
        }
    }

    // Insert or update a taskDefinition in the database
    // Since SQLite doesn't support "upsert" we need to fall back on an attempt to UPDATE (in case the
    // taskDefinition already exists) optionally followed by an INSERT (in case the taskDefinition does not already exist).
    // Unfortunately, there is a bug with the insertOnConflict method
    // (https://code.google.com/p/android/issues/detail?id=13045) so we need to fall back to the more
    // verbose option of querying for the taskDefinition's primary key if we did an update.
    public void addOrUpdateTask(TaskDefinition taskDefinition) {
        // The database connection is cached so it's not expensive to call getWriteableDatabase() multiple times.
        SQLiteDatabase db = getWritableDatabase();

        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            values.put(TaskDefinitionsContract.TasksDefinitionEntry._ID, taskDefinition.ID);
            values.put(TaskDefinitionsContract.TasksDefinitionEntry.NAME, taskDefinition.name);
            values.put(TaskDefinitionsContract.TasksDefinitionEntry.TYPE, taskDefinition.type);
            values.put(TaskDefinitionsContract.TasksDefinitionEntry.IMPORTANCE, taskDefinition.status);

            // First try to update the taskDefinition in case it already exists in the database
            // IDs are unique
            int rowCount = db.update(TaskDefinitionsContract.TasksDefinitionEntry.TABLE_NAME, values, TaskDefinitionsContract.TasksDefinitionEntry._ID + "= ?", new String[]{String.valueOf(taskDefinition.ID)});

            // Check if update succeeded
            if (rowCount == 1) {
                db.setTransactionSuccessful();
            } else {
                // TaskDefinition with this taskDefinition ID did not already exist, so insert new taskDefinition
                taskDefinition.ID = db.insertOrThrow(TaskDefinitionsContract.TasksDefinitionEntry.TABLE_NAME, null, values);
                db.setTransactionSuccessful();
            }
        } catch (Exception e) {
            ; // TODO: Log, or something?
        } finally {
            db.endTransaction();
        }
    }
}