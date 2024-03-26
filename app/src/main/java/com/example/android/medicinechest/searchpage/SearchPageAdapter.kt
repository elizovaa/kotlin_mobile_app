package com.example.android.medicinechest.searchpage

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.android.medicinechest.database.Product
import com.example.android.medicinechest.R
import com.example.android.medicinechest.listpage.OnClickListener

class ProductViewHolder(val button: LinearLayout) : RecyclerView.ViewHolder(button)

class SearchPageAdapter(var productList: List<Product>): RecyclerView.Adapter<ProductViewHolder>(), Filterable {
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

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                var resultList = listOf<Product>()
                if (charSearch.isEmpty())
                    resultList = productList
                else {
                    val result = mutableListOf<Product>()
                    for (product in productList) {
                        if (product.name.lowercase().contains(charSearch.lowercase()))
                            result.add(product)
                    }
                    resultList = result
                }
                val filterResults = FilterResults()
                filterResults.values = resultList
                return filterResults
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                data = results?.values as List<Product>
            }
        }
    }
}
