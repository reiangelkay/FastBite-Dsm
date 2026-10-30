package com.example.fastbite

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fastbite.adapters.MenuAdapter
import com.example.fastbite.models.MenuItem
import com.example.fastbite.models.Restaurant
import com.example.fastbite.utils.CartManager

class RestaurantDetailActivity : AppCompatActivity() {

    private lateinit var restaurant: Restaurant
    private lateinit var menuRecyclerView: RecyclerView
    private lateinit var menuAdapter: MenuAdapter
    private var menuItems: List<MenuItem> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restaurant_detail)

        // Get restaurant data from intent
        @Suppress("DEPRECATION")
        restaurant = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
            intent.getSerializableExtra("restaurant", Restaurant::class.java)!!
        } else {
            intent.getSerializableExtra("restaurant") as Restaurant
        }

        setupToolbar()
        setupRestaurantInfo()
        setupMenuList()
    }

    private fun setupToolbar() {
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        toolbar.setNavigationOnClickListener {
            finish()
        }
    }

    private fun setupRestaurantInfo() {
        val headerImage = findViewById<ImageView>(R.id.restaurantHeaderImage)
        val name = findViewById<TextView>(R.id.restaurantName)
        val category = findViewById<TextView>(R.id.restaurantCategory)
        val rating = findViewById<TextView>(R.id.restaurantRating)
        val deliveryTime = findViewById<TextView>(R.id.restaurantDeliveryTime)
        val deliveryFee = findViewById<TextView>(R.id.restaurantDeliveryFee)
        val address = findViewById<TextView>(R.id.restaurantAddress)

        name.text = restaurant.name
        category.text = restaurant.category
        rating.text = restaurant.rating.toString()
        deliveryTime.text = restaurant.deliveryTime
        deliveryFee.text = "Envío: ${restaurant.deliveryFee}"
        address.text = restaurant.address
        headerImage.setImageResource(restaurant.imageRes)
    }

    private fun setupMenuList() {
        menuItems = getSampleMenuItems(restaurant.category)

        menuAdapter = MenuAdapter(menuItems) { menuItem ->
            onAddToCart(menuItem)
        }

        menuRecyclerView = findViewById(R.id.menuRecyclerView)
        menuRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@RestaurantDetailActivity)
            adapter = menuAdapter
        }
    }

    private fun onAddToCart(menuItem: MenuItem) {
        CartManager.addToCart(menuItem)
        Toast.makeText(
            this,
            "${getString(R.string.added_to_cart)}: ${menuItem.name}",
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun getSampleMenuItems(category: String): List<MenuItem> {
        return when (category) {
            "Hamburguesas" -> listOf(
                MenuItem(
                    id = "1",
                    name = "Hamburguesa Clásica",
                    description = "Carne de res, lechuga, tomate, cebolla y salsa especial",
                    price = 12.99,
                    imageRes = R.drawable.banner_hamburguesa,
                    category = "Hamburguesas"
                ),
                MenuItem(
                    id = "2",
                    name = "Hamburguesa con Queso",
                    description = "Doble carne, queso cheddar, bacon y cebolla caramelizada",
                    price = 15.99,
                    imageRes = R.drawable.banner_hamburguesa,
                    category = "Hamburguesas"
                ),
                MenuItem(
                    id = "3",
                    name = "Hamburguesa Pollo",
                    description = "Pollo crispy, lechuga, mayonesa y pepinillos",
                    price = 11.99,
                    imageRes = R.drawable.banner_hamburguesa,
                    category = "Hamburguesas"
                ),
                MenuItem(
                    id = "4",
                    name = "Papas Fritas",
                    description = "Papas fritas crujientes con sal",
                    price = 4.99,
                    imageRes = R.drawable.banner_hamburguesa,
                    category = "Acompañamientos"
                ),
                MenuItem(
                    id = "5",
                    name = "Refresco Grande",
                    description = "Coca-Cola, Sprite o Fanta",
                    price = 3.99,
                    imageRes = R.drawable.banner_hamburguesa,
                    category = "Bebidas"
                )
            )
            "Pizza" -> listOf(
                MenuItem(
                    id = "1",
                    name = "Pizza Pepperoni",
                    description = "Salsa de tomate, mozzarella y pepperoni",
                    price = 18.99,
                    imageRes = R.drawable.banner_hamburguesa,
                    category = "Pizza"
                ),
                MenuItem(
                    id = "2",
                    name = "Pizza Hawaiana",
                    description = "Salsa de tomate, mozzarella, piña y jamón",
                    price = 19.99,
                    imageRes = R.drawable.banner_hamburguesa,
                    category = "Pizza"
                ),
                MenuItem(
                    id = "3",
                    name = "Pizza Vegetariana",
                    description = "Salsa de tomate, mozzarella, champiñones y pimientos",
                    price = 17.99,
                    imageRes = R.drawable.banner_hamburguesa,
                    category = "Pizza"
                ),
                MenuItem(
                    id = "4",
                    name = "Alitas de Pollo",
                    description = "10 alitas con salsa buffalo",
                    price = 12.99,
                    imageRes = R.drawable.banner_hamburguesa,
                    category = "Acompañamientos"
                )
            )
            "Sushi" -> listOf(
                MenuItem(
                    id = "1",
                    name = "California Roll",
                    description = "Cangrejo, aguacate, pepino y semillas de sésamo",
                    price = 14.99,
                    imageRes = R.drawable.banner_hamburguesa,
                    category = "Sushi"
                ),
                MenuItem(
                    id = "2",
                    name = "Spicy Tuna Roll",
                    description = "Atún picante, pepino y salsa de ají",
                    price = 16.99,
                    imageRes = R.drawable.banner_hamburguesa,
                    category = "Sushi"
                ),
                MenuItem(
                    id = "3",
                    name = "Sashimi Mixto",
                    description = "12 piezas de salmón, atún y camarón",
                    price = 22.99,
                    imageRes = R.drawable.banner_hamburguesa,
                    category = "Sushi"
                ),
                MenuItem(
                    id = "4",
                    name = "Miso Soup",
                    description = "Sopa de miso tradicional con tofu y alga wakame",
                    price = 5.99,
                    imageRes = R.drawable.banner_hamburguesa,
                    category = "Sopas"
                )
            )
            "Mexicana" -> listOf(
                MenuItem(
                    id = "1",
                    name = "Tacos al Pastor",
                    description = "3 tacos con carne al pastor, piña y cilantro",
                    price = 9.99,
                    imageRes = R.drawable.banner_hamburguesa,
                    category = "Tacos"
                ),
                MenuItem(
                    id = "2",
                    name = "Burrito Supreme",
                    description = "Carne asada, arroz, frijoles, guacamole y crema",
                    price = 12.99,
                    imageRes = R.drawable.banner_hamburguesa,
                    category = "Burritos"
                ),
                MenuItem(
                    id = "3",
                    name = "Quesadilla Grande",
                    description = "Queso Oaxaca, pollo y pimientos",
                    price = 10.99,
                    imageRes = R.drawable.banner_hamburguesa,
                    category = "Quesadillas"
                ),
                MenuItem(
                    id = "4",
                    name = "Nachos con Guacamole",
                    description = "Nachos crujientes con guacamole casero",
                    price = 8.99,
                    imageRes = R.drawable.banner_hamburguesa,
                    category = "Acompañamientos"
                )
            )
            "Asiática" -> listOf(
                MenuItem(
                    id = "1",
                    name = "Pad Thai",
                    description = "Fideos de arroz con camarones, maní y vegetales",
                    price = 15.99,
                    imageRes = R.drawable.banner_hamburguesa,
                    category = "Fideos"
                ),
                MenuItem(
                    id = "2",
                    name = "Pollo Kung Pao",
                    description = "Pollo con cacahuetes, pimientos y salsa picante",
                    price = 14.99,
                    imageRes = R.drawable.banner_hamburguesa,
                    category = "Pollo"
                ),
                MenuItem(
                    id = "3",
                    name = "Arroz Frito",
                    description = "Arroz frito con huevo, vegetales y cerdo",
                    price = 11.99,
                    imageRes = R.drawable.banner_hamburguesa,
                    category = "Arroz"
                ),
                MenuItem(
                    id = "4",
                    name = "Rollitos Primavera",
                    description = "6 rollitos de primavera crujientes",
                    price = 7.99,
                    imageRes = R.drawable.banner_hamburguesa,
                    category = "Aperitivos"
                )
            )
            else -> listOf(
                MenuItem(
                    id = "1",
                    name = "Plato Especial",
                    description = "Delicioso plato especial del chef",
                    price = 15.99,
                    imageRes = R.drawable.banner_hamburguesa,
                    category = "Especial"
                )
            )
        }
    }
}
