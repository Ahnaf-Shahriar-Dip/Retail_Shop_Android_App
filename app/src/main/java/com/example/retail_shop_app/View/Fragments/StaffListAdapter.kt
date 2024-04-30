


package com.example.retail_shop_app.View.Fragments

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.retail_shop_app.Model.Entity.Product
import com.example.retail_shop_app.Model.Entity.Customer
import com.example.retail_shop_app.Model.Entity.Staff
import com.example.retail_shop_app.R
import android.content.Context
import android.graphics.Color // Import the Color class









class StaffListAdapter(private val context: Context) : ListAdapter<Staff, StaffListAdapter.StaffViewHolder>(StaffsComparator()) {

    interface OnItemLongClickListener {
        fun onItemLongClick(staff: Staff): Boolean
    }

    private var longClickListener: OnItemLongClickListener? = null
    private var selectedStaff: Staff? = null

    fun setOnItemLongClickListener(listener: OnItemLongClickListener) {
        longClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StaffViewHolder {
        return StaffViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: StaffViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current.staffName, current.staffAddress, current.staffPhone, current.staffSalary.toString())
        // Selection Logic
        val isSelected = selectedStaff == current
        holder.itemView.setBackgroundColor(
            if (isSelected) Color.RED
            else Color.WHITE
        )

        holder.itemView.setOnLongClickListener {
            selectedStaff = if (isSelected) null else current
            notifyDataSetChanged()
            longClickListener?.onItemLongClick(current) ?: false
        }
    }

    class StaffViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val staffItemViewName: TextView = itemView.findViewById(R.id.textViewStaffName)
        private val staffItemViewAddress: TextView = itemView.findViewById(R.id.textViewStaffAddress)
        private val staffItemViewPhone: TextView = itemView.findViewById(R.id.textViewStaffPhone)
        private val staffItemViewSalary: TextView = itemView.findViewById(R.id.textViewStaffSalary)

         fun bind(staffName: String, staffAddress: String, staffPhone: String,staffSalary:String){
            staffItemViewName.text = staffName
            staffItemViewAddress.text = staffAddress
            staffItemViewPhone.text = staffPhone
            staffItemViewSalary.text = staffSalary
        }

        companion object {
            fun create(parent: ViewGroup): StaffViewHolder {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.staff_list_item, parent, false)
                return StaffViewHolder(view)
            }
        }
    }

    class StaffsComparator : DiffUtil.ItemCallback<Staff>() {
        override fun areItemsTheSame(oldItem: Staff, newItem: Staff): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Staff, newItem: Staff): Boolean {
            return oldItem.staffId == newItem.staffId
        }
    }

    fun getSelectedStaff(): Staff? {
        return selectedStaff
    }

    fun clearSelection() {
        selectedStaff = null
        notifyDataSetChanged()
    }
}


//--