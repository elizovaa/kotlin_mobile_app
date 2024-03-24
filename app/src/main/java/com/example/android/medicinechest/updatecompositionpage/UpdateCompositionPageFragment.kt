package com.example.android.medicinechest.updatecompositionpage

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
import com.example.android.medicinechest.databinding.FragmentUpdateCompositionPageBinding

class UpdateCompositionPageFragment : Fragment() {
    private lateinit var viewModel: UpdateCompositionPageViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding: FragmentUpdateCompositionPageBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_update_composition_page, container, false)

        val args = UpdateCompositionPageFragmentArgs.fromBundle(requireArguments())
        val application = requireNotNull(this.activity).application
        val dao = MedicineChestDatabase.getInstance(application).getMedicineChestDatabaseDao()
        val viewModelFactory = UpdateCompositionPageViewModelFactory(args.isUpdateList, args.objectId, dao, application)
        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(UpdateCompositionPageViewModel::class.java)

        val adapter = UpdateCompositionPageAdapter()
        binding.list.adapter = adapter
        binding.objectName.text = args.name

        viewModel.objects.observe(viewLifecycleOwner, Observer { products ->
            if (products != null)
                adapter.data = products
        })

        binding.saveListButton.setOnClickListener {
            viewModel.prepareForNavigationToObject(adapter.checks)
        }

        viewModel.navigateToSave.observe(viewLifecycleOwner,  Observer { shouldNavigate ->
            if (shouldNavigate!!) {
                if (args.isUpdateList)
                    this.findNavController().navigate(
                        UpdateCompositionPageFragmentDirections
                            .actionUpdateListPageFragmentToListPageFragment(args.objectId, args.name)
                    )
                else
                    this.findNavController().navigate(
                        UpdateCompositionPageFragmentDirections
                            .actionUpdateListPageFragmentToProductPageFragment(
                                args.objectId,
                                args.name,
                                args.type,
                                args.amount,
                                args.dosage,
                                args.comment
                            )
                    )
                viewModel.doneNavigating()
            }
        })

        return binding.root
    }
}