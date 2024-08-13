package com.rakarguntara.filmku.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rakarguntara.filmku.BuildConfig
import com.rakarguntara.filmku.R
import com.rakarguntara.filmku.databinding.RvContentMovieItemBinding
import com.rakarguntara.filmku.models.ResultsUpcomingItem
import com.rakarguntara.filmku.utils.animations.animateMcvClick
import com.rakarguntara.filmku.utils.listener.OnMovieItemClickListener

class MovieUpcomingAdapter: RecyclerView.Adapter<MovieUpcomingAdapter.MovieUpcomingViewHolder>() {
    private val movieUpcomingArrayList = ArrayList<ResultsUpcomingItem>()
    private var onMovieItemClickListener: OnMovieItemClickListener? = null

    fun setData(list: List<ResultsUpcomingItem>){
        movieUpcomingArrayList.clear()
        movieUpcomingArrayList.addAll(list)
        notifyDataSetChanged()
    }

    fun onMovieItemClickListener(onMovieItemClickListener: OnMovieItemClickListener){
        this.onMovieItemClickListener = onMovieItemClickListener
    }

    inner class MovieUpcomingViewHolder(private val binding: RvContentMovieItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ResultsUpcomingItem){
            Glide.with(itemView.context)
                .load(BuildConfig.IMAGE_BASE_URL+"/t/p/w200${item.posterPath}")
                .placeholder(R.drawable.iv_thank)
                .error(R.drawable.iv_thank)
                .into(binding.ivMoviePopularItem)

            binding.mcvMoviePopularItem.setOnClickListener {
                animateMcvClick(binding.mcvMoviePopularItem)
                onMovieItemClickListener?.onMovieItemClick(item.id)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieUpcomingViewHolder {
        val binding = RvContentMovieItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieUpcomingViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return movieUpcomingArrayList.size
    }

    override fun onBindViewHolder(holder: MovieUpcomingViewHolder, position: Int) {
        val data: ResultsUpcomingItem = movieUpcomingArrayList[position]
        return holder.bind(data)
    }
}