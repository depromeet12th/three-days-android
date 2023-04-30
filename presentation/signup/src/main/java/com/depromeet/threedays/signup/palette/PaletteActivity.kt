package com.depromeet.threedays.signup.palette

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.depromeet.threedays.signup.R

class PaletteActivity : AppCompatActivity() {
    lateinit var paletteAdapter: PaletteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_palette)

        paletteAdapter = PaletteAdapter()

        intent.getStringArrayListExtra("colors0")?.let {
            paletteAdapter.setList(it.toList())
        }

        intent.getStringArrayListExtra("colors0Dark")?.let {
            paletteAdapter.setList(it.toList())
        }

        intent.getStringArrayListExtra("colors1")?.let {
            paletteAdapter.setList(it.toList())
        }

        intent.getStringArrayListExtra("colors2")?.let {
            paletteAdapter.setList(it.toList())
        }

        intent.getStringArrayListExtra("colors3")?.let {
            paletteAdapter.setList(it.toList())
        }

        intent.getStringArrayListExtra("colors4")?.let {
            paletteAdapter.setList(it.toList())
        }



        val rv = findViewById<RecyclerView>(R.id.rv_palette)
        rv.apply {
            layoutManager = LinearLayoutManager(this@PaletteActivity)
            adapter = paletteAdapter
        }
    }
}