package com.example.android.medicinechest.listpage

import android.content.res.Resources
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.android.medicinechest.database.Product
import com.example.android.medicinechest.R

class ProductViewHolder private constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val res: Resources = itemView.context.resources
    private val id: TextView = itemView.findViewById(R.id.id)
    private val name: TextView = itemView.findViewById(R.id.name)
    private val amount: TextView = itemView.findViewById(R.id.amount)
    private val type: TextView = itemView.findViewById(R.id.type)
    private val dosage: TextView = itemView.findViewById(R.id.dosage)

    fun bind(item: Product) {
        id.text = item.productId.toString()
        name.text = item.name
        amount.text = item.amount.toString()
        type.text = item.type
        dosage.text = item.dosage
    }

    companion object {
        fun from(parent: ViewGroup): ProductViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val view = layoutInflater
                .inflate(R.layout.list_item, parent, false)
            return ProductViewHolder(view)
        }
    }
}

class ListPageAdapter: RecyclerView.Adapter<ProductViewHolder>() {
    var data = listOf<Product>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val item = data[position]
        holder.bind(item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        return ProductViewHolder.from(parent)
    }
}
