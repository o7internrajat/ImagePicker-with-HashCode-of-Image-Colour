package com.example.imagepicker

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.Color.red
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.provider.MediaStore.Video.Media
import android.view.MotionEvent
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import java.io.File
import java.security.Permission

class MainActivity : AppCompatActivity() {

    lateinit var btnPicker:Button
    lateinit var tvColorCode:TextView
    lateinit var ivImage:ImageView
    lateinit var tvColor:TextView
    lateinit var bitmap: Bitmap

    private  val gallery=registerForActivityResult(ActivityResultContracts.GetContent()){ uri:Uri? ->
        uri?.let {
            ivImage.setImageURI(uri)
        }
        var latestUri: Uri?=null
        val previewImage by lazy {
            findViewById<ImageView>(R.id.ivImage)
        }
    }

    @SuppressLint("MissingInflatedId", "ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        btnPicker=findViewById(R.id.btnPicker)
        tvColor=findViewById(R.id.tvColor)
        tvColorCode=findViewById(R.id.tvColorCode)
        ivImage=findViewById(R.id.ivImage)


        btnPicker.setOnClickListener{
            selectImageFromGallery()
        }

        ivImage.isDrawingCacheEnabled=true
        ivImage.buildDrawingCache(true)

        ivImage.setOnTouchListener{ _, event ->
            if(event.action==MotionEvent.ACTION_DOWN|| event.action==MotionEvent.ACTION_MOVE){
                bitmap=ivImage.drawingCache
                val pixel=bitmap.getPixel(event.x.toInt(),event.y.toInt())
                val r = Color.red(pixel)
                val g = Color.green(pixel)
                val b = Color.blue(pixel)

                tvColorCode.text="${Integer.toHexString(pixel)}"
                tvColor.setBackgroundColor(Color.rgb(r,g,b))
            }
            true
        }
    }

    private fun selectImageFromGallery() =gallery.launch("image/*")
      private  fun getFileUri():Uri{
            val file =File.createTempFile("image_file",".png",cacheDir).apply {
                createNewFile()
                deleteOnExit()
            }
            return FileProvider.getUriForFile(applicationContext,"${BuildConfig.APPLICATION_ID}.provider",file)
        }
    }
