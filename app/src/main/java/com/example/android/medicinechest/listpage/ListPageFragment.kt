package com.example.android.medicinechest.listpage

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
import androidx.navigation.ui.NavigationUI
import com.example.android.medicinechest.database.Inventory
import com.example.android.medicinechest.database.Product
import com.example.android.medicinechest.mainpage.MainPageFragmentDirections
import com.example.android.medicinechest.listpage.OnClickListener

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

        val adapter = ListPageAdapter()
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

//        binding.addButton.setOnClickListener {
//            viewModel.onTurnOnNavigateToAdd()
//        }

//        binding.clearButton.setOnClickListener {
//            viewModel.onClear()
//        }
//
//        binding.startButton.setOnClickListener {
//            viewModel.onStartTracking()
//        }
//        binding.stopButton.setOnClickListener {
//            viewModel.onStopTracking()
//        }
//        binding.clearButton.setOnClickListener {
//            viewModel.onClear()
//        }
//        viewModel.navigateToAdd.observe(viewLifecycleOwner, Observer { shouldNavigation ->
//            if (shouldNavigation!!) {
//                this.findNavController().navigate(
//                    ListPageFragmentDirections
//                        .actionListPageFragmentToAddProductPageFragment())
//                viewModel.doneNavigating()
//            }
//        })

//        viewModel.startButtonVisible.observe(viewLifecycleOwner, Observer { visible ->
//            binding.startButton.isEnabled = visible
//        })
//        viewModel.stopButtonVisible.observe(viewLifecycleOwner, Observer { visible ->
//            binding.stopButton.isEnabled = visible
//        })
//        viewModel.clearButtonVisible.observe(viewLifecycleOwner, Observer { visible ->
//            binding.clearButton.isEnabled = visible
//        })

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
            R.id.update_list_page_fragment -> {
                requireView().findNavController().navigate(
                    ListPageFragmentDirections
                        .actionListPageFragmentToUpdateListPageFragment(args.listId, args.name))
                return true
            }
            else -> {
                return super.onOptionsItemSelected(item)
            }
        }
    }
}