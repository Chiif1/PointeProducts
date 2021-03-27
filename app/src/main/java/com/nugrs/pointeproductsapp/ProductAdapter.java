package com.nugrs.pointeproductsapp;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ProductAdapter extends ArrayAdapter<String> {
    private Context mContext;
    private String[] priceArray;
    private String[] productArray;
    private ArrayList<PurchaseClass> mlistArray;
    private  int mResource;
    int total = 0;

    //Product Adapter for list view on Checkout
    public ProductAdapter(Context context, int resource, ArrayList<PurchaseClass> purchases) {
        super(context, R.layout.activity_x_single_row, R.id.Xprice_product_listView);
        mContext = context;
        mResource = resource;
        mlistArray = purchases;


    }

    @Override
    public int getCount() {
        return mlistArray.size();
    }

        /*@Override
        public Object getItem(int i) {
            return mlistArray;
        }*/

    @Override
    public long getItemId(int i) {

        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Log.d("myapp", "adapter run");
        String mProduct = mlistArray.get(i).getProductName();
        String mPrice = mlistArray.get(i).getProductPrice();
        LayoutInflater inflater = LayoutInflater.from(mContext);
        view = inflater.inflate(mResource, viewGroup, false);

        TextView productNameText = (TextView) view.findViewById(R.id.productColumn);
        TextView priceNameText = (TextView) view.findViewById(R.id.priceColumn);

        MainActivity formatPrice = new MainActivity();
        productNameText.setText(mProduct);
        priceNameText.setText(formatPrice.formatPrice(mPrice));

        ListView listview = viewGroup.findViewById(R.id.Xprice_product_listView);
        return view;
    }
}
