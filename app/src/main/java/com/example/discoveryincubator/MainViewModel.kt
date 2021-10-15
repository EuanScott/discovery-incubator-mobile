package com.example.discoveryincubator

import androidx.lifecycle.ViewModel
import com.example.discoveryincubator.models.Issue
import com.example.discoveryincubator.network.ComicApi
import com.example.discoveryincubator.services.IssuesSearch
import io.reactivex.Observable

class MainViewModel : ViewModel() {
    fun getIssueList(searchIssues: IssuesSearch): Observable<List<Issue>> {
        return ComicApi.retrofitService.getIssues(searchIssues)
    }
}