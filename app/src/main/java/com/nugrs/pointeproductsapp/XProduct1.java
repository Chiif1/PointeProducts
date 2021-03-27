package com.nugrs.pointeproductsapp;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;


public class XProduct1 extends AppCompatActivity {
    TextView product;
    CartHolder cartholder;
    String price1;
    String product1;
    String id1;
    String description1;
    TextView price;
    TextView description;
    ImageView image1;
    String image;
    String productName;
    String productPrice;
    String productDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_x_product1);

        cartholder = CartHolder.getInstance();

        product = findViewById(R.id.XproductName1Purchase);
        price = findViewById(R.id.XproductPricePurchase);
        description = findViewById(R.id.XdescriptionPurchase);


        //Parse JSON of products informations
        String jsonText = aws3Read();
        try {
            JSONArray jsonArray = new JSONArray(jsonText);
            JSONObject array1 = jsonArray.getJSONObject(0);
            JSONObject obect1 = array1.getJSONObject("product");
            price1 = obect1.get("productPrice").toString();
            product1 = obect1.get("productName").toString();
            id1 = obect1.get("id").toString();
            description1 = obect1.get("description").toString();

        } catch (JSONException e) {
            e.printStackTrace();
        }
        //Add product to cart button
        Button addToCart = (Button) findViewById(R.id.XpurchaseButton1);
        addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cartholder.addToArrayList(id1, productName, productPrice);
            }
        });
        addData();
    }

    public void addData() {
        MainActivity mainActivity = new MainActivity();
        //Product information transferred from Homepaage
        Intent intent = getIntent();
        image = intent.getStringExtra("IMAGE");
        productName = intent.getStringExtra("PRODUCT");
        productPrice = intent.getStringExtra("PRICE");
        productDescription = intent.getStringExtra("DESCRIPTION");
        image1 = (ImageView) findViewById(R.id.Xproduct1ImagePurchase);
        getProductImages(image);


        TextView productNamePurchase = findViewById(R.id.XproductName1Purchase);
        productNamePurchase.setText(productName);

        TextView productPricePurchase = findViewById(R.id.XproductPricePurchase);
        productPricePurchase.setText(mainActivity.formatPrice(productPrice));

        TextView productDescriptionPurchase = findViewById(R.id.XdescriptionPurchase);
        productDescriptionPurchase.setText(productDescription);

        //Checkout button
        Button checkOutbutton = findViewById(R.id.XcheckOutButton1);

        checkOutbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("checkout btn", "checklout button pressed");
                Intent intent = new Intent(XProduct1.this, XPurchaseCheck.class);
                startActivity(intent);
            }
        });
    }

    public void getProductImages(String image) {
        File imageFromInternal1 = new File(getFilesDir(), image);
        Bitmap myBitmap = BitmapFactory.decodeFile(imageFromInternal1.getAbsolutePath());
        Log.d("MyAmplifyApp", "Successfully downloaded: eere");
        image1.setImageDrawable(Drawable.createFromPath(imageFromInternal1.toString()));
    }

    //Load product text from internal memory
    public String aws3Read() {
        StringBuilder stringBuilder = new StringBuilder();
        String fileName = "download.txt";
        try {
            File file = new File(getFilesDir(), "download.txt");
            if (file.exists()) {
                Log.d("file exists", "file exists");
            } else {
                Log.d("file exists", " No file exists");
            }
            FileInputStream fileInputStream = new FileInputStream(file);

            if (fileInputStream != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String line = null;
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line);
                }
                fileInputStream.close();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }
}