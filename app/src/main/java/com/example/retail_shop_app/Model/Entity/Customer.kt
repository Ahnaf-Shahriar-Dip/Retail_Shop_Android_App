package com.example.retail_shop_app.Model.Entity


import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "customer")
data class Customer(
    @PrimaryKey(autoGenerate = true)
    val customerId: Int = 0, // Room will autogenerate ID
    val customerName: String,
    val customerAddress: String,
    val customerPhone: String


)
