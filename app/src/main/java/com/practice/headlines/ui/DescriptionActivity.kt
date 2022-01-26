package com.practice.headlines.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.widget.ImageView
import androidx.navigation.navArgs
import com.practice.headlines.Myapplication
import com.practice.headlines.databinding.ActivityDescriptionBinding
import com.practice.headlines.dependencies.CoreComponent
import com.squareup.picasso.Picasso

class DescriptionActivity : AppCompatActivity() {
    lateinit var binding: ActivityDescriptionBinding
    lateinit var coreComponent: CoreComponent
    lateinit var myapplication: Myapplication
    lateinit var picasso:Picasso
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN)
        binding= ActivityDescriptionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        myapplication=this.application as Myapplication
        coreComponent=myapplication.coreComponent
        picasso=coreComponent.picaaso()
        val safeArgs: DescriptionActivityArgs by navArgs()
        picasso.load(safeArgs.urltoimage).fit().into(binding.image)
        binding.newstitle.text=safeArgs.title
        binding.description.text=safeArgs.description
        binding.date.text=safeArgs.date
        binding.source.text=safeArgs.source
        binding.back.setOnClickListener { onBackPressed() }
    }
}