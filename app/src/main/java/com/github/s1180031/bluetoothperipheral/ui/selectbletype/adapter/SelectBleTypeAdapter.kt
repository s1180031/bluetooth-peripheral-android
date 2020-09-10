package com.github.s1180031.bluetoothperipheral.ui.selectbletype.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.github.s1180031.bluetoothperipheral.databinding.ItemSelectBleTypeBinding
import com.github.s1180031.bluetoothperipheral.ui.selectbletype.BleTypeItem

typealias SelectBleTypeItemClickListener = (BleTypeItem) -> Unit

class SelectBleTypeAdapter(
    var listener: SelectBleTypeItemClickListener? = null
) : RecyclerView.Adapter<SelectBleTypeAdapter.ViewHolder>() {
    var items: List<BleTypeItem> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(ItemSelectBleTypeBinding.inflate(layoutInflater, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.binding.text.text = item.text
        holder.binding.root.setOnClickListener {
            listener?.invoke(item)
        }
    }

    override fun getItemCount(): Int = items.count()

    class ViewHolder(val binding: ItemSelectBleTypeBinding) : RecyclerView.ViewHolder(binding.root)
}
