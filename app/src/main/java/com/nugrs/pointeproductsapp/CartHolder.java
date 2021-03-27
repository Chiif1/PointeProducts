package com.nugrs.pointeproductsapp;

import android.util.Log;
import java.util.ArrayList;

//Products added to cart using Singleton
public class CartHolder {
    private static CartHolder firstInstance;
    private ArrayList<PurchaseClass> listArray;

    private CartHolder() {
        listArray = new ArrayList<>();
    }

    public static CartHolder getInstance() {

        if (firstInstance == null) {
            firstInstance = new CartHolder();
            Log.d("MyAmplify App", "New Cart");
        }
        return firstInstance;
    }

    public ArrayList<PurchaseClass> getListArray() {
        return listArray;
    }

    public void addToArrayList(String id, String product, String price) {
        listArray.add(new PurchaseClass(id, product, price));
    }
    public void clearArrayList() {
        listArray.clear();
    }
}
