package com.example.fastbite.models

data class MenuItem(
    val id: String,
    val name: String,
    val description: String,
    val price: Double,
    val imageRes: Int,
    val category: String
)
