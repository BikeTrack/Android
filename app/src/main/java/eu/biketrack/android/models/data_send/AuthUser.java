package eu.biketrack.android.models.data_send;

/**
 * Created by adrienschricke on 17/09/2016 for Android.
 */
public class AuthUser {
    private String email;
    private String password;
    private String facebook;

    public AuthUser(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public AuthUser(String email, String password, String facebook) {
        this.email = email;
        this.password = password;
        this.facebook = facebook;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getFacebook() {
        return facebook;
    }

    @Override
    public String toString() {
        return "AuthUser{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", facebook='" + facebook + '\'' +
                '}';
    }
}
