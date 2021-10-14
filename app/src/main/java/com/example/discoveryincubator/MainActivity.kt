package com.example.discoveryincubator

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import io.reactivex.rxjava3.schedulers.Schedulers
import android.widget.Toast


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
                {
                    if (it.isNotEmpty()) {
                        for (i in it) {
                            Log.i(TAG, i.title)
                        }
                    } else {
                        displayToast("An unexpected error occurred. Please try again.")
                    }
                },
                { error ->
                    Log.i("pls", "onError: $error")
                    displayToast("Unable to fetch Issue data. Please try again")
                }
            )
    }

    // https://stackoverflow.com/a/12897386
    private fun displayToast(toastMessage: String) {
        runOnUiThread {
            Toast.makeText(this, toastMessage, Toast.LENGTH_SHORT).show()
        }
    }
}
