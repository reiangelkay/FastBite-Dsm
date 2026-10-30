package com.example.fastbite.models

import java.io.Serializable

data class Restaurant(
    val id: String,
    val name: String,
    val category: String,
    val rating: Double,
    val deliveryTime: String,
    val deliveryFee: String,
    val imageRes: Int,
    val address: String
) : Serializable
