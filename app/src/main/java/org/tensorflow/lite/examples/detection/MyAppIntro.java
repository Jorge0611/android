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
                description = "Let us colaborate together!",
                (Integer)(imageDrawable = R.drawable.slider1),
                (Integer) (backgroundDrawable = R.drawable.bien)






        ));


        addSlide(AppIntroFragment.newInstance(

                title = "Different!",
                description = "Respossive application just for you",
                (Integer)(imageDrawable = R.drawable.slider2),
                ContextCompat.getColor(getApplicationContext(),R.color.color1)



        ));
        addSlide(AppIntroFragment.newInstance(

                 "Innovative!",
              "We're your link Between emergency and help",
                (Integer)(imageDrawable = R.drawable.slider4),
                R.drawable.slider4




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
