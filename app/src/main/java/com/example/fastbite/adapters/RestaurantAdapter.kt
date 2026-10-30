package com.example.fastbite.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.fastbite.R
import com.example.fastbite.models.Restaurant

class RestaurantAdapter(
    private val restaurants: List<Restaurant>,
    private val onRestaurantClick: (Restaurant) -> Unit
) : RecyclerView.Adapter<RestaurantAdapter.RestaurantViewHolder>() {

    inner class RestaurantViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.findViewById(R.id.restaurantImage)
        val name: TextView = itemView.findViewById(R.id.restaurantName)
        val category: TextView = itemView.findViewById(R.id.restaurantCategory)
        val rating: TextView = itemView.findViewById(R.id.restaurantRating)
        val deliveryTime: TextView = itemView.findViewById(R.id.restaurantDeliveryTime)
        val deliveryFee: TextView = itemView.findViewById(R.id.restaurantDeliveryFee)

        fun bind(restaurant: Restaurant) {
            name.text = restaurant.name
            category.text = restaurant.category
            rating.text = restaurant.rating.toString()
            deliveryTime.text = restaurant.deliveryTime
            deliveryFee.text = "Envío: ${restaurant.deliveryFee}"
            image.setImageResource(restaurant.imageRes)

            itemView.setOnClickListener {
                onRestaurantClick(restaurant)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestaurantViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_restaurant, parent, false)
        return RestaurantViewHolder(view)
    }

    override fun onBindViewHolder(holder: RestaurantViewHolder, position: Int) {
        holder.bind(restaurants[position])
    }

    override fun getItemCount(): Int = restaurants.size
}
