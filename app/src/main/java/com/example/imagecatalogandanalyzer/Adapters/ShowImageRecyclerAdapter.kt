package com.example.imagecatalogandanalyzer.Adapters

import android.content.Context
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.FragmentManager
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.imagecatalogandanalyzer.Classes.ImageColor
import com.example.imagecatalogandanalyzer.Classes.ImageForFirebase
import com.example.imagecatalogandanalyzer.Classes.ParcelableImageColor
import com.example.imagecatalogandanalyzer.Fragments.ColorGridDialogFragment
import com.example.imagecatalogandanalyzer.R
import com.squareup.picasso.Picasso
import java.lang.Exception
import java.util.ArrayList
import java.util.HashMap

class ShowImageRecyclerAdapter(context: Context,fragmentManager: FragmentManager,imageList: List<ImageForFirebase>):RecyclerView.Adapter<ShowImageRecyclerAdapter.ShowImageHolder>(){

    internal var layoutInflater: LayoutInflater
    var imageList:List<ImageForFirebase>
    var fragmentManager:FragmentManager
    init {
        layoutInflater = LayoutInflater.from(context)
        this.imageList = imageList
        this.fragmentManager=fragmentManager



    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ShowImageHolder {
        val view = layoutInflater.inflate(R.layout.card_imageinfo,p0,false)
        return ShowImageHolder(view)
    }

    override fun getItemCount(): Int {
        return imageList.size
    }

    override fun onBindViewHolder(viewHolder: ShowImageHolder, i: Int) {
        var colorCode:String
        var colorPercentage:String
        try {
            Picasso.get().load(imageList[i].uri).into(viewHolder.image)
        }catch (e:Exception){
        }

        var colorList = ArrayList<ImageColor>()
        var colorParcables=ArrayList<ParcelableImageColor>()
        imageList[i].colorAttributes.forEach{(key,value)->
            colorList.add(ImageColor(value[4],value[1],value[2],value[3],value[0],value[5]))
            colorParcables.add(ParcelableImageColor(value[4],value[1],value[2],value[3],value[0],value[5]))
        }
        var sat=colorList[0].hsvval!!.toFloat()
        if(sat >= 0.50){
            viewHolder.percentage.setTextColor(Color.parseColor("#000000"))
        }else{
            viewHolder.percentage.setTextColor(Color.parseColor("#FFFFFF"))
        }

        var entry=imageList[i].colorAttributes.entries.iterator().next()
        colorCode=entry.value[3]
        colorPercentage=entry.value[0]
        viewHolder.percentage.background.setTint(Color.parseColor(colorCode))
        viewHolder.percentage.setText(colorPercentage)

        viewHolder.image.setOnClickListener(object :View.OnClickListener{
            override fun onClick(v: View?) {
                var bundle= Bundle()
                var dialogFragment = ColorGridDialogFragment(colorList)
                dialogFragment.arguments=bundle

                dialogFragment.show(fragmentManager,"")




            }
        })



    }

    inner class ShowImageHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        var image:ImageView
        var percentage:TextView

        init {
            this.image=itemView.findViewById(R.id.imageMain)
            this.percentage=itemView.findViewById(R.id.percentageMain)
        }
    }
}