package com.example.retail_shop_app.Model.Dao_Querry

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.retail_shop_app.Model.Entity.Invoice
import com.example.retail_shop_app.Model.Entity.CustomerDueAmount

@Dao
interface InvoiceDao {
    @Insert
    fun insertInvoice(invoice: List<Invoice>)

    @Query("SELECT * FROM invoice")
    fun getAllInvoices(): LiveData<List<Invoice>>



    @Query("SELECT SUM(total) FROM invoice WHERE DATE(date) = :date")
    fun getTotalSellsForDate(date: String): LiveData<Double>

    @Query("SELECT SUM(profit) FROM invoice WHERE DATE(date) = :date")
    fun getTotalProfitForDate(date: String): LiveData<Double>




    @Query("SELECT SUM(dueAmount) FROM invoice WHERE DATE(date) = :date")
    fun getTotalDueForDate(date: String): LiveData<Double>



    @Query("SELECT SUM(total) FROM invoice")
    fun getTotalSells(): LiveData<Double>

    @Query("SELECT SUM(profit) FROM invoice")
    fun getTotalProfit(): LiveData<Double>


    @Query("SELECT customerName, SUM(dueAmount) AS totalDueAmount FROM invoice GROUP BY customerId ORDER BY totalDueAmount DESC LIMIT 5")
    fun getTotalDueAmountByCustomer(): LiveData<List<CustomerDueAmount>>






}