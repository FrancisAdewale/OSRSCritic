package com.example.osrscritic

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.osrscritic.databinding.ActivityDisplayUserBinding
import com.example.osrscritic.view.PostDialogFragment
import com.example.osrscritic.view.PostsAdapter
import com.example.osrscritic.view.StatsAdapter
import com.example.osrscritic.viewmodel.DisplayUserViewModel
import com.google.firebase.auth.FirebaseAuth
import org.koin.androidx.viewmodel.ext.android.viewModel

class DisplayUserActivity : AppCompatActivity() {

    private val displayUserViewModel : DisplayUserViewModel by viewModel()
    lateinit var binding: ActivityDisplayUserBinding
    val statsAdapter = StatsAdapter()
    val postsAdapter = PostsAdapter()
    lateinit var critiquing: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val extras = intent.extras

        critiquing = extras?.getString("c")!!


        binding = ActivityDisplayUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rsStatsRv.adapter = statsAdapter
        binding.rsStatsRv.layoutManager = LinearLayoutManager(this,
            LinearLayoutManager.VERTICAL, false)

        binding.rvPosts.adapter = postsAdapter
        binding.rvPosts.layoutManager = LinearLayoutManager(this,
            LinearLayoutManager.VERTICAL, false)

        configureObservers()

        displayUserViewModel.getFirebaseRef()

        displayUserViewModel.getOSRSPlayer()

        binding.ivWritePost.setOnClickListener {

           PostDialogFragment.newInstance(critiquing).show(supportFragmentManager
               , PostDialogFragment.KEY)



//            val intent = Intent(this, PostDialogFragment::class.java)
//            intent.putExtras(b)
//            startActivity(intent)

        }
    }

    private fun configureObservers() {

        displayUserViewModel.displayUserLiveData.observe(this, {
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