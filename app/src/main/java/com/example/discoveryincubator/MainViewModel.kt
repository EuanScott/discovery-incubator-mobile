package com.example.discoveryincubator

import androidx.lifecycle.ViewModel
import com.example.discoveryincubator.models.Issue
import com.example.discoveryincubator.models.SearchIssues
import com.example.discoveryincubator.network.ComicApi
import io.reactivex.rxjava3.core.Observable

class MainViewModel : ViewModel() {

    lateinit var issuesPlsWork: Observable<List<Issue>>

    init {
        val searchIssues = SearchIssues(null)
        getIssues(searchIssues)
    }

    /**
     * Retrieves a list of Comic Issues to be displayed to the user
     */
    private fun getIssues(searchIssues: SearchIssues) {
        issuesPlsWork = ComicApi.retrofitService.getIssues(searchIssues)
    }
}