package com.example.fastbite.utils

import com.example.fastbite.models.CartItem
import com.example.fastbite.models.MenuItem

object CartManager {
    private val cartItems = mutableListOf<CartItem>()

    fun addToCart(menuItem: MenuItem) {
        val existingItem = cartItems.find { it.menuItem.id == menuItem.id }
        if (existingItem != null) {
            existingItem.quantity++
        } else {
            cartItems.add(CartItem(menuItem, 1))
        }
    }

    fun removeFromCart(menuItemId: String) {
        cartItems.removeAll { it.menuItem.id == menuItemId }
    }

    fun updateQuantity(menuItemId: String, quantity: Int) {
        val item = cartItems.find { it.menuItem.id == menuItemId }
        if (item != null) {
            if (quantity <= 0) {
                removeFromCart(menuItemId)
            } else {
                item.quantity = quantity
            }
        }
    }

    fun getCartItems(): List<CartItem> {
        return cartItems.toList()
    }

    fun getTotalPrice(): Double {
        return cartItems.sumOf { it.totalPrice }
    }

    fun getTotalItems(): Int {
        return cartItems.sumOf { it.quantity }
    }

    fun clearCart() {
        cartItems.clear()
    }

    fun isEmpty(): Boolean {
        return cartItems.isEmpty()
    }
}
