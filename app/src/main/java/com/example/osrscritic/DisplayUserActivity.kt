package com.example.osrscritic

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.osrscritic.databinding.ActivityDisplayUserBinding
import com.example.osrscritic.viewmodel.DisplayUserViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class DisplayUserActivity : AppCompatActivity() {

    private val displayUserViewModel : DisplayUserViewModel by viewModel()
    lateinit var binding: ActivityDisplayUserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDisplayUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        displayUserViewModel.getOSRSPlayer()
    }
}