package com.example.android.medicinechest.mainpage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.android.medicinechest.R
import com.example.android.medicinechest.database.Inventory
import com.example.android.medicinechest.database.MedicineChestDatabase
import com.example.android.medicinechest.databinding.FragmentMainPageBinding

class MainPageFragment : Fragment() {
    private lateinit var viewModel: MainPageViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding: FragmentMainPageBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_main_page, container, false)

        val application = requireNotNull(this.activity).application
        val dao = MedicineChestDatabase.getInstance(application).getMedicineChestDatabaseDao()
        val viewModelFactory = MainPageViewModelFactory(dao, application)
        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(MainPageViewModel::class.java)
        setHasOptionsMenu(true)

        val adapter = MainPageAdapter()
        val it = this
        adapter.setOnClickListener(object :
            OnClickListener {
            override fun onClick(position: Int, model: Inventory) {
                it.findNavController().navigate(
                    MainPageFragmentDirections
                        .actionMainPageFragmentToListPageFragment(model.listId, model.name)
                )
            }
        })
        binding.listList.adapter = adapter

        viewModel.lists.observe(viewLifecycleOwner, Observer { lists ->
            if (lists != null)
                adapter.data = lists
        })

        binding.allProductsButton.setOnClickListener {
            this.findNavController().navigate(
                    MainPageFragmentDirections
                        .actionMainPageFragmentToListPageFragment(0, getString(R.string.list_all_page_title)))
        }

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.main_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.add_product_page_fragment -> {
                requireView().findNavController().navigate(
                    MainPageFragmentDirections
                        .actionMainPageFragmentToAddProductPageFragment(0L, false, "", "", 0, "", ""))
                return true
            }
            R.id.add_list_page_fragment -> {
                requireView().findNavController().navigate(
                    MainPageFragmentDirections
                        .actionMainPageFragmentToAddListPageFragment(
                            0L,
                            "",
                            false
                        ))
                return true
            }
            R.id.search_page_fragment -> {
                requireView().findNavController().navigate(
                    MainPageFragmentDirections
                        .actionMainPageFragmentToSearchPageFragment())
                return true
            }
            else -> {
                return super.onOptionsItemSelected(item)
            }
        }
        return true
    }
}