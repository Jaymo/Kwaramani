package com.Ramali.android.app;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;



import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;

public class ImageManager {
	public static String savePath = "";
	public static final String PREFS_NAME = "KwaRamani";
	public static String fileName = "";


    // Images
    public static Drawable getImages(String path,String fileName) {

        Drawable d = null;
        BitmapDrawable bD = new BitmapDrawable((new File(path, fileName)).toString());
        d = bD.mutate();

        return d;
    }
    public static void loadSettings(Context context) {
    	final SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, 0);

        final String path = context.getDir("",
                Context.MODE_WORLD_READABLE | Context.MODE_WORLD_WRITEABLE).toString();
    	 savePath = settings.getString("savePath", path + "/");
    	 final File dir = new File(ImageManager.savePath);
         dir.mkdirs();
    }





    public static void writeImage(byte[] data, String filename, String path) {

        deleteImage(filename,path);
        Log.d("Deleting Images: ",path+filename);
        if (data != null) {
            FileOutputStream fOut;
            try {
                fOut = new FileOutputStream(new File(path, filename), false);
                fOut.write(data);
                fOut.flush();
                fOut.close();
            } catch (final FileNotFoundException e) {

                e.printStackTrace();
            } catch (final IOException e) {

                e.printStackTrace();
            }
        }

    }

    public static void deleteImage(String filename, String path) {
        File f = new File(path, filename);
        if (f.exists()) {
            f.delete();
        }
    }

    public static Bitmap getBitmap(String fileName,String path) {
        try {
            File imageFile = new File(path, fileName);
            // Decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(new FileInputStream(imageFile),null, o);

            // The new size we want to scale to
            final int REQUIRED_SIZE = 400;

            // Find the correct scale value. It should be the power of 2.
            int width_tmp = o.outWidth, height_tmp = o.outHeight;
            int scale = 1;
            while (true) {
                if (width_tmp / 2 < REQUIRED_SIZE || height_tmp / 2 < REQUIRED_SIZE)
                    break;
                width_tmp /= 2;
                height_tmp /= 2;
                scale *= 2;
            }

            // Decode with inSampleSize
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            return BitmapFactory.decodeStream(
                    new FileInputStream(imageFile), null, o2);
        } catch (FileNotFoundException e) {
        }
        return null;
    }

    

}

