package com.sorychan.elecalc.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sorychan.elecalc.R
import com.sorychan.elecalc.data.Device
import com.sorychan.elecalc.databinding.DeviceItemBinding
import com.sorychan.elecalc.viewmodels.DevicesViewModel

class DeviceAdapter(private val context: Context, private val viewModel: DevicesViewModel): ListAdapter<Device, DeviceAdapter.DeviceViewHolder>(DiffCallback) {

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<Device>() {
            override fun areItemsTheSame(oldItem: Device, newItem: Device): Boolean {
                return oldItem.name == newItem.name
            }

            override fun areContentsTheSame(oldItem: Device, newItem: Device): Boolean {
                return oldItem.name == newItem.name && oldItem.power == newItem.power
                        && oldItem.duration == newItem.duration && oldItem.usage == newItem.usage
            }
        }
    }

    class DeviceViewHolder(private var binding: DeviceItemBinding, private val context: Context, private val viewModel: DevicesViewModel): RecyclerView.ViewHolder(binding.root) {

        fun bind(device: Device) {
            binding.nameText.text = device.name
            binding.powerText.text = context.getString(R.string.power_string, device.power, device.powerUnit)
            binding.durationText.text = context.getString(R.string.duration_string, device.duration, device.durationUnit)
            binding.usageText.text = context.getString(R.string.usage_string, device.usage, device.usageUnit)
            binding.costText.text = context.getString(R.string.cost_string, device.formatCost(), device.currency)

            binding.deleteButton.setOnClickListener {
                viewModel.deviceList.value?.remove(device)
                viewModel.totalConsumption.value = viewModel.totalConsumption.value?.minus(device.consumption)
                viewModel.totalCost.value = viewModel.totalCost.value?.minus(device.cost)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeviceViewHolder {
        return DeviceViewHolder(
            DeviceItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            context,
            viewModel
        )
    }

    override fun onBindViewHolder(holder: DeviceViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}