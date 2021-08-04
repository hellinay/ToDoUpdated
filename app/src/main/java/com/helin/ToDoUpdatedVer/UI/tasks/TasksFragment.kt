package com.helin.ToDoUpdatedVer.UI.tasks

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.helin.ToDoUpdatedVer.R
import com.helin.ToDoUpdatedVer.databinding.FragmentTasksBinding

class TasksFragment : Fragment(R.layout.fragment_tasks) {

    private val viewModel:TasksVM by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding=FragmentTasksBinding.bind(view) // no need inflate, layout is already inflated R.layout...
        val tasksAdapter=TasksAdapter()

        binding.apply {
            recyclerViewTasks.apply {
                adapter=tasksAdapter


            }
        }
        viewModel.tasks.observe(viewLifecycleOwner){
        //whenever db changes we recieve it in observe so we update here
            tasksAdapter.submitList(it) //submit list method of list adapter
        }
    }

}