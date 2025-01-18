package com.gyaanguru.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gyaanguru.Activities.CurrentAffairsActivity;
import com.gyaanguru.Activities.NewsActivity;
import com.gyaanguru.Adapter.SliderAdapter;
import com.gyaanguru.Models.SliderData;
import com.gyaanguru.R;

import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;

public class HomeFragment extends Fragment implements View.OnClickListener {

    private SliderAdapter adapter;
    private ArrayList<SliderData> sliderDataArrayList;
    private SliderView sliderView;

    private ImageView bd_pratidin, karatoa, jugantor, kaler_kantho, inqilab, jamuna_tv;
    private Button dec_2024, jan_2025;

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

        bd_pratidin.setOnClickListener(this);
        karatoa.setOnClickListener(this);
        jugantor.setOnClickListener(this);
        kaler_kantho.setOnClickListener(this);
        inqilab.setOnClickListener(this);
        jamuna_tv.setOnClickListener(this);

        dec_2024 = (Button) view.findViewById(R.id.dec_2024);
        jan_2025 = (Button) view.findViewById(R.id.jan_2025);
        dec_2024.setOnClickListener(this);
        jan_2025.setOnClickListener(this);
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
        startActivity(newsIntent);

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

}