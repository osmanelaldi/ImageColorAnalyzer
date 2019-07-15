package com.example.imagecatalogandanalyzer.Classes

import android.net.Uri

class ImageForFirebase(uri:String,colorAttributes:HashMap<String,List<String>>) {
    var uri:String
    var colorAttributes:HashMap<String,List<String>>
    var rank:Int=65
    init {
        this.uri=uri
        var temp:LinkedHashMap<String,List<String>> = LinkedHashMap()
        for( (key,value) in colorAttributes){
            var newKey="color"+rank.toChar()
            value[1].replace(".",",")
            temp.put(newKey,value)
            rank++
        }
        this.colorAttributes=temp
    }
    constructor():this("",HashMap<String,List<String>>()){}
}