package com.example.retail_shop_app.Model.Dao_Querry

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.retail_shop_app.Model.Entity.Pos

@Dao
interface PosDao {
    @Insert
    fun insertPos(pos: List<Pos>)

    @Query("SELECT * FROM product_order")
    fun getAllOrders(): LiveData<List<Pos>>




}
