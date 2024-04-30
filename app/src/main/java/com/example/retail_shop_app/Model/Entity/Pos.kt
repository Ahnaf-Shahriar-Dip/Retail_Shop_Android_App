package com.example.retail_shop_app.Model.Entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "product_order", foreignKeys = [
    ForeignKey(entity = Product::class,
        parentColumns = ["productId"],
        childColumns = ["productId"],
        onDelete = ForeignKey.CASCADE),
    ForeignKey(entity = Customer::class,
        parentColumns = ["customerId"],
        childColumns = ["customerId"],
        onDelete = ForeignKey.CASCADE)
])


data class Pos(
    @PrimaryKey(autoGenerate = true)
    val orderId: Int = 0,
    val productId: Int? = null,
    val productName: String? = null,
    val customerId: Int? = null,
    val customerName: String? = null,
    val price: Double? = null,
    val quantity: Int? = null,
    val subtotal: Double? = null,
    val profit: Double? = null,
    val invoiceNumber: String? = null,
    val date: String? = null
)