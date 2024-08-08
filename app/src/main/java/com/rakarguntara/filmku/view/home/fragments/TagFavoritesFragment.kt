package com.rakarguntara.filmku.view.home.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.rakarguntara.filmku.databinding.FragmentTagFavoritesBinding
import com.rakarguntara.filmku.view.adapters.MovieFavoriteAdapter
import com.rakarguntara.filmku.viewmodels.local.LocalViewModels
import kotlinx.coroutines.launch

class TagFavoritesFragment : Fragment() {
    //layout
    private var _binding : FragmentTagFavoritesBinding? = null
    private val binding get() = _binding!!
    //adapter
    private var movieFavoriteAdapter: MovieFavoriteAdapter? = null
    //viewmodel
    private var localViewModels: LocalViewModels? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        localViewModels = ViewModelProvider(requireActivity())[LocalViewModels::class.java]
        _binding = FragmentTagFavoritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //setup adapter
        setupMovieFavoriteAdapter()
    }

    private fun setupMovieFavoriteAdapter() {
        movieFavoriteAdapter = MovieFavoriteAdapter()
        binding.rvMovieFavorites.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        setupMovieFavoriteList()
    }

    private fun setupMovieFavoriteList() {
        viewLifecycleOwner.lifecycleScope.launch {
            localViewModels?.movieList?.collect { list ->
                movieFavoriteAdapter?.setData(list)
                binding.rvMovieFavorites.adapter = movieFavoriteAdapter
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}