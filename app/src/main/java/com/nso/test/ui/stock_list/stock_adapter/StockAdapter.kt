package com.nso.test.ui.stock_list.stock_adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.khoben.ticker.ui.recyclerview.adapter.StockDiffCallback
import com.nso.test.R
import com.nso.test.databinding.ItemStockBinding
import com.nso.test.domain.entity.StockEntity
import com.nso.test.utils.isPositive
import com.nso.test.utils.setImagePath

class StockAdapter(
    private val onItemClicked: ((StockEntity) -> Unit)? = null,
    private val onFavouriteClick: ((StockEntity) -> Unit)? = null
) : ListAdapter<StockEntity, StockAdapter.StockViewHolder>(StockDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StockViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemStockBinding.inflate(layoutInflater, parent, false)
        return StockViewHolder(
            binding,
            { onItemClicked?.invoke(getItem(it)) },
            { onFavouriteClick?.invoke(getItem(it)) })
    }

    override fun onBindViewHolder(holder: StockViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    inner class StockViewHolder(
        private val binding: ItemStockBinding,
        private val onItemClicked: ((Int) -> Unit)? = null,
        private val onFavouriteClick: ((Int) -> Unit)? = null
    ) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.item.setOnClickListener {
                onItemClicked?.invoke(adapterPosition)
            }
            binding.favoriteButton.setOnClickListener {
                onFavouriteClick?.invoke(adapterPosition)
            }
        }

        fun bind(stock: StockEntity) {
            binding.stock = stock
            binding.executePendingBindings()
            binding.stockCompany.isSelected = true
            binding.stockLogo.setImagePath(stock.logo)
            binding.favoriteButton.isChecked = stock.isFavorite

            if (stock.delta.isPositive()) {
                binding.stockRate.setTextColor(
                    ContextCompat.getColor(
                        binding.root.context,
                        R.color.green
                    )
                )
            } else {
                binding.stockRate.setTextColor(
                    ContextCompat.getColor(
                        binding.root.context,
                        R.color.red
                    )
                )
            }
        }
    }
}