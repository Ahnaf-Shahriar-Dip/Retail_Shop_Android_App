package com.example.retail_shop_app.View.Fragments

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.retail_shop_app.R

import android.widget.Toast


import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.retail_shop_app.Model.Entity.Customer
import com.example.retail_shop_app.Model.Entity.Product
import com.example.retail_shop_app.Model.Entity.Pos

import com.example.retail_shop_app.Model.Entity.Invoice
import com.example.retail_shop_app.ViewModel.ViewModel


import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

import android.text.TextWatcher

import android.text.Editable
import android.util.Log
import androidx.annotation.RequiresApi
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter






import android.widget.ArrayAdapter
import android.widget.Spinner



class Pos_Fragment : Fragment() {

    private lateinit var viewModel: ViewModel
    private lateinit var editTextCustomerSearch: EditText
    private lateinit var layoutSelectedCustomerRow: LinearLayout
    private lateinit var editTextProductSearch: EditText
    private lateinit var layoutSelectedProductRow: LinearLayout

    private lateinit var editTextShippingCost: EditText
    private lateinit var editTextLaborCost: EditText
    private lateinit var textViewFinalTotal: TextView // Add this


    private lateinit var editTextTotalPaid: EditText
    private lateinit var editTextTotalDue: EditText

    private lateinit var textViewChangeAmount: TextView






    private val selectedProducts = mutableMapOf<Int, SelectedProduct>()

    private var selectedCustomerId = 0
    private var selectedCustomerName = ""

    private lateinit var spinnerPaymentMethod: Spinner




    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_pos, container, false)


        // Initialize the spinner
        spinnerPaymentMethod = view.findViewById(R.id.spinnerPaymentMethod)

        // Initialize the adapter
        val paymentMethods = listOf("Bkash", "Cash") // Example payment methods
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, paymentMethods)

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        // Apply the adapter to the spinner
        spinnerPaymentMethod.adapter = adapter


        editTextShippingCost = view.findViewById<EditText>(R.id.editTextShippingCost)
        editTextLaborCost = view.findViewById<EditText>(R.id.editTextLaborCost)
        textViewFinalTotal = view.findViewById<TextView>(R.id.textViewFinalTotal) // Initialize the final total EditText



        editTextTotalPaid = view.findViewById<EditText>(R.id.editTextTotalPaid)

        editTextTotalDue = view.findViewById<EditText>(R.id.editTextTotalDue)
        textViewChangeAmount = view.findViewById<TextView>(R.id.textViewChangeAmount)


        viewModel = ViewModelProvider(this)[ViewModel::class.java]

        editTextCustomerSearch = view.findViewById(R.id.editTextCustomerSearch)
        layoutSelectedCustomerRow = view.findViewById(R.id.layoutSelectedCustomerRow)
        editTextProductSearch = view.findViewById(R.id.editTextProductSearch)
        layoutSelectedProductRow = view.findViewById(R.id.layoutSelectedProductRowss)

        view.findViewById<Button>(R.id.buttonSearchCustomer).setOnClickListener {
            searchCustomer()
        }
        view.findViewById<Button>(R.id.buttonSearchProduct).setOnClickListener {
            searchProduct()
        }
        view.findViewById<Button>(R.id.buttonSubmit).setOnClickListener {
            saveOrder()
        }


        // Set text change listeners for shipping and labor cost EditTexts
        editTextShippingCost.addTextChangedListener(textChangeListener)
        editTextLaborCost.addTextChangedListener(textChangeListener)


        editTextTotalPaid.addTextChangedListener(totalPaidTextChangeListener)

        return view
    }


    // Text change listener for total paid amount
    private val totalPaidTextChangeListener = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

        override fun afterTextChanged(s: Editable?) {
            // Calculate due amount and change amount
            calculateDueAndChange()
        }
    }

    // Method to calculate due amount and change amount
    private fun calculateDueAndChange() {
        // Retrieve values from EditTexts
        val totalPaid = editTextTotalPaid.text.toString().toDoubleOrNull() ?: 0.0
        val finalTotal = textViewFinalTotal.text.toString().toDoubleOrNull() ?: 0.0

        // Calculate due amount
        val dueAmount = if (finalTotal > totalPaid) finalTotal - totalPaid else 0.0

        // Calculate change amount
        val changeAmount = if (totalPaid >= finalTotal) totalPaid - finalTotal else 0.0

        // Display due amount and change amount
        editTextTotalDue.setText(dueAmount.toString())
        textViewChangeAmount.text = changeAmount.toString()
    }


    // Text change listener to update final total whenever shipping or labor cost changes
    private val textChangeListener = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

        override fun afterTextChanged(s: Editable?) {
            updateFinalTotal() // Calculate and update final total
            calculateDueAndChange()
        }
    }




    // Method to update the final total based on subtotal, shipping, and labor cost
    private fun updateFinalTotal() {
        // Retrieve values from EditTexts
        val shippingCost = editTextShippingCost.text.toString().toDoubleOrNull() ?: 0.0
        val laborCost = editTextLaborCost.text.toString().toDoubleOrNull() ?: 0.0

        // Calculate subtotal from selected products (assuming it's stored in selectedProducts)
        val subtotal = selectedProducts.values.sumByDouble { it.price * it.quantity }

        // Calculate final total
        val finalTotal = subtotal + shippingCost + laborCost

        // Display final total in the EditText
        textViewFinalTotal.setText(finalTotal.toString())
    }








    private fun searchCustomer() {
        val customerName = editTextCustomerSearch.text.toString().trim()

        if (customerName.isNotEmpty()) {
            viewModel.searchCustomerByName(customerName).observe(viewLifecycleOwner, Observer { customers ->
                if (customers.isNotEmpty()) {
                    addCustomerToSelected(customers[0])
                } else {
                    Toast.makeText(requireContext(), "Customer not found.", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }

    private fun searchProduct() {
        val productName = editTextProductSearch.text.toString().trim()

        if (productName.isNotEmpty()) {
            viewModel.searchProductByName(productName).observe(viewLifecycleOwner, Observer { products ->
                if (products.isNotEmpty()) {
                    addProductToSelected(products[0])
                } else {
                    Toast.makeText(requireContext(), "Product not found.", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }

    private fun addCustomerToSelected(customer: Customer) {
        editTextCustomerSearch.text.clear()
        layoutSelectedCustomerRow.removeAllViews()

        val rowView = layoutInflater.inflate(R.layout.customer_row, layoutSelectedCustomerRow, false)
        val customerNameTextView = rowView.findViewById<TextView>(R.id.textViewCustomerName) // Assuming the ID is correct
        val customerPhoneTextView = rowView.findViewById<TextView>(R.id.textViewCustomerPhone) // Example
        val customerAddressTextView = rowView.findViewById<TextView>(R.id.textViewCustomerAddress)  // Example

        selectedCustomerId = customer.customerId
        selectedCustomerName = customer.customerName
        customerPhoneTextView.text = customer.customerPhone  // Set if you have a phone field
        customerAddressTextView.text = customer.customerAddress // Set if you have an address field

        customerNameTextView.text = selectedCustomerName
        layoutSelectedCustomerRow.addView(rowView)

        val deleteIcon = rowView.findViewById<ImageView>(R.id.imageViewDelete_c)
        // Set click listener to remove the customer
        deleteIcon.setOnClickListener {
            selectedCustomerId = 0
            selectedCustomerName = ""
            layoutSelectedCustomerRow.removeAllViews() // Refresh customer display
        }


    }




    private fun addProductToSelected(product: Product) {
        editTextProductSearch.text.clear()
        layoutSelectedProductRow.removeAllViews()

        val quantity = getQuantityFromUser()

        selectedProducts[product.productId] = SelectedProduct(
            productId = product.productId,
            productName = product.productName,
            price = product.productSellingPrice,
            quantity = quantity
        )

        updateSelectedProductsDisplay()
        updateFinalTotal()
        calculateDueAndChange()

    }








    private fun getQuantityFromUser(): Int {
        // Implement a dialog or other input mechanism
        return 1 // Temporary default value
    }





    private fun updateSelectedProductsDisplay() {
        layoutSelectedProductRow.removeAllViews()

        Log.d("DEBUG", "updateSelectedProductsDisplay() called")

        for (product in selectedProducts.values) {
            val rowView = layoutInflater.inflate(R.layout.product_row, layoutSelectedProductRow, false)
            val productNameTextView = rowView.findViewById<TextView>(R.id.textViewProductName)
            val productQuantityEditText = rowView.findViewById<EditText>(R.id.textViewProductQuantity)
            val productPriceTextView = rowView.findViewById<TextView>(R.id.textViewProductPrice)
            val productSubtotalTextView = rowView.findViewById<TextView>(R.id.textViewProduct_Subtotal)
            val deleteIcon = rowView.findViewById<ImageView>(R.id.imageViewDelete_p)

            productNameTextView.text = product.productName
            productQuantityEditText.setText(product.quantity.toString())
            productPriceTextView.text = product.price.toString()

            // Calculate and display subtotal
            val itemSubtotal = product.price * product.quantity
            productSubtotalTextView.text = itemSubtotal.toString()

            // Set click listener to update the quantity
            productQuantityEditText.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

                override fun afterTextChanged(s: Editable?) {
                    val newQuantity = s?.toString()?.toIntOrNull() ?: return
                    if (newQuantity > 0) {
                        // Update the quantity of the selected product-
                        selectedProducts[product.productId]?.quantity = newQuantity
                        // Update subtotal
                        productSubtotalTextView.text = (product.price * newQuantity).toString()
                        // Call updateFinalTotal to recalculate the final total
                        updateFinalTotal()
                        calculateDueAndChange()
                    }
                }
            })

            // Set click listener to remove the product
            deleteIcon.setOnClickListener {
                selectedProducts.remove(product.productId)
                updateSelectedProductsDisplay() // Refresh the display
            }

            layoutSelectedProductRow.addView(rowView)
        }
    }






    @RequiresApi(Build.VERSION_CODES.O)
    private fun saveOrder() {
        if (selectedCustomerId != 0 && selectedCustomerName.isNotEmpty() && selectedProducts.isNotEmpty()) {
            val currentDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
            val invoiceNumber = generateInvoiceNumber() // Generate a single invoice number for all orders




            // Initialize an empty list to hold the orders
            val orderList = mutableListOf<Pos>()
            val invoiceList = mutableListOf<Invoice>()

            val uniqueInvoiceMap = mutableMapOf<Triple<String, Int, String>, Invoice>()

            var totalSubtotal = 0.0

            // Retrieve shipping and labor costs from EditText fields
            val shippingCost = editTextShippingCost.text.toString().toDoubleOrNull() ?: 0.0
            val laborCost = editTextLaborCost.text.toString().toDoubleOrNull() ?: 0.0



            val totalPaid = editTextTotalPaid.text.toString().toDoubleOrNull() ?: 0.0
            val finalTotal = textViewFinalTotal.text.toString().toDoubleOrNull() ?: 0.0
            val dueAmount = if (finalTotal > totalPaid) finalTotal - totalPaid else 0.0
            val changeAmount = if (totalPaid >= finalTotal) totalPaid - finalTotal else 0.0

            // Retrieve the selected payment method from the spinner
            val selectedPaymentMethod = spinnerPaymentMethod.selectedItem.toString()






            // Iterate through each selected product
            for (product in selectedProducts.values) {


                // Retrieve the product information from the database
                viewModel.getProductById(product.productId)?.observe(viewLifecycleOwner) { searchedProduct ->
                    searchedProduct?.let { foundProduct ->




                        // Calculate profit and subtotal
                        val profit = (searchedProduct.productSellingPrice - searchedProduct.productPrice) * (product.quantity ?: 1)
                        val subtotal = product.price * product.quantity


//Need changeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee
                        viewModel.updateProductQuantity(product.productId, searchedProduct.productQuantity - product.quantity)


                        // Increment the total subtotal for the invoice
                        totalSubtotal += subtotal

                        val invoiceTotal = subtotal + shippingCost + laborCost


                        // Create a new Pos object with the calculated values
                        val order = Pos(
                            customerId = selectedCustomerId,
                            customerName = selectedCustomerName,
                            productId = product.productId,
                            productName = product.productName,
                            price = product.price,
                            quantity = product.quantity,
                            subtotal = subtotal,
                            invoiceNumber = invoiceNumber,
                            profit = profit, // Store individual profit for each order
                            date = currentDate
                        )

                        // Add the order to the list
                        orderList.add(order)





                        // Create a unique invoice key based on invoice number, customer ID, and date
                        val invoiceKey = Triple(invoiceNumber, selectedCustomerId, currentDate)

                        // Check if an invoice with the same key exists in the map
                        if (!uniqueInvoiceMap.containsKey(invoiceKey)) {
                            // Create a new Invoice object and add it to the invoice map
                            val invoiceProfit = profit // Initialize invoice profit with the current order's profit


                            val invoice = Invoice(
                                invoiceNumber = invoiceNumber,
                                customerId = selectedCustomerId,
                                shippingCost = shippingCost,
                                labourCost = laborCost,
                                total = invoiceTotal,
                                customerName = selectedCustomerName,
                                paymentMethod = selectedPaymentMethod,
                                paymentAmount = totalPaid,
                                dueAmount = dueAmount,
                                changeAmount = changeAmount,
                                profit = invoiceProfit, // Store the total profit for the invoice
                                date = currentDate


                            )
                            // Add the invoice to the map using the unique key
                            uniqueInvoiceMap[invoiceKey] = invoice
                        } else {
                            // If the invoice already exists, update the profit by adding the current order's profit
                            val currentInvoice = uniqueInvoiceMap[invoiceKey]
                            currentInvoice?.let {
                                currentInvoice.profit = currentInvoice.profit?.plus(profit) // Update the profit for the existing invoice
                                currentInvoice.total = currentInvoice.total?.plus(subtotal) // Update the total for the existing invoice
                            }
                        }

                        // Check if all orders have been processed
                        if (orderList.size == selectedProducts.size) {
                            // Add all orders to the database
                            viewModel.addInvoice(uniqueInvoiceMap.values.toList())
                            // Add all orders to the database
                            viewModel.addOrder(orderList)
                            Toast.makeText(requireContext(), "Orders saved successfully!", Toast.LENGTH_SHORT).show()
                            clearSelections()


                            selectedProducts.clear()

                            // Clear EditText fields
                            editTextShippingCost.setText("")
                            editTextLaborCost.setText("")
                            editTextTotalPaid.setText("")
                            editTextTotalDue.setText("")
                            textViewChangeAmount.text = ""

                            // Reset spinner to the first item
                            spinnerPaymentMethod.setSelection(0)
                            layoutSelectedProductRow.removeAllViews()
                        }
                    }
                }
            }
        } else {
            Toast.makeText(requireContext(), "Invalid order details.", Toast.LENGTH_SHORT).show()
        }



    }














    private fun generateInvoiceNumber(): String {
        // Implement logic to generate a unique invoice number, such as combining current date/time with a random string
        //val currentDate = SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault()).format(Date())
        val randomString = (1..4).map { ('1'..'5').random() }.joinToString("")
        return "INV-$randomString"
        //return "INV-$currentDate-$randomString"
    }






    private fun clearSelections() {
        selectedCustomerId = 0
        selectedCustomerName = ""
        selectedProducts.clear()
        layoutSelectedCustomerRow.removeAllViews()
        layoutSelectedProductRow.removeAllViews()
    }

    // Data class to hold selected product data
    data class SelectedProduct(
        val productId: Int,
        val productName: String,
        val price: Double,
        var quantity: Int
    )
}



//Worked 100%
