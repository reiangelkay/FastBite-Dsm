package com.example.fastbite

import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fastbite.adapters.CartAdapter
import com.example.fastbite.utils.CartManager

class CartActivity : AppCompatActivity() {

    private lateinit var cartRecyclerView: RecyclerView
    private lateinit var cartAdapter: CartAdapter
    private lateinit var emptyState: LinearLayout
    private lateinit var checkoutCard: LinearLayout
    private lateinit var tvSubtotal: TextView
    private lateinit var tvDeliveryFee: TextView
    private lateinit var tvTotal: TextView
    private lateinit var btnCheckout: Button

    private val deliveryFee = 2.99

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        setupToolbar()
        setupViews()
        setupCartList()
        updateCartUI()
    }

    private fun setupToolbar() {
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        toolbar.setNavigationOnClickListener {
            finish()
        }
    }

    private fun setupViews() {
        cartRecyclerView = findViewById(R.id.cartRecyclerView)
        emptyState = findViewById(R.id.emptyState)
        checkoutCard = findViewById(R.id.checkoutCard)
        tvSubtotal = findViewById(R.id.tvSubtotal)
        tvDeliveryFee = findViewById(R.id.tvDeliveryFee)
        tvTotal = findViewById(R.id.tvTotal)
        btnCheckout = findViewById(R.id.btnCheckout)

        btnCheckout.setOnClickListener {
            onCheckoutClicked()
        }
    }

    private fun setupCartList() {
        cartAdapter = CartAdapter(CartManager.getCartItems()) {
            updateCartUI()
        }

        cartRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@CartActivity)
            adapter = cartAdapter
        }
    }

    private fun updateCartUI() {
        val cartItems = CartManager.getCartItems()
        cartAdapter.updateData(cartItems)

        if (cartItems.isEmpty()) {
            cartRecyclerView.visibility = android.view.View.GONE
            emptyState.visibility = android.view.View.VISIBLE
            checkoutCard.visibility = android.view.View.GONE
        } else {
            cartRecyclerView.visibility = android.view.View.VISIBLE
            emptyState.visibility = android.view.View.GONE
            checkoutCard.visibility = android.view.View.VISIBLE

            val subtotal = CartManager.getTotalPrice()
            val total = subtotal + deliveryFee

            tvSubtotal.text = "$${String.format("%.2f", subtotal)}"
            tvDeliveryFee.text = "$${String.format("%.2f", deliveryFee)}"
            tvTotal.text = "$${String.format("%.2f", total)}"
        }
    }

    private fun onCheckoutClicked() {
        Toast.makeText(
            this,
            "${getString(R.string.processing_order)} de $${String.format("%.2f", CartManager.getTotalPrice() + deliveryFee)}",
            Toast.LENGTH_SHORT
        ).show()

        // TODO: Implement checkout flow
        // For now, clear the cart
        CartManager.clearCart()
        updateCartUI()
    }

    override fun onResume() {
        super.onResume()
        updateCartUI()
    }
}
