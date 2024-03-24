package com.example.android.medicinechest.updatecompositionpage

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.android.medicinechest.R
import com.example.android.medicinechest.database.ObjectCheck

class ObjectViewHolder(val button: RelativeLayout) : RecyclerView.ViewHolder(button)

class UpdateCompositionPageAdapter: RecyclerView.Adapter<ObjectViewHolder>() {

    var data = listOf<ObjectCheck>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    val checks = mutableListOf<Long>()

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: ObjectViewHolder, position: Int) {
        val item = data[position]
        val nameTextView = holder.button.getChildAt(0) as TextView
        nameTextView.text = item.name
        val checkbox = holder.button.getChildAt(1) as CheckBox
        checkbox.isChecked = item.isChecked

        checkbox.setOnClickListener {
            if (!checks.contains(item.id))
                checks.add(item.id)
            else
                checks.remove(item.id)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ObjectViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val button = layoutInflater
            .inflate(R.layout.update_composition_item, parent, false) as RelativeLayout
        return ObjectViewHolder(button)
    }
}
