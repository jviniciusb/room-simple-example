package com.jviniciusb.room.data.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.util.*

@Entity(
    foreignKeys = [ForeignKey(
        entity = User::class,
        parentColumns = ["id"],
        childColumns = ["parentId"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class Dependent(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var parentId: Int = 0,
    var name: String = "",
    var birth: Date = Date()
)
