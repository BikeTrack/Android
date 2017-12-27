package eu.biketrack.android.utils;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.CursorLoader;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import eu.biketrack.android.R;

/**
 * Created by 42900 on 16/12/2017 for BikeTrack_Android.
 */

public class PictureManager {
    private static final int STORAGE_PERMISSION_CODE = 123;
    private static final int SELECT_PICTURE = 1;
    private static final String TAG = "PictureManager";
    private Uri uri;
    private Bitmap bitmap;
    byte[] imageInByte;

    public PictureManager(Uri uri) {
        this.uri = uri;
    }

    public Bitmap getBitmap(ContentResolver contentResolver) {
        if (bitmap == null) {
            try {
                bitmap = MediaStore.Images.Media.getBitmap(contentResolver, uri);
            } catch (Exception e) {
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

    public String getPath() {
        return uri.getPath();
    }

    public long getSize() {
        if (bitmap == null) return 0;
        return imageInByte.length;
    }

    public Bitmap compress() {
        if (getSize() > 20971520) { // si superieur a 20Mo
            int compressLevel = 1;
            for (int i = 2; i < 20; i++) {
                if (getSize() / i <= 20971520) {
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

    public static String getRealPath(Context context, Uri uri) {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
            return getRealPathFromURI_API19(context, uri);
        else
            return getRealPathFromURI_API11to18(context, uri);
    }

    @SuppressLint("NewApi")
    private static String getRealPathFromURI_API19(Context context, Uri uri) {
        String filePath = "";
        String wholeID = DocumentsContract.getDocumentId(uri);

        // Split at colon, use second item in the array
        String id = wholeID.split(":")[1];

        String[] column = {MediaStore.Images.Media.DATA};

        // where id is equal to
        String sel = MediaStore.Images.Media._ID + "=?";

        Cursor cursor = context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                column, sel, new String[]{id}, null);

        int columnIndex = cursor.getColumnIndex(column[0]);

        if (cursor.moveToFirst()) {
            filePath = cursor.getString(columnIndex);
        }
        cursor.close();
        return filePath;
    }


    @SuppressLint("NewApi")
    private static String getRealPathFromURI_API11to18(Context context, Uri contentUri) {
        String[] proj = {MediaStore.Images.Media.DATA};
        String result = null;

        CursorLoader cursorLoader = new CursorLoader(
                context,
                contentUri, proj, null, null, null);
        Cursor cursor = cursorLoader.loadInBackground();

        if (cursor != null) {
            int column_index =
                    cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            result = cursor.getString(column_index);
        }
        return result;
    }

    private static String getRealPathFromURI_BelowAPI11(Context context, Uri contentUri) {
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
        int column_index
                = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    public static void selectPicture(Activity activity) {
        Intent intent = new Intent();
//    intent.setType("image/jpg");
//    intent.setType("image/png");
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        activity.startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE);
    }

    public static String onActivityResult(Activity activity, int requestCode, int resultCode, Intent data, ImageView imageView) {
        String selectedImagePath = null;
        if (resultCode == activity.RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                Uri selectedImageUri = data.getData();
                selectedImagePath = PictureManager.getRealPath(activity, selectedImageUri);
                Log.d(TAG, "onActivityResult: " + selectedImageUri.getPath());
                Log.d(TAG, "onActivityResult: " + selectedImagePath);
                PictureManager pictureManager = new PictureManager(selectedImageUri);
                if (imageView != null)
                    imageView.setImageBitmap(pictureManager.getBitmap(activity.getContentResolver()));
                Log.d(TAG, "size image -> " + pictureManager.getSize());
            }
        }
        return selectedImagePath;
    }

    //Requesting permission
    public static void requestStoragePermission(Activity activity) {
        if (ContextCompat.checkSelfPermission(activity.getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
            return;

        if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            new AlertDialog.Builder(activity)
                    .setIcon(android.R.drawable.ic_dialog_info)
                    .setTitle(R.string.access_disk)
                    .setMessage(R.string.access_disk_message)
                    .setNegativeButton(R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
                        }
                    })
                    .setCancelable(false)
                    .show();
        } else {
            //And finally ask for the permission
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
        }
    }


    //This method will be called when the user will tap on allow or deny
    public static boolean onRequestPermissionsResult(Activity activity, int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        //Checking the request code of our request
        if (requestCode == STORAGE_PERMISSION_CODE) {

            //If permission is granted
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //Displaying a toast
//                Toast.makeText(activity, "Permission granted now you can read the storage", Toast.LENGTH_LONG).show();
                return true;
            } else {
                //Displaying another toast if permission is not granted
//                Toast.makeText(activity, "Oops you just denied the permission", Toast.LENGTH_LONG).show();
                return false;
            }
        }
        return false;
    }
}
