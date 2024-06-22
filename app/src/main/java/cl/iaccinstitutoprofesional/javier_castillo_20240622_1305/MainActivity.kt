package cl.iaccinstitutoprofesional.javier_castillo_20240622_1305

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import cl.iaccinstitutoprofesional.javier_castillo_20240622_1305.animation.AnimacionActivity
import cl.iaccinstitutoprofesional.javier_castillo_20240622_1305.capture.CapturaActivity
import cl.iaccinstitutoprofesional.javier_castillo_20240622_1305.driver.ConductoresActivity
import cl.iaccinstitutoprofesional.javier_castillo_20240622_1305.grafico.GraficoActivity
import cl.iaccinstitutoprofesional.javier_castillo_20240622_1305.video.VideoActivity

class MainActivity : AppCompatActivity() {
    private lateinit var btn_animacion: Button
    private lateinit var btn_video: Button
    private lateinit var btn_conductores: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        btn_animacion = findViewById(R.id.btnAnimation)
        btn_video = findViewById(R.id.btnVideo)
        btn_conductores = findViewById(R.id.btnDrivers)

        btn_animacion.setOnClickListener {
            startActivity(Intent(this, AnimacionActivity::class.java))
        }

        btn_video.setOnClickListener {
            startActivity(Intent(this, VideoActivity::class.java))
        }

        btn_conductores.setOnClickListener {
            startActivity(Intent(this, ConductoresActivity::class.java))
        }

    }
}