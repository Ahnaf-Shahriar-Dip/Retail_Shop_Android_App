package com.example.retail_shop_app.Model.Entity





import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "staff")
data class Staff(
    @PrimaryKey(autoGenerate = true)
    val staffId: Int = 0, // Room will autogenerate ID
    val staffName: String,
    val staffAddress: String,
    val staffPhone: String,
    val staffSalary: Double


)
