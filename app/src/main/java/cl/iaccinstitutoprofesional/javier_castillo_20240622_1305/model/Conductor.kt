package cl.iaccinstitutoprofesional.javier_castillo_20240622_1305.model

import android.os.Parcel
import android.os.Parcelable

data class Conductor(val nombre: String, var fotoUri: String? = null, val cantidadViaje: Int = 0) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString(),
        parcel.readInt()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(nombre)
        parcel.writeString(fotoUri)
        parcel.writeInt(cantidadViaje)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Conductor> {
        override fun createFromParcel(parcel: Parcel): Conductor {
            return Conductor(parcel)
        }

        override fun newArray(size: Int): Array<Conductor?> {
            return arrayOfNulls(size)
        }
    }
}
