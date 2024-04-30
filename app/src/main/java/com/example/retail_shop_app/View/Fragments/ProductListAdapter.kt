package com.example.retail_shop_app.View.Fragments



import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.retail_shop_app.Model.Entity.Product
import com.example.retail_shop_app.R
import android.content.Context


import android.graphics.Color // Import the Color class




class ProductListAdapter(private val context: Context) : ListAdapter<Product, ProductListAdapter.ProductViewHolder>(ProductsComparator()) {

    interface OnItemLongClickListener {
        fun onItemLongClick(product: Product): Boolean
    }

    private var longClickListener: OnItemLongClickListener? = null
    private var selectedProduct: Product? = null

    fun setOnItemLongClickListener(listener: OnItemLongClickListener) {
        longClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        return ProductViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current.productName, current.productQuantity.toString(), current.productPrice.toString(), current.productSellingPrice.toString())

        // Selection Logic
        val isSelected = selectedProduct == current
        holder.itemView.setBackgroundColor(
            if (isSelected) Color.RED
            else Color.WHITE
        )

        holder.itemView.setOnLongClickListener {
            selectedProduct = if (isSelected) null else current
            notifyDataSetChanged()
            longClickListener?.onItemLongClick(current) ?: false
        }
    }

    class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val productItemViewName: TextView = itemView.findViewById(R.id.textViewProductName)
        private val productItemViewQuantity: TextView = itemView.findViewById(R.id.textViewProductQuantity)
        private val productItemViewPrice: TextView = itemView.findViewById(R.id.textViewProductPrice)
        private val productItemViewSellingPrice: TextView = itemView.findViewById(R.id.textViewedProductPriceWorth)

        fun bind(name: String, quantity: String, price: String,selling_price:String) {
            productItemViewName.text = name
            productItemViewQuantity.text = quantity
            productItemViewPrice.text = price
            productItemViewSellingPrice.text=selling_price
        }

        companion object {
            fun create(parent: ViewGroup): ProductViewHolder {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.product_list_item, parent, false)
                return ProductViewHolder(view)
            }
        }
    }

    class ProductsComparator : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.productId == newItem.productId
        }
    }

    fun getSelectedProduct(): Product? {
        return selectedProduct
    }

    fun clearSelection() {
        selectedProduct = null
        notifyDataSetChanged()
    }
}



//worked 400%
