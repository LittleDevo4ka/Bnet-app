package com.example.bnetapp.model

import androidx.sqlite.db.SupportSQLiteQuery
import com.example.bnetapp.model.retrofit.DrugsAPIService
import com.example.bnetapp.model.retrofit.drugs.Drugs
import com.example.bnetapp.viewModel.RepositoryViewModel

class Repository(private val viewModel: RepositoryViewModel) {

    private val drugsAPIService = DrugsAPIService(this)

    fun updateDrugs(searchQuery: String, pageNum: Int) {
        drugsAPIService.getDrugs(searchQuery, pageNum)
    }


    fun setDrugs(data: Drugs?, code: Int) {
        viewModel.setDrugs(data, code)
    }

    fun loadNextPage(data: Drugs?, code: Int) {
        viewModel.setNextPage(data, code)
    }
}