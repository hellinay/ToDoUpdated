package com.helin.todoupdated.UI.tasks

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.helin.todoupdated.UI.ADD_TASK_RESULT_OK
import com.helin.todoupdated.UI.EDIT_TASK_RESULT_OK
import com.helin.todoupdated.UI.tasks.TasksVM.TasksEvent.NavigateToEditTaskScreen
import com.helin.todoupdated.classes.PreferencesManager
import com.helin.todoupdated.classes.SortOrder
import com.helin.todoupdated.classes.Task
import com.helin.todoupdated.classes.TaskDao
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class TasksVM @ViewModelInject constructor(
    private val taskDao: TaskDao,
    val preferencesManager: PreferencesManager,
    @Assisted private val state:SavedStateHandle //will store searchquery when app is left
) : ViewModel() {

    val searchQuery =state.getLiveData("searchQuery","")
    //val searchQuery = MutableStateFlow("")

    val preferencesFlow = preferencesManager.preferencesFlow

    private val tasksEventChannel = Channel<TasksEvent>()
    val tasksEvent = tasksEventChannel.receiveAsFlow()

    private val tasksFlow = combine(
        searchQuery.asFlow(),
        preferencesFlow
    ) { query, filterPreferences ->
        Pair(query, filterPreferences)
    }.flatMapLatest { (query, filterPreferences) ->//flatmap changes flow values
        taskDao.getTasks(query, filterPreferences.sortOrder, filterPreferences.hideCompleted)//execute latest values in query
    }
    val tasks = tasksFlow.asLiveData()//hold tasks list as live data
    fun onSortOrderSelected(sortOrder: SortOrder) = viewModelScope.launch {
        preferencesManager.updateSortOrder(sortOrder)
    }

    fun onHideCompletedClick(hideCompleted: Boolean) = viewModelScope.launch {
        preferencesManager.updateHideCompleted(hideCompleted)
    }

    fun onTaskSelected(task: Task) = viewModelScope.launch {
        tasksEventChannel.send(TasksEvent.NavigateToEditTaskScreen(task))
    }

    fun onTaskCheckedChanged(task: Task, isChecked: Boolean) = viewModelScope.launch {
        taskDao.update(task.copy(completed = isChecked))
    }
    fun onTaskSwiped(task: Task) = viewModelScope.launch {
        taskDao.delete(task)
        tasksEventChannel.send(TasksEvent.ShowUndoDeleteTaskMessage(task))
    }

    fun onUndoDeleteClick(task: Task) = viewModelScope.launch {
        taskDao.insert(task)
    }
    private fun showTaskSavedConfirmationMsg(text:String)=viewModelScope.launch {
        tasksEventChannel.send(TasksEvent.showTaskSavedConfirmationMsg(text))
    }
    sealed class TasksEvent {
        data class ShowUndoDeleteTaskMessage(val task: Task) : TasksEvent()
        object navigateToAddTaskScreen: TasksEvent()
        data class NavigateToEditTaskScreen(val task:Task) : TasksEvent()
        data class showTaskSavedConfirmationMsg(val msg:String):TasksEvent()
        object NavigateDoneToDeleteAllScreen:TasksEvent()
    }

    fun onAddNewTaskClick()= viewModelScope.launch {
        tasksEventChannel.send(TasksEvent.navigateToAddTaskScreen)

    }
    fun onAddEditResult(result:Int){
        when(result){
            ADD_TASK_RESULT_OK->showTaskSavedConfirmationMsg("Task added")
            EDIT_TASK_RESULT_OK->showTaskSavedConfirmationMsg("Task updated")

        }
    }

    fun onDeleteAllCompClick()= viewModelScope.launch {
        tasksEventChannel.send(TasksEvent.NavigateDoneToDeleteAllScreen)
    }


}
  /*  val searchQuery = MutableStateFlow("") //like mutablelivedata will pass initial value
    val sortOrder = MutableStateFlow(SortOrder.BY_DATE)
    val hideCompleted = MutableStateFlow(false)

*/



