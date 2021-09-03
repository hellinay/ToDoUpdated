package com.helin.todoupdated.util

import androidx.appcompat.widget.SearchView

inline fun SearchView.onQueryTextChanged(crossinline listener:(String) -> Unit) {
    this.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(query: String?): Boolean { //no need to confirm search see result immediately
            return true
        }

        override fun onQueryTextChange(newText: String?): Boolean {
            newText?.let { listener(it) }
            return true
        }
    })
}