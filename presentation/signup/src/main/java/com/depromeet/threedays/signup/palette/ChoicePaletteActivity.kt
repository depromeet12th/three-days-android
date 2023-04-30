package com.depromeet.threedays.signup.palette

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.depromeet.threedays.signup.R

class ChoicePaletteActivity : AppCompatActivity() {
    val colors0 = arrayListOf("#FFFFFF", "#F4F6F8", "#E8EBF0", "#D8DCE2", "#C4CAD4", "#A5ADBD", "#8E95A3", "#5B616C", "#353C49", "#1A1E27", "#121213")
    val colors0Dark = arrayListOf("#17171B", "#2C2C34", "#3D3D58", "#62626C", "#7E7E86", "#9E9EA3", "#C3C3C6", "#E4E4E5", "#FFFFFF", "#101012")
    val colors1 = arrayListOf("#121212", "#1E1E1E", "#2F2F2F", "#363636", "#474747", "#8F8F8F", "#ABABAB", "#DFDFDF", "#FFFFFF", "#121212")
    val colors2 = arrayListOf("#242526", "#34363B", "#4C505B", "#61656E", "#84878E", "#A0A4AC", "#B4B7BE", "#D8D9DB", "#FFFFFF", "#101012")
    val colors3 = arrayListOf("#1C1C1E", "#2C2C2E", "#3A3A3C", "#48484A", "#636366", "#8E8E93", "#BBBBBE", "#D2D2D4", "#FFFFFF", "#101012")
    val colors4 = arrayListOf("#151718", "#2B2F31", "#313538", "#4C5155", "#697177", "#787F85", "#9BA1A6", "#ECEDEE", "#FFFFFF", "#101012")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choice_palette)

        val btn0 = findViewById<Button>(R.id.btn0)
        val btn0Dark = findViewById<Button>(R.id.btn0_dark)
        val btn1 = findViewById<Button>(R.id.btn1)
        val btn2 = findViewById<Button>(R.id.btn2)
        val btn3 = findViewById<Button>(R.id.btn3)
        val btn4 = findViewById<Button>(R.id.btn4)

        val intent = Intent(this, PaletteActivity::class.java)

        btn0.setOnClickListener {
            intent.putStringArrayListExtra("colors0", colors0)
            startActivity(intent)
        }

        btn0Dark.setOnClickListener {
            intent.putStringArrayListExtra("colors0Dark", colors0Dark)
            startActivity(intent)
        }

        btn1.setOnClickListener {
            intent.putStringArrayListExtra("colors1", colors1)
            startActivity(intent)
        }

        btn2.setOnClickListener {
            intent.putStringArrayListExtra("colors3", colors2)
            startActivity(intent)
        }

        btn3.setOnClickListener {
            intent.putStringArrayListExtra("colors4", colors3)
            startActivity(intent)
        }

        btn4.setOnClickListener {
            intent.putStringArrayListExtra("colors4", colors4)
            startActivity(intent)
        }
    }
}