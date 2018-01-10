package eu.biketrack.android.bill;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import eu.biketrack.android.R;
import eu.biketrack.android.root.App;
import eu.biketrack.android.utils.ErrorManager;

public class Bill extends AppCompatActivity implements BillMVP.View {
    @Inject
    public BillMVP.Presenter presenter;
    @BindView(R.id.billProgressBar)
    ProgressBar billProgressBar;
    @BindView(R.id.billImage)
    ImageView billImage;
    private String bikeId = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((App) getApplication()).getComponent().inject(this);
        setContentView(R.layout.activity_bill);
        ButterKnife.bind(this);
        billProgressBar.setVisibility(View.GONE);
        bikeId = getIntent().getStringExtra("BikeId");
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.setView(this);
        presenter.loadBill(bikeId);
    }

    @Override
    public void close() {
        finish();
    }

    @Override
    public void progressBarVisibility(boolean visible) {
        billProgressBar.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    @Override
    public void setBitmap(Bitmap bitmap) {
        billImage.setImageBitmap(bitmap);
    }

    @Override
    public void displayBitmapNull() {
        ErrorManager errorManager = new ErrorManager(this, getResources());
        Toast.makeText(this, R.string.bill_bitmap_null, Toast.LENGTH_LONG).show();
    }
}
