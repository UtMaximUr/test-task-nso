package com.khoben.ticker.ui.recyclerview.adapter

import androidx.recyclerview.widget.DiffUtil
import com.nso.test.domain.entity.StockEntity

class StockDiffCallback : DiffUtil.ItemCallback<StockEntity>() {
    override fun areItemsTheSame(oldItem: StockEntity, newItem: StockEntity): Boolean {
        return (oldItem.ticker == newItem.ticker)
    }

    override fun areContentsTheSame(oldItem: StockEntity, newItem: StockEntity): Boolean {
        return (oldItem == newItem)
    }

}
