package com.example.bnetapp.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.bnetapp.model.Repository
import com.example.bnetapp.model.retrofit.drugs.Drugs
import com.example.bnetapp.model.retrofit.drugs.DrugsItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application), RepositoryViewModel {


    private var mutableDrugs = mutableListOf<DrugsItem>()
    private val drugsMutable: MutableStateFlow<List<DrugsItem>> = MutableStateFlow(mutableDrugs.toList())
    private val drugsState = drugsMutable.asStateFlow()
    private val drugsCode: MutableStateFlow<Int?> = MutableStateFlow(null)

    private val repository = Repository(this)

    private var pageNum = 1
    private var lastPage = false

    private var lastSearchQuery: String = ""

    private var singItemId: Int = -1
    private val singleDrugMutable: MutableStateFlow<DrugsItem?> = MutableStateFlow(null)
    val singleDrugState = singleDrugMutable.asStateFlow()

    fun updateDrugs(searchQuery: String) {
        lastPage = false
        pageNum = 1
        lastSearchQuery = searchQuery
        if (searchQuery.isEmpty()) {
            repository.updateDrugs(searchQuery, 0)
        } else {
            repository.updateDrugs(searchQuery, 0)
        }
    }

    fun loadNextPage() {
        println(pageNum)
        if (!lastPage) {
            repository.updateDrugs(lastSearchQuery, pageNum)
            pageNum++
        }
    }

    fun getDrugs(): StateFlow<List<DrugsItem>> {
        return drugsState
    }

    fun getDrugsCode(): MutableStateFlow<Int?> {
        return drugsCode
    }

    override fun setDrugs(data: List<DrugsItem>?, code: Int) {
        if (data != null) {
            singItemId = -1
            mutableDrugs = data as MutableList<DrugsItem>
            viewModelScope.launch(Dispatchers.IO) {
                drugsMutable.emit(mutableDrugs.toList())
            }
        }
        drugsCode.value = code
    }

    override fun setNextPage(data: Drugs?, code: Int) {

        mutableDrugs.addAll(data as MutableList<DrugsItem>)

        if (data.size < 10) {
            lastPage = true
        }
        viewModelScope.launch(Dispatchers.IO) {
            drugsMutable.emit(mutableDrugs.toList())
        }
    }

    fun setSingItemId(id: Int) {
        singleDrugMutable.value = drugsMutable.value[id]
    }


}