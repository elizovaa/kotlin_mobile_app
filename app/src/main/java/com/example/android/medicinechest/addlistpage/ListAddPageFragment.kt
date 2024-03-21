package com.example.android.medicinechest.addlistpage

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
import com.example.android.medicinechest.database.MedicineChestDatabase
import androidx.navigation.fragment.findNavController
import com.example.android.medicinechest.databinding.FragmentAddListPageBinding
import com.example.android.medicinechest.databinding.FragmentAddProductPageBinding

class ListAddPageFragment : Fragment() {
    private lateinit var viewModel: ListAddPageViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding: FragmentAddListPageBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_add_list_page, container, false)

        val application = requireNotNull(this.activity).application
        val dao = MedicineChestDatabase.getInstance(application).getMedicineChestDatabaseDao()
        val viewModelFactory = ListAddPageViewModelFactory(dao, application)
        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(ListAddPageViewModel::class.java)

        binding.cancelButton.setOnClickListener {
            this.findNavController().navigate(
                ListAddPageFragmentDirections
                    .actionAddListPageFragmentToMainPageFragment()
            )
        }

        binding.addButton.setOnClickListener {
            try {
                val name = validateNonEmptyText(binding.nameEditText, "название")
                viewModel.prepareForNavigationToAdd(name)
            } catch (e: RuntimeException) {
                return@setOnClickListener
            }
        }

        viewModel.navigateToAdd.observe(viewLifecycleOwner,  Observer { shouldNavigate ->
            if (shouldNavigate!!) {
                this.findNavController().navigate(ListAddPageFragmentDirections
                    .actionAddListPageFragmentToMainPageFragment())
                viewModel.doneNavigating()
            }
        })
        return binding.root
    }

    private fun validateNonEmptyText(editText: EditText, nameForError: String): String {
        if (editText.text.toString().isEmpty()) {
            editText.error = "Поле $nameForError обязательно для заполнения"
            Log.i("ListAddPageFragment", "Поле $nameForError обязательно для заполнения")
            throw RuntimeException()
        }
        return editText.text.toString()
    }
}