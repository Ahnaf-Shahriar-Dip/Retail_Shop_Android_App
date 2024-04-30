package com.example.retail_shop_app.Model.Repository

import androidx.lifecycle.LiveData
import com.example.retail_shop_app.Model.Dao_Querry.InvoiceDao
import com.example.retail_shop_app.Model.Entity.Invoice
import com.example.retail_shop_app.Model.Entity.CustomerDueAmount

import java.text.SimpleDateFormat
import java.util.*

class InvoiceRepository(private val invoiceDao: InvoiceDao) {

    val allInvoices: LiveData<List<Invoice>> = invoiceDao.getAllInvoices()

    suspend fun insertInvoice(invoice: List<Invoice>) {
        invoiceDao.insertInvoice(invoice)
    }

    fun getTotalSellsForDate(date: String): LiveData<Double> {
        return invoiceDao.getTotalSellsForDate(date)
    }

    fun getTotalProfitForDate(date: String): LiveData<Double> {
        return invoiceDao.getTotalProfitForDate(date)
    }

    fun getTotalDueForDate(date: String): LiveData<Double> {
        return invoiceDao.getTotalDueForDate(date)
    }


    fun getTotalSells(): LiveData<Double> {
        return invoiceDao.getTotalSells()
    }

    fun getTotalProfit(): LiveData<Double> {
        return invoiceDao.getTotalProfit()
    }



    fun getTotalDueAmountByCustomer(): LiveData<List<CustomerDueAmount>> {
        return invoiceDao.getTotalDueAmountByCustomer()
    }












    // You can add more functions here as needed for retrieving, updating, or deleting invoices.
}