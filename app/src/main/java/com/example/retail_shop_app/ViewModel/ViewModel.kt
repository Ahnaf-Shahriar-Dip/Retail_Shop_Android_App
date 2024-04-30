package com.example.retail_shop_app.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.retail_shop_app.Model.Entity.Product
import com.example.retail_shop_app.Model.Database.UserDatabase
import com.example.retail_shop_app.Model.Entity.Customer
import com.example.retail_shop_app.Model.Entity.Staff
import com.example.retail_shop_app.Model.Entity.Pos
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.example.retail_shop_app.Model.Repository.ProductRepository // Renamed for consistency
import com.example.retail_shop_app.Model.Repository.CustomerRepository // Renamed for consistency
import com.example.retail_shop_app.Model.Repository.StaffRepository
import com.example.retail_shop_app.Model.Repository.PosRepository

import com.example.retail_shop_app.Model.Entity.Invoice // Import Invoice entity
import com.example.retail_shop_app.Model.Entity.CustomerDueAmount
import com.example.retail_shop_app.Model.Repository.InvoiceRepository // Import Invoice repository
import java.text.SimpleDateFormat
import java.util.*




class ViewModel(application: Application) : AndroidViewModel(application) {

    val allProducts: LiveData<List<Product>>
    private val repository: ProductRepository

    val allCustomers: LiveData<List<Customer>>
    private val repository_c: CustomerRepository

    val allStaff: LiveData<List<Staff>> // New LiveData for Staff
    private val repository_s: StaffRepository // New repository for Staff

    val allOrders: LiveData<List<Pos>> // LiveData for Orders
    private val repository_o: PosRepository // Repository for Orders


    val allInvoices: LiveData<List<Invoice>> // LiveData for Invoices
    private val repository_i: InvoiceRepository // Repository for Invoices




    init {
        val productDao = UserDatabase.getDatabase(application).productDao()
        repository = ProductRepository(productDao)
        allProducts = repository.getAllProducts

        val customerDao = UserDatabase.getDatabase(application).customerDao()
        repository_c = CustomerRepository(customerDao)
        allCustomers = repository_c.getAllCustomer

        val staffDao = UserDatabase.getDatabase(application).staffDao() // Initialize Staff DAO
        repository_s = StaffRepository(staffDao) // Initialize Staff Repository
        allStaff = repository_s.getAllStaff // Initialize LiveData for Staff

        val posDao = UserDatabase.getDatabase(application).posDao() // Initialize Pos DAO
        repository_o = PosRepository(posDao) // Initialize Pos Repository
        allOrders = repository_o.allOrders // Initialize LiveData for Orders


        val invoiceDao = UserDatabase.getDatabase(application).invoiceDao() // Initialize Invoice DAO
        repository_i = InvoiceRepository(invoiceDao) // Initialize Invoice Repository
        allInvoices = repository_i.allInvoices // Initialize LiveData for Invoices


    }












    //order

    fun addOrder(pos: List<Pos>) {
        viewModelScope.launch(Dispatchers.IO) {
            repository_o.insertPos(pos)
        }
    }

    //Invoice


    fun addInvoice(invoice: List<Invoice>) {
        viewModelScope.launch(Dispatchers.IO) {
            repository_i.insertInvoice(invoice)
        }
    }

    fun getTotalSellsForDate(date: String): LiveData<Double> {
        return repository_i.getTotalSellsForDate(date)
    }

    fun getTotalProfitForDate(date: String): LiveData<Double> {
        return repository_i.getTotalProfitForDate(date)
    }

    fun getTotalDueForDate(date: String): LiveData<Double> {
        return repository_i.getTotalDueForDate(date)
    }

    fun getTotalSells(): LiveData<Double> {
        return repository_i.getTotalSells()
    }

    fun getTotalProfit(): LiveData<Double> {
        return repository_i.getTotalProfit()
    }

    fun getTotalDueAmountByCustomer(): LiveData<List<CustomerDueAmount>> {
        return repository_i.getTotalDueAmountByCustomer()
    }














    //Product



    fun searchProductByName(productName: String): LiveData<List<Product>> {
        return repository.searchProductByName(productName)
    }






    fun addProduct(product: Product) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertProduct(product)
        }
    }

    fun deleteProduct(product: Product) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteProduct(product)
        }
    }


    fun updateProduct(product: Product) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateProduct(product)
        }
    }

    fun updateProductQuantity(productId: Int, newQuantity: Int) {
        viewModelScope.launch {
            repository.updateProductQuantity(productId, newQuantity)
        }
    }















    fun getProductById(productId: Int): LiveData<Product?> {
        return repository.getProductById(productId)
    }


    fun getTop5ProductsByQuantity(): LiveData<List<Product>> {
        return repository.getTop5ProductsByQuantity()
    }






    //customer
    fun addCustomer(customer: Customer) {
        viewModelScope.launch(Dispatchers.IO) {
            repository_c.insertCustomer(customer)
        }
    }

    fun deleteCustomer(customer: Customer) {
        viewModelScope.launch(Dispatchers.IO) {
            repository_c.deleteCustomer(customer)
        }
    }

    fun updateCustomer(customer: Customer) {
        viewModelScope.launch(Dispatchers.IO) {
            repository_c.updateCustomer(customer)
        }
    }

    // Functions for Staff
    fun addStaff(staff: Staff) {
        viewModelScope.launch(Dispatchers.IO) {
            repository_s.insertStaff(staff)
        }
    }

    fun deleteStaff(staff: Staff) {
        viewModelScope.launch(Dispatchers.IO) {
            repository_s.deleteStaff(staff)
        }
    }

    fun updateStaff(staff: Staff) {
        viewModelScope.launch(Dispatchers.IO) {
            repository_s.updateStaff(staff)
        }
    }




    fun searchCustomerByName(customerName: String): LiveData<List<Customer>> {
        return repository_c.searchCustomerByName(customerName)
    }


}