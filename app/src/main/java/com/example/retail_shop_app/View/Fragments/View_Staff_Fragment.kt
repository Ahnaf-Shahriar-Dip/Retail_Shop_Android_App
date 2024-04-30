package com.example.retail_shop_app.View.Fragments




import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.retail_shop_app.R
import android.widget.Button
import com.example.retail_shop_app.Model.Entity.Staff
import com.example.retail_shop_app.ViewModel.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.retail_shop_app.databinding.FragmentStuffBinding
import android.app.AlertDialog
import android.widget.EditText




class View_Staff_Fragment : Fragment() {

    private lateinit var viewModel: ViewModel
    private var _binding: FragmentStuffBinding? = null
    private val binding get() = _binding!!
    private var selectedStaff: Staff? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentStuffBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[ViewModel::class.java]

        val recyclerView = binding.recyclerViewsss
        val adapter = StaffListAdapter(requireContext())
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Selection & Deletion Logic
        adapter.setOnItemLongClickListener(object : StaffListAdapter.OnItemLongClickListener {
            override fun onItemLongClick(staff: Staff): Boolean {
                selectedStaff = staff
                return true
            }
        })

        val deleteButton = view.findViewById<Button>(R.id.deleteButton_s)
        deleteButton.setOnClickListener {
            val selectedStaff = adapter.getSelectedStaff()
            selectedStaff?.let { staff ->
                viewModel.deleteStaff(staff)
                adapter.clearSelection()
            }
        }

        val updateButton = view.findViewById<Button>(R.id.updateButton_s)
        updateButton.setOnClickListener {
            val selectedStaff = adapter.getSelectedStaff()
            selectedStaff?.let { staff ->
                showUpdateDialog(staff)
            }
        }

        // LiveData Observation
        viewModel.allStaff.observe(viewLifecycleOwner) { staffList ->
            adapter.submitList(staffList)
            adapter.clearSelection()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
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
            }
        }

        dialogView.findViewById<Button>(R.id.buttonCancelUpdate_s).setOnClickListener {
            dialog.dismiss()
        }
    }
}

//--
