package com.example.retail_shop_app.View.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.retail_shop_app.R
import android.widget.Button
import com.example.retail_shop_app.Model.Entity.Customer
import com.example.retail_shop_app.ViewModel.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.retail_shop_app.databinding.FragmentViewCustomerBinding
import android.app.AlertDialog
import android.widget.EditText



class View_Customer_Fragment : Fragment() {

    private lateinit var customerViewModel: ViewModel
    private var _binding: FragmentViewCustomerBinding? = null
    private val binding get() = _binding!!
    private var selectedCustomer: Customer? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentViewCustomerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        customerViewModel = ViewModelProvider(this)[ViewModel::class.java]

        val recyclerView = binding.recyclerView
        val adapter = CustomerListAdapter(requireContext())
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Selection & Deletion Logic
        adapter.setOnItemLongClickListener(object : CustomerListAdapter.OnItemLongClickListener {
            override fun onItemLongClick(customer: Customer): Boolean {
                selectedCustomer = customer
                return true
            }
        })

        val deleteButton = view.findViewById<Button>(R.id.deleteButton_c)
        deleteButton.setOnClickListener {
            val selectedCustomer = adapter.getSelectedCustomer()
            selectedCustomer?.let { customer ->
                customerViewModel.deleteCustomer(customer)
                adapter.clearSelection()
            }
        }

        val updateButton = view.findViewById<Button>(R.id.updateButton_c)
        updateButton.setOnClickListener {
            val selectedCustomer = adapter.getSelectedCustomer()
            selectedCustomer?.let { customer ->
                showUpdateDialog(customer)
            }
        }

        // LiveData Observation
        customerViewModel.allCustomers.observe(viewLifecycleOwner) { customers ->
            adapter.submitList(customers)
            adapter.clearSelection()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showUpdateDialog(customer: Customer) {
        val dialogBuilder = AlertDialog.Builder(requireContext())
        val dialogView = layoutInflater.inflate(R.layout.fragment_update_customer_dialog, null)

        dialogBuilder.setView(dialogView)

        dialogView.findViewById<EditText>(R.id.editTextCustomerName).setText(customer.customerName)
        dialogView.findViewById<EditText>(R.id.editTextCustomerAddress).setText(customer.customerAddress)
        dialogView.findViewById<EditText>(R.id.editTextCustomerPhone).setText(customer.customerPhone)


        val dialog = dialogBuilder.create()
        dialog.show()

        dialogView.findViewById<Button>(R.id.buttonUpdateCustomer_c).setOnClickListener {
            val updatedName = dialogView.findViewById<EditText>(R.id.editTextCustomerName).text.toString()
            val updatedAddress = dialogView.findViewById<EditText>(R.id.editTextCustomerAddress).text.toString()
            val updatedPhone = dialogView.findViewById<EditText>(R.id.editTextCustomerPhone).text.toString()


            if (updatedName.isNotEmpty() && updatedAddress.isNotEmpty() && updatedPhone.isNotEmpty() ) {
                val updatedCustomer = customer.copy(
                    customerName = updatedName,
                    customerAddress = updatedAddress,
                    customerPhone = updatedPhone

                )
                customerViewModel.updateCustomer(updatedCustomer)
                dialog.dismiss()
            } else {
                // Show an error message if fields are invalid
            }
        }

        dialogView.findViewById<Button>(R.id.buttonCancelUpdate_c).setOnClickListener {
            dialog.dismiss()
        }
    }
}





//worked 400%
