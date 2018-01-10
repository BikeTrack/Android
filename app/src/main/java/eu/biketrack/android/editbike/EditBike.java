package eu.biketrack.android.editbike;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import eu.biketrack.android.R;
import eu.biketrack.android.models.biketracker.BikeTrackerList;
import eu.biketrack.android.models.data_reception.Bike;
import eu.biketrack.android.models.data_send.SendBikeInfo;
import eu.biketrack.android.root.App;
import eu.biketrack.android.utils.ErrorManager;
import eu.biketrack.android.utils.PictureManager;

public class EditBike extends Activity implements EditBikeMVP.View {
    public static final int SELECT_PICTURE_BIKE = 1;
    public static final int REQUEST_IMAGE_CAPTURE_BIKE = 2;
    public static final int SELECT_PICTURE_BILL = 3;
    public static final int REQUEST_IMAGE_CAPTURE_BILL = 4;
    private static final String TAG = "EditBike";
    @Inject
    public EditBikeMVP.Presenter presenter;
    @BindView(R.id.bike_name_edit)
    EditText _name;
    @BindView(R.id.bike_trackerid_edit)
    EditText _trackerid;
    @BindView(R.id.bike_serial_edit)
    EditText _serial;
    @BindView(R.id.bike_delete_button)
    Button button_delete_bike;
    @BindView(R.id.tv_bike)
    TextView tv_bike;
    @BindView(R.id.image_to_upload)
    ImageView imageToUpload;
    @BindView(R.id.button_selectBike)
    Button buttonSelectBike;
    @BindView(R.id.button_captureBike)
    Button buttonCaptureBike;
    @BindView(R.id.tv_bill)
    TextView tv_bill;
    @BindView(R.id.bill_to_upload)
    ImageView billToUpload;
    @BindView(R.id.button_selectBill)
    Button buttonSelectBill;
    @BindView(R.id.button_captureBill)
    Button buttonCaptureBill;
    private Bike tmp = null;
    private String selectedImagePathBike;
    private String selectedImagePathBill;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((App) getApplication()).getComponent().inject(this);
        setContentView(R.layout.activity_edit_bike);
        ButterKnife.bind(this);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        String bikeid = getIntent().getStringExtra("BikeId");
        button_delete_bike.setVisibility(View.GONE);
        if (bikeid != null && !bikeid.isEmpty()) {
            tmp = BikeTrackerList.getInstance().getBikeByBikeId(bikeid);
            _name.setText(tmp.getName());
            _trackerid.setText(tmp.getTracker());
            _serial.setText(tmp.getSerial());

            button_delete_bike.setVisibility(View.VISIBLE);

            tv_bill.setVisibility(View.VISIBLE);
            tv_bike.setVisibility(View.VISIBLE);
            imageToUpload.setVisibility(View.VISIBLE);
            buttonSelectBike.setVisibility(View.VISIBLE);
            buttonCaptureBike.setVisibility(View.VISIBLE);
            billToUpload.setVisibility(View.VISIBLE);
            buttonSelectBill.setVisibility(View.VISIBLE);
            buttonCaptureBill.setVisibility(View.VISIBLE);
        } else {
            tv_bill.setVisibility(View.GONE);
            tv_bike.setVisibility(View.GONE);
            imageToUpload.setVisibility(View.GONE);
            buttonSelectBike.setVisibility(View.GONE);
            buttonCaptureBike.setVisibility(View.GONE);
            billToUpload.setVisibility(View.GONE);
            buttonSelectBill.setVisibility(View.GONE);
            buttonCaptureBill.setVisibility(View.GONE);
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
        SendBikeInfo bike = new SendBikeInfo(_name.getText().toString(), "", _trackerid.getText().toString(), _serial.getText().toString());
        if (tmp == null)
            presenter.createBike(bike);
        else {
            if (selectedImagePathBike != null) {
                presenter.uploadBikePhoto(selectedImagePathBike, tmp.getId());
            }
            if (selectedImagePathBill != null) {
                presenter.uploadBikeBill(selectedImagePathBill, tmp.getId());
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

    @OnClick(R.id.button_selectBike)
    public void selectBike() {
        PictureManager.selectPicture(this, SELECT_PICTURE_BIKE);
    }

    @OnClick(R.id.button_captureBike)
    public void captureBike() {
        PictureManager.openCamera(this, REQUEST_IMAGE_CAPTURE_BIKE);
    }

    @OnClick(R.id.button_selectBill)
    public void selectBill() {
        PictureManager.selectPicture(this, SELECT_PICTURE_BILL);
    }

    @OnClick(R.id.button_captureBill)
    public void captureBill() {
        PictureManager.openCamera(this, REQUEST_IMAGE_CAPTURE_BILL);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == SELECT_PICTURE_BIKE || requestCode == REQUEST_IMAGE_CAPTURE_BIKE) {
            selectedImagePathBike = PictureManager.onActivityResult(this, requestCode, resultCode, data, imageToUpload, SELECT_PICTURE_BIKE, REQUEST_IMAGE_CAPTURE_BIKE);
            if (selectedImagePathBike == null) {
                new AlertDialog.Builder(this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle(R.string.bad_picture)
                        .setMessage(R.string.bad_picture_message)
                        .setPositiveButton(R.string.ok, null)
                        .show();
            }
        }
        if (requestCode == SELECT_PICTURE_BILL || requestCode == REQUEST_IMAGE_CAPTURE_BILL) {
            selectedImagePathBill = PictureManager.onActivityResult(this, requestCode, resultCode, data, billToUpload, SELECT_PICTURE_BILL, REQUEST_IMAGE_CAPTURE_BILL);
            if (selectedImagePathBill == null) {
                new AlertDialog.Builder(this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle(R.string.bad_picture)
                        .setMessage(R.string.bad_picture_message)
                        .setPositiveButton(R.string.ok, null)
                        .show();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Boolean ret = PictureManager.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
        if (!ret) {
            tv_bill.setVisibility(View.GONE);
            tv_bike.setVisibility(View.GONE);
            imageToUpload.setVisibility(View.GONE);
            buttonSelectBike.setVisibility(View.GONE);
            buttonCaptureBike.setVisibility(View.GONE);
            billToUpload.setVisibility(View.GONE);
            buttonSelectBill.setVisibility(View.GONE);
            buttonCaptureBill.setVisibility(View.GONE);
        } else {
            tv_bill.setVisibility(View.VISIBLE);
            tv_bike.setVisibility(View.VISIBLE);
            imageToUpload.setVisibility(View.VISIBLE);
            buttonSelectBike.setVisibility(View.VISIBLE);
            buttonCaptureBike.setVisibility(View.VISIBLE);
            billToUpload.setVisibility(View.VISIBLE);
            buttonSelectBill.setVisibility(View.VISIBLE);
            buttonCaptureBill.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void displayError(Throwable throwable) {
        ErrorManager errorManager = new ErrorManager(this, getResources());
        Toast.makeText(this, errorManager.getMessageFromThrowable(throwable), Toast.LENGTH_LONG).show();
    }
}
