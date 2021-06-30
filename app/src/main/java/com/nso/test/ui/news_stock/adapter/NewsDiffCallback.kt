package com.nso.test.ui.news_stock.adapter

import androidx.recyclerview.widget.DiffUtil
import com.nso.test.data.remote.model.CompanyNews

class NewsDiffCallback : DiffUtil.ItemCallback<CompanyNews>() {
    override fun areItemsTheSame(oldItem: CompanyNews, newItem: CompanyNews): Boolean {
        return (oldItem.id == newItem.id)
    }

    override fun areContentsTheSame(oldItem: CompanyNews, newItem: CompanyNews): Boolean {
        return (oldItem == newItem)
    }

}
