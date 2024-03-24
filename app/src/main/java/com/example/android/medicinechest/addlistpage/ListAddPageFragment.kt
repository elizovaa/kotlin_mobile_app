package com.example.android.medicinechest.addlistpage

import android.os.Bundle
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
import com.example.android.medicinechest.database.Inventory
import com.example.android.medicinechest.databinding.FragmentAddListPageBinding

class ListAddPageFragment : Fragment() {
    private lateinit var viewModel: ListAddPageViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding: FragmentAddListPageBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_add_list_page, container, false)

        val args = ListAddPageFragmentArgs.fromBundle(requireArguments())
        val application = requireNotNull(this.activity).application
        val dao = MedicineChestDatabase.getInstance(application).getMedicineChestDatabaseDao()
        val viewModelFactory = ListAddPageViewModelFactory(dao, application)
        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(ListAddPageViewModel::class.java)

        if (args.update) {
            binding.titleText.text = getString(R.string.edit_button)
            binding.addButton.text = getString(R.string.save_button)
            binding.nameEditText.text.insert(0,  args.name)
        }

        binding.addButton.setOnClickListener {
            try {
                val name = validateNonEmptyText(binding.nameEditText, getString(R.string.name))
                val insertList = Inventory(
                    listId = args.id,
                    name = name
                )
                viewModel.prepareForNavigationToList(insertList, args.update)
            } catch (e: RuntimeException) {
                return@setOnClickListener
            }
        }

        viewModel.navigateToList.observe(viewLifecycleOwner,  Observer { shouldNavigate ->
            if (shouldNavigate!!) {
                val list = viewModel.list
                this.findNavController().navigate(ListAddPageFragmentDirections
                    .actionAddListPageFragmentToListPageFragment(list.listId, list.name))
                viewModel.doneNavigating()
            }
        })
        return binding.root
    }

    private fun validateNonEmptyText(editText: EditText, nameForError: String): String {
        if (editText.text.toString().isEmpty()) {
            editText.error = "Поле $nameForError обязательно для заполнения"
            throw RuntimeException()
        }
        return editText.text.toString()
    }
}