package com.sorychan.elecalc.ui

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.sorychan.elecalc.R
import com.sorychan.elecalc.data.Device
import com.sorychan.elecalc.data.Duration
import com.sorychan.elecalc.data.Power
import com.sorychan.elecalc.data.Usage
import com.sorychan.elecalc.databinding.FragmentAddDevicesBinding
import com.sorychan.elecalc.viewmodels.DevicesViewModel

private const val TAG = "AddDeviceFragment"

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

            if (sharedPreferences.getLong("price", 0) == 0L) {
                Toast.makeText(context, "Please input a price first!", Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_nav_add_devices_to_nav_settings)
            }

            if (!name.isNullOrBlank() && !power.isNullOrBlank()
                && !duration.isNullOrBlank() && !usage.isNullOrBlank()
            ) {
                val powerUnit = powerSpinner.selectedItem as Power
                val durationUnit = durationSpinner.selectedItem as Duration
                val usageUnit = usageSpinner.selectedItem

                // Avoid bad input like 26 hours/day on usage
                var badUsage = false
                when (usageUnit) {
                    usageOptions[1] -> if (usage.toString().toLong() > 24) {
                        Log.d(TAG, "Bad usage hours")
                        badUsage = true
                    }
                    usageOptions[2] -> if (usage.toString().toLong() > 31) {
                        Log.d(TAG, "Bad usage days")
                        badUsage = true
                    }
                    usageOptions[0] -> if (usage.toString().toLong() > 60) {
                        Log.d(TAG, "Bad usage minutes")
                        badUsage = true
                    }
                }
                Log.d(TAG, "Bad usage: $badUsage")
                if (badUsage) {
                    Toast.makeText(context, "Usage too big!", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                val device = Device(
                    name = name.toString(),
                    power = power.toString().toLong(), powerUnit = powerUnit,
                    duration = duration.toString().toLong(), durationUnit = durationUnit,
                    usage = usage.toString().toLong(), usageUnit = usageUnit.toString()
                )
                val price = requireContext().getSharedPreferences("prefs", Context.MODE_PRIVATE).getLong("price", 0)
                device.apply {
                    calculateCost(price)
                    formatCost()
                }
                viewModel.addDevice(device)
                name.clear()
                power.clear()
                duration.clear()
                usage.clear()
            } else {
                Toast.makeText(context, "Please fill all fields correctly", Toast.LENGTH_LONG)
                    .show()
            }
            findNavController().navigate(R.id.action_nav_add_devices_to_nav_devices)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}