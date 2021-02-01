package com.jviniciusb.room.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class NewEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var value: String = ""
)
