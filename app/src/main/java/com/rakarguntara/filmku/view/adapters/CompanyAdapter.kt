package com.rakarguntara.filmku.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rakarguntara.filmku.BuildConfig
import com.rakarguntara.filmku.databinding.RvContentCompanyItemBinding
import com.rakarguntara.filmku.models.ProductionCompaniesItem

class CompanyAdapter: RecyclerView.Adapter<CompanyAdapter.CompanyViewHolder>() {
    private val genreArrayList = ArrayList<ProductionCompaniesItem>()

    fun setData(list: List<ProductionCompaniesItem>){
        genreArrayList.clear()
        genreArrayList.addAll(list)
        notifyDataSetChanged()
    }

    inner class CompanyViewHolder(private val binding: RvContentCompanyItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ProductionCompaniesItem){
            Glide.with(itemView.context)
                .load(BuildConfig.IMAGE_BASE_URL+"/t/p/w500${item.logoPath}")
                .into(binding.ivCompany)

            binding.tvCompanyName.text = item.name
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CompanyViewHolder {
        val binding = RvContentCompanyItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CompanyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return genreArrayList.size
    }

    override fun onBindViewHolder(holder: CompanyViewHolder, position: Int) {
        val data : ProductionCompaniesItem = genreArrayList[position]
        return holder.bind(data)
    }
}