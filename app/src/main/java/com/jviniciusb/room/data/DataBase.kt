package com.jviniciusb.room.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.jviniciusb.room.data.Migrations.MIGRATION_1_2
import com.jviniciusb.room.data.dao.DependentDao
import com.jviniciusb.room.data.dao.UserDao
import com.jviniciusb.room.data.entity.Dependent
import com.jviniciusb.room.data.entity.NewEntity
import com.jviniciusb.room.data.entity.User
import com.jviniciusb.room.data.entity.UserBasicInfo

/*
* - Abstraction layer over SQLite.
*   - Compile-time verification of SQL queries.
*   - Convenience annotations that minimize repetitive and error-prone boilerplate code.
*   - Streamlined database migration paths.
*/

@Database(
    entities = [User::class, Dependent::class, NewEntity::class],
    version = 2,
    views = [UserBasicInfo::class],
    exportSchema = true
)
@TypeConverters(DateConverter::class)
abstract class DataBase : RoomDatabase() {

    abstract fun userDao(): UserDao

    abstract fun dependentDao(): DependentDao

    companion object {
        private var INSTANCE: DataBase? = null

        fun getAppDataBase(context: Context): DataBase? {
            if (INSTANCE == null) {
                synchronized(DataBase::class) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        DataBase::class.java,
                        "db_name"
                    )
                        .createFromAsset("prepopulated_db.db")
                        .addMigrations(MIGRATION_1_2)
                        .build()
                }
            }
            return INSTANCE
        }

        fun destroyDataBase() {
            INSTANCE = null
        }
    }
}

object Migrations {

    val MIGRATION_1_2: Migration = object : Migration(1, 2) {
        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL(
                "CREATE TABLE 'NewEntity' ('id' INTEGER NOT NULL, "
                        + "'value' TEXT NOT NULL, PRIMARY KEY('id'))"
            )
        }
    }
}
