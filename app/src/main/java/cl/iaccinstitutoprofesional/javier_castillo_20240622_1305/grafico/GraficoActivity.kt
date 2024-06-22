package cl.iaccinstitutoprofesional.javier_castillo_20240622_1305.grafico

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import cl.iaccinstitutoprofesional.javier_castillo_20240622_1305.MainActivity
import cl.iaccinstitutoprofesional.javier_castillo_20240622_1305.R
import cl.iaccinstitutoprofesional.javier_castillo_20240622_1305.databinding.ActivityGraficoBinding
import cl.iaccinstitutoprofesional.javier_castillo_20240622_1305.model.Conductor
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.LegendEntry
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.utils.ColorTemplate

class GraficoActivity : AppCompatActivity() {
    private lateinit var barChart: BarChart
    private lateinit var conductores: List<Conductor>
    private lateinit var btnBack: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_grafico)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        // Recibir la lista de conductores
        conductores = intent.getParcelableArrayListExtra("conductores") ?: listOf()
         barChart  = findViewById(R.id.barChart)
        btnBack = findViewById(R.id.goBack)
        val entries = ArrayList<BarEntry>()
        for ((index, conductor) in conductores.withIndex()) {
            entries.add(BarEntry(index.toFloat(), conductor.cantidadViaje.toFloat()))
        }
        val colors = ColorTemplate.COLORFUL_COLORS.toList()

        val barDataSet = BarDataSet(entries, "")
        barDataSet.colors = colors

        val data = BarData(barDataSet)
        barChart.data = data
        barChart.animateY(2000)
        barChart.setFitBars(true)

        val description = Description()
        description.text = "Conductores con mayor cantidad de viajes"
        barChart.description = description

        val xAxis = barChart.xAxis
        xAxis.valueFormatter = IndexAxisValueFormatter(conductores.map { it.cantidadViaje.toString() })
        xAxis.granularity = 1f
        xAxis.setDrawLabels(true)
        xAxis.setDrawGridLines(false)
        xAxis.position = com.github.mikephil.charting.components.XAxis.XAxisPosition.BOTTOM

        val legendEntries = ArrayList<LegendEntry>()
        for ((index, conductor) in conductores.withIndex()) {
            val legendEntry = LegendEntry()
            legendEntry.label = conductor.nombre
            legendEntry.formColor = colors[index % colors.size]
            legendEntries.add(legendEntry)
        }

        val legend = barChart.legend
        legend.setCustom(legendEntries)
        legend.formSize = 4f
        legend.form = Legend.LegendForm.CIRCLE
        legend.textSize = 6f
        legend.xEntrySpace = 5f

        barChart.invalidate()

        btnBack.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }

}