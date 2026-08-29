package com.example.fastbite

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // Inicializar Firebase Auth
        auth = FirebaseAuth.getInstance()

        // Manejo de Insets para diseño Edge-to-Edge
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Vincular vistas de perfil
        val inputNombre = findViewById<EditText>(R.id.input_nombre_perfil)
        val inputCorreo = findViewById<EditText>(R.id.input_correo_perfil)
        val btnGuardar = findViewById<Button>(R.id.btnGuardarPerfil)

        // Cargar datos actuales del usuario (si está autenticado)
        val currentUser = auth.currentUser
        currentUser?.let {
            inputNombre.setText(it.displayName ?: "Carlos Mendoza")
            inputCorreo.setText(it.email ?: "carlos.mendoza@email.com")
        }

        // Acción de guardar perfil
        btnGuardar.setOnClickListener {
            val nuevoNombre = inputNombre.text.toString().trim()
            val nuevoCorreo = inputCorreo.text.toString().trim()

            if (nuevoNombre.isNotEmpty() && nuevoCorreo.isNotEmpty()) {
                // Aquí se integra la lógica para guardar en Realtime Database o SharedPreferences
                Toast.makeText(this, "Datos actualizados: $nuevoNombre", Toast.LENGTH_SHORT).show()
                
                // Limpiar el foco de edición tras guardar
                inputNombre.clearFocus()
                inputCorreo.clearFocus()
            } else {
                Toast.makeText(this, "Por favor, completa nombre y correo", Toast.LENGTH_SHORT).show()
            }
        }

        // Configuración del botón de Cerrar Sesión
        val btnLogout = findViewById<Button>(R.id.btnLogout)
        btnLogout.setOnClickListener {
            // Cerrar sesión en Firebase
            auth.signOut()
            Toast.makeText(this, "Sesión cerrada correctamente", Toast.LENGTH_SHORT).show()
            
            // Redirigir al LoginActivity
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
