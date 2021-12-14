package com.example.osrscritic

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.osrscritic.databinding.ActivityDashboardBinding
import com.example.osrscritic.view.DashboardPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator

class DashboardActivity : AppCompatActivity() {

    lateinit var binding: ActivityDashboardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = DashboardPagerAdapter(supportFragmentManager, lifecycle)

        binding.viewPager2.adapter = adapter

        TabLayoutMediator(binding.tabLayout, binding.viewPager2) { tab, position ->

            when(position){

                0 -> tab.text = "Account"
                else -> tab.text = "Locate"
            }

        }.attach()

    }
}