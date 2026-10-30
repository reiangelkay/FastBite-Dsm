package com.example.fastbite

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fastbite.adapters.RestaurantAdapter
import com.example.fastbite.models.Restaurant

class HomeActivity : AppCompatActivity() {

    private lateinit var restaurantRecyclerView: RecyclerView
    private lateinit var searchBar: EditText
    private lateinit var restaurantAdapter: RestaurantAdapter
    private var restaurants: List<Restaurant> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        restaurantRecyclerView = findViewById(R.id.restaurantRecyclerView)
        searchBar = findViewById(R.id.searchBar)

        setupRestaurantList()
        setupSearch()
        setupCategoryFilters()
        setupBottomNavigation()
    }

    private fun setupRestaurantList() {
        restaurants = getSampleRestaurants()

        restaurantAdapter = RestaurantAdapter(restaurants) { restaurant ->
            onRestaurantClicked(restaurant)
        }

        restaurantRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@HomeActivity)
            adapter = restaurantAdapter
        }
    }

    private fun setupSearch() {
        searchBar.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                filterRestaurants(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun setupCategoryFilters() {
        val categoryAll = findViewById<TextView>(R.id.categoryAll)
        val categoryPizza = findViewById<TextView>(R.id.categoryPizza)
        val categoryBurger = findViewById<TextView>(R.id.categoryBurger)
        val categorySushi = findViewById<TextView>(R.id.categorySushi)
        val categoryMexican = findViewById<TextView>(R.id.categoryMexican)
        val categoryAsian = findViewById<TextView>(R.id.categoryAsian)

        categoryAll.setOnClickListener {
            updateCategorySelection(categoryAll)
            filterByCategory("Todos")
        }

        categoryPizza.setOnClickListener {
            updateCategorySelection(categoryPizza)
            filterByCategory("Pizza")
        }

        categoryBurger.setOnClickListener {
            updateCategorySelection(categoryBurger)
            filterByCategory("Hamburguesas")
        }

        categorySushi.setOnClickListener {
            updateCategorySelection(categorySushi)
            filterByCategory("Sushi")
        }

        categoryMexican.setOnClickListener {
            updateCategorySelection(categoryMexican)
            filterByCategory("Mexicana")
        }

        categoryAsian.setOnClickListener {
            updateCategorySelection(categoryAsian)
            filterByCategory("Asiática")
        }
    }

    private fun updateCategorySelection(selectedCategory: TextView) {
        val categories = listOf(
            findViewById<TextView>(R.id.categoryAll),
            findViewById<TextView>(R.id.categoryPizza),
            findViewById<TextView>(R.id.categoryBurger),
            findViewById<TextView>(R.id.categorySushi),
            findViewById<TextView>(R.id.categoryMexican),
            findViewById<TextView>(R.id.categoryAsian)
        )

        categories.forEach { category ->
            if (category == selectedCategory) {
                category.backgroundTintList = android.content.res.ColorStateList.valueOf(
                    android.graphics.Color.parseColor("#FF6B00")
                )
                category.setTextColor(android.graphics.Color.parseColor("#FFFFFF"))
            } else {
                category.backgroundTintList = android.content.res.ColorStateList.valueOf(
                    android.graphics.Color.parseColor("#F6F3F2")
                )
                category.setTextColor(android.graphics.Color.parseColor("#1C1B1B"))
            }
        }
    }

    private fun filterRestaurants(query: String) {
        val filtered = restaurants.filter { restaurant ->
            restaurant.name.contains(query, ignoreCase = true) ||
            restaurant.category.contains(query, ignoreCase = true)
        }
        restaurantAdapter = RestaurantAdapter(filtered) { restaurant ->
            onRestaurantClicked(restaurant)
        }
        restaurantRecyclerView.adapter = restaurantAdapter
    }

    private fun filterByCategory(category: String) {
        val filtered = if (category == "Todos") {
            restaurants
        } else {
            restaurants.filter { it.category == category }
        }
        restaurantAdapter = RestaurantAdapter(filtered) { restaurant ->
            onRestaurantClicked(restaurant)
        }
        restaurantRecyclerView.adapter = restaurantAdapter
    }

    private fun onRestaurantClicked(restaurant: Restaurant) {
        val intent = Intent(this, RestaurantDetailActivity::class.java)
        intent.putExtra("restaurant", restaurant)
        startActivity(intent)
    }

    private fun setupBottomNavigation() {
        val navHome = findViewById<TextView>(R.id.navHome)
        val navSearch = findViewById<TextView>(R.id.navSearch)
        val navOrders = findViewById<TextView>(R.id.navOrders)
        val navProfile = findViewById<TextView>(R.id.navProfile)
        val cartIcon = findViewById<ImageView>(R.id.cartIcon)

        navProfile.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        cartIcon.setOnClickListener {
            val intent = Intent(this, CartActivity::class.java)
            startActivity(intent)
        }
    }

    private fun getSampleRestaurants(): List<Restaurant> {
        return listOf(
            Restaurant(
                id = "1",
                name = "Burger Palace",
                category = "Hamburguesas",
                rating = 4.5,
                deliveryTime = "25-35 min",
                deliveryFee = "$2.99",
                imageRes = R.drawable.banner_hamburguesa,
                address = "Av. Principal 123"
            ),
            Restaurant(
                id = "2",
                name = "Pizza Hut",
                category = "Pizza",
                rating = 4.2,
                deliveryTime = "30-40 min",
                deliveryFee = "$3.50",
                imageRes = R.drawable.banner_hamburguesa,
                address = "Calle 456"
            ),
            Restaurant(
                id = "3",
                name = "Sushi Express",
                category = "Sushi",
                rating = 4.8,
                deliveryTime = "20-30 min",
                deliveryFee = "$4.00",
                imageRes = R.drawable.banner_hamburguesa,
                address = "Plaza Central"
            ),
            Restaurant(
                id = "4",
                name = "Tacos Locos",
                category = "Mexicana",
                rating = 4.6,
                deliveryTime = "15-25 min",
                deliveryFee = "$1.99",
                imageRes = R.drawable.banner_hamburguesa,
                address = "Av. México 789"
            ),
            Restaurant(
                id = "5",
                name = "Wok House",
                category = "Asiática",
                rating = 4.3,
                deliveryTime = "35-45 min",
                deliveryFee = "$3.99",
                imageRes = R.drawable.banner_hamburguesa,
                address = "Calle Asia 321"
            ),
            Restaurant(
                id = "6",
                name = "McDonald's",
                category = "Hamburguesas",
                rating = 4.0,
                deliveryTime = "20-30 min",
                deliveryFee = "$2.50",
                imageRes = R.drawable.banner_hamburguesa,
                address = "Centro Comercial"
            )
        )
    }
}
