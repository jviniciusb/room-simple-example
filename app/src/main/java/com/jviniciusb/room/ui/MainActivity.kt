package com.jviniciusb.room.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.jviniciusb.room.R
import com.jviniciusb.room.data.DataBase
import com.jviniciusb.room.data.entity.Address
import com.jviniciusb.room.data.entity.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private val db by lazy {
        DataBase.getAppDataBase(context = applicationContext)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
    }

    private fun init() {
        executeDbAction { accessDb() }
    }

    private fun executeDbAction(action: () -> (Unit)) {
        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                action.invoke()
            }
        }
    }

    private fun accessDb() {
        db?.userDao()?.findUsersAndDependents()
    }

    private fun createPrepopulatedDb() {
        val newUser =
            User(
                name = "Jader",
                address = Address(
                    country = "Brazil",
                    address = "Belo Horizonte",
                    city = "Belo Horizonte",
                    zipCode = "00000-000"
                ),
                birth = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).parse("23/01/1994")
                    ?: Date()
            )
        db?.userDao()?.insert(newUser)
/*        db?.dependentDao()?.insert(
            Dependent(parentId = 1, name = "Jadeilson", birth = Date()),
            Dependent(parentId = 1, name = "Jadcleydson", birth = Date()),
            Dependent(parentId = 1, name = "Jadnelson", birth = Date())
        )*/
    }
}