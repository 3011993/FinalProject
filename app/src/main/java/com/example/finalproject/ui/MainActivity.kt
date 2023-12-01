package com.example.finalproject.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.finalproject.R
import com.example.finalproject.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var moviesViewModel: MoviesViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        moviesViewModel = ViewModelProvider(this)[MoviesViewModel::class.java]

        lifecycleScope.launch {
            moviesViewModel.movies.collect { movies ->
                //Todo submit movies to adapter list
                Log.i("movies", "${movies.size}")
            }
        }
        lifecycleScope.launch {
            moviesViewModel.state.collectLatest { state ->
                when(state) {
                    is UiState.Loading -> {
                        //Todo show circular loading view
                    }
                    is UiState.NetworkError -> {
                        Snackbar.make(
                            binding.root,
                            getString(R.string.network_error),
                            Snackbar.LENGTH_LONG
                        ).show()
                    }
                    is UiState.InvalidLogin -> {
                        //Todo log out and navigate to home screen
                        Snackbar.make(
                            binding.root,
                            getString(R.string.invalid_login),
                            Snackbar.LENGTH_LONG
                        ).show()
                    }
                    is UiState.IoException -> {
                        Snackbar.make(
                            binding.root,
                            state.error,
                            Snackbar.LENGTH_LONG
                        ).show()
                    }
                }
            }
        }
    }
}