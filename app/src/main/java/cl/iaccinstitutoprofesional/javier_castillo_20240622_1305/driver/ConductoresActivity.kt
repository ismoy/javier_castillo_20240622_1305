package cl.iaccinstitutoprofesional.javier_castillo_20240622_1305.driver

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cl.iaccinstitutoprofesional.javier_castillo_20240622_1305.R
import cl.iaccinstitutoprofesional.javier_castillo_20240622_1305.adapter.ConductoresAdapter
import cl.iaccinstitutoprofesional.javier_castillo_20240622_1305.capture.CapturaActivity
import cl.iaccinstitutoprofesional.javier_castillo_20240622_1305.grafico.GraficoActivity
import cl.iaccinstitutoprofesional.javier_castillo_20240622_1305.model.Conductor

class ConductoresActivity : AppCompatActivity() {
    private lateinit var recyclerViewConductores: RecyclerView
    private lateinit var adapter: ConductoresAdapter
    private lateinit var btnChart: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_conductores)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        recyclerViewConductores = findViewById(R.id.recyclerViewConductores)
        btnChart = findViewById(R.id.btnChart)
        val conductores = listOf(
            Conductor("Juan Pérez","",20),
            Conductor("María Gómez","",25),
            Conductor("Carlos Ruiz","",40),
            Conductor("Ana Sánchez","",80),
            Conductor("Luis Fernández", "", 350),
            Conductor("Marta López", "", 200),
            Conductor("José Díaz", "", 150),
            Conductor("Lucía Torres", "", 180),
            Conductor("Pedro Martínez", "", 120),
            Conductor("Sofía Ramírez", "", 220)
        )
        adapter = ConductoresAdapter(conductores, this)

        recyclerViewConductores.layoutManager = LinearLayoutManager(this)
        recyclerViewConductores.adapter = adapter

        btnChart.setOnClickListener {
            val intent = Intent(this, GraficoActivity::class.java)
            intent.putParcelableArrayListExtra("conductores", adapter.conductores.toCollection(ArrayList()))
            startActivity(intent)
        }

    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            val position = data?.getIntExtra(EXTRA_CONDUCTOR_POSITION, -1) ?: return
            val imageUri = data.getStringExtra(EXTRA_IMAGE_URI) ?: return
            adapter.updateConductorImage(position, imageUri)
        }
    }

    companion object {
        const val REQUEST_IMAGE_CAPTURE = 1
        const val EXTRA_CONDUCTOR_POSITION = "EXTRA_CONDUCTOR_POSITION"
        const val EXTRA_IMAGE_URI = "EXTRA_IMAGE_URI"
    }
}