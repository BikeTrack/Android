package eu.biketrack.android.models.data_reception;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Bike implements Parcelable {

    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("brand")
    @Expose
    private String brand;
    @SerializedName("color")
    @Expose
    private String color;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("tracker")
    @Expose
    private String tracker;
    @SerializedName("__v")
    @Expose
    private Integer v;
    @SerializedName("updated")
    @Expose
    private String updated;
    @SerializedName("created")
    @Expose
    private String created;


    public static final Parcelable.Creator<Bike> CREATOR = new Parcelable.Creator<Bike>() {
        public Bike createFromParcel(Parcel in) {
            return new Bike(in);
        }

        public Bike[] newArray(int size) {
            return new Bike[size];
        }
    };

    private Bike(Parcel in){
        id = in.readString();
        brand = in.readString();
        color = in.readString();
        name = in.readString();
        color = in.readString();
        tracker = in.readString();
        v = in.readInt();
        updated = in.readString();
        created = in.readString();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTracker() {
        return tracker;
    }

    public void setTracker(String tracker) {
        this.tracker = tracker;
    }

    public Integer getV() {
        return v;
    }

    public void setV(Integer v) {
        this.v = v;
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    @Override
    public String toString() {
        return "Bike{" +
                "id='" + id + '\'' +
                ", brand='" + brand + '\'' +
                ", color='" + color + '\'' +
                ", name='" + name + '\'' +
                ", tracker='" + tracker + '\'' +
                ", v=" + v +
                ", updated='" + updated + '\'' +
                ", created='" + created + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(brand);
        dest.writeString(color);
        dest.writeString(name);
        dest.writeString(tracker);
        dest.writeInt(v);
        dest.writeString(updated);
        dest.writeString(created);

    }
}