package com.jviniciusb.room.data.dao

import androidx.room.*

@Dao
interface BaseDao<T> {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg entity: T)

    @Update
    fun update(vararg entity: T)

    @Delete
    fun delete(vararg entity: T)
}