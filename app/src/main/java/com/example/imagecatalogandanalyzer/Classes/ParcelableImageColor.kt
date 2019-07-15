package com.example.imagecatalogandanalyzer.Classes

import android.os.Parcel
import android.os.Parcelable

class ParcelableImageColor(colorFamily:String?,colorHue:String?,colorRGB:String?,colorHEX:String?,colorPercentage:String?,hsvval:String?):Parcelable{


    constructor(parcel: Parcel):this(
        parcel.readString(),parcel.readString(),parcel.readString(),parcel.readString(),parcel.readString(),parcel.readString()
    ){

    }
    override fun writeToParcel(dest: Parcel?, flags: Int) {
    }

    override fun describeContents(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    companion object CREATOR:Parcelable.Creator<ParcelableImageColor> {
        override fun createFromParcel(source: Parcel): ParcelableImageColor {
            return ParcelableImageColor(source)
        }

        override fun newArray(size: Int): Array<ParcelableImageColor?> {
            return arrayOfNulls(size)
        }
    }
}