package com.example.retail_shop_app.View.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.retail_shop_app.R






import com.example.retail_shop_app.databinding.FragmentPaymentBinding

import com.example.retail_shop_app.Model.Entity.Invoice
import com.example.retail_shop_app.ViewModel.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager

import android.widget.EditText


class Payment_Fragment : Fragment() {

    private lateinit var viewModel: ViewModel
    private var _binding: FragmentPaymentBinding? = null
    private val binding get() = _binding!!



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {


        _binding = FragmentPaymentBinding.inflate(inflater, container, false)
        return binding.root
    }




    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[ViewModel::class.java]

        val recyclerView = binding.recyclerView
        val adapter = PaymentListAdapter(requireContext())
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())




        // LiveData Observation
        viewModel.allInvoices.observe(viewLifecycleOwner) { invoice ->
            adapter.submitList(invoice)

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}

