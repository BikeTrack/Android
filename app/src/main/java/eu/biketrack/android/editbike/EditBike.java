package eu.biketrack.android.editbike;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.android.gms.common.images.ImageManager;

import java.io.FileDescriptor;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import eu.biketrack.android.R;
import eu.biketrack.android.models.biketracker.BikeTrackerList;
import eu.biketrack.android.models.data_reception.Bike;
import eu.biketrack.android.models.data_send.SendBikeInfo;
import eu.biketrack.android.root.App;
import utils.PictureManager;

public class EditBike extends Activity implements EditBikeMVP.View {
    private static final String TAG = "EditBike";
    private Bike tmp = null;
    private static final int SELECT_PICTURE = 1;
    private String selectedImagePath;


    @Inject
    public EditBikeMVP.Presenter presenter;

    @BindView(R.id.bike_name_edit)
    EditText _name;
    @BindView(R.id.bike_trackerid_edit)
    EditText _trackerid;
    @BindView(R.id.bike_delete_button)
    Button button_delete_bike;
    @BindView(R.id.image_to_upload)
    ImageView imageToUpload;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((App) getApplication()).getComponent().inject(this);
        setContentView(R.layout.fragment_edit_bike);
        ButterKnife.bind(this);
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        String bikeid = getIntent().getStringExtra("BikeId");
        button_delete_bike.setVisibility(View.GONE);
        if (bikeid != null && !bikeid.isEmpty()) {
            tmp = BikeTrackerList.getInstance().getBikeByBikeId(bikeid);
            _name.setText(tmp.getName());
            _trackerid.setText(tmp.getTracker());
            button_delete_bike.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.setView(this);
    }

    @OnClick(R.id.bike_save_button)
    public void saveBike() {
        SendBikeInfo bike = new SendBikeInfo(_name.getText().toString(), "", _trackerid.getText().toString());
        if (tmp == null)
            presenter.createBike(bike);
        else{
            Log.d(TAG, "saveBike: " +selectedImagePath);
            presenter.uploadBikePhoto(selectedImagePath, tmp.getId());
            //presenter.updateBike(tmp.getId(), bike);
        }

    }

    @Override
    public void close() {
        this.finish();
    }

    @OnClick(R.id.bike_delete_button)
    public void deleteBike() {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle(R.string.alert_confirmation_delete_title)
                .setMessage(R.string.alert_confirmation_delete_message)
                .setPositiveButton(R.string.alert_confirmation_delete_yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        presenter.deleteBike(tmp.getId(), null);
                    }
                })
                .setNegativeButton(R.string.alert_confirmation_delete_no, null)
                .show();

    }

@OnClick(R.id.button_selectImage)
public void selectImage(){
    Intent intent = new Intent();
//    intent.setType("image/jpg");
//    intent.setType("image/png");
    intent.setType("image/*");
    intent.setAction(Intent.ACTION_GET_CONTENT);
    startActivityForResult(Intent.createChooser(intent,
            "Select Picture"), SELECT_PICTURE);
}

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                Uri selectedImageUri = data.getData();
                selectedImagePath = getRealPathFromURI_API19(this, selectedImageUri);//getPath(selectedImageUri);
                Log.d(TAG, "onActivityResult: " + selectedImageUri.getPath());
                Log.d(TAG, "onActivityResult: " + selectedImagePath);
                PictureManager pictureManager = new PictureManager(selectedImageUri);
                this.imageToUpload.setImageBitmap(pictureManager.getBitmap(this.getContentResolver()));
                Log.d(TAG, "size image -> " + pictureManager.getSize());

            }
        }
    }

    /**
     * helper to retrieve the path of an image URI
     */
    public String getPath(Uri uri) {
        // just some safety built in
        if( uri == null ) {
            // TODO perform some logging or show user feedback
            return null;
        }
        // try to retrieve the image from the media store first
        // this will only work for images selected from gallery
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        if( cursor != null ){
            int column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            String path = cursor.getString(column_index);
            cursor.close();
            return path;
        }
        // this is our fallback here
        return uri.getPath();
    }
    public static String getRealPathFromURI_API11to18(Context context, Uri contentUri) {
        String[] proj = { MediaStore.Images.Media.DATA };
        String result = null;

        CursorLoader cursorLoader = new CursorLoader(
                context,
                contentUri, proj, null, null, null);
        Cursor cursor = cursorLoader.loadInBackground();

        if(cursor != null){
            int column_index =
                    cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            result = cursor.getString(column_index);
        }
        return result;
    }
    @SuppressLint("NewApi")
    public static String getRealPathFromURI_API19(Context context, Uri uri){
        String filePath = "";
        String wholeID = DocumentsContract.getDocumentId(uri);

        // Split at colon, use second item in the array
        String id = wholeID.split(":")[1];

        String[] column = { MediaStore.Images.Media.DATA };

        // where id is equal to
        String sel = MediaStore.Images.Media._ID + "=?";

        Cursor cursor = context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                column, sel, new String[]{ id }, null);

        int columnIndex = cursor.getColumnIndex(column[0]);

        if (cursor.moveToFirst()) {
            filePath = cursor.getString(columnIndex);
        }
        cursor.close();
        return filePath;
    }

}
