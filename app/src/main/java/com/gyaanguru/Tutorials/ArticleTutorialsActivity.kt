package com.gyaanguru.Tutorials

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.gyaanguru.Activities.NewsActivity
import com.gyaanguru.R

class ArticleTutorialsActivity : AppCompatActivity(), View.OnClickListener {
    var javatpoint: ImageView? = null
    var geeksforgeeks: ImageView? = null
    var tutorialspoint: ImageView? = null
    var w3school: ImageView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.enableEdgeToEdge()
        setContentView(R.layout.activity_article_tutorials)
        window.statusBarColor = resources.getColor(R.color.dark_green)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v: View, insets: WindowInsetsCompat ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        javatpoint = findViewById<View>(R.id.javatpoint) as ImageView
        geeksforgeeks = findViewById<View>(R.id.geeksforgeeks) as ImageView
        tutorialspoint = findViewById<View>(R.id.tutorialspoint) as ImageView
        w3school = findViewById<View>(R.id.w3school) as ImageView

        javatpoint!!.setOnClickListener(this)
        geeksforgeeks!!.setOnClickListener(this)
        tutorialspoint!!.setOnClickListener(this)
        w3school!!.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        val articleTutorialsIntent = Intent(this, NewsActivity::class.java)
        if (view.id == R.id.javatpoint) {
            articleTutorialsIntent.putExtra("NewsURL", "https://www.javatpoint.com/")
        } else if (view.id == R.id.geeksforgeeks) {
            articleTutorialsIntent.putExtra("NewsURL", "https://www.geeksforgeeks.org/")
        } else if (view.id == R.id.tutorialspoint) {
            articleTutorialsIntent.putExtra("NewsURL", "https://www.tutorialspoint.com/")
        } else if (view.id == R.id.w3school) {
            articleTutorialsIntent.putExtra("NewsURL", "https://www.w3schools.com/")
        }
        startActivity(articleTutorialsIntent)
    }

    fun back(view: View?) {
        finish()
    }

    override fun onPointerCaptureChanged(hasCapture: Boolean) {
        super.onPointerCaptureChanged(hasCapture)
    }
}