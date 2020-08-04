package org.tensorflow.lite.examples.detection;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import com.github.appintro.AppIntro;
import com.github.appintro.AppIntroFragment;

public class MyAppIntro extends AppIntro {

    private String title;
    private String description;
    private int imageDrawable;
    private int backgroundDrawable;


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
                (Integer) (backgroundDrawable = R.drawable.slider2)



        ));
        addSlide(AppIntroFragment.newInstance(

                title = "Innovative!",
                description = "We're your link betwen emergency and help",
                (Integer)(imageDrawable = R.drawable.slider4),
                (Integer) (backgroundDrawable = R.drawable.bien)



        ));




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