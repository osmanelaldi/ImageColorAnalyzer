package com.example.imagecatalogandanalyzer.Classes

import android.graphics.Bitmap
import android.graphics.Color
import android.os.DropBoxManager
import android.support.annotation.IntegerRes
import java.util.*
import kotlin.collections.HashMap
import kotlin.collections.LinkedHashMap

class AnalyzedImage(bitmap: Bitmap) {
    var bitmap: Bitmap
    var pixel: Int = 0
    var red: Int = 0
    var green: Int = 0
    var blue: Int = 0
    var hue: Int = 0
    var amount: Int = 0
    var pictureSize: Int = bitmap.width * bitmap.height;
    var sat: Float = 0F
    var hsvval: Float = 0F
    var hsvString: String = ""
    var hsv = FloatArray(3)
    var colors: HashMap<String, Int> = HashMap<String, Int>()
    var colorAttributes:HashMap<String,List<String>> = HashMap<String,List<String>>()
    init {
        this.bitmap = bitmap
        for (i in 0 until bitmap.width) {
            for (j in 0 until bitmap.height) {
                pixel = bitmap.getPixel(i, j)
                red = Color.red(pixel)
                green = Color.green(pixel)
                blue = Color.blue(pixel)
                Color.RGBToHSV(red, green, blue, hsv)

                hue = hsv[0].toInt()
                sat = hsv[1]
                hsvval = hsv[2]

                hsvString = "" + hue + "-" + sat + "-" + hsvval
                try {
                    amount = colors.get(hsvString)!!
                    colors.put(hsvString, amount + 1)
                } catch (e: NullPointerException) {
                    colors.put(hsvString, 1)
                }

            }
            this.colorAttributes=defineColorAttributes(getop10(colors,pictureSize))

        }

        }
    fun getop10(hm:HashMap<String,Int>,picSize:Int):HashMap<String,String> {
        var count: Int = 0
        var top10color: HashMap<String, String> = LinkedHashMap<String, String>()
        var list = LinkedList<Map.Entry<String, Int>>(hm.entries)
        Collections.sort(list) { o1, o2 -> o2.value.compareTo(o1.value) }
        list.forEach { (key, value) ->
            if (count >= 10) return@forEach
            top10color.put(key, (value.toFloat() / pictureSize).toString())
            count++
        }
        return top10color
    }

    fun defineColorAttributes(hm:HashMap<String,String>):HashMap<String,List<String>>{
        var colorAttributes:HashMap<String,List<String>> = LinkedHashMap<String,List<String>>()

        hm.forEach{(key,value)->
            var colorAttr:MutableList<String> = mutableListOf<String>("","","","","","")
            colorAttr[0]="% "+String.format("%.1f",(value.toFloat()*100))
            var hsvvalues=key.split("-")
            var hsvhue=hsvvalues[0].toInt()
            var hsvsat=hsvvalues[1].toFloat()
            var hsvvalue=hsvvalues[2].toFloat()
            colorAttr[1]= "(" + hsvhue + "-" + String.format("%.2f", hsvsat) + "-" + String.format("%.2f", hsvvalue) + ")"
            var reghsv=FloatArray(3)
            reghsv[0]=hsvhue.toFloat()
            reghsv[1]=hsvsat
            reghsv[2]=hsvvalue
            var color:Int=Color.HSVToColor(reghsv)
            colorAttr[2]= "(" + Color.red(color) + "-" + Color.green(color) + "-" + Color.blue(color) + ")"
            colorAttr[3]=String.format("#%02x%02x%02x", Color.red(color), Color.green(color), Color.blue(color))
            colorAttr[5]=hsvvalue.toString()

            if (hsvvalue.equals(0.00F)){
                colorAttr[4]="Black"
                colorAttributes.put(key,colorAttr)
            }else if(hsvvalue.equals(1.00F)){
                colorAttr[4]="White"
                colorAttributes.put(key,colorAttr)
            }else if(hsvsat.equals(0.00F)){
                colorAttr[4]="Gray Color Range"
                colorAttributes.put(key,colorAttr)
            }else if (hsvhue>355 || hsvhue<=10){
                colorAttr[4]="Red Color Range"
                colorAttributes.put(key,colorAttr)
            }else if( hsvhue>=11 && hsvhue<=20){
                colorAttr[4]="Red-Orange Color Range"
                colorAttributes.put(key,colorAttr)
            }else if( hsvhue>=21 && hsvhue<=40){
                colorAttr[4]="Orange-Brown Color Range"
                colorAttributes.put(key,colorAttr)
            }else if( hsvhue>=41 && hsvhue<=50){
                colorAttr[4]="Orange-Yellow Color Range"
                colorAttributes.put(key,colorAttr)
            }else if( hsvhue>=51 && hsvhue<=60){
                colorAttr[4]="Yellow Color Range"
                colorAttributes.put(key,colorAttr)
            }else if( hsvhue>=61 && hsvhue<=80){
                colorAttr[4]="Yellow-Green Color Range"
                colorAttributes.put(key,colorAttr)
            }else if( hsvhue>=81 && hsvhue<=140){
                colorAttr[4]="Green Color Range"
                colorAttributes.put(key,colorAttr)
            }else if( hsvhue>=141 && hsvhue<=169){
                colorAttr[4]="Green-Cyan Color Range"
                colorAttributes.put(key,colorAttr)
            }else if( hsvhue>=170 && hsvhue<=200){
                colorAttr[4]="Cyan Color Range"
                colorAttributes.put(key,colorAttr)
            }else if( hsvhue>=201 && hsvhue<=220){
                colorAttr[4]="Cyan-Blue Range"
                colorAttributes.put(key,colorAttr)
            } else if( hsvhue>=221 && hsvhue<=240){
                colorAttr[4]="Blue Color Range"
                colorAttributes.put(key,colorAttr)
            }else if( hsvhue>=241 && hsvhue<=280){
                colorAttr[4]="Blue-Magenta Color Range"
                colorAttributes.put(key,colorAttr)
            }else if( hsvhue>=281 && hsvhue<=320){
                colorAttr[4]="Magenta Color Range"
                colorAttributes.put(key,colorAttr)
            }else if( hsvhue>=321 && hsvhue<=330){
                colorAttr[4]="Magenta-Pink Color Range"
                colorAttributes.put(key,colorAttr)
            }else if( hsvhue>=331 && hsvhue<=345){
                colorAttr[4]="Pink Color Range"
                colorAttributes.put(key,colorAttr)
            }else if( hsvhue>=346 && hsvhue<=355){
                colorAttr[4]="Pink-Red Color Range"
                colorAttributes.put(key,colorAttr)
            }

        }
        return colorAttributes
    }

    }


