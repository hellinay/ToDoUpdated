package com.helin.todoupdated.UI.tasks


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.helin.todoupdated.classes.Task
import com.helin.todoupdated.databinding.CardviewTaskBinding

class TasksAdapter(private val listener:OnItemClickListener) : ListAdapter<Task,TasksAdapter.TasksViewHolder>(DiffCallBack()){ //we always get new list

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TasksViewHolder {
        //create binding object
        val binding = CardviewTaskBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return TasksViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TasksViewHolder, position: Int) {
        val currentItem=getItem(position)
        holder.bind(currentItem) //bind item at the current position
    }

    inner class TasksViewHolder(private val binding:CardviewTaskBinding):RecyclerView.ViewHolder(binding.root){
//inner is static in java
        init {
            binding.apply {
                root.setOnClickListener {
                    val position = adapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        val task = getItem(position)
                        listener.onItemClick(task)
                    }
                }
                checkBoxCompleted.setOnClickListener {
                    val position = adapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        val task = getItem(position)
                        listener.onCheckBoxClick(task, checkBoxCompleted.isChecked)
                    }
                }
            }
        }

        fun bind(task:Task){
            binding.apply { //apply enables multiple without writing binding everytime
                checkBoxCompleted.isChecked= task.completed
                textTask.text=task.name
                textTask.paint.isStrikeThruText=task.completed
                imagePriority.isVisible=task.important
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(task: Task)
        fun onCheckBoxClick(task: Task, isChecked: Boolean)
    }

    class DiffCallBack : DiffUtil.ItemCallback<Task>()//to enable Adapter detect changes bw items
    {
        override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean {
            return oldItem==newItem // we compare all these properties name,important,...
        }
    }

}