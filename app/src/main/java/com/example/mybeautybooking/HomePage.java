package com.example.mybeautybooking;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
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
TextView pro,text1,text2;
Button  vente,beaute;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        pro=(TextView) findViewById(R.id.pro);
        vente=(Button)findViewById(R.id.vente);
        beaute=(Button)findViewById(R.id.repertoire);
        text1=(TextView)findViewById(R.id.first);
        text2=(TextView)findViewById(R.id.second);

        final ValueAnimator animator = ValueAnimator.ofFloat(0.0f, 2.0f);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setInterpolator(new LinearInterpolator());
        animator.setDuration(20000L);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                final float progress = (float) animation.getAnimatedValue();
                final float width = text1.getMeasuredWidth();
                final float width2 = text2.getMeasuredWidth();
                final float translationX = width * progress;
                text1.setTranslationX(translationX);
                text2.setTranslationX(translationX-width);
            }
        });
        animator.start();
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
