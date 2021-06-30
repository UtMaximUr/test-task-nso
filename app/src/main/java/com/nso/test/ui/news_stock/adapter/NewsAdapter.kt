package com.nso.test.ui.news_stock.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nso.test.data.remote.model.CompanyNews
import com.nso.test.databinding.ItemNewsBinding
import com.nso.test.utils.setImagePath

class NewsAdapter(
    private val onItemClicked: ((CompanyNews) -> Unit)? = null,
) : ListAdapter<CompanyNews, NewsAdapter.NewsViewHolder>(NewsDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemNewsBinding.inflate(layoutInflater, parent, false)
        return NewsViewHolder(
            binding
        ) { onItemClicked?.invoke(getItem(it)) }
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    inner class NewsViewHolder(
        private val binding: ItemNewsBinding,
        private val onItemClicked: ((Int) -> Unit)? = null
    ) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.item.setOnClickListener {
                onItemClicked?.invoke(adapterPosition)
            }
        }

        fun bind(news: CompanyNews) {
            binding.news = news
            binding.executePendingBindings()
            binding.company.isSelected = true
            binding.logo.setImagePath(news.image.toString())
        }
    }
}