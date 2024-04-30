package com.example.retail_shop_app.Model.Entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "product")
data class Product(
    @PrimaryKey(autoGenerate = true)
    val productId: Int = 0, // Room will autogenerate ID
    val productName: String,
    var productQuantity: Int,
    val productPrice: Double,
    val productSellingPrice: Double

)
