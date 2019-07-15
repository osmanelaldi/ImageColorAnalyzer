package com.example.imagecatalogandanalyzer.Adapters

import android.content.Context
import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.example.imagecatalogandanalyzer.Classes.ImageColor
import com.example.imagecatalogandanalyzer.R
import kotlinx.android.synthetic.main.card_gridcolor.view.*

class ColorGridRecyclerAdapter(context:Context,list: List<ImageColor>):RecyclerView.Adapter<ColorGridRecyclerAdapter.ColorGridHolder>(){

    var colorList:List<ImageColor>
    internal var layoutInflater: LayoutInflater
    init {
        layoutInflater = LayoutInflater.from(context)
        this.colorList=list
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ColorGridHolder {
        var view=layoutInflater.inflate(R.layout.card_gridcolor,p0,false)
        var height=p0.measuredHeight/4
        view.minimumHeight=height
        return ColorGridHolder(view)
    }

    override fun getItemCount(): Int {
        return colorList.size
    }

    override fun onBindViewHolder(colorGridHolder: ColorGridHolder, i: Int) {
        colorGridHolder.colorFamily.text="Color Family: " + colorList[i].colorFamily

        var sat=colorList[i].hsvval!!.toFloat()
        if(sat >= 0.50){
            colorGridHolder.colorFamily.setTextColor(Color.parseColor("#000000"))
            colorGridHolder.colorHue.setTextColor(Color.parseColor("#000000"))
            colorGridHolder.colorRGB.setTextColor(Color.parseColor("#000000"))
            colorGridHolder.colorHEX.setTextColor(Color.parseColor("#000000"))
            colorGridHolder.colorPercentage.setTextColor(Color.parseColor("#000000"))

        }else{
            colorGridHolder.colorFamily.setTextColor(Color.parseColor("#FFFFFF"))
            colorGridHolder.colorHue.setTextColor(Color.parseColor("#FFFFFF"))
            colorGridHolder.colorRGB.setTextColor(Color.parseColor("#FFFFFF"))
            colorGridHolder.colorHEX.setTextColor(Color.parseColor("#FFFFFF"))
            colorGridHolder.colorPercentage.setTextColor(Color.parseColor("#FFFFFF"))
        }


        colorGridHolder.colorHue.text = "Hue Code: " + colorList[i].colorHue
        colorGridHolder.colorRGB.text = "RGB Code: " + colorList[i].colorRGB
        colorGridHolder.colorHEX.text = "HEX Code: " + colorList[i].colorHEX
        colorGridHolder.colorPercentage.setText(colorList[i].colorPercentage)
        colorGridHolder.card.setBackgroundColor(Color.parseColor(colorList[i].colorHEX))
    }

    inner class ColorGridHolder(itemView: View):RecyclerView.ViewHolder(itemView){

        internal var colorFamily: TextView
        internal var colorHue: TextView
        internal var colorRGB: TextView
        internal var colorHEX: TextView
        internal var colorPercentage: TextView
        internal var card: LinearLayout


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