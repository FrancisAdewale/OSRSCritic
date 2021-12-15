package com.example.osrscritic

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.osrscritic.databinding.ActivityDisplayUserBinding
import com.example.osrscritic.view.StatsAdapter
import com.example.osrscritic.viewmodel.DisplayUserViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class DisplayUserActivity : AppCompatActivity() {

    private val displayUserViewModel : DisplayUserViewModel by viewModel()
    lateinit var binding: ActivityDisplayUserBinding
    val statsAdapter = StatsAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDisplayUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rsStatsRv.adapter = statsAdapter
        binding.rsStatsRv.layoutManager = LinearLayoutManager(this,
            LinearLayoutManager.VERTICAL, false)

        configureObservers()

        displayUserViewModel.getOSRSPlayer()
    }

    private fun configureObservers() {

        displayUserViewModel.displayerUserLiveData.observe(this, {
            statsAdapter.setStatsList(it.skillvalues)
            binding.tvRunescapeName.text = String.format("Account Name: %s", it.name)
            binding.tvCombat.text = String.format("Combat Level: %s", it.combatlevel.toString())

        })

        displayUserViewModel.loadingState.observe(this, {
            when(it){
                true -> binding.pgBar.visibility = View.VISIBLE
                false -> binding.pgBar.visibility = View.GONE
            }
        })

        displayUserViewModel.errorData.observe(this, {
            Toast.makeText(this, it, Toast.LENGTH_LONG).show()

        })

    }
}