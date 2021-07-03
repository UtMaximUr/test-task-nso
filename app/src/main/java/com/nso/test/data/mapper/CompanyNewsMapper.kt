package com.nso.test.data.mapper

import com.nso.test.data.extension.toCompanyNewsEntityList
import com.nso.test.data.remote.model.CompanyNews
import com.nso.test.domain.entity.CompanyNewsEntity

class CompanyNewsMapper {

    fun companyNewsEntityMapper(list: List<CompanyNews>): List<CompanyNewsEntity> {
        return list.toCompanyNewsEntityList()
    }
}