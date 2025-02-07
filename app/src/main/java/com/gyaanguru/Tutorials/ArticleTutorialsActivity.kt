package com.gyaanguru.Tutorials

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.gyaanguru.R
import android.content.Intent
import android.view.View
import android.view.Window
import androidx.core.content.ContextCompat
import com.gyaanguru.Activities.NewsActivity
import com.gyaanguru.databinding.ActivityArticleTutorialsBinding

class ArticleTutorialsActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityArticleTutorialsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityArticleTutorialsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val window: Window =this@ArticleTutorialsActivity.window
        window.statusBarColor= ContextCompat.getColor(this@ArticleTutorialsActivity, R.color.dark_green)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.javatpoint.setOnClickListener(this)
        binding.geeksforgeeks.setOnClickListener(this)
        binding.tutorialspoint.setOnClickListener(this)
        binding.w3school.setOnClickListener(this)
        binding.backImageView.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

    }

    override fun onClick(view: View) {
        val articleTutorialsIntent = Intent(this, NewsActivity::class.java)
        when (view.id) {
            R.id.javatpoint -> articleTutorialsIntent.putExtra("NewsURL", "https://www.javatpoint.com/")
            R.id.geeksforgeeks -> articleTutorialsIntent.putExtra("NewsURL", "https://www.geeksforgeeks.org/")
            R.id.tutorialspoint -> articleTutorialsIntent.putExtra("NewsURL", "https://www.tutorialspoint.com/")
            R.id.w3school -> articleTutorialsIntent.putExtra("NewsURL", "https://www.w3schools.com/")
        }
        startActivity(articleTutorialsIntent)
    }

}