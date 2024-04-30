





package com.example.retail_shop_app.View.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.retail_shop_app.R



import android.widget.Toast
import androidx.lifecycle.ViewModelProvider

import com.example.retail_shop_app.ViewModel.ViewModel
import android.widget.Button
import android.widget.EditText
import com.example.retail_shop_app.Model.Entity.Staff


import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.app.AlertDialog












class StuffFragment : Fragment() {
    private lateinit var viewModel: ViewModel
    private lateinit var staffNameEditText: EditText
    private lateinit var staffAddressEditText: EditText
    private lateinit var staffPhoneEditText: EditText
    private lateinit var staffSalaryEditText: EditText
    private lateinit var addButton: Button
    private lateinit var staffRecyclerView: RecyclerView
    private lateinit var staffListAdapter: StaffListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_stuff_, container, false)

        staffNameEditText = rootView.findViewById(R.id.editTextStaffName)
        staffAddressEditText = rootView.findViewById(R.id.editTextStaffAddress)
        staffPhoneEditText = rootView.findViewById(R.id.editTextStaffPhone)
        staffSalaryEditText = rootView.findViewById(R.id.editTextStaffSalary)
        addButton = rootView.findViewById(R.id.buttonInsertStaff)
        staffRecyclerView = rootView.findViewById(R.id.recyclerViewsss)

        addButton.setOnClickListener { saveStaff() }

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize ViewModel
        viewModel = ViewModelProvider(this).get(ViewModel::class.java)

        // Initialize RecyclerView
        staffListAdapter = StaffListAdapter(requireContext())
        staffRecyclerView.adapter = staffListAdapter
        staffRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Observe LiveData
        viewModel.allStaff.observe(viewLifecycleOwner) { staffList ->
            staffListAdapter.submitList(staffList)
        }

        // Setup delete and update buttons
        view.findViewById<Button>(R.id.deleteButton_s).setOnClickListener {
            val selectedStaff = staffListAdapter.getSelectedStaff()
            selectedStaff?.let { staff ->
                viewModel.deleteStaff(staff)
                staffListAdapter.clearSelection()
            }
        }

        view.findViewById<Button>(R.id.updateButton_s).setOnClickListener {
            val selectedStaff = staffListAdapter.getSelectedStaff()
            selectedStaff?.let { staff ->
                showUpdateDialog(staff)
            }
        }
    }

    private fun saveStaff() {
        val staffName = staffNameEditText.text.toString().trim()
        val staffAddress = staffAddressEditText.text.toString().trim()
        val staffPhone = staffPhoneEditText.text.toString().trim()
        val staffSalary = staffSalaryEditText.text.toString().toDoubleOrNull()

        if (staffName.isEmpty() || staffAddress.isEmpty() || staffPhone.isEmpty() || staffSalary == null) {
            Toast.makeText(requireContext(), "Please fill all fields", Toast.LENGTH_SHORT).show()
            return
        }

        val staff = Staff(0, staffName, staffAddress, staffPhone, staffSalary)
        viewModel.addStaff(staff)

        Toast.makeText(requireContext(), "Staff Saved!", Toast.LENGTH_SHORT).show()

        // Clear input fields after saving staff
        staffNameEditText.text.clear()
        staffAddressEditText.text.clear()
        staffPhoneEditText.text.clear()
        staffSalaryEditText.text.clear()
    }

    private fun showUpdateDialog(staff: Staff) {
        val dialogBuilder = AlertDialog.Builder(requireContext())
        val dialogView = layoutInflater.inflate(R.layout.fragment_update_staff_dialog, null)

        dialogBuilder.setView(dialogView)

        dialogView.findViewById<EditText>(R.id.editTextStaffName).setText(staff.staffName)
        dialogView.findViewById<EditText>(R.id.editTextStaffAddress).setText(staff.staffAddress)
        dialogView.findViewById<EditText>(R.id.editTextStaffPhone).setText(staff.staffPhone)
        dialogView.findViewById<EditText>(R.id.editTextStaffSalary).setText(staff.staffSalary.toString()) // Set staff salary

        val dialog = dialogBuilder.create()
        dialog.show()

        dialogView.findViewById<Button>(R.id.buttonUpdateStaff_s).setOnClickListener {
            val updatedName = dialogView.findViewById<EditText>(R.id.editTextStaffName).text.toString()
            val updatedAddress = dialogView.findViewById<EditText>(R.id.editTextStaffAddress).text.toString()
            val updatedPhone = dialogView.findViewById<EditText>(R.id.editTextStaffPhone).text.toString()
            val updatedSalary = dialogView.findViewById<EditText>(R.id.editTextStaffSalary).text.toString().toDoubleOrNull()

            if (updatedName.isNotEmpty() && updatedAddress.isNotEmpty() && updatedPhone.isNotEmpty() && updatedSalary != null ) {
                val updatedStaff = staff.copy(
                    staffName = updatedName,
                    staffAddress = updatedAddress,
                    staffPhone = updatedPhone,
                    staffSalary = updatedSalary // Update staff salary
                )
                viewModel.updateStaff(updatedStaff)
                dialog.dismiss()
            } else {
                // Show an error message if fields are invalid
                Toast.makeText(requireContext(), "Please fill all fields", Toast.LENGTH_SHORT).show()
            }
        }

        dialogView.findViewById<Button>(R.id.buttonCancelUpdate_s).setOnClickListener {
            dialog.dismiss()
        }
    }
}
//--aaa