package com.example.retail_shop_app.View.Fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup





import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.retail_shop_app.R
import com.example.retail_shop_app.ViewModel.ViewModel
import com.example.retail_shop_app.databinding.FragmentHomeBinding
import java.text.SimpleDateFormat
import java.util.*
import android.graphics.Color

import android.widget.LinearLayout
import android.widget.TextView
import android.view.Gravity


class HomeFragment : Fragment() {

    private lateinit var viewModel: ViewModel
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(ViewModel::class.java)

        // Get today's date in the required format
        val currentDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())

        var totalSells: Double? = null // Declare totalSells variable

        // Observe the total sells for today
        viewModel.getTotalSellsForDate(currentDate).observe(viewLifecycleOwner, Observer { totalSellsValue ->
            totalSells = totalSellsValue // Assign totalSellsValue to totalSells
            binding.totalSellsTextView.text = totalSells.toString()
        })

        // Observe the total profit for today
        viewModel.getTotalProfitForDate(currentDate).observe(viewLifecycleOwner, Observer { totalProfit ->
            // Update the total profit TextView with the fetched value
            binding.todaysProfitTextView.text = "$totalProfit taka"

            // Observe the total due for today
            viewModel.getTotalDueForDate(currentDate).observe(viewLifecycleOwner, Observer { totalDue ->
                // Update the total profit TextView with the fetched value
                binding.todaysDueTextView.text = "$totalDue taka"

                // Calculate net earning
                val netEarning = (totalSells ?: 0.0) - (totalDue ?: 0.0) + (totalProfit ?: 0.0)

                // Set the net earning TextView
                binding.todaysNetEarningTextView.text = "$netEarning taka"
            })
        })




        viewModel.getTotalSells().observe(viewLifecycleOwner, Observer { totalSellsValue ->
            totalSells = totalSellsValue
            binding.LifetimeSellsTextView.text = totalSells.toString()
        })


        viewModel.getTotalProfit().observe(viewLifecycleOwner, Observer { totalProfitsValue ->
            totalSells = totalProfitsValue
            binding.LifetimeProfitTextView.text = totalSells.toString()
        })



        viewModel.getTop5ProductsByQuantity().observe(viewLifecycleOwner, Observer { productList ->
            // Clear existing views before adding new ones
            binding.productContainer.removeAllViews()

            // Create a header LinearLayout for "Product Name" and "Product Quantity"
            val headerLayout = LinearLayout(requireContext()).apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
                orientation = LinearLayout.HORIZONTAL
                weightSum = 2f // Divide the width into two equal parts
            }

            // Create a new TextView for the header "Product Name"
            val productNameHeader = TextView(requireContext()).apply {
                layoutParams = LinearLayout.LayoutParams(
                    0,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    1f // Weight 1 to take up half of the available width
                )
                text = "Product Name"
                textSize = 20f
                setTextColor(Color.BLACK)
                gravity = Gravity.CENTER
            }

            // Create a new TextView for the header "Product Quantity"
            val productQuantityHeader = TextView(requireContext()).apply {
                layoutParams = LinearLayout.LayoutParams(
                    0,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    1f // Weight 1 to take up half of the available width
                )
                text = "Product Quantity"
                textSize = 20f
                setTextColor(Color.BLACK)
                gravity = Gravity.CENTER
            }

            // Add the header TextViews to the headerLayout LinearLayout
            headerLayout.addView(productNameHeader)
            headerLayout.addView(productQuantityHeader)

            // Add the headerLayout to the productContainer LinearLayout
            binding.productContainer.addView(headerLayout)

            // Iterate through the product list
            for (product in productList) {
                // Create a new LinearLayout to hold the product name and quantity horizontally
                val productLayout = LinearLayout(requireContext()).apply {
                    layoutParams = ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                    )
                    orientation = LinearLayout.HORIZONTAL
                }

                // Create a new TextView for the product name
                val productNameTextView = TextView(requireContext()).apply {
                    layoutParams = LinearLayout.LayoutParams(
                        0,
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        1f // weight 1 to take up half of the available width
                    )
                    text = product.productName // Set the product name
                    textSize = 18f
                    setTextColor(Color.BLACK)
                    gravity = Gravity.CENTER
                }

                // Create a new TextView for the product quantity
                val productQuantityTextView = TextView(requireContext()).apply {
                    layoutParams = LinearLayout.LayoutParams(
                        0,
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        1f // weight 1 to take up half of the available width
                    )
                    text = product.productQuantity.toString() // Set the product quantity
                    textSize = 18f
                    setTextColor(Color.BLACK)
                    gravity = Gravity.CENTER
                }

                // Add the TextViews to the productLayout LinearLayout
                productLayout.addView(productNameTextView)
                productLayout.addView(productQuantityTextView)

                // Add the productLayout to the productContainer LinearLayout
                binding.productContainer.addView(productLayout)
            }
        })


        viewModel.getTotalDueAmountByCustomer().observe(viewLifecycleOwner, Observer { customerDueAmountList ->
            // Clear existing views before adding new ones
            binding.dueContainer.removeAllViews()

            // Iterate through the list of CustomerDueAmount objects
            for (customerDueAmount in customerDueAmountList) {
                // Create a new LinearLayout to hold the customer name and due amount horizontally
                val customerLayout = LinearLayout(requireContext()).apply {
                    layoutParams = ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                    )
                    orientation = LinearLayout.HORIZONTAL
                }

                // Create a new TextView for the customer name
                val customerNameTextView = TextView(requireContext()).apply {
                    layoutParams = LinearLayout.LayoutParams(
                        0,
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        1f // weight 1 to take up half of the available width
                    )
                    text = "Customer Name: ${customerDueAmount.customerName}"
                    textSize = 15f
                    setTextColor(Color.BLACK)
                }

                // Create a new TextView for the total due amount
                val customerDueTextView = TextView(requireContext()).apply {
                    layoutParams = LinearLayout.LayoutParams(
                        0,
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        1f // weight 1 to take up half of the available width
                    )
                    text = "Due: ${customerDueAmount.totalDueAmount}"
                    textSize = 15f
                    setTextColor(Color.BLACK)
                }

                // Add the TextViews to the customerLayout LinearLayout
                customerLayout.addView(customerNameTextView)
                customerLayout.addView(customerDueTextView)

                // Add the customerLayout to the dueContainer LinearLayout
                binding.dueContainer.addView(customerLayout)
            }
        })











    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}


//okkk


