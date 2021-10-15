package com.example.discoveryincubator

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.discoveryincubator.adapters.IssueAdapter
import com.example.discoveryincubator.databinding.ActivityMainBinding
import com.example.discoveryincubator.models.Issue
import com.example.discoveryincubator.services.IssuesSearch
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val TAG: String = MainActivity::class.java.name

    private lateinit var viewModel: MainViewModel
    private lateinit var binding: ActivityMainBinding
    private lateinit var userSearchTerm: String

    private var adapterObservable: Disposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        displayIssueList(null)

        val view = binding.root
        val etSearchInput = binding.etSearchInput
        val bSearch = binding.bSearch

        etSearchInput.textChangedListener()
        etSearchInput.setOnEditorActionListener { _, actionId, _ -> getIssuesByFilter(actionId) }

        bSearch.setOnClickListener { getIssuesByFilter(actionId = 6) }

        setContentView(view)
    }

    private fun displayIssueList(userSearchTerm: String?) {
        viewModel.getIssueList(IssuesSearch(userSearchTerm))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { onSuccessIssuesReceived(it) },
                { onErrorNoIssues(it) }
            )
    }

    private fun getIssuesByFilter(actionId: Int): Boolean {
        return if (actionId == EditorInfo.IME_ACTION_DONE) {
            displayIssueList(userSearchTerm)
            true
        } else {
            false
        }
    }

    private fun onSuccessIssuesReceived(issues: List<Issue>) {
        if (issues.isNotEmpty()) {
            runOnUiThread {
                if (adapterObservable != null && !adapterObservable!!.isDisposed) {
                    adapterObservable!!.dispose()
                }

                val issueAdapter = IssueAdapter(this, issues)
                rvIssues.adapter = issueAdapter
                rvIssues.layoutManager = LinearLayoutManager(this)

                adapterObservable = issueAdapter
                    .userInteraction()
                    .subscribe { Toast.makeText(this, it, Toast.LENGTH_SHORT).show() }
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

    private fun EditText.textChangedListener() {
        addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                userSearchTerm = s.toString()

                if (userSearchTerm.isEmpty()) {
                    displayIssueList(null)
                }
            }
        })
    }
}