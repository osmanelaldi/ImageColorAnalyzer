package com.example.imagecatalogandanalyzer.Adapters

import android.content.Context
import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.example.imagecatalogandanalyzer.Classes.AnalyzedImage
import com.example.imagecatalogandanalyzer.Classes.ImageColor
import com.example.imagecatalogandanalyzer.R
import java.lang.Exception
import java.util.ArrayList
import java.util.HashMap

class AnalyzeRecyclerViewAdapter(context: Context, analyzedImage: AnalyzedImage) :
    RecyclerView.Adapter<AnalyzeRecyclerViewAdapter.AnalyzeRecyclerVievHolder>() {

    internal var layoutInflater: LayoutInflater
    var colorAttributes: HashMap<String, List<String>>
    var colorList: MutableList<ImageColor>

    init {
        layoutInflater = LayoutInflater.from(context)
        colorAttributes = analyzedImage.colorAttributes
        colorList = ArrayList<ImageColor>()

        analyzedImage.colorAttributes.forEach{(key,value)->
            colorList.add(ImageColor(value[4],value[1],value[2],value[3],value[0],value[5]))
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): AnalyzeRecyclerVievHolder {
        val view = layoutInflater.inflate(R.layout.card_colorinfo, viewGroup, false)
        return AnalyzeRecyclerVievHolder(view)

    }

    override fun onBindViewHolder(analyzeRecyclerVievHolder: AnalyzeRecyclerVievHolder, i: Int) {
        analyzeRecyclerVievHolder.colorFamily.text = "Color Family: " + colorList[i].colorFamily

            var sat=colorList[i].hsvval!!.toFloat()
            if(sat >= 0.50){
                analyzeRecyclerVievHolder.colorFamily.setTextColor(Color.parseColor("#000000"))
                analyzeRecyclerVievHolder.colorHue.setTextColor(Color.parseColor("#000000"))
                analyzeRecyclerVievHolder.colorRGB.setTextColor(Color.parseColor("#000000"))
                analyzeRecyclerVievHolder.colorHEX.setTextColor(Color.parseColor("#000000"))
                analyzeRecyclerVievHolder.colorPercentage.setTextColor(Color.parseColor("#000000"))

            }else{
                analyzeRecyclerVievHolder.colorFamily.setTextColor(Color.parseColor("#FFFFFF"))
                analyzeRecyclerVievHolder.colorHue.setTextColor(Color.parseColor("#FFFFFF"))
                analyzeRecyclerVievHolder.colorRGB.setTextColor(Color.parseColor("#FFFFFF"))
                analyzeRecyclerVievHolder.colorHEX.setTextColor(Color.parseColor("#FFFFFF"))
                analyzeRecyclerVievHolder.colorPercentage.setTextColor(Color.parseColor("#FFFFFF"))
            }


        analyzeRecyclerVievHolder.colorHue.text = "Hue Code: " + colorList[i].colorHue
        analyzeRecyclerVievHolder.colorRGB.text = "RGB Code: " + colorList[i].colorRGB
        analyzeRecyclerVievHolder.colorHEX.text = "HEX Code: " + colorList[i].colorHEX
        analyzeRecyclerVievHolder.colorPercentage.setText(colorList[i].colorPercentage)
        analyzeRecyclerVievHolder.card.setBackgroundColor(Color.parseColor(colorList[i].colorHEX))
    }

    override fun getItemCount(): Int {
        return colorList.size
    }

    inner class AnalyzeRecyclerVievHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal var colorFamily: TextView
        internal var colorHue: TextView
        internal var colorRGB: TextView
        internal var colorHEX: TextView
        internal var colorPercentage: TextView
        internal var card:LinearLayout

        init {
            colorFamily = itemView.findViewById(R.id.colorFamily) as TextView
            colorHue = itemView.findViewById(R.id.colorHue) as TextView
            colorRGB = itemView.findViewById(R.id.colorRGB) as TextView
            colorHEX = itemView.findViewById(R.id.colorHEX) as TextView
            colorPercentage = itemView.findViewById(R.id.colorPercentage) as TextView
            card=itemView.findViewById(R.id.card) as LinearLayout

        }

    }
}