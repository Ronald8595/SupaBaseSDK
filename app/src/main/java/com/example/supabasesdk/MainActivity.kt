package com.example.supabasesdk

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.supabasesdk.data.SupabaseManager
import com.example.supabasesdk.models.Alumno
import com.example.supabasesdk.utils.SupabaseErrorHandler
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.Order
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var txtAlumnos: EditText
    private lateinit var progressMaterias: ProgressBar
    private val supabaseManager = SupabaseManager()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        txtAlumnos = findViewById(R.id.txtAlumnos)
        progressMaterias = findViewById(R.id.progressMaterias)

        findViewById<Button>(R.id.btnConsultar)
            .setOnClickListener { consultarAlumnos() }

        consultarAlumnos()
    }

    private fun consultarAlumnos() {
        lifecycleScope.launch {

            try {
                val alumnos = supabaseManager.client
                    .from("alumnos")
                    .select {
                        order("nombres", Order.ASCENDING)
                    }
                    .decodeList<Alumno>()

                val resultado = if (alumnos.isEmpty()) {
                    "No se encontraron alumnos."
                } else {
                    alumnos.joinToString("\n\n") { a ->
                        "Nombre: ${a.nombres}\n" +
                                "Paralelo: ${a.paralelo}\n" +
                                "Correo: ${a.correo ?: "Sin registrar"}\n" +
                                "Teléfono: ${a.telefono ?: "Sin registrar"}"
                    }
                }
                txtAlumnos.setText(resultado)
            } catch (e: Exception) {
                SupabaseErrorHandler.show(this@MainActivity, e)
            } finally {

            }
        }
    }
}
