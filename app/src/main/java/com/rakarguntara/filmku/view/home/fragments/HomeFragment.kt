package com.rakarguntara.filmku.view.home.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.rakarguntara.filmku.databinding.FragmentHomeBinding
import com.rakarguntara.filmku.network.NetworkState
import com.rakarguntara.filmku.utils.loading.showLoading
import com.rakarguntara.filmku.view.adapters.MoviePopularAdapter
import com.rakarguntara.filmku.view.adapters.MovieTopRatedAdapter
import com.rakarguntara.filmku.viewmodels.HomeViewModel

class HomeFragment : Fragment() {

    private var _binding : FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private var homeViewModel : HomeViewModel? = null
    private var moviePopularAdapter: MoviePopularAdapter? = null
    private var movieTopRatedAdapter: MovieTopRatedAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        homeViewModel = ViewModelProvider(requireActivity())[HomeViewModel::class.java]
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupMoviePopularAdapter()
        setupTopRatedMovieAdapter()
    }

    private fun setupTopRatedMovieAdapter() {
        movieTopRatedAdapter = MovieTopRatedAdapter()
        binding.rvMovieTopRated.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        homeViewModel?.getAllTopRatedMovie(1)?.observe(viewLifecycleOwner){response ->
            if(response != null){
                when(response){
                    is NetworkState.Error -> {
                        showLoading(binding.pbHome, false)
                        Toast.makeText(context, response.error ,Toast.LENGTH_SHORT).show()
                    }
                    NetworkState.Loading -> {
                        showLoading(binding.pbHome, true)
                    }
                    is NetworkState.Success -> {
                        showLoading(binding.pbHome, false)
                        movieTopRatedAdapter?.setData(response.data.results)
                        binding.rvMovieTopRated.adapter = movieTopRatedAdapter
                        Toast.makeText(context, response.data.results.size ,Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun setupMoviePopularAdapter(){
        moviePopularAdapter = MoviePopularAdapter()
        binding.rvMoviePopular.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        homeViewModel?.getAllPopularMovie(1)?.observe(viewLifecycleOwner){ response ->
            if(response != null){
                when(response){
                    is NetworkState.Error -> {
                        showLoading(binding.pbHome, false)
                        Toast.makeText(context, response.error, Toast.LENGTH_SHORT).show()
                    }
                    NetworkState.Loading -> {
                        showLoading(binding.pbHome, true)
                    }
                    is NetworkState.Success -> {
                        showLoading(binding.pbHome, false)
                        moviePopularAdapter?.setData(response.data.results)
                        binding.rvMoviePopular.adapter = moviePopularAdapter
                    }
                }
            }
        }
    }

}