package com.example.fastbite.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.fastbite.R
import com.example.fastbite.models.MenuItem

class MenuAdapter(
    private val menuItems: List<MenuItem>,
    private val onAddToCart: (MenuItem) -> Unit
) : RecyclerView.Adapter<MenuAdapter.MenuViewHolder>() {

    inner class MenuViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.findViewById(R.id.menuItemImage)
        val name: TextView = itemView.findViewById(R.id.menuItemName)
        val description: TextView = itemView.findViewById(R.id.menuItemDescription)
        val price: TextView = itemView.findViewById(R.id.menuItemPrice)
        val btnAdd: Button = itemView.findViewById(R.id.btnAddToCart)

        fun bind(menuItem: MenuItem) {
            name.text = menuItem.name
            description.text = menuItem.description
            price.text = "$${String.format("%.2f", menuItem.price)}"
            image.setImageResource(menuItem.imageRes)

            btnAdd.setOnClickListener {
                onAddToCart(menuItem)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_menu, parent, false)
        return MenuViewHolder(view)
    }

    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        holder.bind(menuItems[position])
    }

    override fun getItemCount(): Int = menuItems.size
}
