package com.nso.test.data.extension

import com.nso.test.data.remote.model.CompanyNews
import com.nso.test.domain.entity.CompanyNewsEntity


fun List<CompanyNews>.toCompanyNewsEntityList(): List<CompanyNewsEntity> {
    val list = ArrayList<CompanyNewsEntity>()
    for (item in this) {
        list.add(
            CompanyNewsEntity(
                category = item.category,
                datetime = item.datetime,
                headline = item.headline,
                id = item.id,
                image = item.image,
                url = item.url
            )
        )
    }
    return list
}