package com.example.countries.service

import com.example.countries.model.Country
import com.example.countries.model.Details
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

interface CountryAPI {

    @Headers(
        "X-RapidAPI-Key: 54e7fc9ab2msh12d0bd36c23bd51p174f0fjsnd562a7ead65b",
        "X-RapidAPI-Host: wft-geo-db.p.rapidapi.com"
    )
    @GET("countries?limit=10")
    fun getCountries() : Call<Country>

    @Headers(
        "X-RapidAPI-Key: 54e7fc9ab2msh12d0bd36c23bd51p174f0fjsnd562a7ead65b",
        "X-RapidAPI-Host: wft-geo-db.p.rapidapi.com"
    )
    @GET("countries/{code}")
    fun getDetails(@Path("code") code: String) : Call<Details>
}