package com.foxhole.myapplication.Model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "matches_table")
class Matches(
    val name: String,
    val age: String,
    val hgt: String,
    val place: String,
    val occupation: String, //  @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    val image: Int
) {
    @PrimaryKey(autoGenerate = true)
    var uid = 0

}