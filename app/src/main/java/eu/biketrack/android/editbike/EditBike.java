package eu.biketrack.android.editbike;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import eu.biketrack.android.R;
import eu.biketrack.android.models.biketracker.BikeTrackerList;
import eu.biketrack.android.models.data_reception.Bike;
import eu.biketrack.android.models.data_send.SendBikeInfo;
import eu.biketrack.android.root.App;
import eu.biketrack.android.utils.PictureManager;

public class EditBike extends Activity implements EditBikeMVP.View {
    private static final String TAG = "EditBike";
    private Bike tmp = null;

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
    @BindView(R.id.button_selectImage)
    Button buttonSelectImage;

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
        } else {
            imageToUpload.setVisibility(View.GONE);
            buttonSelectImage.setVisibility(View.GONE);
        }

        PictureManager.requestStoragePermission(this);
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
            if (selectedImagePath != null) {
                Log.d(TAG, "saveBike: " + selectedImagePath);
                presenter.uploadBikePhoto(selectedImagePath, tmp.getId());
            }
            presenter.updateBike(tmp.getId(), bike);
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
        PictureManager.selectPicture(this);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        selectedImagePath = PictureManager.onActivityResult(this, requestCode, resultCode, data, imageToUpload);
        if (selectedImagePath == null){
            new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle(R.string.bad_picture)
                    .setMessage(R.string.bad_picture_message)
                    .setPositiveButton(R.string.ok, null)
                    .show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Boolean ret = PictureManager.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
        if (!ret){
            buttonSelectImage.setVisibility(View.GONE);
            imageToUpload.setVisibility(View.GONE);
        } else{
            buttonSelectImage.setVisibility(View.VISIBLE);
            imageToUpload.setVisibility(View.VISIBLE);
        }
    }
}
