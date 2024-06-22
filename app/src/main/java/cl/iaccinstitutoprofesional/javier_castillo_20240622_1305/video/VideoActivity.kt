package cl.iaccinstitutoprofesional.javier_castillo_20240622_1305.video

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.MediaController
import android.widget.VideoView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import cl.iaccinstitutoprofesional.javier_castillo_20240622_1305.R

class VideoActivity : AppCompatActivity() {
    private lateinit var videoView: VideoView
    private lateinit var backButton: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_video)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        videoView = findViewById(R.id.videoView)
        backButton = findViewById(R.id.back)
        val mediaController = MediaController(this)
        val uriPath = "android.resource://" + packageName + "/" + R.raw.video
        val uri = Uri.parse(uriPath)
        videoView.setMediaController(mediaController)
        videoView.setVideoURI(uri)
        videoView.start()
        backButton.setOnClickListener {
            finish()
        }
    }
}