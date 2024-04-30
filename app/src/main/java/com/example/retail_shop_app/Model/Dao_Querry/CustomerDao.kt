
package com.example.retail_shop_app.Model.Dao_Querry

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.retail_shop_app.Model.Entity.Customer



@Dao
interface CustomerDao{

    @Insert(onConflict = OnConflictStrategy.IGNORE) // Optional: Specify conflict strategy
    suspend fun insertCustomer(customer: Customer)

    @Update
    suspend fun updateCustomer(customer: Customer)

    @Delete
    suspend fun deleteCustomer(customer: Customer)

    @Query("SELECT * FROM customer")
    fun getAllCustomers(): LiveData<List<Customer>>

    @Query("SELECT * FROM customer WHERE customerName LIKE :customerName")
    fun searchCustomerByName(customerName: String): LiveData<List<Customer>>

}
