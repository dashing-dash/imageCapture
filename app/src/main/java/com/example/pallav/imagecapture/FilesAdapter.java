package com.example.pallav.imagecapture;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

// Create the basic adapter extending from RecyclerView.Adapter
// Note that we specify the custom ViewHolder which gives us access to our views
public class FilesAdapter extends
        RecyclerView.Adapter<FilesAdapter.ViewHolder> {

    private List<ImageFileObj> mFiles;
    private Activity activity;

    // Pass in the contact array into the constructor
    public FilesAdapter(List<ImageFileObj> files, Activity activity) {
        mFiles = files;
        this.activity = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View fileView = inflater.inflate(R.layout.item_file, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(fileView);
        return viewHolder;
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int position) {
        // Get the data model based on position
        ImageFileObj imgFileObj = mFiles.get(position);

        // Set item views based on your views and data model
        TextView textView = viewHolder.fileNameTextView;
        textView.setText(imgFileObj.getFileName());
        Button button = viewHolder.openButton;
        button.setText("View");
        button.setEnabled(true);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent resultIntent = new Intent();
                resultIntent.putExtra("filePath", mFiles.get(position).getFilePath());
//                resultIntent.putExtra("filePath", "hello");
                activity.setResult(Activity.RESULT_OK, resultIntent);
                activity.finish();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mFiles.size();
    }

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    public class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView fileNameTextView;
        public Button openButton;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            fileNameTextView = (TextView) itemView.findViewById(R.id.file_name);
            openButton = (Button) itemView.findViewById(R.id.open_button);
        }
    }
}