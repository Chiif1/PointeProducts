package com.nugrs.pointeproductsapp;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;

public class XPurchaseCheck extends AppCompatActivity {
    ListView listView;
    String[] products;
    String[] prices;
    ArrayList<PurchaseClass> purchases;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_x_purchase_check);

        listView = (ListView) findViewById(R.id.Xprice_product_listView);

        //Products added to cart using a Singleton
        purchases = CartHolder.getInstance().getListArray();

        int added = 0;

        //Get prices from array in chart holder for total sale cost
        for(int i = 0; i < purchases.size(); i++) {
            String num = purchases.get(i).getProductPrice().toString();
            double numNumber =  Double.parseDouble(num);
            added += numNumber;

            Log.d("MyApp Listview",  "received Text");

        }

        String numToView = Integer.toString(added);
        MainActivity mainActivity = new MainActivity();
        TextView showTotal = findViewById(R.id.XtotalSaleText);
       showTotal.setText(mainActivity.formatPrice(numToView));
        setProductAdpater();
    }
    public void setProductAdpater() {

        // checkout list View
        ProductAdapter productAdapter = new ProductAdapter(this, R.layout.activity_x_single_row, purchases);
        listView.setAdapter(productAdapter);

        Button button = findViewById(R.id.XbuyMoreBtn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(XPurchaseCheck.this, XHomePage.class);
                startActivity(intent);
            }
        });

        Button checkOutPayButton = findViewById(R.id.XcheckOutPayButton);
        checkOutPayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(XPurchaseCheck.this, XSecureCheckout.class);
                startActivity(intent);
            }
        });
    }
}