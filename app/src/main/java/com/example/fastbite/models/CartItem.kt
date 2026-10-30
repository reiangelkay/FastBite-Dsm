package com.example.fastbite.models

data class CartItem(
    val menuItem: MenuItem,
    var quantity: Int
) {
    val totalPrice: Double
        get() = menuItem.price * quantity
}
