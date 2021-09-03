package com.helin.todoupdated.UI.deleteallcomp

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment

import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DeleteAllCompDialogFragment : DialogFragment() {
private val viewModel: DeleteAllCompVM by viewModels()
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
        AlertDialog.Builder(requireContext())
            .setTitle("Confirm Deletion")
            .setMessage("Dou you want to delete all completed tasks?")
            .setNegativeButton("Cancel", null)
            .setPositiveButton("Yes"){_,_->
                //call VM
                viewModel.onConfirmClick()
            }
            .create()




}

/*
androidx.appcompat.app.AlertDialog.Builder(requireContext())
.setTitle("Confirm Deletion")
.setMessage("Dou you want to delete all completed tasks?")
.setPositiveButton("Yes")
.setNegativeButton("Cancel",null)*/
