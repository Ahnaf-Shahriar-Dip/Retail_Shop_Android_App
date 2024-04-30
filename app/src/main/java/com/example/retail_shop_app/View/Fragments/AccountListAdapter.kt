package com.example.retail_shop_app.View.Fragments
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.retail_shop_app.Model.Entity.Pos
import com.example.retail_shop_app.R
import android.content.Context
import android.graphics.Color // Import the Color class



class AccountListAdapter(private val context: Context) : ListAdapter<Pos, AccountListAdapter.AccountViewHolder>(AccountComparator()) {




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AccountViewHolder {
        return AccountViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: AccountViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current.invoiceNumber.toString(), current.customerName.toString(), current.productName.toString(), current.quantity.toString(), current.subtotal.toString()    )




    }

    class AccountViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val Invoice_ID: TextView = itemView.findViewById(R.id.textViewInvoiceId)
        private val customers_name: TextView = itemView.findViewById(R.id.textViewCustomerNamess)
        private val productName: TextView = itemView.findViewById(R.id.textViewProductNamess)
        private val P_quantity: TextView = itemView.findViewById(R.id.textViewProductQuantitys)
        private val P_subtotal: TextView = itemView.findViewById(R.id.textViewSubtotalls)

        fun bind(Invoice: String, customer_name: String, product_name: String,quantity:String,subtotal:String) {
            Invoice_ID.text = Invoice
            customers_name.text = customer_name
            productName.text = product_name
            P_quantity.text=quantity
            P_subtotal.text=subtotal
        }

        companion object {
            fun create(parent: ViewGroup): AccountViewHolder {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.account_list_item, parent, false)
                return AccountViewHolder(view)
            }
        }
    }

    class AccountComparator : DiffUtil.ItemCallback<Pos>() {
        override fun areItemsTheSame(oldItem: Pos, newItem: Pos): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Pos, newItem: Pos): Boolean {
            return oldItem.productId == newItem.productId
        }
    }


}




