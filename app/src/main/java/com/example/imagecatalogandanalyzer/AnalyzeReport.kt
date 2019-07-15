package com.example.imagecatalogandanalyzer

import android.app.ProgressDialog
import android.content.ContentResolver
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.opengl.Visibility
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.webkit.MimeTypeMap
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import com.example.imagecatalogandanalyzer.Adapters.AnalyzeRecyclerViewAdapter
import com.example.imagecatalogandanalyzer.Classes.AnalyzedImage
import com.example.imagecatalogandanalyzer.Classes.ImageForFirebase
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.tasks.Task
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_analyze_report.*
import java.io.ByteArrayOutputStream
import java.io.FileNotFoundException
import java.io.IOException
import java.util.HashMap
import java.util.jar.Attributes

class AnalyzeReport : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_analyze_report)

        var analyzeButton=findViewById<Button>(R.id.analyze)
        var submitButton=findViewById<Button>(R.id.submit)
        var bitmapImageView = findViewById(R.id.bitmapImageView) as ImageView
        var progressBar:ProgressBar=findViewById(R.id.progress_bar)
        var recyclerView = findViewById(R.id.colorrecylerview) as RecyclerView
        var linearLayoutManager = LinearLayoutManager(applicationContext)
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL)
        recyclerView.setLayoutManager(linearLayoutManager)

        var imageUri:Uri=Uri.parse(intent.getStringExtra("bitmapUri"))
        val getoptions = BitmapFactory.Options()
        getoptions.inSampleSize = 2

        var bitmap:Bitmap= getBitMap(this.contentResolver,imageUri,getoptions)
        var byteArray=bitmapToArray(bitmap)



        val options = BitmapFactory.Options()
        options.inSampleSize = 4
        val compressedbitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size, options)

        var analyzedImage = AnalyzedImage(compressedbitmap)
        Picasso.get().load(imageUri).resize(400,700).into(bitmapImageView)


        analyzeButton.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {

                progressBar.visibility=View.VISIBLE


                val adapter = AnalyzeRecyclerViewAdapter(applicationContext, analyzedImage)
                recyclerView.setAdapter(adapter)

                progressBar.visibility=View.GONE
                submitButton.visibility=View.VISIBLE

            }
        })
        submitButton.setOnClickListener(object :View.OnClickListener{
            override fun onClick(v: View?) {
                var database=FirebaseDatabase.getInstance().getReference("Images")
                var storage=FirebaseStorage.getInstance().getReference("Images")
                var fileReference=storage.child((System.currentTimeMillis()).toString() + "." + getFileExtension(imageUri))
                fileReference.putFile(imageUri).addOnSuccessListener {

                    fileReference.downloadUrl.addOnSuccessListener {
                        var image = ImageForFirebase(it.toString(), analyzedImage.colorAttributes)
                        var key = database.push().key
                        database.child(key!!).setValue(image).addOnSuccessListener {
                            Toast.makeText(this@AnalyzeReport, "Image was successfully saved", Toast.LENGTH_LONG).show()
                            startActivity(Intent(this@AnalyzeReport,MainActivity::class.java))
                        }
                    }

                }


                }

        })

    }
    fun bitmapToArray(bitmap: Bitmap):ByteArray{
        var stream: ByteArrayOutputStream = ByteArrayOutputStream()

        bitmap.compress(Bitmap.CompressFormat.PNG,100,stream)

        var byteArray=stream.toByteArray()
        return  byteArray
    }
    fun getFileExtension(uri:Uri?):String?{
        var cr=contentResolver
        var mime=MimeTypeMap.getSingleton()
        return mime.getMimeTypeFromExtension(cr.getType(uri!!))
    }

    fun getBitMap(contentResolver: ContentResolver,uri: Uri?,options:BitmapFactory.Options):Bitmap{

            var inputStream=contentResolver.openInputStream(uri)
            var bitmap=BitmapFactory.decodeStream(inputStream,null,options)
            inputStream.close()

            return bitmap
    }
}
