package com.example.countries.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "country")
class CountryModelDB(

    @PrimaryKey
    @ColumnInfo(name = "name")
    val name : String,

    @ColumnInfo(name = "code")
    val code : String,

    @ColumnInfo(name= "isSaved")
    val isSaved : Boolean
)