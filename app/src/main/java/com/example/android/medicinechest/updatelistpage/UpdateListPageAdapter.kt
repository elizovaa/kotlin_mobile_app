package com.example.android.medicinechest.updatelistpage

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.android.medicinechest.R
import com.example.android.medicinechest.database.ProductCheck

class ProductViewHolder(val button: RelativeLayout) : RecyclerView.ViewHolder(button)


class UpdateListPageAdapter: RecyclerView.Adapter<ProductViewHolder>() {

    var data = listOf<ProductCheck>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    val checks = mutableMapOf<Long, Boolean>()

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val item = data[position]
        val nameTextView = holder.button.getChildAt(0) as TextView
        nameTextView.text = item.name
        val checkbox = holder.button.getChildAt(1) as CheckBox
        checkbox.isChecked = item.isChecked

        checkbox.setOnClickListener {
            if (!checks.contains(item.productId))
                checks[item.productId] = checkbox.isChecked
            else
                checks.remove(item.productId)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val button = layoutInflater
            .inflate(R.layout.update_list_item, parent, false) as RelativeLayout
        return ProductViewHolder(button)
    }
}
