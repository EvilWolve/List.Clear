package com.productivity.android_dev.wolf.listclear;

import java.util.Date;

/**
 * Created by Wolf on 21/02/2017.
 */

public class TaskDefinition {
    // Duration can be used for any task, day/week/repeat
    // Pattern can be used for repetition day specifications or just mark a task as open/done.

    public long ID = -1;        // Unique row ID, stored for easy updates
    public String name;         // Descriptive task name
    public int importance;      // Importance of the task, in points
    public int type;            // Type of task: TaskDefinition on this day, during this week, or repeated on specific days
    public Date date;           // Start date of task
    public int pattern;         // If the task is repeated, use a bitmask of seven entries to set the weekdays
    public float duration;      // How much time needs to be spent on the task or how often. -1 means just an open/done task.
    public int amount;          // How often this task needs to be performed per time frame. 1 for simple completion.
}
