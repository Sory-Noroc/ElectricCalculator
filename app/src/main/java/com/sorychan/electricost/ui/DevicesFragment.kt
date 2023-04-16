package com.sorychan.electricost.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sorychan.electricost.adapters.DeviceAdapter
import com.sorychan.electricost.databinding.FragmentDevicesBinding
import com.sorychan.electricost.viewmodels.DevicesViewModel

private const val TAG = "DevicesFragment"

class DevicesFragment : Fragment() {

    private var _binding: FragmentDevicesBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val viewModel: DevicesViewModel by activityViewModels()
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDevicesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = binding.devicesRecyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        val adapter = DeviceAdapter(requireContext(), viewModel)
        recyclerView.adapter = adapter
        viewModel.deviceList.observe(viewLifecycleOwner) {
            adapter.submitList(it)
            Log.i(TAG, "Observing list: $it")
        }
        // Currency changes for every item instead of changing only for next items that will be added
        if (viewModel.deviceList.value?.isEmpty() == true) {
            Log.i(TAG, "Empty list")
            binding.textNoDevices.visibility = View.VISIBLE
            binding.devicesRecyclerView.visibility = View.INVISIBLE
        } else {
            // If we got some devices in the list
            binding.textNoDevices.visibility = View.INVISIBLE
            binding.devicesRecyclerView.visibility = View.VISIBLE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}