package com.example.discoveryincubator

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.discoveryincubator.models.Issue
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MainActivity : AppCompatActivity() {

    private val TAG: String = MainActivity::class.java.name

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        viewModel.getIssueList()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { onSuccess -> onSuccessIssuesReceived(onSuccess) },
                { onError -> onErrorNoIssues(onError) }
            )
    }

    private fun onSuccessIssuesReceived(issues: List<Issue>) {
        if (issues.isNotEmpty()) {
            for (issue in issues) {
                Log.i(TAG, issue.title)
            }
        } else {
            displayToast("An unexpected error occurred. Please try again.")
        }
    }

    private fun onErrorNoIssues(error: Throwable) {
        Log.i("pls", "onError: $error")
        displayToast("Unable to fetch Issue data. Please try again")
    }


    // https://stackoverflow.com/a/12897386
    private fun displayToast(toastMessage: String) {
        runOnUiThread {
            Toast.makeText(this, toastMessage, Toast.LENGTH_SHORT).show()
        }
    }
}
