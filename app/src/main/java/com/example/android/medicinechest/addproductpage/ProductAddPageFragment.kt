package com.example.android.medicinechest.addproductpage

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
import com.example.android.medicinechest.databinding.FragmentAddProductPageBinding
import com.example.android.medicinechest.listpage.ListPageFragmentArgs
import com.example.android.medicinechest.productpage.ProductPageFragmentArgs

class ProductAddPageFragment : Fragment() {
    private lateinit var viewModel: ProductAddPageViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding: FragmentAddProductPageBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_add_product_page, container, false)

        val args = ProductAddPageFragmentArgs.fromBundle(requireArguments())
        val application = requireNotNull(this.activity).application
        val dao = MedicineChestDatabase.getInstance(application).getMedicineChestDatabaseDao()
        val viewModelFactory = ProductAddPageViewModelFactory(dao, application)
        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(ProductAddPageViewModel::class.java)

        binding.nameEditText.text.insert(0,  args.name)
        binding.typeEditText.text.insert(0,  args.type)
        binding.amountEditText.text.insert(0,  args.amount.toString())
        binding.dosageEditText.text.insert(0,  args.dosage)
        binding.commentEditText.text.insert(0,  args.comment)

        binding.cancelButton.setOnClickListener {
            this.findNavController().navigate(
                ProductAddPageFragmentDirections
                    .actionAddProductPageFragmentToMainPageFragment()
            )
        }

        binding.addProductButton.setOnClickListener {
            try {
                val name = validateNonEmptyText(binding.nameEditText, "наименование")
                val type = validateNonEmptyText(binding.typeEditText, "формат выпуска")
                val amount = Integer.parseInt(validateNonEmptyText(binding.amountEditText, "количество"))
                val dosage = validateNonEmptyText(binding.dosageEditText, "дозировка")
                val comment = binding.commentEditText.text.toString()
                viewModel.prepareForNavigationToAdd(name, type, amount, dosage, comment)
            } catch (e: RuntimeException) {
                return@setOnClickListener
            }
        }

        viewModel.navigateToAdd.observe(viewLifecycleOwner,  Observer { shouldNavigate ->
            if (shouldNavigate!!) {
                val product = viewModel.product!!
                this.findNavController().navigate(ProductAddPageFragmentDirections
                    .actionAddProductPageFragmentToProductPageFragment(
                        product.name,
                        product.type,
                        product.amount,
                        product.dosage,
                        product.comment,
                        product.productId
                    ))
                viewModel.doneNavigating()
            }
        })
        return binding.root
    }

    private fun validateNonEmptyText(editText: EditText, nameForError: String): String {
        if (editText.text.toString().isEmpty()) {
            editText.error = "Поле $nameForError обязательно для заполнения"
            Log.i("ProductAddPageFragment", "Поле $nameForError обязательно для заполнения")
            throw RuntimeException()
        }
        return editText.text.toString()
    }

//    private fun validateIntegerText(editText: EditText, nameForError: String): Boolean {
//        try {
//            Integer.parseInt(editText.text.toString())
//        } catch (e: NumberFormatException) {
//            editText.error = "Поле $nameForError должно содержать целое число"
//            return false
//        }
//        return true
//    }
}