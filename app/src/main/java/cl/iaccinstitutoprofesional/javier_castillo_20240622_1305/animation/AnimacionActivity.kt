package cl.iaccinstitutoprofesional.javier_castillo_20240622_1305.animation

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import cl.iaccinstitutoprofesional.javier_castillo_20240622_1305.R
import com.bumptech.glide.Glide

class AnimacionActivity : AppCompatActivity() {
    private lateinit var imageView: ImageView
    private lateinit var btnBack: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_animacion)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        imageView = findViewById(R.id.gifView)
        btnBack = findViewById(R.id.back)
        btnBack.setOnClickListener { finish() }
        Glide.with(this)
            .asGif()
            .load(R.raw.transport)
            .into(imageView)

        btnBack.setOnClickListener {
            finish()
        }
    }
}