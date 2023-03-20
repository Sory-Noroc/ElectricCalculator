package com.sorychan.elecalc.ui

import android.content.Context
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

        val powerSpinner: Spinner = binding.powerSpinner
        val powerOptions = listOf(Power.mW, Power.W, Power.kW)
        powerSpinner.adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, powerOptions).also {
            it.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }
        powerSpinner.setSelection(2)

        val durationSpinner: Spinner = binding.durationSpinner
        val durationOptions = listOf(Duration.MIN, Duration.H, Duration.D, Duration.M)
        durationSpinner.adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, durationOptions).also {
            it.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }
        durationSpinner.setSelection(2)

        val usageSpinner: Spinner = binding.usageSpinner
        val usageOptions = listOf(Usage.MH.text, Usage.HD.text, Usage.DM.text)
        usageSpinner.adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, usageOptions).also {
            it.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }
        usageSpinner.setSelection(1)

        binding.addButton.setOnClickListener {
            val name = binding.nameInput.text
            val power = binding.powerInput.text
            val duration = binding.durationInput.text
            val usage = binding.usageInput.text

            if (!name.isNullOrBlank() && power?.isDigitsOnly() == true
                && duration?.isDigitsOnly() == true && usage?.isDigitsOnly() == true
            ) {
                val device = Device(name.toString(),
                                    power.toString().toLong(), powerSpinner.selectedItem as Power,
                                    duration.toString().toLong(), durationSpinner.selectedItem as Duration,
                                    usage.toString().toLong(), usageSpinner.selectedItem.toString()
                )
//                device.calculateCost()
                val price = requireContext().getSharedPreferences("prefs", Context.MODE_PRIVATE).getLong("price", 0)
                device.calculateCost(price)
                viewModel.addDevice(device)
                name.clear()
                power.clear()
                duration.clear()
                usage.clear()
            } else {
                Toast.makeText(context, "Please fill all fields correctly", Toast.LENGTH_LONG)
                    .show()
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}