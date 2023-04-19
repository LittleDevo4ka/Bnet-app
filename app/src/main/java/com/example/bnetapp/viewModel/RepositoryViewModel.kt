package com.example.bnetapp.viewModel

import com.example.bnetapp.model.retrofit.drugs.Drugs
import com.example.bnetapp.model.retrofit.drugs.DrugsItem

interface RepositoryViewModel {

    fun setDrugs(data: List<DrugsItem>?, code: Int)
    fun setNextPage(data: Drugs?, code: Int)
}