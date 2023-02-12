package com.example.borutoapp.data.local

import androidx.room.TypeConverter

class DatabaseConverter {

    private val separator = ","

    @TypeConverter
    fun convertListToString(list: List<String>): String {
        val stringBuild = StringBuilder()
        for (item in list) {
            stringBuild.append(item).append(separator)
        }
        //Delete the last comma
        stringBuild.setLength(stringBuild.length - separator.length)
        return stringBuild.toString()
    }

    @TypeConverter
    fun convertStringToList(string: String): List<String> {
        return string.split(separator)
    }
}