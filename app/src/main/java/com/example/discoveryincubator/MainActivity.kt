package com.example.discoveryincubator

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.inputmethod.EditorInfo
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.discoveryincubator.adapters.IssueAdapter
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

        // Listens for when the app retrieves a list of Comic Issues to be able to update the View
        viewModel.issues.observe(this, {
            // Set API data to display in the RecyclerView
            rvIssues.adapter = IssueAdapter(this, it)
            rvIssues.layoutManager = LinearLayoutManager(this)
        })

        val view = binding.root
        val etSearchInput = binding.etSearchInput
        val bSearch = binding.bSearch

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

    private fun handleDoneButton(actionId: Int): Boolean {
        return if (actionId == EditorInfo.IME_ACTION_DONE) {
            viewModel.getIssueByName(userSearchTerm)
            true
        } else {
            false
        }
    }
}