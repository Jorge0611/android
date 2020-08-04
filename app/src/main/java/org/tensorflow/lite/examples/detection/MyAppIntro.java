package org.tensorflow.lite.examples.detection;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import com.github.appintro.AppIntro;
import com.github.appintro.AppIntroFragment;
import com.github.appintro.AppIntroPageTransformerType;

import org.tensorflow.lite.examples.detection.tflite.SimilarityClassifier;

public class MyAppIntro extends AppIntro {

    private String title;
    private String description;
    private int imageDrawable;
    private int backgroundDrawable;
    private Object backgroundResource;
    private boolean isColorTransitionsEnabled;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addSlide(AppIntroFragment.newInstance(
                title = "Helpful!",
                description = "An easy way to show different hand signals belongs to their meaning." +
                        "Let us colaborate together!",
                (Integer)(imageDrawable = R.drawable.slider6),
                ContextCompat.getColor(getApplicationContext(),R.color.color2)

        ));


        addSlide(AppIntroFragment.newInstance(

                title = "Different!",
                description = "Amazing and powerfull app that includes facial recognition just for you.",
                (Integer)(imageDrawable = R.drawable.slider7),
                R.drawable.bien

        ));
        addSlide(AppIntroFragment.newInstance(

                 "Innovative!",
              "We're your link betwen emergency and help.",
                (Integer) (backgroundDrawable = R.drawable.slider9),
                ContextCompat.getColor(getApplicationContext(),R.color.color1)

        ));


        isColorTransitionsEnabled = true;
        setTransformer(new AppIntroPageTransformerType.Parallax());
        setImmersiveMode();

    }



    @Override
    public void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);
        Intent intent= new Intent(MyAppIntro.this, MainActivity.class);
        MyAppIntro.this.startActivity(intent);
        finish();

    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        Intent intent= new Intent(MyAppIntro.this, MainActivity.class);
        MyAppIntro.this.startActivity(intent);
        finish();


    }
}