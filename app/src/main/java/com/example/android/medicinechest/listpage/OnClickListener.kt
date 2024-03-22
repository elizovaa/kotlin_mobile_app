package com.example.android.medicinechest.listpage

import com.example.android.medicinechest.database.Product

interface OnClickListener {
    fun onClick(position: Int, model: Product)
}