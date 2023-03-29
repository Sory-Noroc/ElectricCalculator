package com.sorychan.elecalc.ui

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.sorychan.elecalc.R
import com.sorychan.elecalc.databinding.FragmentSettingsBinding
import com.sorychan.elecalc.viewmodels.DevicesViewModel

private const val TAG = "SettingsFragment"

class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val viewModel: DevicesViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sharedPreferences = requireContext().getSharedPreferences("prefs", Context.MODE_PRIVATE)

        val currencySpinner: Spinner = binding.currencySpinner
        currencySpinner.adapter = ArrayAdapter.createFromResource(requireContext(),
            R.array.currency_array, android.R.layout.simple_spinner_item).also {
            it.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }
        currencySpinner.setSelection(2)
        currencySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                // Handle item selection
                val selectedCurrency = currencySpinner.selectedItem.toString()
                val editor = sharedPreferences.edit()
                editor.putString("currency", selectedCurrency)
                editor.apply()
                Log.i(TAG, "New currency \"$selectedCurrency\"")
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                Log.i(TAG, "Nothing selected")
            }
        }

        binding.priceInput.setText(sharedPreferences.getLong("price", 0L).toString())
        binding.priceInput.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                val editor = sharedPreferences.edit()
                editor.putLong("price", binding.priceInput.text.toString().toLong())
                editor.apply()
                // TODO: Close the keyboard
                true
            } else {
                false
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}