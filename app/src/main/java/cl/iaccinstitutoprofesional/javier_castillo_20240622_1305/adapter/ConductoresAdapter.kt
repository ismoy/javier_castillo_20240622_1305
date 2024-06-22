package cl.iaccinstitutoprofesional.javier_castillo_20240622_1305.adapter

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import cl.iaccinstitutoprofesional.javier_castillo_20240622_1305.R
import cl.iaccinstitutoprofesional.javier_castillo_20240622_1305.capture.CapturaActivity
import cl.iaccinstitutoprofesional.javier_castillo_20240622_1305.databinding.ItemConductorBinding
import cl.iaccinstitutoprofesional.javier_castillo_20240622_1305.driver.ConductoresActivity
import cl.iaccinstitutoprofesional.javier_castillo_20240622_1305.model.Conductor

class ConductoresAdapter(val conductores: List<Conductor>, private val activity: AppCompatActivity) :
    RecyclerView.Adapter<ConductoresAdapter.ConductorViewHolder>() {

    var onImageCapture: ((Int) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ConductorViewHolder {
        val binding = ItemConductorBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ConductorViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ConductorViewHolder, position: Int) {
        val conductor = conductores[position]
        holder.bind(conductor)
        holder.binding.buttonCapturarFoto.setOnClickListener {
            onImageCapture?.invoke(position)
            val intent = Intent(activity, CapturaActivity::class.java)
            intent.putExtra(ConductoresActivity.EXTRA_CONDUCTOR_POSITION, position)
            activity.startActivityForResult(intent, ConductoresActivity.REQUEST_IMAGE_CAPTURE)
        }
    }

    override fun getItemCount(): Int = conductores.size

    fun updateConductorImage(position: Int, imageUri: String) {
        conductores[position].fotoUri = imageUri
        notifyItemChanged(position)
    }

    class ConductorViewHolder(val binding: ItemConductorBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(conductor: Conductor) {
            binding.textViewNombre.text = conductor.nombre
            conductor.fotoUri?.let { binding.imageViewConductor.setImageURI(Uri.parse(it)) }
            binding.quantityRides.text = conductor.cantidadViaje.toString()
        }
    }
}
