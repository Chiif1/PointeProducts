package com.nugrs.pointeproductsapp;

import android.os.Parcel;
import android.os.Parcelable;


public class SingleRow implements Parcelable {
    String product;
    String price;

    SingleRow(String product, String price) {
        this.product = product;
        this.price = price;
    }

    protected SingleRow(Parcel in) {
        product = in.readString();
        price = in.readString();
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public static final Creator<SingleRow> CREATOR = new Creator<SingleRow>() {
        @Override
        public SingleRow createFromParcel(Parcel in) {
            return new SingleRow(in);
        }

        @Override
        public SingleRow[] newArray(int size) {
            return new SingleRow[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(product);
        dest.writeString(price);
    }
}
