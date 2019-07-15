package com.example.imagecatalogandanalyzer.Fragments


import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.imagecatalogandanalyzer.Adapters.ColorGridRecyclerAdapter
import com.example.imagecatalogandanalyzer.Classes.ImageColor
import com.example.imagecatalogandanalyzer.Classes.ParcelableImageColor

import com.example.imagecatalogandanalyzer.R

@SuppressLint("ValidFragment")
class ColorGridDialogFragment constructor(list: ArrayList<ImageColor>): DialogFragment() {

    var colorList:ArrayList<ImageColor>

    init {
        this.colorList=list
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var view= inflater.inflate(R.layout.fragment_color_grid_dialog, container, false)
        var recyclerView=view.findViewById<RecyclerView>(R.id.colorGridRecycler)
        recyclerView.layoutManager= StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)
        var adapter = ColorGridRecyclerAdapter(context!!,colorList)
        recyclerView.adapter=adapter



        return view
    }



}
