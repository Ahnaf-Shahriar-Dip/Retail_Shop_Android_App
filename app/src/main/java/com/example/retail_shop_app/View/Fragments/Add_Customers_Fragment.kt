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
import com.example.retail_shop_app.Model.Entity.Customer
import com.example.retail_shop_app.ViewModel.ViewModel
import android.widget.Button
import android.widget.EditText





class Add_Customers_Fragment : Fragment() {


    private lateinit var customerViewModel: ViewModel
    private lateinit var customerNameEditText: EditText
    private lateinit var customerAddressEditText: EditText
    private lateinit var customerPhoneEditText: EditText


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_add__customers_, container, false)


        customerNameEditText = rootView.findViewById(R.id.editTextCustomerName)
        customerAddressEditText = rootView.findViewById(R.id.editTextCustomerAddress)
        customerPhoneEditText = rootView.findViewById(R.id.editTextCustomerPhone)

        val saveButton: Button = rootView.findViewById(R.id.buttonInsertCustomer)

        saveButton.setOnClickListener {
            saveCustomer()
        }

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        customerViewModel = ViewModelProvider(this).get(ViewModel::class.java)
    }

    private fun saveCustomer() {
        val customerName = customerNameEditText.text.toString().trim()
        val customer_Address = customerAddressEditText.text.toString().trim()
        val customer_Phone = customerPhoneEditText.text.toString().trim()


        // Basic input validation
        if (customerName.isEmpty() || customer_Address.isEmpty() || customer_Phone.isEmpty() ) {
            Toast.makeText(requireContext(), "Please fill all fields", Toast.LENGTH_SHORT).show()
            return
        }

        val customer = Customer(0, customerName, customer_Address, customer_Phone)
        customerViewModel.addCustomer(customer)

        Toast.makeText(requireContext(), "Customer Saved!", Toast.LENGTH_SHORT).show()

        // Clear input fields after saving (optional)
        customerNameEditText.text.clear()
        customerAddressEditText.text.clear()
        customerPhoneEditText.text.clear()


        // Navigate back (optional)
        findNavController().navigate(R.id.add_Customers_Fragment)
    }

}