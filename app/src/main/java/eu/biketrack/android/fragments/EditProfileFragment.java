package eu.biketrack.android.fragments;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import eu.biketrack.android.R;
import eu.biketrack.android.activities.AutoLogin;
import eu.biketrack.android.api_connection.ApiConnect;
import eu.biketrack.android.api_connection.BiketrackService;
import eu.biketrack.android.api_connection.Statics;
import eu.biketrack.android.models.User;
import eu.biketrack.android.models.data_reception.ReceptDeleteUser;
import eu.biketrack.android.models.data_reception.ReceptUser;
import eu.biketrack.android.models.data_reception.ReceptUserUpdate;
import eu.biketrack.android.models.data_send.DeleteUser;
import eu.biketrack.android.models.data_send.Img;
import eu.biketrack.android.models.data_send.SendUserUpdate;
import eu.biketrack.android.models.data_send.UserUpdate;
import eu.biketrack.android.session.LoginManager;
import eu.biketrack.android.session.Session;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Response;


public class EditProfileFragment extends Fragment {
    public static final int GET_FROM_GALLERY = 3;
    private static String TAG = "BIKETRACK - EditProfile";
    private Unbinder unbinder;
    private Session session;
    private BiketrackService biketrackService;
    private CompositeDisposable _disposables;
    private User user;
    Bitmap bitmap = null;
    File photo;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tmp_img)
    ImageView image_tmp;
    @BindView(R.id.profile_email_edit)
    EditText _email;
    @BindView(R.id.profile_lastname_edit)
    EditText _lastname;
    @BindView(R.id.profile_firstname_edit)
    EditText _firstname;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        biketrackService = ApiConnect.createService();
        _disposables = new CompositeDisposable();
        session = Session.getInstance();
        getUser();
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_edit_profile, container, false);
        unbinder = ButterKnife.bind(this, layout);
        toolbar.setNavigationIcon(R.drawable.ic_close_white_24px);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeFragment();
            }
        });
        return layout;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //Detects request codes
        if(requestCode==GET_FROM_GALLERY && resultCode == Activity.RESULT_OK) {
            Uri selectedImage = data.getData();
            photo = new File(selectedImage.getPath());
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), selectedImage);
                image_tmp.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void getUser() {
        _disposables.add(
                biketrackService.getUser(Statics.TOKEN_API, session.getToken(), session.getUserId())
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableObserver<ReceptUser>() {

                            @Override
                            public void onComplete() {
                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.e(TAG, "Error has occurred while getting user info", e);
//                                //check error type and raise toast
//                                if (e.getMessage().equals("HTTP 401 Unauthorized"))
//                                    Toast.makeText(getActivity(), "Wrong user ?", Toast.LENGTH_SHORT).show();
//                                else if (e.getMessage().equals("HTTP 404 Not Found"))
//                                    Toast.makeText(getActivity(), "You are not in our database, you should create an account", Toast.LENGTH_SHORT).show();
//                                else
//                                    Toast.makeText(getActivity(), "Maybe an error somewhere : " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onNext(ReceptUser receptUser) {
                                user = receptUser.getUser();
                                setDatas();
                            }
                        })
        );
    }

    private void setDatas(){
        if (_email != null)
            _email.setText(user.getMail());
        if (_lastname != null)
            _lastname.setText(user.getLastname());
        if (_firstname != null)
            _firstname.setText(user.getName());
    }

    @OnClick(R.id.delete_account_button)
    public void deleteAccount() {
        new AlertDialog.Builder(this.getContext())
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle(R.string.alert_confirmation_delete_account_title)
                .setMessage(R.string.alert_confirmation_delete_account_message)
                .setPositiveButton(R.string.alert_confirmation_delete_account_yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DeleteUser deleteUser = new DeleteUser(session.getUserId());
                        _disposables.add(
                                biketrackService.deleteUser(Statics.TOKEN_API, session.getToken(), deleteUser)
                                        .subscribeOn(Schedulers.newThread())
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .subscribeWith(new DisposableObserver<Response<ReceptDeleteUser>>() {

                                            @Override
                                            public void onComplete() {
                                                Log.d(TAG, "DeleteBike completed");
                                            }

                                            @Override
                                            public void onError(Throwable e) {
                                                Log.e(TAG, "Error has occurred while deleting bike", e);
                                            }

                                            @Override
                                            public void onNext(Response<ReceptDeleteUser> response) {
                                                Log.d(TAG, "ACCOUNT DELETED");
                                                session.clear();
                                                LoginManager loginManager = LoginManager.getInstance();
                                                loginManager.clear();
                                                Intent autologin_intent = new Intent(getActivity(), AutoLogin.class);
                                                startActivity(autologin_intent);
                                                getActivity().finish();
                                            }
                                        })
                        );
                    }
                })
                .setNegativeButton(R.string.alert_confirmation_delete_account_no, null)
                .show();
    }

    @OnClick(R.id.save_account_button)
    public void saveAccount() {
        SendUserUpdate sendUserUpdate = new SendUserUpdate(session.getUserId(), new UserUpdate(user));
        sendUserUpdate.getUser().setMail(_email.getText().toString());
        sendUserUpdate.getUser().setLastname(_lastname.getText().toString());
        sendUserUpdate.getUser().setName(_firstname.getText().toString());
        _disposables.add(
                biketrackService.updateUser(Statics.TOKEN_API, session.getToken(), sendUserUpdate)
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableObserver<Response<ReceptUserUpdate>>() {

                                           @Override
                                           public void onComplete() {
                                           }

                                           @Override
                                           public void onError(Throwable e) {
                                               Log.e(TAG, "Error has occurred while modifying your profile", e);
                                           }

                                           @Override
                                           public void onNext(Response<ReceptUserUpdate> response) {
                                               user = response.body().getUser();
                                               LoginManager loginManager = LoginManager.getInstance();
                                               loginManager.storeEmail(user.getMail());
                                               setDatas();
                                           }
                                       }
                        )
        );
    }

    @OnClick(R.id.upload_picture)
    public void openGallery(){
        startActivityForResult(new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI), GET_FROM_GALLERY);
    }


    public void closeFragment(){
        getActivity().getSupportFragmentManager().popBackStack();
    }
}
