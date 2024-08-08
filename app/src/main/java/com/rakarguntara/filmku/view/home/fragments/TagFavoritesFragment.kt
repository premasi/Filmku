package com.rakarguntara.filmku.view.home.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.rakarguntara.filmku.databinding.FragmentTagFavoritesBinding
import com.rakarguntara.filmku.models.DetailMovieResponse
import com.rakarguntara.filmku.utils.listener.OnItemViewClickListener
import com.rakarguntara.filmku.utils.loading.showLoading
import com.rakarguntara.filmku.view.adapters.MovieFavoriteAdapter
import com.rakarguntara.filmku.view.detail.DetailMovieActivity
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
    ): View {
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
        movieFavoriteAdapter?.onMovieItemClickListener(object : OnItemViewClickListener{
            override fun onMovieItemClick(detailMovieResponse: DetailMovieResponse) {
                val intent = Intent(requireActivity(), DetailMovieActivity::class.java)
                intent.putExtra(DetailMovieActivity.MOVIE_DETAIL, detailMovieResponse)
                startActivity(intent, ActivityOptionsCompat.makeSceneTransitionAnimation(requireActivity()).toBundle())
            }

        })
    }

    private fun setupMovieFavoriteList() {
        viewLifecycleOwner.lifecycleScope.launch {
            localViewModels?.movieList?.collect { list ->
                if(list.isNotEmpty()){
                    binding.rvMovieFavorites.visibility = View.VISIBLE
                    binding.clEmpty.visibility = View.GONE
                    showLoading(binding.pbFav, false)
                    movieFavoriteAdapter?.setData(list)
                    binding.rvMovieFavorites.adapter = movieFavoriteAdapter
                } else {
                    binding.rvMovieFavorites.visibility = View.GONE
                    showLoading(binding.pbFav,false)
                    binding.clEmpty.visibility = View.VISIBLE
                }

            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}