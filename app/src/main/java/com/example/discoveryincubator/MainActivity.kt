package com.example.discoveryincubator

import android.app.Activity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import io.reactivex.rxjava3.schedulers.Schedulers
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.discoveryincubator.adapters.IssueAdapter
import com.example.discoveryincubator.models.Issue
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private val TAG: String = MainActivity::class.java.name

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        viewModel.issuesPlsWork
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .subscribe(
                { onSuccessIssuesReceived(it) },
                { onErrorNoIssues(it) }
            )
    }

    private fun onSuccessIssuesReceived(issues: List<Issue>) {
        if (issues.isNotEmpty()) {
            runOnUiThread {
                rvIssues.adapter = IssueAdapter(this, issues)
                rvIssues.layoutManager = LinearLayoutManager(this)
            }
        } else {
            displayToast("An unexpected error occurred. Please try again.")
        }
    }

    private fun onErrorNoIssues(error: Throwable) {
        Log.i(TAG, "onError: $error")
        displayToast("Unable to fetch Issue data. Please try again")
    }

    // https://stackoverflow.com/a/12897386
    private fun displayToast(toastMessage: String) {
        runOnUiThread {
            Toast.makeText(this, toastMessage, Toast.LENGTH_SHORT).show()
        }
    }
}
