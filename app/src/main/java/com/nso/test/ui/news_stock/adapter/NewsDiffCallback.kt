package com.nso.test.ui.news_stock.adapter

import androidx.recyclerview.widget.DiffUtil
import com.nso.test.domain.entity.CompanyNewsEntity

class NewsDiffCallback : DiffUtil.ItemCallback<CompanyNewsEntity>() {
    override fun areItemsTheSame(oldItem: CompanyNewsEntity, newItem: CompanyNewsEntity): Boolean {
        return (oldItem.id == newItem.id)
    }

    override fun areContentsTheSame(oldItem: CompanyNewsEntity, newItem: CompanyNewsEntity): Boolean {
        return (oldItem == newItem)
    }

}
