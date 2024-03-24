package com.example.android.medicinechest.searchpage

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.android.medicinechest.R
import com.example.android.medicinechest.database.MedicineChestDatabase
import com.example.android.medicinechest.database.Product
import com.example.android.medicinechest.databinding.FragmentSearchPageBinding
import com.example.android.medicinechest.listpage.OnClickListener

class SearchPageFragment : Fragment() {
    private lateinit var viewModel: SearchPageViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding: FragmentSearchPageBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_search_page, container, false)

        val application = requireNotNull(this.activity).application
        val dao = MedicineChestDatabase.getInstance(application).getMedicineChestDatabaseDao()
        val viewModelFactory = SearchPageViewModelFactory(dao, application)
        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(SearchPageViewModel::class.java)

        var productList = listOf<Product>()
        val adapter = SearchPageAdapter(productList)
        binding.productList.adapter = adapter
        val it = this
        adapter.setOnClickListener(object :
            OnClickListener {
            override fun onClick(position: Int, model: Product) {
                it.findNavController().navigate(
                    SearchPageFragmentDirections
                        .actionSearchPageFragmentToProductPageFragment(
                            model.productId,
                            model.name,
                            model.type,
                            model.amount,
                            model.dosage,
                            model.comment
                        )
                )
            }
        })

        viewModel.products.observe(viewLifecycleOwner, Observer { products ->
            if (products != null) {
                adapter.data = products
                adapter.productList = products
            }
        })

        binding.search.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filter.filter(newText)
                return false
            }
        })

        val searchIcon = binding.search.findViewById<ImageView>(androidx.appcompat.R.id.search_mag_icon)
        searchIcon.setColorFilter(Color.WHITE)
        val cancelIcon = binding.search.findViewById<ImageView>(androidx.appcompat.R.id.search_close_btn)
        cancelIcon.setColorFilter(Color.WHITE)
        val textView = binding.search.findViewById<TextView>(androidx.appcompat.R.id.search_src_text)
        textView.setTextColor(Color.WHITE)

        return binding.root
    }
}