package com.example.pallav.imagecapture;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.File;
import java.io.IOException;



public class MainActivity extends AppCompatActivity {

    private Button btnCamera;
    private Button btnFile;
    private ImageView capturedImage;
    private String imageFilePath;
    private String fileName = "default";
    private static final int REQUEST_CAPTURE_IMAGE = 100;
    private static final int REQUEST_FILE_PATH = 1234;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        Typeface font = Typeface.createFromAsset( getAssets(), "fontawesome-webfont.ttf" );
        btnCamera = (Button) findViewById(R.id.btnCamera);
        btnFile = (Button) findViewById(R.id.btnFile);

        capturedImage = (ImageView) findViewById(R.id.capturedImage);

//        btnCamera.setTypeface(font);

        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                capturedImage.setImageResource(0);
                showPopUp();
            }
        });

        btnFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), FilesActivity.class);
                startActivityForResult(intent, REQUEST_FILE_PATH);
            }
        });
    }



    private void showPopUp() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Enter name/id");

        // Set up the input
        final EditText input = new EditText(this);
        // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        // Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                fileName = input.getText().toString();
                openCamera();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    private void openCamera() {
        Intent pictureIntent = new Intent(
                MediaStore.ACTION_IMAGE_CAPTURE);
        if (pictureIntent.resolveActivity(getPackageManager()) != null) {
            //Create a file to store the image
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.pallav.provider", photoFile);
                pictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                        photoURI);
                startActivityForResult(pictureIntent,
                        REQUEST_CAPTURE_IMAGE);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        /*if(resultCode == RESULT_OK) {
            Bitmap bp = (Bitmap) data.getExtras().get("data");
            capturedImage.setImageBitmap(bp);
        }*/
        switch(requestCode) {
            case (REQUEST_CAPTURE_IMAGE) : {
                if (resultCode == Activity.RESULT_OK) {
                    Toast.makeText(this, imageFilePath, Toast.LENGTH_SHORT);
                    Glide.with(this).load(imageFilePath).into(capturedImage);
                }
            }break;
            case (REQUEST_FILE_PATH) : {
                if (resultCode == Activity.RESULT_OK) {
//                    Toast.makeText(this, "returned file path : " + data.getStringExtra("filePath"), Toast.LENGTH_SHORT);
                    if(null != data.getStringExtra("filePath")){
                        Glide.with(this).load(data.getStringExtra("filePath")).into(capturedImage);
                    }

                }
            }break;
        }
        /*else if(resultCode == Activity.RESULT_CANCELLED) {
            // User Cancelled the action
        }*/
    }


    private File createImageFile() throws IOException {

        File storageDir =
                getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        File image = new File(storageDir, fileName + ".jpg");

        imageFilePath = image.getAbsolutePath();
        return image;
    }

}