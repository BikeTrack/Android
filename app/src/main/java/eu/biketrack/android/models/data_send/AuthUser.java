package eu.biketrack.android.models.data_send;

/**
 * Created by adrienschricke on 17/09/2016 for Android.
 */
public class AuthUser {
    private String mail;
    private String password;

    public AuthUser(String mail, String password) {
        this.mail = mail;
        this.password = password;
    }
}
