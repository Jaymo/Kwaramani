package com.Ramali.android.app;
/**
 * Thanks to HenryAdo {.Jaymo}
 */

import java.io.File;

import android.app.Activity;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.view.Display;

public class CaptureImage {

    private Bitmap scaled;

    private static final String CLASS_TAG = CaptureImage.class.getCanonicalName();

    public CaptureImage() {
        scaled = null;
    }

    public int getScreenOrientation(Activity context) {
        Display display = context.getWindowManager().getDefaultDisplay();
        if (display.getWidth() == display.getHeight()) {
            return Configuration.ORIENTATION_SQUARE;
        } else {
            if (display.getWidth() < display.getHeight()) {
                return Configuration.ORIENTATION_PORTRAIT;
            } else {
                return Configuration.ORIENTATION_LANDSCAPE;
            }
        }
    }

    public Uri getPhotoUri(String filename, Activity activity) {
        File path = new File(Environment.getExternalStorageDirectory(), activity.getPackageName());
        if (!path.exists() && path.mkdir()) {
            // /directory created
        }
        return Uri.fromFile(new File(path, filename));
    }

    public Bitmap getBitmap(Uri uri, Activity activity) {
        if (uri != null) {

            // Decode image size to handle proper image consumption
            Log.i(CLASS_TAG, "Decoding bitmap image to handle proper memory consumption ");
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;

            BitmapFactory.decodeFile(uri.getPath(), options);
            final int IMAGE_MAX_SIZE = 200;

            int scale = 1;
            if (options.outHeight > IMAGE_MAX_SIZE || options.outWidth > IMAGE_MAX_SIZE) {
                scale = (int)Math.pow(
                        2,
                        (int)Math.round(Math.log(IMAGE_MAX_SIZE
                                / (double)Math.max(options.outHeight, options.outWidth))
                                / Math.log(0.5)));
            }

            // Decode with inSampleSize
            BitmapFactory.Options options2 = new BitmapFactory.Options();
            options2.inSampleSize = scale;
            Bitmap original = BitmapFactory.decodeFile(uri.getPath(), options2);

            if (getScreenOrientation(activity) == Configuration.ORIENTATION_PORTRAIT
                    && original.getWidth() > original.getHeight()) {
                Matrix matrix = new Matrix();
                matrix.postRotate(90);
                Bitmap rotated = Bitmap.createBitmap(original, 0, 0, original.getWidth(),
                        original.getHeight(), matrix, true);
                original.recycle();
                return rotated;
            }
            return original;
        }
        return null;
    }

    public Bitmap scaleBitmap(Bitmap original) {
        Log.i(CLASS_TAG, "scaleBitmap is called ");
        if (original != null) {
            float ratio = (float)original.getWidth() / (float)original.getHeight();
            Log.i(CLASS_TAG, "Scalling image to " + 200 + " x " + ratio);
            scaled = Bitmap.createScaledBitmap(original, (int)(200 * ratio),
                    200, true);
            original.recycle();

            return scaled;
        }

        return null;
    }

}
