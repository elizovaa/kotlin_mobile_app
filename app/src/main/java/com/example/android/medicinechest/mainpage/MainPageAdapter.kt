package com.example.android.medicinechest.mainpage

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.android.medicinechest.R
import com.example.android.medicinechest.database.Inventory

class TextItemViewHolder(val textView: TextView): RecyclerView.ViewHolder(textView)

class MainPageAdapter: RecyclerView.Adapter<TextItemViewHolder>() {
    var data = listOf<Inventory>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: TextItemViewHolder, position: Int) {
        val item = data[position]
        holder.textView.text = item.name
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TextItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater
            .inflate(R.layout.text_item_view, parent, false) as TextView
        return TextItemViewHolder(view)
    }
}
