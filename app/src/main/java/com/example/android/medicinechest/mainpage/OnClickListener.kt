package com.example.android.medicinechest.mainpage

import com.example.android.medicinechest.database.Inventory

interface OnClickListener {
    fun onClick(position: Int, model: Inventory)
}