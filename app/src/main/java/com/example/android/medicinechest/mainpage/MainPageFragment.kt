package com.example.android.medicinechest.mainpage

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.android.medicinechest.R
import com.example.android.medicinechest.database.ProductDatabase
import com.example.android.medicinechest.databinding.FragmentMainPageBinding
import androidx.navigation.fragment.findNavController

class MainPageFragment : Fragment() {
    private lateinit var viewModel: MainPageViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding: FragmentMainPageBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_main_page, container, false)

        val application = requireNotNull(this.activity).application
        val dao = ProductDatabase.getInstance(application).getProductDatabaseDao()
        val viewModelFactory = MainPageViewModelFactory(dao, application)
        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(MainPageViewModel::class.java)
//
//        val adapter = SleepNightAdapter()
//        binding.sleepList.adapter = adapter

//        viewModel.nights.observe(viewLifecycleOwner, Observer { nights ->
//            if (nights != null)
//                adapter.data = nights
//        })
//
//        binding.allProductsButton.setOnClickListener {
//            this.findNavController().navigate(
//                    MainPageFragmentDirections
//                        .actionMainPageFragmentToListPageFragment())
//        }
//
//        binding.addProductButton.setOnClickListener {
//            this.findNavController().navigate(
//                MainPageFragmentDirections
//                    .actionMainPageFragmentToAddProductPageFragment()
//            )
//        }
        Log.i("MainPage", "start")
//        binding.stopButton.setOnClickListener {
//            viewModel.onStopTracking()
//        }
//        binding.clearButton.setOnClickListener {
//            viewModel.onClear()
//        }
//        viewModel.navigateToSleepQuality.observe(viewLifecycleOwner, Observer { night ->
//            if (night != null) {
//                this.findNavController().navigate(
//                    SleepTrackerFragmentDirections
//                        .actionSleepTrackerFragmentToSleepQualityFragment(night.nightId))
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
}