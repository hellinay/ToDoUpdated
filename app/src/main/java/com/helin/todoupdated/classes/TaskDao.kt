package com.helin.todoupdated.classes


import androidx.room.*
import com.helin.todoupdated.UI.tasks.TasksVM
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {

    fun getTasks(query:String, sortOrder: SortOrder, hideCompleted: Boolean):Flow<List<Task>> =
        when(sortOrder) {
            SortOrder.BY_DATE -> getTasksSortedByDateCreated(query, hideCompleted)
            SortOrder.BY_NAME -> getTasksSortedByName(query, hideCompleted)
        }

    @Query("SELECT*FROM task_table WHERE (completed != :hideCompleted OR completed=0) AND name LIKE '%' || :searchQuery || '%' ORDER BY important DESC,name" ) // menu options optimization
    //fun getTasks(searchQuery:String):Flow<List<Task>> //Flow list of tasks, suspension inside flow no need
    fun getTasksSortedByName(searchQuery:String, hideCompleted: Boolean): Flow<List<Task>>

    @Query("SELECT*FROM task_table WHERE (completed != :hideCompleted OR completed=0) AND name LIKE '%' || :searchQuery || '%' ORDER BY important DESC,created" ) // menu options optimization
    //fun getTasksSorted(searchQuery:String, hideCompleted: Boolean): Flow<List<Task>>
    fun getTasksSortedByDateCreated(searchQuery:String, hideCompleted: Boolean): Flow<List<Task>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(task:Task)

    @Update
    suspend fun update(task: Task)

    @Delete
    suspend fun delete(task: Task)

    @Query("DELETE FROM task_table WHERE completed=1")
    suspend fun deleteAllCompleted()

}