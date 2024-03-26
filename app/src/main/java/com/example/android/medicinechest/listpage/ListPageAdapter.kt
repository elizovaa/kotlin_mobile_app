package com.example.android.medicinechest.listpage

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.android.medicinechest.database.Product
import com.example.android.medicinechest.R

class ProductViewHolder(val button: LinearLayout) : RecyclerView.ViewHolder(button)

class ListPageAdapter: RecyclerView.Adapter<ProductViewHolder>() {
    private var onClickListener: OnClickListener? = null
    var data = listOf<Product>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val item = data[position]
        val nameTextView = holder.button.getChildAt(0) as TextView
        nameTextView.text = item.name
        val moreInfoLayout = holder.button.getChildAt(1) as LinearLayout
        (moreInfoLayout.getChildAt(0) as TextView).text = R.string.type_info + " " + item.type
        (moreInfoLayout.getChildAt(1) as TextView).text = R.string.amount_info + " " + item.amount
        (moreInfoLayout.getChildAt(2) as TextView).text = R.string.dosage_info + " " + item.dosage
        holder.button.setOnClickListener {
            if (onClickListener != null) {
                onClickListener!!.onClick(position, item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val button = layoutInflater
            .inflate(R.layout.list_item, parent, false) as LinearLayout
        return ProductViewHolder(button)
    }

    fun setOnClickListener(onClickListener: OnClickListener) {
        this.onClickListener = onClickListener
    }
}
