package eu.biketrack.android.models.data_send;

/**
 * Created by adrienschricke on 17/09/2016 for Android.
 */
public class AuthUser {
    private String mail;
    private String password;
    private String facebook;

    public AuthUser(String mail, String password) {
        this.mail = mail;
        this.password = password;
    }

    public AuthUser(String mail, String password, String facebook) {
        this.mail = mail;
        this.password = password;
        this.facebook = facebook;
    }

    public String getMail() {
        return mail;
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
                "mail='" + mail + '\'' +
                ", password='" + password + '\'' +
                ", facebook='" + facebook + '\'' +
                '}';
    }
}
