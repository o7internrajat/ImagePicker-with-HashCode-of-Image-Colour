package com.example.imagepicker

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.Color.red
import android.graphics.Rect
import android.graphics.RectF
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.provider.MediaStore.Video.Media
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.MotionEvent.INVALID_POINTER_ID
import android.view.ScaleGestureDetector
import android.view.ScaleGestureDetector.OnScaleGestureListener
import android.view.ScaleGestureDetector.SimpleOnScaleGestureListener
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.core.graphics.scaleMatrix
import androidx.core.view.MotionEventCompat
import java.io.File
import java.security.Permission
import kotlin.math.max
import kotlin.math.min

class MainActivity : AppCompatActivity() {

    lateinit var btnPicker:Button
    lateinit var tvColorCode:TextView
    lateinit var ivImage:ImageView
    lateinit var tvColor:TextView
    lateinit var bitmap: Bitmap
    lateinit var mScaleDetector: ScaleGestureDetector
    var scaleFactor = 1.0f
    private var mActivePointerId = INVALID_POINTER_ID
//    private val mCurrentViewport = RectF(AXIS_X_MIN, AXIS_Y_MIN, AXIS_X_MAX, AXIS_Y_MAX)
    private val mContentRect: Rect?=null
    private var mLastTouchX: Float=1.0f
    private var mLastTouchY: Float=1.0f
    private var mPosX=0.0f
    private var mPosY=0.0f







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

//        mScaleDetector = GestureDetector(this,ScaleListener())
        mScaleDetector = ScaleGestureDetector(this,ScaleListener())


        btnPicker.setOnClickListener{
            selectImageFromGallery()
        }

        ivImage.isDrawingCacheEnabled=true
        ivImage.buildDrawingCache(true)




        ivImage.setOnTouchListener { _, event ->
            Log.e("AHAHHAHAHAH", event.toString())
            if(event.action==MotionEvent.ACTION_DOWN){
                bitmap=ivImage.drawingCache
                val pixel=bitmap.getPixel(event.x.toInt(), event.y.toInt())
                val r = Color.red(pixel)
                val g = Color.green(pixel)
                val b = Color.blue(pixel)

                tvColorCode.text="${Integer.toHexString(pixel)}"
                tvColor.setBackgroundColor(Color.rgb(r,g,b))
            }

            onTouchEvent(event)

            true
        }
    }



    private fun selectImageFromGallery() =gallery.launch("image/*")

    private fun getFileUri():Uri{
        val file =File.createTempFile("image_file",".png",cacheDir).apply {
            createNewFile()
            deleteOnExit()
        }
        return FileProvider.getUriForFile(applicationContext,"${BuildConfig.APPLICATION_ID}.provider",file)
    }

    override fun onTouchEvent(motionEvent: MotionEvent): Boolean {
         mScaleDetector.onTouchEvent(motionEvent)

//         val action =MotionEventCompat.getActionMasked(motionEvent)
//        when(action){
//            MotionEvent.ACTION_DOWN ->{
//                MotionEventCompat.getActionIndex(motionEvent).also { pointerIndex->
//                     mLastTouchX = MotionEventCompat.getX(motionEvent,pointerIndex)
//                     mLastTouchY=MotionEventCompat.getY(motionEvent,pointerIndex)
//                }
//                mActivePointerId=MotionEventCompat.getPointerId(motionEvent,0)
//            }
//            MotionEvent.ACTION_MOVE ->{
//                val (x:Float, y:Float)=
//                    MotionEventCompat.findPointerIndex(motionEvent,mActivePointerId).let { pointerIndex->
//                        MotionEventCompat.getX(motionEvent,pointerIndex) to
//                                MotionEventCompat.getY(motionEvent,pointerIndex)
//                    }
//                mPosX += x-mLastTouchX
//                mPosY += y-mLastTouchY
//                invalidate()
//                mLastTouchY = x
//                mLastTouchY = y
//            }
//            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL->{
//                mActivePointerId= INVALID_POINTER_ID
//            }
//            MotionEvent.ACTION_POINTER_UP ->{
//                    MotionEventCompat.getActionIndex(motionEvent).also { pointerIndex->
//                        MotionEventCompat.getPointerId(motionEvent,pointerIndex)
//                            .takeIf { it==mActivePointerId }?.
//                                run{
//                                    val newPointerIndex=if(pointerIndex==0)1 else 0
//                                    mLastTouchX=MotionEventCompat.getX(motionEvent,newPointerIndex)
//                                    mLastTouchY=MotionEventCompat.getY(motionEvent,newPointerIndex)
//                                    mActivePointerId=MotionEventCompat.getPointerId(motionEvent,newPointerIndex)
//                                }
//                    }
//
//            }
//
//        }



        return true

    }

    private fun invalidate() {

    }

    private inner class ScaleListener : SimpleOnScaleGestureListener() {
        override fun onScale(scaleGestureDetector: ScaleGestureDetector):Boolean {
            scaleFactor *= scaleGestureDetector.scaleFactor
            scaleFactor = max(0.1f, min(scaleFactor,10.0f))

            ivImage.scaleX = scaleFactor
            ivImage.scaleY = scaleFactor
            return true

        }
    }
}

