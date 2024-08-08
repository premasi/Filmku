package com.rakarguntara.filmku.view.more

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.rakarguntara.filmku.R
import com.rakarguntara.filmku.databinding.ActivityMoreBinding
import com.rakarguntara.filmku.network.NetworkState
import com.rakarguntara.filmku.utils.animations.animateIvClick
import com.rakarguntara.filmku.utils.animations.animateTextViewClick
import com.rakarguntara.filmku.utils.listener.OnMovieItemClickListener
import com.rakarguntara.filmku.utils.loading.showLoading
import com.rakarguntara.filmku.view.adapters.MovieNowPlayingAdapter
import com.rakarguntara.filmku.view.adapters.MoviePopularAdapter
import com.rakarguntara.filmku.view.adapters.MovieTopRatedAdapter
import com.rakarguntara.filmku.view.adapters.MovieUpcomingAdapter
import com.rakarguntara.filmku.view.detail.DetailMovieActivity
import com.rakarguntara.filmku.viewmodels.network.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MoreActivity : AppCompatActivity() {
    //layout
    private var _binding: ActivityMoreBinding? = null
    private val binding get() = _binding!!
    //variable
    private var page: Int = 1
    private var type: String = ""
    //adapter
    private var moviePopularAdapter : MoviePopularAdapter? = null
    private var movieTopRatedAdapter : MovieTopRatedAdapter? = null
    private var movieNowPlayingAdapter : MovieNowPlayingAdapter? = null
    private var movieUpcomingAdapter : MovieUpcomingAdapter? = null
    //viewmodel
    private val homeViewModel: HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        _binding = ActivityMoreBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        if (savedInstanceState != null) {
            page = savedInstanceState.getInt("page", 1)
            type = savedInstanceState.getString("movie_type", "Popular")
        } else {
            type = intent.getStringExtra(TYPE) ?: "Popular"
        }
        binding.tvMovieType.text = type

        setupPopularAdapter(page)
        nextOrBackClick(type)

        //setup click listener
        setupConditionMovieItemClick()

        //back
        binding.ivArrowBack.setOnClickListener {
            animateIvClick(binding.ivArrowBack)
            onBackPressedDispatcher.onBackPressed()
        }

    }





    private fun setupConditionMovieItemClick() {
        movieUpcomingAdapter?.onMovieItemClickListener(object : OnMovieItemClickListener {
            override fun onMovieItemClick(id: Int?) {
                setupMovieDetailSimple(id!!)
            }

        })

        movieNowPlayingAdapter?.onMovieItemClickListener(object : OnMovieItemClickListener {
            override fun onMovieItemClick(id: Int?) {
                setupMovieDetailSimple(id!!)
            }
        })

        movieTopRatedAdapter?.onMovieItemClickListener(object : OnMovieItemClickListener {
            override fun onMovieItemClick(id: Int?) {
                setupMovieDetailSimple(id!!)
            }

        })

        moviePopularAdapter?.onMovieItemClickListener(object : OnMovieItemClickListener {
            override fun onMovieItemClick(id: Int?) {
                setupMovieDetailSimple(id!!)
            }

        })
    }

    private fun setupMovieDetailSimple(id: Int) {
        homeViewModel.getMovieDetail(id).observe(this){ response ->
            if(response != null){
                when(response){
                    is NetworkState.Error -> {
                        showLoading(binding.pbMore, false)
                        Toast.makeText(this@MoreActivity, response.error, Toast.LENGTH_SHORT).show()
                    }
                    NetworkState.Loading -> {
                        showLoading(binding.pbMore, true)
                    }
                    is NetworkState.Success -> {
                        showLoading(binding.pbMore, false)
                        intent = Intent(this@MoreActivity, DetailMovieActivity::class.java)
                        intent.putExtra(DetailMovieActivity.MOVIE_DETAIL, response.data)
                        startActivity(intent, ActivityOptionsCompat.makeSceneTransitionAnimation(this@MoreActivity).toBundle())
                    }
                }
            }

        }
    }


    private fun nextOrBackClick(type: String) {
        binding.tvNext.setOnClickListener {
            animateTextViewClick(binding.tvNext)
            if(page == 30){
                Toast.makeText(this@MoreActivity, "Maximum page is 30", Toast.LENGTH_SHORT).show()
            } else {
                page++
                binding.tvNumber.text = page.toString()
                when(type){
                    "Popular" -> setupPopularAdapter(page)
                    "Top Rated" -> setupTopRatedAdapter(page)
                    "Now Playing" -> setupNowPlayingAdapter(page)
                    "Upcoming" -> setupUpcomingAdapter(page)
                }
                setupConditionMovieItemClick()
            }
        }

        binding.tvBack.setOnClickListener {
            animateTextViewClick(binding.tvBack)
            if(page == 1){
                Toast.makeText(this@MoreActivity, "Minimum page is 1", Toast.LENGTH_SHORT).show()
            } else {
                page--
                binding.tvNumber.text = page.toString()
                when(type){
                    "Popular" -> setupPopularAdapter(page)
                    "Top Rated" -> setupTopRatedAdapter(page)
                    "Now Playing" -> setupNowPlayingAdapter(page)
                    "Upcoming" -> setupUpcomingAdapter(page)
                }
                setupConditionMovieItemClick()
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("page", page)
        outState.putString("movie_type", type)
    }

    private fun setupUpcomingAdapter(page: Int) {
        movieUpcomingAdapter = MovieUpcomingAdapter()
        binding.rvMovie.layoutManager = GridLayoutManager(this@MoreActivity, 3)
        homeViewModel.getAllUpcomingMovie(page).observe(this@MoreActivity){response ->
            if(response != null){
                when(response){
                    is NetworkState.Error -> {
                        showLoading(binding.pbMore, false)
                        Toast.makeText(this@MoreActivity, response.error ,Toast.LENGTH_SHORT).show()
                    }
                    NetworkState.Loading -> {
                        showLoading(binding.pbMore, true)
                    }
                    is NetworkState.Success -> {
                        showLoading(binding.pbMore, false)
                        movieUpcomingAdapter?.setData(response.data.results!!)
                        binding.rvMovie.adapter = movieUpcomingAdapter
                    }
                }
            }
        }
    }

    private fun setupNowPlayingAdapter(page: Int) {
        movieNowPlayingAdapter = MovieNowPlayingAdapter()
        binding.rvMovie.layoutManager = GridLayoutManager(this@MoreActivity, 3)
        homeViewModel.getAllNowPlayingMovie(page).observe(this@MoreActivity){response ->
            if(response != null){
                when(response){
                    is NetworkState.Error -> {
                        showLoading(binding.pbMore, false)
                        Toast.makeText(this@MoreActivity, response.error ,Toast.LENGTH_SHORT).show()
                    }
                    NetworkState.Loading -> {
                        showLoading(binding.pbMore, true)
                    }
                    is NetworkState.Success -> {
                        showLoading(binding.pbMore, false)
                        movieNowPlayingAdapter?.setData(response.data.results!!)
                        binding.rvMovie.adapter = movieNowPlayingAdapter
                    }
                }
            }
        }
    }

    private fun setupTopRatedAdapter(page: Int) {
        movieTopRatedAdapter = MovieTopRatedAdapter()
        binding.rvMovie.layoutManager = GridLayoutManager(this@MoreActivity, 3)
        homeViewModel.getAllTopRatedMovie(page).observe(this@MoreActivity){response ->
            if(response != null){
                when(response){
                    is NetworkState.Error -> {
                        showLoading(binding.pbMore, false)
                        Toast.makeText(this@MoreActivity, response.error ,Toast.LENGTH_SHORT).show()
                    }
                    NetworkState.Loading -> {
                        showLoading(binding.pbMore, true)
                    }
                    is NetworkState.Success -> {
                        showLoading(binding.pbMore, false)
                        movieTopRatedAdapter?.setData(response.data.results!!)
                        binding.rvMovie.adapter = movieTopRatedAdapter
                    }
                }
            }
        }
    }

    private fun setupPopularAdapter(page: Int) {
        moviePopularAdapter = MoviePopularAdapter()
        binding.rvMovie.layoutManager = GridLayoutManager(this@MoreActivity, 3)
        homeViewModel.getAllPopularMovie(page).observe(this@MoreActivity){response ->
            if(response != null){
                when(response){
                    is NetworkState.Error -> {
                        showLoading(binding.pbMore, false)
                        Toast.makeText(this@MoreActivity, response.error, Toast.LENGTH_SHORT).show()
                    }
                    NetworkState.Loading -> {
                        showLoading(binding.pbMore, true)
                    }
                    is NetworkState.Success -> {
                        showLoading(binding.pbMore, false)
                        moviePopularAdapter?.setData(response.data.results!!)
                        binding.rvMovie.adapter = moviePopularAdapter
                    }
                }
            }
        }

    }

    companion object{
        const val TYPE = "type"
    }
}