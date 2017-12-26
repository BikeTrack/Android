package eu.biketrack.android.utils;

import android.content.ContentResolver;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

/**
 * Created by 42900 on 16/12/2017 for BikeTrack_Android.
 */

public class PictureManager {
    private static final String TAG = "PictureManager";
    private Uri uri;
    private Bitmap bitmap;
    byte[] imageInByte;

    public PictureManager(Uri uri) {
        this.uri = uri;
    }

    public Bitmap getBitmap(ContentResolver contentResolver){
        if (bitmap == null){
            try {
                bitmap = MediaStore.Images.Media.getBitmap(contentResolver, uri);
            } catch (Exception e){
                Log.e(TAG, "getBitmap: ", e);
            }
        }
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        imageInByte = stream.toByteArray();
        return bitmap;
    }

    public Uri getUri() {
        return uri;
    }

    public String getPath(){
        return uri.getPath();
    }

    public long getSize(){
        if (bitmap == null) return 0;
        return imageInByte.length;
    }

    public Bitmap compress(){
        if (getSize() > 20971520) { // si superieur a 20Mo
            int compressLevel = 1;
            for (int i = 2; i < 20; i++){
                if (getSize() / i <= 20971520){
                    compressLevel = i;
                    break;
                }
            }
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = compressLevel;
            return BitmapFactory.decodeStream(new ByteArrayInputStream(imageInByte), null, options);
        }
        return bitmap; // inferieur donc retourné non compressé
    }

}
