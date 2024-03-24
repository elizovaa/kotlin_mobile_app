package com.example.android.medicinechest.productpage

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
import androidx.navigation.findNavController
import com.example.android.medicinechest.R
import com.example.android.medicinechest.database.MedicineChestDatabase
import com.example.android.medicinechest.databinding.FragmentProductPageBinding

class ProductPageFragment : Fragment() {
    private lateinit var viewModel: ProductPageViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding: FragmentProductPageBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_product_page, container, false)

        val args = ProductPageFragmentArgs.fromBundle(requireArguments())
        val application = requireNotNull(this.activity).application
        val dao = MedicineChestDatabase.getInstance(application).getMedicineChestDatabaseDao()
        val viewModelFactory = ProductPageViewModelFactory(args.id, dao, application)
        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(ProductPageViewModel::class.java)
        setHasOptionsMenu(true)

        binding.name.text = args.name
        binding.type.text = getString(R.string.type_info) + " " + args.type
        binding.amount.text = getString(R.string.amount_info) + " " + args.amount
        binding.dosage.text = getString(R.string.dosage_info) + " " + args.dosage
        binding.comment.text = "${args.comment}"
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.product_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val args = ProductPageFragmentArgs.fromBundle(requireArguments())
        when(item.itemId) {
            R.id.add_product_page_fragment -> {
                requireView().findNavController().navigate(
                    ProductPageFragmentDirections
                        .actionProductPageFragmentToAddProductPageFragment(
                            args.id,
                            true,
                            args.name,
                            args.type,
                            args.amount,
                            args.dosage,
                            args.comment
                        ))
                return true
            }
            R.id.main_page_fragment -> {
                viewModel.delete(args.id)
                requireView().findNavController().navigate(
                    ProductPageFragmentDirections
                        .actionProductPageFragmentToMainPageFragment())
                return true
            }
            R.id.update_composition_page_fragment -> {
                requireView().findNavController().navigate(
                    ProductPageFragmentDirections
                        .actionProductPageFragmentToUpdateCompositionPageFragment(
                            args.id,
                            args.name,
                            false,
                            args.type,
                            args.amount,
                            args.dosage,
                            args.comment
                        ))
                return true
            }
            else -> {
                return super.onOptionsItemSelected(item)
            }
        }
        return true
    }
}