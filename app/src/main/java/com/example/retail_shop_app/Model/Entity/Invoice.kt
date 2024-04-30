package com.example.retail_shop_app.Model.Entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "invoice",
    foreignKeys = [
        ForeignKey(entity = Customer::class,
            parentColumns = ["customerId"],
            childColumns = ["customerId"],
            onDelete = ForeignKey.CASCADE)
    ])
data class Invoice(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0, // Room will autogenerate ID
    val invoiceNumber: String? = null,
    val customerId: Int? = null,
    val shippingCost: Double? = null,
    val labourCost: Double? = null,
    var total: Double? = null,
    var profit: Double? = null,
    val date: String? = null,

    val paymentAmount: Double? = null,
    val dueAmount: Double? = null,
    val changeAmount: Double? = null,
    val paymentMethod: String? = null,
    var customerName: String? = null // Add customer name field



)
