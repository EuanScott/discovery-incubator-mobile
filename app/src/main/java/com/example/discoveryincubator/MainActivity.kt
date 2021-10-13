package com.example.discoveryincubator

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.discoveryincubator.adapters.IssueAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val TAG: String = MainActivity::class.java.name

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        // Listens for when the app retrieves a list of Comic Issues to be able to update the View
        viewModel.issues.observe(this, {
            // Set API data to display in the RecyclerView
            rvIssues.adapter = IssueAdapter(this, it)
            rvIssues.layoutManager = LinearLayoutManager(this)
        })
    }
}
