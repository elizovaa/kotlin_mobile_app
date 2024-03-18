package com.example.android.medicinechest.searchpage

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.android.medicinechest.R
import com.example.android.medicinechest.database.ProductDatabase
import androidx.navigation.fragment.findNavController
import com.example.android.medicinechest.databinding.FragmentAddProductPageBinding
import com.example.android.medicinechest.databinding.FragmentSearchPageBinding

class SearchPageFragment : Fragment() {
    private lateinit var viewModel: SearchPageViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding: FragmentSearchPageBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_search_page, container, false)

        val application = requireNotNull(this.activity).application
        val dao = ProductDatabase.getInstance(application).getProductDatabaseDao()
        val viewModelFactory = SearchPageViewModelFactory(dao, application)
        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(SearchPageViewModel::class.java)

        return binding.root
    }
}