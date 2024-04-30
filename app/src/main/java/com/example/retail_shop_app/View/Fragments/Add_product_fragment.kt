package com.example.retail_shop_app.View.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.retail_shop_app.R
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController // For Navigation
import com.example.retail_shop_app.Model.Entity.Product
import com.example.retail_shop_app.ViewModel.ViewModel
import android.widget.Button
import android.widget.EditText


class Add_product_fragment : Fragment() {

    private lateinit var viewModel: ViewModel
    private lateinit var productNameEditText: EditText
    private lateinit var productQuantityEditText: EditText
    private lateinit var productPriceEditText: EditText
    private lateinit var productSellingPriceEditText: EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_add_product_fragment, container, false)


        productNameEditText = rootView.findViewById(R.id.editTextProductName)
        productQuantityEditText = rootView.findViewById(R.id.editTextProductQuantity)
        productPriceEditText = rootView.findViewById(R.id.editTextProductPrice)
        productSellingPriceEditText = rootView.findViewById(R.id.editTextProductPriceWorth)
        val saveButton: Button = rootView.findViewById(R.id.buttonInsertProduct)

        saveButton.setOnClickListener {
            saveProduct()
        }

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(ViewModel::class.java)
    }

    private fun saveProduct() {
        val productName = productNameEditText.text.toString().trim()
        val productQuantity = productQuantityEditText.text.toString().toIntOrNull()
        val productPrice = productPriceEditText.text.toString().toDoubleOrNull()
        val productSellingPrice = productSellingPriceEditText.text.toString().toDoubleOrNull()

        // Basic input validation
        if (productName.isEmpty() || productQuantity == null || productPrice == null || productSellingPrice == null) {
            Toast.makeText(requireContext(), "Please fill all fields", Toast.LENGTH_SHORT).show()
            return
        }

        val product = Product(
            0,
            productName,
            productQuantity,
            productPrice,
            productSellingPrice)


        viewModel.addProduct(product)

        Toast.makeText(requireContext(), "Product Saved!", Toast.LENGTH_SHORT).show()

        // Clear input fields after saving (optional)
        productNameEditText.text.clear()
        productQuantityEditText.text.clear()
        productPriceEditText.text.clear()
        productSellingPriceEditText.text.clear()

        // Navigate back (optional)
        findNavController().navigate(R.id.add_product_fragment)
    }
}
