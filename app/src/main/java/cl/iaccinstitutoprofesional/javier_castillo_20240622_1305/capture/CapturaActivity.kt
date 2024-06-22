package cl.iaccinstitutoprofesional.javier_castillo_20240622_1305.capture

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import cl.iaccinstitutoprofesional.javier_castillo_20240622_1305.R
import cl.iaccinstitutoprofesional.javier_castillo_20240622_1305.driver.ConductoresActivity
import java.io.IOException
import java.io.OutputStream

class CapturaActivity : AppCompatActivity() {
    private val REQUEST_IMAGE_CAPTURE = 1
    private val REQUEST_PERMISSIONS = 2
    private lateinit var imageViewFoto: ImageView
    private lateinit var buttonCapturar: Button
    private var conductorPosition: Int = -1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_captura)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        conductorPosition = intent.getIntExtra(ConductoresActivity.EXTRA_CONDUCTOR_POSITION, -1)

        imageViewFoto = findViewById(R.id.imageViewFoto)
        buttonCapturar = findViewById(R.id.buttonCapturar)
        buttonCapturar.setOnClickListener {
            if (checkPermissions()) {
                dispatchTakePictureIntent()
            } else {
                requestPermissions()
            }
        }
        if (checkPermissions()) {
            dispatchTakePictureIntent()
        } else {
            requestPermissions()
        }
    }
    private fun checkPermissions(): Boolean {
        val cameraPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
        val writePermission = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        val readPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
        return cameraPermission == PackageManager.PERMISSION_GRANTED &&
                writePermission == PackageManager.PERMISSION_GRANTED &&
                readPermission == PackageManager.PERMISSION_GRANTED
    }

    private fun requestPermissions() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE),
            REQUEST_PERMISSIONS
        )
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_PERMISSIONS) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                dispatchTakePictureIntent()
            } else {
                Toast.makeText(this, "Permisos denegados", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun dispatchTakePictureIntent() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(packageManager)?.also {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            val imageBitmap = data?.extras?.get("data") as Bitmap
            imageViewFoto.setImageBitmap(imageBitmap)
            saveImageToMediaStore(imageBitmap)
        }
    }

    private fun saveImageToMediaStore(bitmap: Bitmap) {
        val contentValues = ContentValues().apply {
            put(MediaStore.Images.Media.DISPLAY_NAME, "IMG_${System.currentTimeMillis()}.jpg")
            put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
            put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/MyApp")
        }

        val uri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)

        if (uri != null) {
            try {
                val outputStream: OutputStream? = contentResolver.openOutputStream(uri)
                outputStream.use {
                    it?.let { it1 -> bitmap.compress(Bitmap.CompressFormat.JPEG, 100, it1) }
                    Toast.makeText(this, "Foto guardada en MediaStore", Toast.LENGTH_LONG).show()
                }
                val resultIntent = Intent().apply {
                    putExtra(ConductoresActivity.EXTRA_CONDUCTOR_POSITION, conductorPosition)
                    putExtra(ConductoresActivity.EXTRA_IMAGE_URI, uri.toString())
                }

                setResult(Activity.RESULT_OK, resultIntent)
                finish()
            } catch (e: IOException) {
                e.printStackTrace()
                Toast.makeText(this, "Error al guardar la foto", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "Error al crear el URI", Toast.LENGTH_SHORT).show()
        }
    }
}