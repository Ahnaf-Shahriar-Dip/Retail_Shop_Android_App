package com.example.retail_shop_app.Model.Database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.retail_shop_app.Model.Dao_Querry.ProductDao
import com.example.retail_shop_app.Model.Entity.Product

import com.example.retail_shop_app.Model.Dao_Querry.CustomerDao
import com.example.retail_shop_app.Model.Dao_Querry.StaffDao
import com.example.retail_shop_app.Model.Entity.Customer
import com.example.retail_shop_app.Model.Entity.Staff

import com.example.retail_shop_app.Model.Dao_Querry.PosDao
import com.example.retail_shop_app.Model.Entity.Pos

import com.example.retail_shop_app.Model.Dao_Querry.InvoiceDao // Add this
import com.example.retail_shop_app.Model.Entity.Invoice // Add this


@Database(entities = [Product::class, Customer::class, Staff::class, Pos::class,Invoice::class], version = 19, exportSchema = false)
abstract class UserDatabase : RoomDatabase() {

    abstract fun productDao(): ProductDao
    abstract fun customerDao(): CustomerDao
    abstract fun staffDao(): StaffDao
    abstract fun posDao(): PosDao // Add PosDao
    abstract fun invoiceDao(): InvoiceDao // Add this

    companion object {
        @Volatile
        private var INSTANCE: UserDatabase? = null

        fun getDatabase(context: Context): UserDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    UserDatabase::class.java,
                    "user_database"
                )
                    // Add migration strategy here if needed
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }

    }
}