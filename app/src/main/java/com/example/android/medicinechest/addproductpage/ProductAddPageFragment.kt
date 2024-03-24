package com.example.android.medicinechest.addproductpage

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
import com.example.android.medicinechest.database.Product
import com.example.android.medicinechest.databinding.FragmentAddProductPageBinding

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

        if (args.update) {
            binding.titleText.text = getString(R.string.edit_button)
            binding.addProductButton.text = getString(R.string.save_button)
            binding.nameEditText.text.insert(0,  args.name)
            binding.typeEditText.text.insert(0,  args.type)
            binding.amountEditText.text.insert(0,  args.amount.toString())
            binding.dosageEditText.text.insert(0,  args.dosage)
            binding.commentEditText.text.insert(0,  args.comment)
        }

        binding.addProductButton.setOnClickListener {
            try {
                val name = validateNonEmptyText(binding.nameEditText, getString(R.string.name))
                val type = validateNonEmptyText(binding.typeEditText, getString(R.string.type))
                val amount = Integer.parseInt(validateNonEmptyText(binding.amountEditText, getString(R.string.amount)))
                val dosage = validateNonEmptyText(binding.dosageEditText, getString(R.string.dosage))
                val comment = binding.commentEditText.text.toString()
                val insertProduct = Product(
                    productId = args.id,
                    name = name,
                    type = type,
                    amount = amount,
                    dosage = dosage,
                    comment = comment
                )
                viewModel.prepareForNavigationToProduct(insertProduct, args.update)
            } catch (e: RuntimeException) {
                return@setOnClickListener
            }
        }

        viewModel.navigateToProduct.observe(viewLifecycleOwner,  Observer { shouldNavigate ->
            if (shouldNavigate!!) {
                val product = viewModel.product
                this.findNavController().navigate(ProductAddPageFragmentDirections
                    .actionAddProductPageFragmentToProductPageFragment(
                        product.productId,
                        product.name,
                        product.type,
                        product.amount,
                        product.dosage,
                        product.comment
                    ))
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