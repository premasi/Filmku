package com.rakarguntara.filmku.view.detail

import android.graphics.Color
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.rakarguntara.filmku.BuildConfig
import com.rakarguntara.filmku.R
import com.rakarguntara.filmku.databinding.ActivityDetailMovieBinding
import com.rakarguntara.filmku.models.DetailMovieResponse
import com.rakarguntara.filmku.models.GenresItem
import com.rakarguntara.filmku.models.ProductionCompaniesItem
import com.rakarguntara.filmku.utils.animations.animateIvClick
import com.rakarguntara.filmku.utils.loading.showLoading
import com.rakarguntara.filmku.utils.togglestate.ToggleResult
import com.rakarguntara.filmku.view.adapters.CompanyAdapter
import com.rakarguntara.filmku.view.adapters.GenreAdapter
import com.rakarguntara.filmku.viewmodels.local.LocalViewModels
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailMovieActivity : AppCompatActivity() {
    //layout
    private var _binding: ActivityDetailMovieBinding? = null
    private val binding get() = _binding!!
    //receive data
    private var movieDetailData : DetailMovieResponse? = null
    //view model
    private val localViewModels: LocalViewModels by viewModels()
    //adapter
    private var genreAdapter: GenreAdapter? = null
    private var companyAdapter: CompanyAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        _binding = ActivityDetailMovieBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0)
            insets
        }

        binding.ivArrowBack.setOnClickListener {
            animateIvClick(binding.ivArrowBack)
            onBackPressedDispatcher.onBackPressed()
        }

        //get parcelable for back up
        movieDetailData = intent.getParcelableExtra<DetailMovieResponse>(MOVIE_DETAIL) as DetailMovieResponse


        showLoading(binding.pbDetail, false)
        setupMovieDetailData(movieDetailData!!)


        //setup favorite condtion
        setupFavoriteCondition(movieDetailData!!)

    }

    private fun setupFavoriteCondition(movieDetailData: DetailMovieResponse) {
        lifecycleScope.launch {
            localViewModels.isFavorite.collect { isFavorite ->
                updateToggleButton(isFavorite)
            }
        }

        lifecycleScope.launch {
            localViewModels.toggleResult.collect{ result ->
                when(result){
                    ToggleResult.Added -> Toast.makeText(this@DetailMovieActivity, "Added to favorite!", Toast.LENGTH_SHORT).show()
                    ToggleResult.Deleted -> Toast.makeText(this@DetailMovieActivity, "Removed from favorite!", Toast.LENGTH_SHORT).show()
                    ToggleResult.None -> {}
                }

            }
        }

        localViewModels.checkIfFavorite(movieDetailData.id!!)

        binding.ivFavorite.setOnClickListener {
            animateIvClick(binding.ivFavorite)
            localViewModels.toggleFavorite(movieDetailData)
        }
    }

    private fun updateToggleButton(isFavorite: Boolean){
        val tintColor = if (isFavorite)
            Color.RED
        else
            ContextCompat.getColor(this, R.color.teal)

        binding.ivFavorite.apply {
            setColorFilter(tintColor)
        }
    }

    private fun setupMovieDetailData(movieDetailData: DetailMovieResponse) {
        Glide.with(this@DetailMovieActivity)
            .load(BuildConfig.IMAGE_BASE_URL+"/t/p/w500${movieDetailData.backdropPath}")
            .placeholder(R.drawable.iv_no_favorite)
            .error(R.drawable.iv_no_favorite)
            .into(binding.ivMovieDetail)
        val tagline = if(movieDetailData.tagline!!.isNotEmpty()) "\"${movieDetailData.tagline}\"" else "\"-\""
        binding.tvMovieDetailTitle.text = movieDetailData.title
        binding.tvTagline.text = tagline
        binding.tvRate.text = movieDetailData.voteAverage.toString().split(".")[0]
        binding.tvMoviePopular.text = movieDetailData.popularity.toString().split(".")[0]
        binding.tvVote.text = movieDetailData.voteCount.toString()
        binding.tvMovieInformationDateActual.text = movieDetailData.releaseDate.toString()
        binding.tvOverviewActual.text = movieDetailData.overview
        setupGenreAdapter(movieDetailData.genres!!)
        setupCompanyAdapter(movieDetailData.productionCompanies!!)
    }

    private fun setupCompanyAdapter(productionCompanies: List<ProductionCompaniesItem>) {
        companyAdapter = CompanyAdapter()
        binding.rvMovieCompany.layoutManager =
            LinearLayoutManager(this@DetailMovieActivity, LinearLayoutManager.HORIZONTAL, false)
        companyAdapter?.setData(productionCompanies)
        binding.rvMovieCompany.adapter = companyAdapter
    }

    private fun setupGenreAdapter(genre: List<GenresItem>) {
        genreAdapter = GenreAdapter()
        binding.rvMovieSimpleInformationGenre.layoutManager =
            LinearLayoutManager(this@DetailMovieActivity, LinearLayoutManager.HORIZONTAL, false)
        genreAdapter?.setData(genre)
        binding.rvMovieSimpleInformationGenre.adapter = genreAdapter
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        movieDetailData = null
        genreAdapter = null
        companyAdapter = null
    }

    companion object{
        const val MOVIE_DETAIL = "movie_detail"
    }
}