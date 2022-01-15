package com.example.gameb.fragment


import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gameb.R
import com.example.gameb.activity.MainActivity
import com.example.gameb.adapter.GameAdapter
import com.example.gameb.data.network.NetworkService
import com.example.gameb.databinding.FragmentGamesBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.*
import kotlinx.serialization.ExperimentalSerializationApi

@ExperimentalSerializationApi
class GamesFragment : Fragment(R.layout.fragment_games) {
    private lateinit var binding: FragmentGamesBinding
    private val coroutineExceptionHandler = CoroutineExceptionHandler{ context,exception ->
        binding.progressBar.visibility = View.GONE
        binding.rvGames.adapter =
            GameAdapter(listOf()) {}
        binding.swRefreshGM.isRefreshing = false
        Snackbar.make(
            requireView(),
            getString(R.string.error),
            Snackbar.LENGTH_SHORT
        ).setBackgroundTint(Color.parseColor("#ED4337"))
            .setActionTextColor(Color.parseColor("#FFFFFF"))
            .show()
    }

    companion object{
        fun NewInstance() = GamesFragment()
    }

    private val scope =
        CoroutineScope(Dispatchers.Main + SupervisorJob() + coroutineExceptionHandler)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentGamesBinding.bind(view)

        binding.imageProfile.setOnClickListener {
            (activity as MainActivity).navigateToFragment(
                ProfileFragment.newInstance()
            )
        }
        loadGames()

        binding.swRefreshGM.setOnRefreshListener {
            binding.swRefreshGM.isRefreshing = true
            loadGames()
            binding.swRefreshGM.isRefreshing = false
        }
    }
    @ExperimentalSerializationApi
    private fun loadGames() {
        scope.launch {
            val games = NetworkService.loadGames()
            binding.rvGames.layoutManager = LinearLayoutManager(context)
            binding.rvGames.adapter = GameAdapter(games) {}
            binding.progressBar.visibility = View.GONE
            binding.swRefreshGM.isRefreshing = false
        }
    }
}


