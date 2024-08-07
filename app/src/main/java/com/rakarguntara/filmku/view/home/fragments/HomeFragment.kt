package com.rakarguntara.filmku.view.home.fragments

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.rakarguntara.filmku.BuildConfig
import com.rakarguntara.filmku.R
import com.rakarguntara.filmku.databinding.FragmentHomeBinding
import com.rakarguntara.filmku.databinding.PopupMovieSimpleInformationBinding
import com.rakarguntara.filmku.models.DetailMovieResponse
import com.rakarguntara.filmku.models.GenresItem
import com.rakarguntara.filmku.network.NetworkState
import com.rakarguntara.filmku.utils.listener.OnMovieItemClickListener
import com.rakarguntara.filmku.utils.loading.showLoading
import com.rakarguntara.filmku.view.adapters.GenreAdapter
import com.rakarguntara.filmku.view.adapters.MovieNowPlayingAdapter
import com.rakarguntara.filmku.view.adapters.MoviePopularAdapter
import com.rakarguntara.filmku.view.adapters.MovieTopRatedAdapter
import com.rakarguntara.filmku.view.adapters.MovieUpcomingAdapter
import com.rakarguntara.filmku.viewmodels.HomeViewModel

class HomeFragment : Fragment() {
    //layout
    private var _binding : FragmentHomeBinding? = null
    private val binding get() = _binding!!
    //viewmodel
    private var homeViewModel : HomeViewModel? = null
    //adapter
    private var moviePopularAdapter: MoviePopularAdapter? = null
    private var movieTopRatedAdapter: MovieTopRatedAdapter? = null
    private var movieNowPlayingAdapter: MovieNowPlayingAdapter? = null
    private var movieUpcomingAdapter: MovieUpcomingAdapter? = null
    private var genreAdapter: GenreAdapter? = null
    //popup dialog
    private var popupMovieSimpleInformationDialog: Dialog? = null
    private var _popupMovieSimpleInformationBinding: PopupMovieSimpleInformationBinding? = null
    private val popupMovieSimpleInformationBinding get() = _popupMovieSimpleInformationBinding!!

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

        //setup all adapter
        setupMoviePopularAdapter()
        setupTopRatedMovieAdapter()
        setupNowPlayingMovieAdapter()
        setupUpcomingMovieAdapter()

        //setup popup dialog
        setupPopupDialog()

        //setup condition for movie item when get click
        setupConditionMovieItemClick()
    }

    private fun setupPopupDialog() {
        popupMovieSimpleInformationDialog = Dialog(requireActivity())
        _popupMovieSimpleInformationBinding = PopupMovieSimpleInformationBinding.inflate(requireActivity().layoutInflater)
        popupMovieSimpleInformationBinding.let {
            popupMovieSimpleInformationDialog?.setContentView(it.root)
        }
        popupMovieSimpleInformationDialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        popupMovieSimpleInformationDialog?.window?.setBackgroundDrawableResource(R.drawable.custom_background)
        popupMovieSimpleInformationDialog?.window?.setDimAmount(0.5f)
        popupMovieSimpleInformationDialog?.setCancelable(true)
    }

    private fun setupConditionMovieItemClick() {
        movieUpcomingAdapter?.onMovieItemClickListener(object : OnMovieItemClickListener{
            override fun onMovieItemClick(id: Int?) {
                popupMovieSimpleInformationDialog?.show()
                setupMovieDetailSimple(id!!)
            }

        })

        movieNowPlayingAdapter?.onMovieItemClickListener(object : OnMovieItemClickListener{
            override fun onMovieItemClick(id: Int?) {
                popupMovieSimpleInformationDialog?.show()
                setupMovieDetailSimple(id!!)
            }
        })

        movieTopRatedAdapter?.onMovieItemClickListener(object : OnMovieItemClickListener{
            override fun onMovieItemClick(id: Int?) {
                popupMovieSimpleInformationDialog?.show()
                setupMovieDetailSimple(id!!)
            }

        })

        moviePopularAdapter?.onMovieItemClickListener(object : OnMovieItemClickListener{
            override fun onMovieItemClick(id: Int?) {
                popupMovieSimpleInformationDialog?.show()
                setupMovieDetailSimple(id!!)
            }

        })
    }

    private fun setupMovieDetailSimple(id: Int) {
        homeViewModel?.getMovieDetail(id)?.observe(viewLifecycleOwner){ response ->
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
                        setMovieDetailSimpleData(response.data)
                    }
                }
            }

        }
    }

    private fun setMovieDetailSimpleData(data: DetailMovieResponse) {
        Glide.with(requireActivity())
            .load(BuildConfig.IMAGE_BASE_URL+"/t/p/w200${data.posterPath}")
            .into(popupMovieSimpleInformationBinding.ivMovieSimpleInformation)

        popupMovieSimpleInformationBinding.tvMovieSimpleInformationTitle.text = data.title
        popupMovieSimpleInformationBinding.tvMovieSimpleInformationStatusActual.text = data.status
        popupMovieSimpleInformationBinding.tvMovieSimpleInformationPopularityActual.text = data.popularity.toString()
        popupMovieSimpleInformationBinding.tvMovieSimpleInformationDateActual.text = data.releaseDate
        setupGenreAdapter(data.genres)
    }

    private fun setupGenreAdapter(genre: List<GenresItem>) {
        genreAdapter = GenreAdapter()
        popupMovieSimpleInformationBinding.rvMovieSimpleInformationGenre.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        genreAdapter?.setData(genre)
        popupMovieSimpleInformationBinding.rvMovieSimpleInformationGenre.adapter =
            genreAdapter
    }

    private fun setupUpcomingMovieAdapter() {
        movieUpcomingAdapter = MovieUpcomingAdapter()
        binding.rvMovieUpcoming.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        homeViewModel?.getAllUpcomingMovie(1)?.observe(viewLifecycleOwner){response ->
            if(response != null){
                when(response){
                    is NetworkState.Error -> {
                        showLoading(binding.pbHome,false)
                        Toast.makeText(context, response.error, Toast.LENGTH_SHORT).show()
                    }
                    NetworkState.Loading -> {
                        showLoading(binding.pbHome, true)
                    }
                    is NetworkState.Success -> {
                        showLoading(binding.pbHome, false)
                        movieUpcomingAdapter?.setData(response.data.results)
                        binding.rvMovieUpcoming.adapter = movieUpcomingAdapter
                    }
                }
            }
        }
    }

    private fun setupNowPlayingMovieAdapter() {
        movieNowPlayingAdapter = MovieNowPlayingAdapter()
        binding.rvMovieNowPlaying.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        homeViewModel?.getAllNowPlayingMovie(1)?.observe(viewLifecycleOwner){response ->
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
                        movieNowPlayingAdapter?.setData(response.data.results)
                        binding.rvMovieNowPlaying.adapter = movieNowPlayingAdapter
                    }
                }
            }
        }
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

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        homeViewModel = null
        moviePopularAdapter = null
        movieTopRatedAdapter = null
        movieNowPlayingAdapter = null
        movieUpcomingAdapter = null
        popupMovieSimpleInformationDialog = null
        _popupMovieSimpleInformationBinding = null
    }

}