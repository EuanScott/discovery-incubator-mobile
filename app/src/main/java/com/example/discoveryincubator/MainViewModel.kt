package com.example.discoveryincubator

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.discoveryincubator.models.Issue
import com.example.discoveryincubator.network.ComicApi
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val _issues = MutableLiveData<List<Issue>>()
    val issues: LiveData<List<Issue>>
        get() = _issues

    init {
        getIssues()
    }

    /**
     * Retrieves a list of Comic Issues to be displayed to the user
     */
    fun getIssues() {
        viewModelScope.launch {
            try {
                val comicIssues = ComicApi.retrofitService.getIssues()
                if (comicIssues.isNotEmpty()) _issues.value = comicIssues
            } catch (e: Throwable) {
                _issues.value = ArrayList()
            }
        }
    }

    /**
     * Retrieve a list of Comic Issues based on the search term provided by the user
     */
    fun getIssueByName(name: String) {
        viewModelScope.launch {
            try {
                val comicIssues = ComicApi.retrofitService.getIssueByName(name)
                if (comicIssues.isNotEmpty()) _issues.value = comicIssues
            } catch (e: Throwable) {
                _issues.value = ArrayList()
            }
        }
    }
}