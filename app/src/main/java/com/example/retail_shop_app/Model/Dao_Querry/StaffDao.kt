
package com.example.retail_shop_app.Model.Dao_Querry


import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.retail_shop_app.Model.Entity.Staff // Changed import statement


@Dao
interface StaffDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE) // Optional: Specify conflict strategy
    suspend fun insertStaff(staff: Staff) // Changed function name and parameter

    @Update
    suspend fun updateStaff(staff: Staff) // Changed function name and parameter

    @Delete
    suspend fun deleteStaff(staff: Staff) // Changed function name and parameter

    @Query("SELECT * FROM staff") // Changed table name
    fun getAllStaff(): LiveData<List<Staff>> // Changed return type and generic type

}
