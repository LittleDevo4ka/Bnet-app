package com.example.bnetapp.model.retrofit

import android.util.Log
import com.example.bnetapp.model.Repository
import com.example.bnetapp.model.retrofit.drugs.Drugs
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DrugsAPIService (private val repository: Repository) {

    private val baseDrugsURL = "http://shans.d2.i-partner.ru/"
    private val retrofit = Retrofit.Builder()
        .baseUrl(baseDrugsURL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(DrugsAPI::class.java)
    private val tag = "DrugsAPIService"

    fun getDrugs(searchQuery: String, pageNum: Int) {
        val offset = pageNum * 10
        val allDrugsUrl = if (searchQuery.isNotEmpty()) {
            "${baseDrugsURL}api/ppp/index/?search=$searchQuery&offset=$offset&limit=10"
        } else {
            "${baseDrugsURL}api/ppp/index/?offset=$offset&limit=10"
        }

        val call = retrofit.getDrugs(allDrugsUrl)
        call.enqueue(object: Callback<Drugs> {
            override fun onResponse(call: Call<Drugs>, response: Response<Drugs>) {
                if (response.isSuccessful) {
                    Log.i(tag, "OK: getDrugs")
                    if (pageNum != 0) {
                        sendNextPage(response.body(), 200)
                    } else {
                        sendDrugs(response.body(), 200)
                    }
                } else {
                    Log.w(tag, "Something went wrong ${response.message()}")
                }
            }

            override fun onFailure(call: Call<Drugs>, t: Throwable) {
                Log.w(tag, "Something went really wrong ${t.message}")
                sendDrugs(null, 400)
            }

        })
    }

    fun sendDrugs(data: Drugs?, code: Int) {
        repository.setDrugs(data, code)
    }

    fun sendNextPage(data: Drugs?, code: Int) {
        repository.loadNextPage(data, code)
    }

}