package com.example.retail_shop_app.Model.Repository

import androidx.lifecycle.LiveData
import androidx.room.Query

import com.example.retail_shop_app.Model.Dao_Querry.ProductDao
import com.example.retail_shop_app.Model.Entity.Customer
import com.example.retail_shop_app.Model.Entity.Product




class ProductRepository(private val productDao: ProductDao) { // Renamed for clarity

    val getAllProducts: LiveData<List<Product>> = productDao.getAllProducts()

    suspend fun insertProduct(product: Product) {
        productDao.insertProduct(product)
    }

    suspend fun deleteProduct(product: Product) {
        productDao.deleteProduct(product)
    }

    suspend fun updateProduct(product: Product) {
        productDao.updateProduct(product)
    }


    fun searchProductByName(productName: String): LiveData<List<Product>> {
        return productDao.searchProductByName("%$productName%")
    }

    fun getProductById(productId: Int): LiveData<Product?> {
        return productDao.getProductById(productId)
    }


    fun getTop5ProductsByQuantity(): LiveData<List<Product>> {
        return productDao.getTop5ProductsByQuantity()
    }

    suspend fun updateProductQuantity(productId: Int, newQuantity: Int) {
        productDao.updateProductQuantity(productId, newQuantity)
    }



}


