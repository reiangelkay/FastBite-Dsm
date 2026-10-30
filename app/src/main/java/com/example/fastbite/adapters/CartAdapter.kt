package com.example.fastbite.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.fastbite.R
import com.example.fastbite.models.CartItem
import com.example.fastbite.utils.CartManager

class CartAdapter(
    private var cartItems: List<CartItem>,
    private val onQuantityChanged: () -> Unit
) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    inner class CartViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.findViewById(R.id.cartItemImage)
        val name: TextView = itemView.findViewById(R.id.cartItemName)
        val price: TextView = itemView.findViewById(R.id.cartItemPrice)
        val total: TextView = itemView.findViewById(R.id.cartItemTotal)
        val quantity: TextView = itemView.findViewById(R.id.tvQuantity)
        val btnIncrease: Button = itemView.findViewById(R.id.btnIncrease)
        val btnDecrease: Button = itemView.findViewById(R.id.btnDecrease)
        val btnRemove: Button = itemView.findViewById(R.id.btnRemove)

        fun bind(cartItem: CartItem) {
            name.text = cartItem.menuItem.name
            price.text = "$${String.format("%.2f", cartItem.menuItem.price)}"
            total.text = "Total: $${String.format("%.2f", cartItem.totalPrice)}"
            quantity.text = cartItem.quantity.toString()
            image.setImageResource(cartItem.menuItem.imageRes)

            btnIncrease.setOnClickListener {
                CartManager.updateQuantity(cartItem.menuItem.id, cartItem.quantity + 1)
                updateItem()
            }

            btnDecrease.setOnClickListener {
                CartManager.updateQuantity(cartItem.menuItem.id, cartItem.quantity - 1)
                updateItem()
            }

            btnRemove.setOnClickListener {
                CartManager.removeFromCart(cartItem.menuItem.id)
                updateItem()
            }
        }

        private fun updateItem() {
            cartItems = CartManager.getCartItems()
            notifyDataSetChanged()
            onQuantityChanged()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_cart, parent, false)
        return CartViewHolder(view)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        holder.bind(cartItems[position])
    }

    override fun getItemCount(): Int = cartItems.size

    fun updateData(newItems: List<CartItem>) {
        cartItems = newItems
        notifyDataSetChanged()
    }
}
