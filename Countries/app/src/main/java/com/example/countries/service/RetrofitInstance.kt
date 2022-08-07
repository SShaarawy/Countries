package com.example.countries.service

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    val api: CountryAPI by lazy {
        Retrofit.Builder()
            .baseUrl("https://wft-geo-db.p.rapidapi.com/v1/geo/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CountryAPI::class.java)
    }
}