package com.helin.todoupdated.classes

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.helin.todoupdated.dependencyInjection.AppScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Provider

@Database(entities = [Task::class],version = 3)
abstract class TaskDB: RoomDatabase() {

    abstract fun taskDao():TaskDao

    class  Callback @Inject constructor(
        val database:Provider<TaskDB>,
        @AppScope val appScope:CoroutineScope
    ) :RoomDatabase.Callback(){ //providers has same purpose, pass necessary dependencies
        override fun onCreate(db: SupportSQLiteDatabase) { //first time operated when db created
            super.onCreate(db)
            //DB operations
            val dao=database.get().taskDao()

            //coroutine needed to call suspend functions
            appScope.launch {
                dao.insert(Task("Wash the dishes"))
                dao.insert(Task("Do the laundry"))
                dao.insert(Task("Buy groceries", important = true))
                dao.insert(Task("Prepare food", completed = true))
                dao.insert(Task("Call mom"))
                dao.insert(Task("Visit grandma", completed = true))
                dao.insert(Task("Repair my bike"))
                dao.insert(Task("Call Elon Musk"))
            }


        }

    }
}