package com.example.retail_shop_app.Model.Dao_Querry

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.retail_shop_app.Model.Entity.Product



@Dao
interface ProductDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE) // Optional: Specify conflict strategy
    suspend fun insertProduct(product: Product)

    @Update
    suspend fun updateProduct(product: Product)

    @Delete
    suspend fun deleteProduct(product: Product)

    @Query("SELECT * FROM product")
    fun getAllProducts(): LiveData<List<Product>>

    @Query("SELECT * FROM product WHERE productName LIKE :searchQuery")
    fun searchProductByName(searchQuery: String): LiveData<List<Product>>


    @Query("SELECT * FROM product WHERE productId = :productId")
    fun getProductById(productId: Int): LiveData<Product?>



    @Query("SELECT * FROM product ORDER BY productQuantity ASC LIMIT 5")
    fun getTop5ProductsByQuantity(): LiveData<List<Product>>

    @Query("UPDATE product SET productQuantity = :newQuantity WHERE productId = :productId")
    suspend fun updateProductQuantity(productId: Int, newQuantity: Int)





}
