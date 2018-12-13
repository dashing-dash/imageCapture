package com.example.pallav.imagecapture;

import android.support.annotation.NonNull;

public class ImageFileObj implements Comparable {
    private String fileName;
    private String filePath;

    public ImageFileObj(String fileName, String filePath){
        this.fileName = fileName;
        this.filePath = filePath;
    }

    public String getFileName() {
        return fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    @Override
    public int compareTo(@NonNull Object o) {
        String name = ((ImageFileObj)o).getFileName().toUpperCase();
        return this.fileName.toUpperCase().compareTo(name);
    }
}
