package com.rakarguntara.filmku.view.detail

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.rakarguntara.filmku.BuildConfig
import com.rakarguntara.filmku.R
import com.rakarguntara.filmku.databinding.ActivityDetailMovieBinding
import com.rakarguntara.filmku.models.DetailMovieResponse
import com.rakarguntara.filmku.models.GenresItem
import com.rakarguntara.filmku.models.ProductionCompaniesItem
import com.rakarguntara.filmku.network.NetworkState
import com.rakarguntara.filmku.utils.animations.animateIvClick
import com.rakarguntara.filmku.utils.loading.showLoading
import com.rakarguntara.filmku.view.adapters.CompanyAdapter
import com.rakarguntara.filmku.view.adapters.GenreAdapter
import com.rakarguntara.filmku.viewmodels.DetalViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailMovieActivity : AppCompatActivity() {
    //layout
    private var _binding: ActivityDetailMovieBinding? = null
    private val binding get() = _binding!!
    //receive data
    private var movieDetailData : DetailMovieResponse? = null
    //view model
    private val detalViewModel: DetalViewModel by viewModels()
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

        //get detail from api
        setupDetailFromApi(movieDetailData!!)
    }

    private fun setupDetailFromApi(movieDetailData: DetailMovieResponse) {
        detalViewModel.getMovieDetail(movieDetailData.id!!).observe(this@DetailMovieActivity){ response ->
            if(response != null){
                when(response){
                    is NetworkState.Error -> {
                        showLoading(binding.pbDetail, false)
                        Toast.makeText(this@DetailMovieActivity, response.error, Toast.LENGTH_SHORT).show()
                    }
                    NetworkState.Loading -> {
                        showLoading(binding.pbDetail,true)
                    }
                    is NetworkState.Success -> {
                        showLoading(binding.pbDetail, false)
                        setupMovideDetailData(response.data)
                    }
                }
            }

        }
    }

    private fun setupMovideDetailData(movieDetailData: DetailMovieResponse) {
        Glide.with(this@DetailMovieActivity)
            .load(BuildConfig.IMAGE_BASE_URL+"/t/p/w500${movieDetailData.backdropPath}")
            .error(R.color.navy)
            .into(binding.ivMovieDetail)
        val tagline = "\"${movieDetailData.tagline}\""
        binding.tvMovieDetailTitle.text = movieDetailData.title
        binding.tvTagline.text = tagline
        binding.tvRate.text = movieDetailData.voteAverage.toString().split(".")[0]
        binding.tvMoviePopular.text = movieDetailData.popularity.toString().split(".")[0]
        binding.tvVote.text = movieDetailData.voteCount.toString()
        binding.tvMovieInformationDateActual.text = movieDetailData.releaseDate.toString()
        binding.tvOverviewActual.text = movieDetailData.overview
        setupGenreAdapter(movieDetailData.genres)
        setupCompanyAdapter(movieDetailData.productionCompanies)
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
    }

    companion object{
        const val MOVIE_DETAIL = "movie_detail"
    }
}