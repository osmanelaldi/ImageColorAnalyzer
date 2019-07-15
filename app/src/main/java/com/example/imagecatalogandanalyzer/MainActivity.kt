package com.example.imagecatalogandanalyzer

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.FragmentManager
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import com.example.imagecatalogandanalyzer.Adapters.ShowImageRecyclerAdapter
import com.example.imagecatalogandanalyzer.Classes.ImageForFirebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.io.FileNotFoundException
import java.io.IOException
import java.security.Key

class MainActivity : AppCompatActivity() {

    val GET_FROM_GALLERY:Int=3
    var database=FirebaseDatabase.getInstance().reference.child("Images")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var fragmentManager:FragmentManager=supportFragmentManager
        var analyzeButton = findViewById<ImageButton>(R.id.upload)
        var imageSearch = findViewById<EditText>(R.id.search)
        var recyclerView=findViewById<RecyclerView>(R.id.imagesRecycler)
        var mainImage:ImageView=findViewById(R.id.preview)
        recyclerView.layoutManager=GridLayoutManager(applicationContext,2)

        analyzeButton.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                var intent=Intent()
                intent.type="image/*"
                intent.action=Intent.ACTION_GET_CONTENT
                startActivityForResult(Intent.createChooser(intent,"Select Picture"),GET_FROM_GALLERY)

            }

        })


        imageSearch.setOnEditorActionListener { v, actionId, event ->
            if ((actionId == EditorInfo.IME_ACTION_DONE) || ((event.keyCode == KeyEvent.KEYCODE_ENTER) && (event.action == KeyEvent.ACTION_DOWN))) {
                var list=ArrayList<ImageForFirebase>()
                var searchText = imageSearch.text.toString()
                database.addValueEventListener(object : ValueEventListener {
                    override fun onCancelled(p0: DatabaseError) {
                    }

                    override fun onDataChange(p0: DataSnapshot) {
                        val children=p0.children

                        children.forEach{
                            var sa=it.getValue(ImageForFirebase::class.java)

                           run searching@{
                               sa!!.colorAttributes.forEach{it->
                                   if(it.value[4].contains(searchText) || it.value[4].contains(searchText.capitalize())){
                                       list.add(sa)
                                       return@searching
                                   }

                               }
                           }

                            var adapter = ShowImageRecyclerAdapter(applicationContext,fragmentManager,list)
                            recyclerView.visibility=View.VISIBLE
                            mainImage.visibility=View.GONE
                            recyclerView.setAdapter(adapter)
                        }
                    }

                })


                true
            }
            false
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode.equals(GET_FROM_GALLERY) && resultCode.equals(Activity.RESULT_OK) && data!=null){
            var uri=data.data;
            try {

                startActivity(Intent(this,AnalyzeReport::class.java).putExtra("bitmapUri",uri.toString()))
            }catch (e:FileNotFoundException){

            }catch (e:IOException){

            }
        }
    }
}
