package com.example.pallav.imagecapture;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class FilesActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_files);
        File storageDir =
                getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        ArrayList<ImageFileObj> list = new ArrayList<>();
        File[] files = storageDir.listFiles();
        Log.d("Files", "Size: "+ files.length);
        for (int i = 0; i < files.length; i++)
        {
//                    Log.d("Files", "FileName:" + files[i].getName());
            list.add(new ImageFileObj(files[i].getName(), files[i].getPath()));
        }
        Collections.sort(list);

        RecyclerView rvFiles = (RecyclerView) findViewById(R.id.rvFiles);

        FilesAdapter adapter = new FilesAdapter(list, this);
        // Attach the adapter to the recyclerview to populate items
        rvFiles.setAdapter(adapter);
        // Set layout manager to position the items
        rvFiles.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
    }
}
