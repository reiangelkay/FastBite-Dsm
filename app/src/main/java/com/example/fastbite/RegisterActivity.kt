package com.example.fastbite

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class RegisterActivity : AppCompatActivity() {

    // Creamos la referencia del objeto FirebaseAuth y los componentes visuales
    private lateinit var auth: FirebaseAuth
    private lateinit var buttonRegister: Button
    private lateinit var textViewLogin: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        // Inicializamos el objeto FirebaseAuth
        auth = FirebaseAuth.getInstance()
        
        buttonRegister = findViewById(R.id.btnRegister)
        textViewLogin = findViewById(R.id.textViewLogin)

        buttonRegister.setOnClickListener {
            val email = findViewById<EditText>(R.id.txtEmail).text.toString().trim()
            val password = findViewById<EditText>(R.id.txtPass).text.toString().trim()
            
            if (email.isNotEmpty() && password.isNotEmpty()) {
                this.register(email, password)
            } else {
                Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show()
            }
        }

        // Evento manual para ir al Login si el usuario presiona "Ingresar"
        textViewLogin.setOnClickListener {
            this.goToLogin()
        }
    }

    private fun register(email: String, password: String) {
        // Utilizamos la función createUserWithEmailAndPassword que recibe correo y contraseña
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // CAMBIO CLAVE: Redirigimos al LoginActivity en lugar del MainActivity
                    Toast.makeText(this, "Cuenta creada con éxito. Inicia sesión.", Toast.LENGTH_LONG).show()
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                    finish() // Evita que el usuario regrese a esta pantalla presionando el botón "Atrás"
                }
            }.addOnFailureListener { exception ->
                Toast.makeText(applicationContext, "Error: ${exception.localizedMessage}", Toast.LENGTH_LONG).show()
            }
    }

    private fun goToLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
}
