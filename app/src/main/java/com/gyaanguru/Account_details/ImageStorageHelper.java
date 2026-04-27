package com.gyaanguru.Account_details;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;

public class ImageStorageHelper {

    public static String saveImageToInternalStorage(Context context, Bitmap bitmap, String fileName) {
        File directory = context.getDir("profile_images", Context.MODE_PRIVATE);
        File mypath = new File(directory, fileName + ".jpg");

        try (FileOutputStream fos = new FileOutputStream(mypath)) {
            // Use 90% quality to balance size and quality
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, fos);
        } catch (Exception e) {
            Log.e("ImageStorageHelper", "Error saving image", e);
            return null;
        }
        return mypath.getAbsolutePath();
    }

    public static Bitmap loadImageFromStorage(String path) {
        try {
            File f = new File(path);
            if (f.exists()) {
                return BitmapFactory.decodeFile(path);
            }
        } catch (Exception e) {
            Log.e("ImageStorageHelper", "Error loading image", e);
        }
        return null;
    }
}
