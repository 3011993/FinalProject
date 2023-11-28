package com.example.finalproject.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
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

        moviesViewModel.movies.observe(this){

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