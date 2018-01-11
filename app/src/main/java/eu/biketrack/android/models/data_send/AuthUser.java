package eu.biketrack.android.models.data_send;

/**
 * Created by adrienschricke on 17/09/2016 for Android.
 */
public class AuthUser {
    private String email;
    private String password;
    private String facebook;
    private String name;
    private String lastname;
    private String dob;

    public AuthUser(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public AuthUser(String email, String password, String facebook) {
        this.email = email;
        this.password = password;
        this.facebook = facebook;
    }

    public AuthUser(String email, String password, String name, String lastname, String dob) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.lastname = lastname;
        this.dob = dob;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFacebook() {
        return facebook;
    }

    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    @Override
    public String toString() {
        return "AuthUser{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", facebook='" + facebook + '\'' +
                ", name='" + name + '\'' +
                ", lastname='" + lastname + '\'' +
                ", dob='" + dob + '\'' +
                '}';
    }
}
