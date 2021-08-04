package com.helin.ToDoUpdatedVer.classes

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {

    @Query("SELECT*FROM task_table")
    fun getTasks():Flow<List<Task>> //Flow list of tasks, suspension inside flow no need


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(task:Task)

    @Update
    suspend fun update(task: Task)

    @Delete
    suspend fun delete(task: Task)

}