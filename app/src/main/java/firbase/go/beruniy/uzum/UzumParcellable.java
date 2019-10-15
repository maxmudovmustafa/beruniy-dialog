package firbase.go.beruniy.uzum;// 17.06.2016

import android.os.Parcel;
import android.os.Parcelable;

public class UzumParcellable implements Parcelable {

    public final byte[] bytes;

    public UzumParcellable(byte[] bytes) {
        this.bytes = bytes;
    }

    protected UzumParcellable(Parcel in) {
        bytes = in.createByteArray();
    }

    public static final Creator<UzumParcellable> CREATOR = new Creator<UzumParcellable>() {
        @Override
        public UzumParcellable createFromParcel(Parcel in) {
            return new UzumParcellable(in);
        }

        @Override
        public UzumParcellable[] newArray(int size) {
            return new UzumParcellable[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeByteArray(bytes);
    }
}
