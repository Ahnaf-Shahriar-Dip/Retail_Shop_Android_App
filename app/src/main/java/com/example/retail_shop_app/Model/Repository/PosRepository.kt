package com.example.retail_shop_app.Model.Repository

import androidx.lifecycle.LiveData
import com.example.retail_shop_app.Model.Dao_Querry.PosDao
import com.example.retail_shop_app.Model.Entity.Pos

class PosRepository(private val posDao: PosDao) {

    val allOrders: LiveData<List<Pos>> = posDao.getAllOrders()

    suspend fun insertPos(pos: List<Pos>) {
        posDao.insertPos(pos)
    }



}



