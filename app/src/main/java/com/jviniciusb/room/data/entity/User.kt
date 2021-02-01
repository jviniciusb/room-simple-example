package com.jviniciusb.room.data.entity

import android.graphics.Bitmap
import androidx.room.*
import com.jviniciusb.room.data.DateConverter
import java.util.*

@Entity
data class User(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    @ColumnInfo(name = "user_name")
    var name: String = "",
    @Embedded
    var address: Address = Address(),
    @TypeConverters(DateConverter::class)
    var birth: Date = Date(), // stored using TypeConverter
    @Ignore
    var picture: Bitmap? = null
)

data class Address(
    var country: String = "",
    var address: String = "",
    var city: String = "",
    var zipCode: String = ""
)

class UserAndDependents {
    @Embedded
    var user: User = User()

    @Relation(
        parentColumn = "id",
        entityColumn = "parentId"
    )
    var dependents: List<Dependent> = emptyList()
}

@DatabaseView(
    "SELECT user.id, user.user_name, COUNT(dependent.id) AS dependentsCount " +
            "FROM user INNER JOIN dependent " +
            "ON dependent.parentId = user.id " +
            "GROUP BY user.id"
)
data class UserBasicInfo(
    val id: Int,
    @ColumnInfo(name = "user_name")
    val name: String,
    val dependentsCount: Int
)

