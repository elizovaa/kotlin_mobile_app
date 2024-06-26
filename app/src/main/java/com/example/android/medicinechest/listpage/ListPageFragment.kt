package com.example.android.medicinechest.listpage

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.android.medicinechest.R
import com.example.android.medicinechest.database.MedicineChestDatabase
import com.example.android.medicinechest.databinding.FragmentListPageBinding
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.android.medicinechest.database.Product

class ListPageFragment : Fragment() {
    private lateinit var viewModel: ListPageViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding: FragmentListPageBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_list_page, container, false)

        val args = ListPageFragmentArgs.fromBundle(requireArguments())
        val application = requireNotNull(this.activity).application
        val dao = MedicineChestDatabase.getInstance(application).getMedicineChestDatabaseDao()
        val viewModelFactory = ListPageViewModelFactory(args.listId, dao, application)
        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(ListPageViewModel::class.java)
        setHasOptionsMenu(true)

        val adapter = ListPageAdapter(container!!.context)
        val it = this
        adapter.setOnClickListener(object :
            OnClickListener {
            override fun onClick(position: Int, model: Product) {
                it.findNavController().navigate(
                    ListPageFragmentDirections
                        .actionListPageFragmentToProductPageFragment(
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
        binding.productList.adapter = adapter
        binding.listName.text = args.name

        viewModel.products.observe(viewLifecycleOwner, Observer { products ->
            if (products != null)
                adapter.data = products
        })

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        val args = ListPageFragmentArgs.fromBundle(requireArguments())
        if (args.listId != 0L)
            inflater.inflate(R.menu.list_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val args = ListPageFragmentArgs.fromBundle(requireArguments())
        when(item.itemId) {
            R.id.add_list_page_fragment -> {
                requireView().findNavController().navigate(
                    ListPageFragmentDirections
                        .actionListPageFragmentToAddListPageFragment(
                            args.listId,
                            args.name,
                            true
                        ))
                return true
            }
            R.id.update_composition_page_fragment -> {
                requireView().findNavController().navigate(
                    ListPageFragmentDirections
                        .actionListPageFragmentToUpdateCompositionPageFragment(
                            args.listId,
                            args.name,
                            true,
                            "",
                            0,
                            "",
                            ""
                        ))
                return true
            }
            R.id.main_page_fragment -> {
                viewModel.delete(args.listId)
                requireView().findNavController().navigate(
                    ListPageFragmentDirections
                        .actionListPageFragmentToMainPageFragment())
                return true
            }
            else -> {
                return super.onOptionsItemSelected(item)
            }
        }
    }
}