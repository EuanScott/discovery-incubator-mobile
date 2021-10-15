package com.example.discoveryincubator

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.text.Editable
import android.text.TextWatcher
import android.view.inputmethod.EditorInfo
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import io.reactivex.rxjava3.schedulers.Schedulers
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.discoveryincubator.adapters.IssueAdapter
import com.example.discoveryincubator.models.Issue
import com.example.discoveryincubator.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private val TAG: String = MainActivity::class.java.name

    private lateinit var viewModel: MainViewModel
    private lateinit var binding: ActivityMainBinding
    private lateinit var userSearchTerm: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        val view = binding.root
        val etSearchInput = binding.etSearchInput
        val bSearch = binding.bSearch

        viewModel.issuesPlsWork
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .subscribe(
                { onSuccessIssuesReceived(it) },
                { onErrorNoIssues(it) }
            )

        etSearchInput.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(
                s: CharSequence,
                start: Int,
                before: Int,
                count: Int
            ) {
                userSearchTerm = s.toString()

                if (userSearchTerm.isEmpty()) {
                    viewModel.getIssues()
                }
            }
        })

        etSearchInput.setOnEditorActionListener { _, actionId, _ ->
            handleDoneButton(actionId)
        }

        bSearch.setOnClickListener { handleDoneButton(6) }

        setContentView(view)
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

    private fun handleDoneButton(actionId: Int): Boolean {
        return if (actionId == EditorInfo.IME_ACTION_DONE) {
            viewModel.getIssueByName(userSearchTerm)
            true
        } else {
            false
        }
    }
}