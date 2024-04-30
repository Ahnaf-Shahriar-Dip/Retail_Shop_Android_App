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
import com.example.retail_shop_app.R
import android.content.Context


import android.graphics.Color // Import the Color class




class CustomerListAdapter(private val context: Context) : ListAdapter<Customer, CustomerListAdapter.CustomerViewHolder>(CustomersComparator()) {

    interface OnItemLongClickListener {
        fun onItemLongClick(customer: Customer): Boolean
    }

    private var longClickListener: OnItemLongClickListener? = null
    private var selectedCustomer: Customer? = null

    fun setOnItemLongClickListener(listener: OnItemLongClickListener) {
        longClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomerViewHolder {
        return CustomerViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: CustomerViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current.customerName, current.customerAddress, current.customerPhone.toString())

        // Selection Logic
        val isSelected = selectedCustomer == current
        holder.itemView.setBackgroundColor(
            if (isSelected) Color.RED
            else Color.WHITE
        )

        holder.itemView.setOnLongClickListener {
            selectedCustomer = if (isSelected) null else current
            notifyDataSetChanged()
            longClickListener?.onItemLongClick(current) ?: false
        }
    }

    class CustomerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val customerItemViewName: TextView = itemView.findViewById(R.id.textViewCustomerName)
        private val customerItemViewAddress: TextView = itemView.findViewById(R.id.textViewCustomerAddress)
        private val customerItemViewPhone: TextView = itemView.findViewById(R.id.textViewCustomerPhone)


        fun bind(name: String, address: String, phone: String) {
            customerItemViewName.text = name
            customerItemViewAddress.text = address
            customerItemViewPhone.text = phone

        }

        companion object {
            fun create(parent: ViewGroup): CustomerViewHolder {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.customer_list_item, parent, false)
                return CustomerViewHolder(view)
            }
        }
    }

    class CustomersComparator : DiffUtil.ItemCallback<Customer>() {
        override fun areItemsTheSame(oldItem: Customer, newItem: Customer): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Customer, newItem: Customer): Boolean {
            return oldItem.customerId == newItem.customerId
        }
    }

    fun getSelectedCustomer(): Customer? {
        return selectedCustomer
    }

    fun clearSelection() {
        selectedCustomer = null
        notifyDataSetChanged()
    }
}



//worked 400%
