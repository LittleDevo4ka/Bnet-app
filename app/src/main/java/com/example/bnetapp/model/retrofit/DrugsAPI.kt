package com.example.bnetapp.model.retrofit

import com.example.bnetapp.model.retrofit.drugs.Drugs
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url

interface DrugsAPI {

    @GET
    fun getDrugs(@Url url: String): Call<Drugs>

}