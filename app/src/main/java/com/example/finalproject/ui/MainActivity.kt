package com.example.finalproject.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
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
        moviesViewModel.movies.observe(this) { state ->
            when (state) {
                is UiState.UiModel -> {
                    Toast.makeText(this, "we got data right", Toast.LENGTH_SHORT).show()
                }
                is UiState.InvalidModel -> {
                    Toast.makeText(this, "we got errors", Toast.LENGTH_SHORT).show()
                }
            }
        }
        lifecycleScope.launch {
            moviesViewModel.showError.collectLatest { value ->
                Snackbar.make(
                    binding.root,
                    value,
                    Snackbar.LENGTH_LONG
                ).show()
            }
        }
    }
}