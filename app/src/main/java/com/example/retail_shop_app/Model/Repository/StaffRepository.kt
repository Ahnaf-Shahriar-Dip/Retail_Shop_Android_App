package com.example.retail_shop_app.Model.Repository

import androidx.lifecycle.LiveData
import com.example.retail_shop_app.Model.Dao_Querry.StaffDao
import com.example.retail_shop_app.Model.Entity.Staff

class StaffRepository(private val staffDao: StaffDao) {

    val getAllStaff: LiveData<List<Staff>> = staffDao.getAllStaff()

    suspend fun insertStaff(staff: Staff) {
        staffDao.insertStaff(staff)
    }

    suspend fun deleteStaff(staff: Staff) {
        staffDao.deleteStaff(staff)
    }

    suspend fun updateStaff(staff: Staff) {
        staffDao.updateStaff(staff)
    }
}
