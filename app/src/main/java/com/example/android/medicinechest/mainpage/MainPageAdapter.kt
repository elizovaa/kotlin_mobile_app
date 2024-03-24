package com.example.android.medicinechest.mainpage

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.example.android.medicinechest.R
import com.example.android.medicinechest.database.Inventory

class ButtonViewHolder(val button: Button): RecyclerView.ViewHolder(button)

class MainPageAdapter: RecyclerView.Adapter<ButtonViewHolder>() {
    private var onClickListener: OnClickListener? = null
    var data = listOf<Inventory>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: ButtonViewHolder, position: Int) {
        val item = data[position]
        holder.button.text = item.name
        holder.button.setOnClickListener {
            if (onClickListener != null) {
                onClickListener!!.onClick(position, item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ButtonViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val button = layoutInflater
            .inflate(R.layout.text_item_view, parent, false) as Button
        return ButtonViewHolder(button)
    }

    fun setOnClickListener(onClickListener: OnClickListener) {
        this.onClickListener = onClickListener
    }
}
