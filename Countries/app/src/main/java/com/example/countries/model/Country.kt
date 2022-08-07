package com.example.countries.model

data class Country(
    val data : ArrayList<Data>,
    val links : ArrayList<Links>,
    val metadata : Metadata

)

data class Data(
    val code : String,
    val currencyCodes : List<String>,
    val name : String,
    val wikiDataId : String
)

data class Links(
    val rel : String,
    val href : String
)

data class Metadata(
    val currentOffset : Int,
    val totalCount : Int
)

data class Details(
    val data : Info
)

data class Info(
    val capital: String,
    val code: String,
    val callingCode: String,
    val currencyCodes: List<String>,
    val flagImageUri: String,
    val name: String,
    val numRegions: Int,
    val wikiDataId: String
)

