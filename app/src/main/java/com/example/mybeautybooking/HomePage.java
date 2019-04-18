package com.example.mybeautybooking;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.igalata.bubblepicker.BubblePickerListener;
import com.igalata.bubblepicker.model.PickerItem;
import com.igalata.bubblepicker.rendering.BubblePicker;

import org.jetbrains.annotations.NotNull;

import java.time.Duration;
import java.util.ArrayList;

public class HomePage extends AppCompatActivity {
BubblePicker bubblePicker;
String [] items={
        "Répértoire Beauté",
        "Vente Privé",
        "Je suis professionnel"
};

int[] images ={
        R.drawable.logo,
        R.drawable.logo,
        R.drawable.logo
};

int [] colors ={
        Color.parseColor("#1A237E"),
        Color.parseColor("#6200EA"),
        Color.parseColor("#1A237E")
};
TextView pro;
Button  vente,beaute;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        pro=(TextView) findViewById(R.id.pro);
        vente=(Button)findViewById(R.id.vente);
        beaute=(Button)findViewById(R.id.repertoire);
        pro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //faire des actions aux boutons et textView lors du click
                pro.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent = new Intent(HomePage.this, Login_pro.class);
                        startActivity(intent);
                    }
                });
            }
        });

        vente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //faire des actions aux boutons et textView lors du click
                vente.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent = new Intent(HomePage.this, ClientLoginActivity.class);
                        startActivity(intent);
                    }
                });
            }
        });


    }





}
