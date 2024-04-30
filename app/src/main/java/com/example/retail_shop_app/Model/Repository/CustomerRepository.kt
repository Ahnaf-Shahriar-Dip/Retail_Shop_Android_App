package com.example.retail_shop_app.Model.Repository





import androidx.lifecycle.LiveData
import com.example.retail_shop_app.Model.Dao_Querry.CustomerDao

import com.example.retail_shop_app.Model.Entity.Customer
import com.example.retail_shop_app.Model.Entity.Product


class CustomerRepository(private val customerDao: CustomerDao) { // Renamed for clarity



    val getAllCustomer: LiveData<List<Customer>> = customerDao.getAllCustomers()

    suspend fun insertCustomer(customer: Customer) {
        customerDao.insertCustomer(customer)
    }


    suspend fun deleteCustomer(customer: Customer) {
        customerDao.deleteCustomer(customer)
    }

    suspend fun updateCustomer(customer: Customer) {
        customerDao.updateCustomer(customer)
    }

    fun searchCustomerByName(customerName: String): LiveData<List<Customer>> {
        return customerDao.searchCustomerByName("%$customerName%")
    }




}

