package org.tensorflow.lite.examples.detection;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.mlkit.common.model.DownloadConditions;
import com.google.mlkit.common.model.RemoteModelManager;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.label.ImageLabel;
import com.google.mlkit.vision.label.ImageLabeler;
import com.google.mlkit.vision.label.ImageLabeling;
import com.google.mlkit.vision.label.automl.AutoMLImageLabelerLocalModel;
import com.google.mlkit.vision.label.automl.AutoMLImageLabelerOptions;
import com.google.mlkit.vision.label.automl.AutoMLImageLabelerRemoteModel;

import java.util.List;

public class HandActivity extends AppCompatActivity {
    private ImageView imageView;
    private Button button, take;
    private TextView textView;
    private ProgressDialog dialog;
    private Bundle extras;
    private Bitmap imageBitmap;

    private static final int ACCESS_FILE = 10;
    private static final int PERMISSION_FILE = 20;
    private static final int REQUEST_IMAGE_CAPTURE = 1;

    AutoMLImageLabelerRemoteModel remoteModel =
            new AutoMLImageLabelerRemoteModel.Builder("Hands_2020721235612").build();

    DownloadConditions downloadConditions = new DownloadConditions.Builder()
            .requireWifi()
            .build();

    AutoMLImageLabelerLocalModel localModel =
            new AutoMLImageLabelerLocalModel.Builder()
                    .setAssetFilePath("model/manifest.json")
                    .build();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hand);

        imageView = findViewById(R.id.image);
        button = findViewById(R.id.button);
        take = findViewById(R.id.take);
        textView = findViewById(R.id.textView);

        dialog = new ProgressDialog(this);


        take.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ContextCompat.checkSelfPermission(HandActivity.this, Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(HandActivity.this, new String[]{Manifest.permission.CAMERA},PERMISSION_FILE);
                }else{
                    dispatchTakePictureIntent();
                }
            }
        });

    }


    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK){

            extras = data.getExtras();
            imageBitmap = (Bitmap) extras.get("data");
            imageView.setImageBitmap(imageBitmap);

            Uri uri = data.getData();
            RemoteModelManager.getInstance().download(remoteModel, downloadConditions);
            setLabelFromLocalModel(uri);
            textView.setText("");
        }
    }

    private void setLabelFromLocalModel(Uri uri){
        showProgressDialog();
        AutoMLImageLabelerOptions options =
                new AutoMLImageLabelerOptions.Builder(localModel)
                        .setConfidenceThreshold(0.0f)
                        .build();
        ImageLabeler labeler = ImageLabeling.getClient(options);
        InputImage image = InputImage.fromBitmap(imageBitmap,180 );
        processImageLabeler(labeler, image);
    }

    public void processImageLabeler(ImageLabeler labeler, InputImage image){
        labeler.process(image).addOnSuccessListener(new OnSuccessListener<List<ImageLabel>>() {
            @Override
            public void onSuccess(List<ImageLabel> labels) {
                dialog.dismiss();
                String eachLabel = "";
                for(ImageLabel label : labels){
                    eachLabel = labels.get(0).getText().toUpperCase();
                    float confidence = labels.get(0).getConfidence();
                    textView.setText(eachLabel+" : "+ (""+confidence * 100).subSequence(0,4)+"%"+"\n");
                }
             switch (eachLabel){
                    case "HELP":
                        textView.append("Comunicate");

                        PackageManager pm=getPackageManager();
                        try {

                            Intent waIntent = new Intent(Intent.ACTION_SEND);
                            waIntent.setType("text/plain");
                            String text = "ESTOY HERIDO, NECESITO AYUDA";

                            PackageInfo info=pm.getPackageInfo("com.whatsapp", PackageManager.GET_META_DATA);
                            //Check if package exists or not. If not then code
                            //in catch block will be called
                            waIntent.setPackage("com.whatsapp");

                            waIntent.putExtra(Intent.EXTRA_TEXT, text);
                            startActivity(Intent.createChooser(waIntent, "Share with"));

                        } catch (PackageManager.NameNotFoundException e) {
                            Toast.makeText(HandActivity.this, "WhatsApp not Installed", Toast.LENGTH_SHORT)
                                    .show();
                        }
                        break;
                    case "INJURED":
                        textView.append("ESTAS HERIDO!");

                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=mhBe7Q6mH3U")));
                        Log.i("Video", "Video Playing....");

                        break;
                    case "GOOD":
                        textView.append("QUE BIEN");

                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=79CPz7P2Ypo")));
                        Log.i("Video", "Video Playing....");
                        break;
                    case "CPR":
                        textView.append("PRIMEROS AUXILIOS");
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=-NodDRTsV88")));
                        Log.i("Video", "Video Playing....");
                        break;
                    case "CALL":
                        Intent intent = new Intent(Intent.ACTION_DIAL);
                        startActivity(intent);
                        break;


                }


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(HandActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showProgressDialog() {
        dialog.setMessage("Loading...");
        dialog.setCancelable(false);
        dialog.show();
    }
}