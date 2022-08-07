package com.example.countries.roomdb

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.countries.model.CountryModelDB

@Dao
interface CountryDao {

    @Insert
    fun insert(country : CountryModelDB)

    @Query("SELECT isSaved FROM country WHERE name = :name")
    fun getStateFromName(name : String): Boolean

    @Query("UPDATE country SET isSaved = :isSaved WHERE name = :name")
    fun updateState(name: String, isSaved: Boolean)

    @Query("SELECT * FROM country")
    fun getAll(): List<CountryModelDB>

    @Query("SELECT code FROM country WHERE name = :name")
    fun getCodeFromName(name : String): String


}