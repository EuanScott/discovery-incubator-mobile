package com.example.discoveryincubator

import androidx.lifecycle.ViewModel
import com.example.discoveryincubator.models.Issue
import com.example.discoveryincubator.network.ComicApi
import com.example.discoveryincubator.services.IssueSearch
import io.reactivex.rxjava3.core.Observable

class MainViewModel : ViewModel() {
    fun getIssueList(searchIssues: IssueSearch): Observable<List<Issue>> {
        return ComicApi.retrofitService.getIssues(searchIssues)
    }
}