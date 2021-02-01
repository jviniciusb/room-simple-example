package com.jviniciusb.room.data.dao

import androidx.lifecycle.LiveData
import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.jviniciusb.room.data.entity.Address
import com.jviniciusb.room.data.entity.User
import com.jviniciusb.room.data.entity.UserAndDependents
import com.jviniciusb.room.data.entity.UserBasicInfo
import kotlinx.coroutines.flow.Flow
import java.util.*

@Dao
interface UserDao : BaseDao<User> {

    @Query("SELECT * FROM user WHERE id = :userId")
    fun findUser(userId: String): User

    // Room automatically maps the columns info to the object properties
    @Query("SELECT country, address, city, zipCode FROM user WHERE id = :userId")
    fun findUserAddress(userId: Int): LiveData<Address> // Observable query using LiveData

    @Query("SELECT country, address, city, zipCode FROM user WHERE id IN (:userIds)")
    fun findUsersAddresses(userIds: List<Int>): Flow<List<Address>> // Observable query using Flow

    // Queries can be performed in multiple tables
    @Query(
        "SELECT * FROM user " +
                "INNER JOIN dependent ON dependent.parentId = user.id " +
                "WHERE dependent.birth > :birth"
    )
    fun findUsersThatHasDependentsBirthAfter(birth: Date): PagingSource<Int, User> // *** Needs Room >= 2.3.0-alpha01 ***

    @Transaction
    @Query("SELECT * FROM user")
    fun findUsersAndDependents(): List<UserAndDependents>

    @Query("SELECT * FROM userbasicinfo WHERE id = :userId") // Views accept only select statements
    fun findUserBasicInfo(userId: String): UserBasicInfo
}