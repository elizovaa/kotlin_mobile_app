package com.example.android.medicinechest.listpage

import android.content.res.Resources
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.android.medicinechest.database.Product
import com.example.android.medicinechest.R
import com.example.android.medicinechest.mainpage.ButtonViewHolder
import com.example.android.medicinechest.listpage.OnClickListener

class ProductViewHolder(val button: Button) : RecyclerView.ViewHolder(button) {
//    private val res: Resources = itemView.context.resources
//    private val productInfo: Button = itemView.findViewById(R.id.product_info)
//    private val name: TextView = itemView.findViewById(R.id.name)
//    private val amount: TextView = itemView.findViewById(R.id.amount)
//    private val type: TextView = itemView.findViewById(R.id.type)
//    private val dosage: TextView = itemView.findViewById(R.id.dosage)

//    fun bind(item: Product) {
//        val properties = listOf<String>(
//            item.productId.toString(),
//            item.name,
//            item.amount.toString(),
//            item.type,
//            item.dosage
//        )
//        productInfo.text = properties.joinToString(separator = "\n")
////        name.text = item.name
////        amount.text = item.amount.toString()
////        type.text = item.type
////        dosage.text = item.dosage
//    }

//    companion object {
//        fun from(parent: ViewGroup): ProductViewHolder {
//            val layoutInflater = LayoutInflater.from(parent.context)
//            val view = layoutInflater
//                .inflate(R.layout.list_item, parent, false)
//            return ProductViewHolder(view)
//        }
//    }
}

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
        holder.button.text = item.toString()
        holder.button.setOnClickListener {
            if (onClickListener != null) {
                onClickListener!!.onClick(position, item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val button = layoutInflater
            .inflate(R.layout.list_item, parent, false) as Button
        return ProductViewHolder(button)
    }

    fun setOnClickListener(onClickListener: OnClickListener) {
        this.onClickListener = onClickListener
    }
}
