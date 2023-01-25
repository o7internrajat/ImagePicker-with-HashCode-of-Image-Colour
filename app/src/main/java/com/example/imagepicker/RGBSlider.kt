package com.example.imagepicker

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.AbsSeekBar
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import android.widget.TextView
import android.widget.Toast
import androidx.core.graphics.blue
import androidx.core.graphics.green
import org.w3c.dom.Text
import java.util.*


class RGBSlider : AppCompatActivity() {

    lateinit var sbRed:SeekBar
    lateinit var sbGreen:SeekBar
    lateinit var sbBlue:SeekBar
    lateinit var tvRgb:TextView
    lateinit var tvHashCode:TextView
    lateinit var clipboardManager: ClipboardManager
//    lateinit var clipData: ClipData


    var red=255
    var green=255
    var blue=255

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rgbslider)
        sbRed=findViewById(R.id.sbRed)
        sbGreen=findViewById(R.id.sbGreen)
        sbBlue=findViewById(R.id.sbBlue)
        tvRgb=findViewById(R.id.tvRgb)
        tvHashCode=findViewById(R.id.tvHashCode)

        sbRed.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(p0: SeekBar?, progress: Int, p2: Boolean) {
                red=progress
                tvRgb.setBackgroundColor(Color.rgb(red, green,blue))

                val colorStr = getColorString()
                tvHashCode.setText(colorStr!!.replace("#","").uppercase())
            }
            override fun onStartTrackingTouch(p0: SeekBar?) {
            }
            override fun onStopTrackingTouch(p0: SeekBar?) {

            }
        })
        sbGreen.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(p0: SeekBar?, progress: Int, p2: Boolean) {
                green=progress
                tvRgb.setBackgroundColor(Color.rgb(red, green, blue))

                val colorStr = getColorString()
                tvHashCode.setText(colorStr!!.replace("#","").uppercase())
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
            }
            override fun onStopTrackingTouch(p0: SeekBar?) {
            }
        })
        sbBlue.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(p0: SeekBar?, progress: Int, p2: Boolean) {
                blue=progress
                tvRgb.setBackgroundColor(Color.rgb(red, green, blue))
                val colorStr = getColorString()
                tvHashCode.setText(colorStr!!.replace("#","").uppercase())
            }
            override fun onStartTrackingTouch(p0: SeekBar?) {
            }
            override fun onStopTrackingTouch(p0: SeekBar?) {
            }
        })
        clipboardManager= getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        tvHashCode!!.setOnClickListener{
           val clipData = ClipData.newPlainText("Label",tvHashCode!!.text.toString())
            clipboardManager.setPrimaryClip(clipData)
            Toast.makeText(this,"Copy",Toast.LENGTH_SHORT).show()
        }
    }
    private fun getColorString(): String? {
        var r = Integer.toHexString(((255*sbRed.progress)/sbRed.max))
        if(r.length==1) r = "0"+r
        var g = Integer.toHexString(((255*sbGreen.progress)/sbGreen.max))
        if(g.length==1) g = "0"+g
        var b = Integer.toHexString(((255*sbBlue.progress)/sbBlue.max))
        if(b.length==1) b = "0"+b
        return "#" + r + g + b

    }
}