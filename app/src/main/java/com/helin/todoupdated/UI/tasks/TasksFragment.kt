package com.helin.todoupdated.UI.tasks
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.helin.todoupdated.R
import com.helin.todoupdated.databinding.FragmentTasksBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TasksFragment : Fragment(R.layout.fragment_tasks) {

    private val viewModel:TasksVM by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding=FragmentTasksBinding.bind(view) // no need inflate, layout is already inflated R.layout...
        val tasksAdapter=TasksAdapter()

        binding.apply {
            recyclerViewTasks.apply {
                adapter=tasksAdapter
                layoutManager = LinearLayoutManager(requireContext())
                setHasFixedSize(true)

            }
        }
        viewModel.tasks.observe(viewLifecycleOwner){
        //whenever db changes we receive it in observe so we update here
            tasksAdapter.submitList(it) //submit list method of list adapter
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_fragment_tasks,menu)

        val searchItem = menu.findItem(R.id.item_search)
        val searchView=searchItem.actionView as SearchView //use like edittext


    }

}