package com.example.discoveryincubator

import androidx.lifecycle.ViewModel
import com.example.discoveryincubator.models.Issue
import com.example.discoveryincubator.network.ComicApi
import com.example.discoveryincubator.services.IssueSearch
import io.reactivex.rxjava3.core.Observable

class MainViewModel : ViewModel() {

    lateinit var issuesPlsWork: Observable<List<Issue>>

    init {
        getIssueList(IssueSearch(null))
    }

    fun getIssueList(searchIssues: IssueSearch) {
        issuesPlsWork = ComicApi.retrofitService.getIssues(searchIssues)
    }
}