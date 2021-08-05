package com.helin.todoupdated.dependencyInjection


import android.app.Application
import androidx.room.Room
import com.helin.todoupdated.classes.TaskDB
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class ) //singleton online used in activity, it contains dependencies
object AppModule {

    @Provides
    @Singleton
    fun provideDB(app:Application, callback:TaskDB.Callback)
    =Room.databaseBuilder(app,TaskDB::class.java,"task_database")
            .fallbackToDestructiveMigration() //drop and create
            .addCallback(callback) // want recycler view with objects in it not empty,  callback to database, added in database
            .build()

    @Provides
    fun provideTaskDao(db:TaskDB)
    =db.taskDao()

    @AppScope //its appscope not any coroutine scope
    @Provides
    @Singleton
    fun provideAppScope()= CoroutineScope(SupervisorJob()) //supervisor keep other childs running if any fail occurs
}
@Retention(AnnotationRetention.RUNTIME)//interoperability
@Qualifier
annotation class AppScope