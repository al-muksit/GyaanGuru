package com.gyaanguru.Fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.gyaanguru.Activities.CurrentAffairsActivity;
import com.gyaanguru.Activities.NewsActivity;
import com.gyaanguru.Adapter.SliderAdapter;
import com.gyaanguru.Models.SliderData;
import com.gyaanguru.R;

import com.gyaanguru.Tutorials.ArticleTutorialsActivity;
import com.gyaanguru.Updates.DailyUpdatesActivity;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;

public class HomeFragment extends Fragment implements View.OnClickListener {

    private SliderAdapter adapter;
    private ArrayList<SliderData> sliderDataArrayList;
    private SliderView sliderView;

    private ImageView bd_pratidin, karatoa, jugantor, kaler_kantho, inqilab, jamuna_tv;
    private Button article_tutorials, dec_2024, jan_2025;

    CardView cardview_daily, cardview_salat_time, cardview_career, cardview_job, cardview_science, cardview_others;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        sliderDataArrayList = new ArrayList<>();
        sliderView = (SliderView) view.findViewById(R.id.slider);
        loadImages();

        bd_pratidin = (ImageView) view.findViewById(R.id.bd_pratidin);
        karatoa = (ImageView) view.findViewById(R.id.karatoa);
        jugantor = (ImageView) view.findViewById(R.id.jugantor);
        kaler_kantho = (ImageView) view.findViewById(R.id.kaler_kantho);
        inqilab = (ImageView) view.findViewById(R.id.inqilab);
        jamuna_tv = (ImageView) view.findViewById(R.id.jamuna_tv);

        cardview_daily = (CardView) view.findViewById(R.id.cardview_daily);
        cardview_salat_time = (CardView) view.findViewById(R.id.cardview_salat_time);
        cardview_career = (CardView) view.findViewById(R.id.cardview_career);
        cardview_job = (CardView) view.findViewById(R.id.cardview_job);
        cardview_science = (CardView) view.findViewById(R.id.cardview_Science);
        cardview_others = (CardView) view.findViewById(R.id.cardview_others);

        bd_pratidin.setOnClickListener(this);
        karatoa.setOnClickListener(this);
        jugantor.setOnClickListener(this);
        kaler_kantho.setOnClickListener(this);
        inqilab.setOnClickListener(this);
        jamuna_tv.setOnClickListener(this);

        dec_2024 = (Button) view.findViewById(R.id.dec_2024);
        jan_2025 = (Button) view.findViewById(R.id.jan_2025);

        article_tutorials = (Button) view.findViewById(R.id.article_tutorials);

        dec_2024.setOnClickListener(this);
        jan_2025.setOnClickListener(this);

        article_tutorials.setOnClickListener(this);

        cardview_daily.setOnClickListener(this);
        cardview_salat_time.setOnClickListener(this);
     //   cardview_career.setOnClickListener(this);
        cardview_job.setOnClickListener(this);
     //   cardview_science.setOnClickListener(this);
     //   cardview_others.setOnClickListener(this);
    }

    private void loadImages() {
        sliderDataArrayList.add(new SliderData(R.drawable.journalist_holding_newspaper));
        sliderDataArrayList.add(new SliderData(R.drawable.slider_job));
        sliderDataArrayList.add(new SliderData(R.drawable.slider_quiz));
        sliderDataArrayList.add(new SliderData(R.drawable.slider_latest));
        sliderDataArrayList.add(new SliderData(R.drawable.slider_tutorials));

        adapter = new SliderAdapter(getContext(), sliderDataArrayList);
        sliderView.setSliderAdapter(adapter);
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
        sliderView.setScrollTimeInSec(3);
        sliderView.setAutoCycle(true);
        sliderView.startAutoCycle();
    }

    @Override
    public void onClick(View view) {
//Updates
        if (view.getId() == R.id.cardview_daily) {
            startActivity(new Intent(getActivity(), DailyUpdatesActivity.class));
        }
        else if (view.getId() == R.id.cardview_job) {
            showOptionsDialog();
        }
//Tutorials
        else if (view.getId() == R.id.article_tutorials) {
            startActivity(new Intent(getActivity(), ArticleTutorialsActivity.class));
        }

//NewsPapers
        Intent newsIntent = new Intent(getActivity(), NewsActivity.class);
        if (view.getId() == R.id.bd_pratidin) {
            newsIntent.putExtra("NewsURL", "https://www.bd-pratidin.com/");
        }
        else if (view.getId() == R.id.karatoa) {
            newsIntent.putExtra("NewsURL", "https://www.dailykaratoa.com/");
        }
        else if (view.getId() == R.id.jugantor) {
            newsIntent.putExtra("NewsURL", "https://www.jugantor.com/");
        }
        else if (view.getId() == R.id.kaler_kantho) {
            newsIntent.putExtra("NewsURL", "https://www.kalerkantho.com/");
        }
        else if (view.getId() == R.id.inqilab) {
            newsIntent.putExtra("NewsURL", "https://dailyinqilab.com/");
        }
        else if (view.getId() == R.id.jamuna_tv) {
            newsIntent.putExtra("NewsURL", "https://jamuna.tv/");
        }
        else if (view.getId() == R.id.cardview_salat_time) {
            newsIntent.putExtra("NewsURL", "https://raskin.top/");  //Daily Salat time Updates
        }
        if (view.getId() == R.id.bd_pratidin || view.getId() == R.id.karatoa || view.getId() == R.id.jugantor || view.getId() == R.id.kaler_kantho || view.getId() == R.id.inqilab || view.getId() == R.id.jamuna_tv || view.getId() == R.id.cardview_salat_time) {
            startActivity(newsIntent);
        }

//Current Affairs
        Intent currentAffairsIntent = new Intent(getActivity(), CurrentAffairsActivity.class);
        if (view.getId() == R.id.jan_2025) {
            currentAffairsIntent.putExtra("CurrentAffairsURL", "https://drive.google.com/file/d/1Y3nohuH9wgTm22BA3cODHh31usofKv6N/view?usp=sharing");
        //    currentAffairsIntent.putExtra("CurrentAffairsAssets", "current_affairs_jan2025.pdf");
        }
        else if (view.getId() == R.id.dec_2024) {
            currentAffairsIntent.putExtra("CurrentAffairsURL", "https://drive.google.com/file/d/1NfogCQQ_cSmYlnz7iJG4hEgQqXELokYc/view?usp=sharing");
        //    currentAffairsIntent.putExtra("CurrentAffairsAssets", "current_affairs_dec2024.pdf");
        }
        startActivity(currentAffairsIntent);

    }

    private void showOptionsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());    // Create an AlertDialog

        LinearLayout layout = new LinearLayout(requireContext());   // Create a LinearLayout for the options
        layout.setOrientation(LinearLayout.VERTICAL);

        Intent jobOptionIntent = new Intent(getActivity(), NewsActivity.class);

    // Create the buttons
        Button option1Button = new Button(requireContext());
        option1Button.setText("bdjobs.com");
        option1Button.setOnClickListener(view -> {  // Perform Task 1
            jobOptionIntent.putExtra("NewsURL", "https://bdjobs.com/");
            startActivity(jobOptionIntent);

        });
        Button option2Button = new Button(requireContext());
        option2Button.setText("ejobs.com.bd");
        option2Button.setOnClickListener(view -> {  // Perform Task 2
            jobOptionIntent.putExtra("NewsURL", "https://www.ejobs.com.bd/");
            startActivity(jobOptionIntent);
        });

    // Add the buttons to the layout
        layout.addView(option1Button);
        layout.addView(option2Button);
        builder.setView(layout);    // Add the layout to the builder

    // Add the dialog to the builder
        builder.setCancelable(true);
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();   // Create the dialog
        dialog.show();  // Show the dialog
    }

}