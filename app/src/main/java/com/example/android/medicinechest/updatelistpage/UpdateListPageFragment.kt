package com.example.android.medicinechest.updatelistpage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.android.medicinechest.R
import com.example.android.medicinechest.database.MedicineChestDatabase
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.android.medicinechest.databinding.FragmentUpdateListPageBinding

class UpdateListPageFragment : Fragment() {
    private lateinit var viewModel: UpdateListPageViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding: FragmentUpdateListPageBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_update_list_page, container, false)

        val args = UpdateListPageFragmentArgs.fromBundle(requireArguments())
        val application = requireNotNull(this.activity).application
        val dao = MedicineChestDatabase.getInstance(application).getMedicineChestDatabaseDao()
        val viewModelFactory = UpdateListPageViewModelFactory(args.listId, dao, application)
        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(UpdateListPageViewModel::class.java)

        val adapter = UpdateListPageAdapter()
        binding.productList.adapter = adapter
        binding.listName.text = args.name

//        adapter.data = viewModel.products
        viewModel.products.observe(viewLifecycleOwner, Observer { products ->
            if (products != null)
                adapter.data = products
        })

        binding.saveListButton.setOnClickListener {
            viewModel.prepareForNavigationToList(adapter.checks)
        }


        viewModel.navigateToSave.observe(viewLifecycleOwner,  Observer { shouldNavigate ->
            if (shouldNavigate!!) {
                this.findNavController().navigate(
                    UpdateListPageFragmentDirections
                        .actionUpdateListPageFragmentToListPageFragment(args.listId, args.name)
                )
                viewModel.doneNavigating()
            }
        })

        return binding.root
    }
}