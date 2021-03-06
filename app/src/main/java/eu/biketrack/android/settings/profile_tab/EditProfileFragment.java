package eu.biketrack.android.settings.profile_tab;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.WindowManager;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.Unbinder;
import eu.biketrack.android.R;
import eu.biketrack.android.api_connection.BiketrackService;
import eu.biketrack.android.models.User;
import eu.biketrack.android.session.Session;


public class EditProfileFragment extends Fragment {
    public static final int GET_FROM_GALLERY = 3;
    private static String TAG = "BIKETRACK - EditProfile";
    private Unbinder unbinder;
    private Session session;
    private BiketrackService biketrackService;
//    private CompositeDisposable _disposables;
//    private Disposable _disposable_email;
    private User user;
    private Boolean error_password_email = true;
//    Bitmap bitmap = null;
//    File photo;

//    @BindView(R.id.toolbar)
//    Toolbar toolbar;
//    @BindView(R.id.tmp_img)
//    ImageView image_tmp;
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
//        biketrackService = ApiConnectModule.createService();
//        _disposables = new CompositeDisposable();
//        session = Session.getInstance();
//        getUser();
    }

//    @Override
//    public View onCreateView(LayoutInflater inflater,
//                             @Nullable ViewGroup container,
//                             @Nullable Bundle savedInstanceState) {
//        View layout = inflater.inflate(R.layout.fragment_edit_profile, container, false);
//        unbinder = ButterKnife.bind(this, layout);
//        toolbar.setNavigationIcon(R.drawable.ic_close_white_24px);
//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                closeFragment();
//            }
//        });
//        return layout;
//    }
//
//    @Override
//    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//        _disposable_email = RxJavaInterop.toV2Observable(RxTextView.textChangeEvents(_email))
//                .debounce(400, TimeUnit.MILLISECONDS)
//                //.filter(changes -> !TextUtils.isEmpty(changes.text().toString()))
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribeWith(_getCheckEmailObserver());
//    }
//
//    private DisposableObserver<TextViewTextChangeEvent> _getCheckEmailObserver() {
//        return new DisposableObserver<TextViewTextChangeEvent>() {
//            @Override
//            public void onComplete() {
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                Log.e(TAG, "Error while checking email", e);
//            }
//
//            @Override
//            public void onNext(TextViewTextChangeEvent onTextChangeEvent) {
//                if (!onTextChangeEvent.text().toString().matches(Statics.REGEXP_EMAIL)) {
//                    if (!StringUtils.isEmpty(onTextChangeEvent.text().toString()))
//                        _email.setError(getActivity().getString(R.string.error_check_email));
//                    error_password_email = true;
//                } else
//                    error_password_email = false;
//
//            }
//        };
//    }
//
//
////    @Override
////    public void onActivityResult(int requestCode, int resultCode, Intent data) {
////        super.onActivityResult(requestCode, resultCode, data);
////
////        //Detects request codes
////        if(requestCode == GET_FROM_GALLERY && resultCode == Activity.RESULT_OK) {
////            Uri selectedImage = data.getData();
////            photo = new File(selectedImage.getPath());
////            Log.d(TAG," ---------> " + String.valueOf(selectedImage) + " + " + photo.getAbsolutePath() );
////            photo.
////
////            try {
////                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), selectedImage);
////                image_tmp.setImageBitmap(bitmap);
////
////
////                RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), photo);
////                MultipartBody.Part multipartBody = MultipartBody.Part.createFormData("buffer", photo.getName(), requestFile);
////
////
////                _disposables.add(
////                        biketrackService.uploadProfilePhoto(Statics.TOKEN_API, session.getToken(), multipartBody, "image/jpeg")
////                                .subscribeOn(Schedulers.newThread())
////                                .observeOn(AndroidSchedulers.mainThread())
////                                .subscribeWith(new DisposableObserver<ReceptUserUpdate>() {
////
////                                    @Override
////                                    public void onComplete() {
////                                    }
////
////                                    @Override
////                                    public void onError(Throwable e) {
////                                        Log.e(TAG, "Error has occurred while uploading image", e);
////                                        Toast.makeText(getActivity(), R.string.network_error, Toast.LENGTH_SHORT).show();
////                                    }
////
////                                    @Override
////                                    public void onNext(ReceptUserUpdate receptUser) {
////
////                                    }
////                                })
////                );
////
////            } catch (FileNotFoundException e) {
////                // TODO Auto-generated catch block
////                e.printStackTrace();
////            } catch (IOException e) {
////                // TODO Auto-generated catch block
////                e.printStackTrace();
////            }
////        }
////    }
//
//    @Override
//    public void onDestroyView() {
//        super.onDestroyView();
//        unbinder.unbind();
//    }
//
//    private void getUser() {
//        _disposables.add(
//                biketrackService.getUser(Statics.TOKEN_API, session.getToken(), session.getUserId())
//                        .subscribeOn(Schedulers.newThread())
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .subscribeWith(new DisposableObserver<ReceptUser>() {
//
//                            @Override
//                            public void onComplete() {
//                            }
//
//                            @Override
//                            public void onError(Throwable e) {
//                                Log.e(TAG, "Error has occurred while getting user info", e);
//                                Toast.makeText(getActivity(), R.string.network_error, Toast.LENGTH_SHORT).show();
//                            }
//
//                            @Override
//                            public void onNext(ReceptUser receptUser) {
//                                user = receptUser.getUser();
//                                setDatas();
//                            }
//                        })
//        );
//    }
//
//    private void setDatas(){
//        if (_email != null)
//            _email.setText(user.getMail());
//        if (_lastname != null)
//            _lastname.setText(user.getLastname());
//        if (_firstname != null)
//            _firstname.setText(user.getName());
//    }
//
//    @OnClick(R.id.delete_account_button)
//    public void deleteAccount() {
//        new AlertDialog.Builder(this.getContext())
//                .setIcon(android.R.drawable.ic_dialog_alert)
//                .setTitle(R.string.alert_confirmation_delete_account_title)
//                .setMessage(R.string.alert_confirmation_delete_account_message)
//                .setPositiveButton(R.string.alert_confirmation_delete_account_yes, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        DeleteUser deleteUser = new DeleteUser(session.getUserId());
//                        _disposables.add(
//                                biketrackService.deleteUser(Statics.TOKEN_API, session.getToken(), deleteUser)
//                                        .subscribeOn(Schedulers.newThread())
//                                        .observeOn(AndroidSchedulers.mainThread())
//                                        .subscribeWith(new DisposableObserver<Response<ReceptDeleteUser>>() {
//
//                                            @Override
//                                            public void onComplete() {
//                                                Log.d(TAG, "DeleteBike completed");
//                                            }
//
//                                            @Override
//                                            public void onError(Throwable e) {
//                                                Log.e(TAG, "Error has occurred while deleting bike", e);
//                                            }
//
//                                            @Override
//                                            public void onNext(Response<ReceptDeleteUser> response) {
//                                                Log.d(TAG, "ACCOUNT DELETED");
//                                                session.clear();
//                                                LoginManagerModule loginManager = LoginManagerModule.getInstance();
//                                                loginManager.clear();
//                                                Intent autologin_intent = new Intent(getActivity(), Initializer.class);
//                                                startActivity(autologin_intent);
//                                                getActivity().finish();
//                                            }
//                                        })
//                        );
//                    }
//                })
//                .setNegativeButton(R.string.alert_confirmation_delete_account_no, null)
//                .show();
//    }
//
//    @OnClick(R.id.save_account_button)
//    public void saveAccount() {
//
//        if (error_password_email){
//            Toast.makeText(getContext(), R.string.error_check_email, Toast.LENGTH_SHORT).show();
//            return;
//        } else if (_lastname.getText().length() == 0 && !StringUtils.isEmpty(user.getLastname())){
//            Toast.makeText(getContext(), R.string.user_error_lastname_empty, Toast.LENGTH_SHORT).show();
//            return;
//        } else if (_firstname.getText().length() == 0 && !StringUtils.isEmpty(user.getName())){
//            Toast.makeText(getContext(), R.string.user_error_firstname_empty, Toast.LENGTH_SHORT).show();
//            return;
//        }
//
//        SendUserUpdate sendUserUpdate = new SendUserUpdate(session.getUserId(), new UserUpdate(user));
//        sendUserUpdate.getUser().setMail(_email.getText().toString());
//        sendUserUpdate.getUser().setLastname(_lastname.getText().toString());
//        sendUserUpdate.getUser().setName(_firstname.getText().toString());
//
//        _disposables.add(
//                biketrackService.updateUser(Statics.TOKEN_API, session.getToken(), sendUserUpdate)
//                        .subscribeOn(Schedulers.newThread())
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .subscribeWith(new DisposableObserver<Response<ReceptUserUpdate>>() {
//
//                                           @Override
//                                           public void onComplete() {
//                                           }
//
//                                           @Override
//                                           public void onError(Throwable e) {
//                                               Log.e(TAG, "Error has occurred while modifying your profile", e);
//                                               Toast.makeText(getActivity(), R.string.network_error, Toast.LENGTH_SHORT).show();
//                                           }
//
//                                           @Override
//                                           public void onNext(Response<ReceptUserUpdate> response) {
//                                               if (response.code() == 200) {
//                                                   user = response.body().getUser();
//                                                   LoginManagerModule loginManager = LoginManagerModule.getInstance();
//                                                   loginManager.storeEmail(user.getMail());
//                                                   setDatas();
//                                                   Toast.makeText(getContext(), R.string.user_saved, Toast.LENGTH_SHORT).show();
//                                                   closeFragment();
//                                               } else {
//                                                   Toast.makeText(getActivity(), R.string.network_error, Toast.LENGTH_SHORT).show();
//                                               }
//                                           }
//                                       }
//                        )
//        );
//    }
//
////    @OnClick(R.id.upload_picture)
////    public void openGallery(){
////        startActivityForResult(new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI), GET_FROM_GALLERY);
////    }
//
//
//    public void closeFragment(){
//        getActivity().getSupportFragmentManager().popBackStack();
//    }
}
