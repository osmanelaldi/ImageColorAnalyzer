package com.example.imagecatalogandanalyzer.Classes

import android.os.Parcelable

class ImageColor(colorFamily:String?,colorHue:String?,colorRGB:String?,colorHEX:String?,colorPercentage:String?,hsvval:String?){
    var colorFamily:String?
    var colorHue:String?
    var colorRGB:String?
    var colorHEX:String?
    var colorPercentage:String?
    var hsvval:String?
    init {
        this.colorFamily=colorFamily
        this.colorHue=colorHue
        this.colorRGB=colorRGB
        this.colorHEX=colorHEX
        this.colorPercentage=colorPercentage
        this.hsvval=hsvval
    }
}