package com.example.retail_shop_app.View.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.retail_shop_app.R


import com.example.retail_shop_app.Model.Entity.Product

import com.example.retail_shop_app.ViewModel.ViewModel


import androidx.lifecycle.ViewModelProvider





import androidx.recyclerview.widget.LinearLayoutManager

import com.example.retail_shop_app.databinding.FragmentViewProductFragmentBinding




import android.app.AlertDialog
import android.widget.EditText

class View_product_fragment : Fragment() {

    private lateinit var viewModel: ViewModel
    private var _binding: FragmentViewProductFragmentBinding? = null
    private val binding get() = _binding!!
    private var selectedProduct: Product? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentViewProductFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[ViewModel::class.java]

        val recyclerView = binding.recyclerView
        val adapter = ProductListAdapter(requireContext())
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Selection & Deletion Logic
        adapter.setOnItemLongClickListener(object : ProductListAdapter.OnItemLongClickListener {
            override fun onItemLongClick(product: Product): Boolean {
                selectedProduct = product
                return true
            }
        })

        val deleteButton = view.findViewById<Button>(R.id.deleteButton)
        deleteButton.setOnClickListener {
            val selectedProduct = adapter.getSelectedProduct()
            selectedProduct?.let { product ->
                viewModel.deleteProduct(product)
                adapter.clearSelection()
            }
        }

        val updateButton = view.findViewById<Button>(R.id.updateButton)
        updateButton.setOnClickListener {
            val selectedProduct = adapter.getSelectedProduct()
            selectedProduct?.let { product ->
                showUpdateDialog(product)
            }
        }

        // LiveData Observation
        viewModel.allProducts.observe(viewLifecycleOwner) { products ->
            adapter.submitList(products)
            adapter.clearSelection()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showUpdateDialog(product: Product) {
        val dialogBuilder = AlertDialog.Builder(requireContext())
        val dialogView = layoutInflater.inflate(R.layout.fragment_update_product_dialog, null)

        dialogBuilder.setView(dialogView)

        dialogView.findViewById<EditText>(R.id.editTextProductName).setText(product.productName)
        dialogView.findViewById<EditText>(R.id.editTextProductQuantity).setText(product.productQuantity.toString())
        dialogView.findViewById<EditText>(R.id.editTextProductPrice).setText(product.productPrice.toString())
        dialogView.findViewById<EditText>(R.id.editTextProductPriceWorth).setText(product.productSellingPrice.toString())

        val dialog = dialogBuilder.create()
        dialog.show()

        dialogView.findViewById<Button>(R.id.buttonUpdateProduct).setOnClickListener {
            val updatedName = dialogView.findViewById<EditText>(R.id.editTextProductName).text.toString()
            val updatedQuantity = dialogView.findViewById<EditText>(R.id.editTextProductQuantity).text.toString().toIntOrNull()
            val updatedPrice = dialogView.findViewById<EditText>(R.id.editTextProductPrice).text.toString().toDoubleOrNull()
            val updatedSellingPrice = dialogView.findViewById<EditText>(R.id.editTextProductPriceWorth).text.toString().toDoubleOrNull()

            if (updatedName.isNotEmpty() && updatedQuantity != null && updatedPrice != null && updatedSellingPrice != null) {
                val updatedProduct = product.copy(
                    productName = updatedName,
                    productQuantity = updatedQuantity,
                    productPrice = updatedPrice,
                    productSellingPrice = updatedSellingPrice
                )
                viewModel.updateProduct(updatedProduct)
                dialog.dismiss()
            } else {
                // Show an error message if fields are invalid
            }
        }

        dialogView.findViewById<Button>(R.id.buttonCancelUpdate).setOnClickListener {
            dialog.dismiss()
        }
    }
}





//worked 400%
