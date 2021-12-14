package com.example.osrscritic.view

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class DashboardPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount(): Int {

        return 2

    }

    override fun createFragment(position: Int): Fragment {

        Log.d("Fragment Called", "createFragment")

        return when(position) {
            0 -> AccountFragment()
            else -> MapsFragment()
        }
    }


}