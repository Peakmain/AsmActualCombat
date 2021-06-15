package com.peakmain.asmactualcombat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val tvName=findViewById<TextView>(R.id.tv_name)
        tvName.setOnClickListener{
            Toast.makeText(this,"你好",Toast.LENGTH_SHORT).show()
        }
    }
}