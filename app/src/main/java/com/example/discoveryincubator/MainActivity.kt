package com.example.discoveryincubator

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider

class MainActivity : AppCompatActivity() {

    private val TAG: String = MainActivity::class.java.name

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        // Listens for when the app retrieves a list of Comic Issues to be able to update the View
        viewModel.issues.observe(this, {
            // For test purposes, just log the title of the comics
            for (i in it) {
                Log.i(TAG, i.title)
            }
        })
    }
}
