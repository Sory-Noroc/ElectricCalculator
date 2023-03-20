package com.sorychan.elecalc.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.core.text.isDigitsOnly
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.sorychan.elecalc.data.Device
import com.sorychan.elecalc.data.Duration
import com.sorychan.elecalc.data.Power
import com.sorychan.elecalc.data.Usage
import com.sorychan.elecalc.databinding.FragmentAddDevicesBinding
import com.sorychan.elecalc.viewmodels.DevicesViewModel

class AddDeviceFragment : Fragment() {

    private var _binding: FragmentAddDevicesBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val viewModel: DevicesViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddDevicesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val powerSpinner: Spinner? = getView()?.findViewById(R.id.power_spinner)
        powerSpinner?.let {
            ArrayAdapter.createFromResource(
                requireContext(),
                R.array.powers_array,
                android.R.layout.simple_spinner_item
            ).also { adapter ->
                // Specify the layout to use when the list of choices appears
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                // Apply the adapter to the spinner
                it.adapter = adapter
            }
        }
        powerSpinner?.setSelection(2)

        val durationSpinner: Spinner? = getView()?.findViewById(R.id.duration_spinner)
        durationSpinner?.let {
            ArrayAdapter.createFromResource(
                requireContext(),
                R.array.durations_array,
                android.R.layout.simple_spinner_item
            ).also { adapter ->
                // Specify the layout to use when the list of choices appears
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                // Apply the adapter to the spinner
                it.adapter = adapter
            }
        }
        durationSpinner?.setSelection(2)

        val usageSpinner: Spinner? = getView()?.findViewById(R.id.usage_spinner)
        usageSpinner?.let {
            ArrayAdapter.createFromResource(
                requireContext(),
                R.array.usages_array,
                android.R.layout.simple_spinner_item
            ).also { adapter ->
                // Specify the layout to use when the list of choices appears
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                // Apply the adapter to the spinner
                it.adapter = adapter
            }
        }
        usageSpinner?.setSelection(1)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}