package com.helin.todoupdated.UI.deleteallcomp

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.helin.todoupdated.classes.TaskDao
import com.helin.todoupdated.dependencyInjection.AppScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class DeleteAllCompVM @ViewModelInject constructor(
    private val taskDao:TaskDao,
    @AppScope private val appScope:CoroutineScope
) :ViewModel(){
    fun onConfirmClick()= appScope.launch {
        taskDao.deleteAllCompleted()
    }

}