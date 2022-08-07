package com.example.countries.roomdb

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.countries.model.CountryModelDB

@Database(entities = [CountryModelDB::class], version = 1)
abstract class CountryDatabase : RoomDatabase() {
    abstract fun countryDao() : CountryDao
}