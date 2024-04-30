package com.example.retail_shop_app.View.Fragments

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.retail_shop_app.Model.Entity.Invoice
import com.example.retail_shop_app.R







class PaymentListAdapter(private val context: Context) : ListAdapter<Invoice, PaymentListAdapter.PaymentViewHolder>(PaymentComparator()) {




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PaymentViewHolder {
        return PaymentViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: PaymentViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current.invoiceNumber.toString(), current.customerName.toString(), current.paymentAmount.toString(), current.dueAmount.toString(), current.paymentMethod.toString()    )




    }

    class PaymentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val Invoice_ID: TextView = itemView.findViewById(R.id.textViewInvoiceId_p)
        private val customers_name: TextView = itemView.findViewById(R.id.textViewCustomerName_p)
        private val payment_amount: TextView = itemView.findViewById(R.id.textViewPaymentAmount_p)
        private val Due_Amount: TextView = itemView.findViewById(R.id.textViewDue_Amount_p)
        private val Payment_Amount: TextView = itemView.findViewById(R.id.textViewPayment_Method_P)


        fun bind(Invoice: String, customer_name: String, payment_amountww: String,Due_Amountaa:String,Payment_Amountaa:String) {
            Invoice_ID.text = Invoice
            customers_name.text = customer_name
            payment_amount.text = payment_amountww
            Due_Amount.text=Due_Amountaa
            Payment_Amount.text=Payment_Amountaa
        }

        companion object {
            fun create(parent: ViewGroup): PaymentViewHolder {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.payment_list_item, parent, false)
                return PaymentViewHolder(view)
            }
        }
    }

    class PaymentComparator : DiffUtil.ItemCallback<Invoice>() {
        override fun areItemsTheSame(oldItem: Invoice, newItem: Invoice): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Invoice, newItem: Invoice): Boolean {
            return oldItem.invoiceNumber == newItem.invoiceNumber
        }
    }


}



