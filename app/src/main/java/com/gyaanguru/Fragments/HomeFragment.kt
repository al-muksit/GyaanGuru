package com.gyaanguru.Fragments

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import com.gyaanguru.Activities.CurrentAffairsActivity
import com.gyaanguru.Activities.NewsActivity
import com.gyaanguru.Adapter.SliderAdapter
import com.gyaanguru.Models.SliderData
import com.gyaanguru.R
import com.gyaanguru.Tutorials.ArticleTutorialsActivity
import com.gyaanguru.Updates.DailyUpdatesActivity
import com.smarteist.autoimageslider.SliderAnimations
import com.smarteist.autoimageslider.SliderView

class HomeFragment : Fragment(), View.OnClickListener {
    private var adapter: SliderAdapter? = null
    private var sliderDataArrayList: ArrayList<SliderData>? = null
    private var sliderView: SliderView? = null

    private var bd_pratidin: ImageView? = null
    private var karatoa: ImageView? = null
    private var jugantor: ImageView? = null
    private var kaler_kantho: ImageView? = null
    private var inqilab: ImageView? = null
    private var jamuna_tv: ImageView? = null
    private var article_tutorials: Button? = null
    private var dec_2024: Button? = null
    private var jan_2025: Button? = null

    var cardview_daily: CardView? = null
    var cardview_salat_time: CardView? = null
    var cardview_career: CardView? = null
    var cardview_job: CardView? = null
    var cardview_science: CardView? = null
    var cardview_others: CardView? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sliderDataArrayList = ArrayList()
        sliderView = view.findViewById<View>(R.id.slider) as SliderView
        loadImages()

        bd_pratidin = view.findViewById<View>(R.id.bd_pratidin) as ImageView
        karatoa = view.findViewById<View>(R.id.karatoa) as ImageView
        jugantor = view.findViewById<View>(R.id.jugantor) as ImageView
        kaler_kantho = view.findViewById<View>(R.id.kaler_kantho) as ImageView
        inqilab = view.findViewById<View>(R.id.inqilab) as ImageView
        jamuna_tv = view.findViewById<View>(R.id.jamuna_tv) as ImageView

        cardview_daily = view.findViewById<View>(R.id.cardview_daily) as CardView
        cardview_salat_time = view.findViewById<View>(R.id.cardview_salat_time) as CardView
        cardview_career = view.findViewById<View>(R.id.cardview_career) as CardView
        cardview_job = view.findViewById<View>(R.id.cardview_job) as CardView
        cardview_science = view.findViewById<View>(R.id.cardview_Science) as CardView
        cardview_others = view.findViewById<View>(R.id.cardview_others) as CardView

        bd_pratidin!!.setOnClickListener(this)
        karatoa!!.setOnClickListener(this)
        jugantor!!.setOnClickListener(this)
        kaler_kantho!!.setOnClickListener(this)
        inqilab!!.setOnClickListener(this)
        jamuna_tv!!.setOnClickListener(this)

        dec_2024 = view.findViewById<View>(R.id.dec_2024) as Button
        jan_2025 = view.findViewById<View>(R.id.jan_2025) as Button

        article_tutorials = view.findViewById<View>(R.id.article_tutorials) as Button

        dec_2024!!.setOnClickListener(this)
        jan_2025!!.setOnClickListener(this)

        article_tutorials!!.setOnClickListener(this)

        cardview_daily!!.setOnClickListener(this)
        cardview_salat_time!!.setOnClickListener(this)
        //   cardview_career.setOnClickListener(this);
        cardview_job!!.setOnClickListener(this)
        //   cardview_science.setOnClickListener(this);
        //   cardview_others.setOnClickListener(this);
    }

    private fun loadImages() {
        sliderDataArrayList!!.add(SliderData(R.drawable.journalist_holding_newspaper))
        sliderDataArrayList!!.add(SliderData(R.drawable.slider_job))
        sliderDataArrayList!!.add(SliderData(R.drawable.slider_quiz))
        sliderDataArrayList!!.add(SliderData(R.drawable.slider_latest))
        sliderDataArrayList!!.add(SliderData(R.drawable.slider_tutorials))

        adapter = SliderAdapter(context, sliderDataArrayList)
        sliderView!!.setSliderAdapter(adapter!!)
        sliderView!!.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION)
        sliderView!!.autoCycleDirection = SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH
        sliderView!!.scrollTimeInSec = 3
        sliderView!!.isAutoCycle = true
        sliderView!!.startAutoCycle()
    }

    override fun onClick(view: View) {
//Updates
        if (view.id == R.id.cardview_daily) {
            startActivity(Intent(activity, DailyUpdatesActivity::class.java))
        } else if (view.id == R.id.cardview_job) {
            showOptionsDialog()
        } else if (view.id == R.id.article_tutorials) {
            startActivity(Intent(activity, ArticleTutorialsActivity::class.java))
        }

        //NewsPapers
        val newsIntent = Intent(activity, NewsActivity::class.java)
        if (view.id == R.id.bd_pratidin) {
            newsIntent.putExtra("NewsURL", "https://www.bd-pratidin.com/")
        } else if (view.id == R.id.karatoa) {
            newsIntent.putExtra("NewsURL", "https://www.dailykaratoa.com/")
        } else if (view.id == R.id.jugantor) {
            newsIntent.putExtra("NewsURL", "https://www.jugantor.com/")
        } else if (view.id == R.id.kaler_kantho) {
            newsIntent.putExtra("NewsURL", "https://www.kalerkantho.com/")
        } else if (view.id == R.id.inqilab) {
            newsIntent.putExtra("NewsURL", "https://dailyinqilab.com/")
        } else if (view.id == R.id.jamuna_tv) {
            newsIntent.putExtra("NewsURL", "https://jamuna.tv/")
        } else if (view.id == R.id.cardview_salat_time) {
            newsIntent.putExtra("NewsURL", "https://raskin.top/") //Daily Salat time Updates
        }
        if (view.id == R.id.bd_pratidin || view.id == R.id.karatoa || view.id == R.id.jugantor || view.id == R.id.kaler_kantho || view.id == R.id.inqilab || view.id == R.id.jamuna_tv || view.id == R.id.cardview_salat_time) {
            startActivity(newsIntent)
        }

        //Current Affairs
        val currentAffairsIntent = Intent(activity, CurrentAffairsActivity::class.java)
        if (view.id == R.id.jan_2025) {
            currentAffairsIntent.putExtra(
                "CurrentAffairsURL",
                "https://drive.google.com/file/d/1Y3nohuH9wgTm22BA3cODHh31usofKv6N/view?usp=sharing"
            )
            //    currentAffairsIntent.putExtra("CurrentAffairsAssets", "current_affairs_jan2025.pdf");
        } else if (view.id == R.id.dec_2024) {
            currentAffairsIntent.putExtra(
                "CurrentAffairsURL",
                "https://drive.google.com/file/d/1NfogCQQ_cSmYlnz7iJG4hEgQqXELokYc/view?usp=sharing"
            )
            //    currentAffairsIntent.putExtra("CurrentAffairsAssets", "current_affairs_dec2024.pdf");
        }
        startActivity(currentAffairsIntent)
    }

    private fun showOptionsDialog() {
        val builder = AlertDialog.Builder(requireContext()) // Create an AlertDialog

        val layout = LinearLayout(requireContext()) // Create a LinearLayout for the options
        layout.orientation = LinearLayout.VERTICAL

        val jobOptionIntent = Intent(activity, NewsActivity::class.java)

        // Create the buttons
        val option1Button = Button(requireContext())
        option1Button.text = "bdjobs.com"
        option1Button.setOnClickListener { view: View? ->   // Perform Task 1
            jobOptionIntent.putExtra("NewsURL", "https://bdjobs.com/")
            startActivity(jobOptionIntent)
        }
        val option2Button = Button(requireContext())
        option2Button.text = "ejobs.com.bd"
        option2Button.setOnClickListener { view: View? ->   // Perform Task 2
            jobOptionIntent.putExtra("NewsURL", "https://www.ejobs.com.bd/")
            startActivity(jobOptionIntent)
        }

        // Add the buttons to the layout
        layout.addView(option1Button)
        layout.addView(option2Button)
        builder.setView(layout) // Add the layout to the builder

        // Add the dialog to the builder
        builder.setCancelable(true)
        builder.setNegativeButton("Cancel") { dialog, which -> dialog.dismiss() }

        val dialog = builder.create() // Create the dialog
        dialog.show() // Show the dialog
    }
}