package com.nugrs.pointeproductsapp;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

// Procuct image and detials addded to Recycler View
public class XHomePage extends AppCompatActivity {
    String jsonText;
    ArrayList<ListObjects> mlistToHome;
    private RecyclerView mRecyclerView;
    private ListAdapter mlistAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_x_list);

        mRecyclerView = (RecyclerView) findViewById(R.id.XListRecycleView);
        mRecyclerView.setHasFixedSize(true);

        //ArrayList of product information
        ArrayList<ListObjects> tList = startJSONSetUP();
        mlistToHome = new ArrayList<>();

        for (int i = 0; i < tList.size(); i++) {
            int num = i + 1;
            String image = "imageFile" + num + ".jpg";
            mlistToHome.add(new ListObjects(image, tList.get(i).productName, tList.get(i).productPrice, tList.get(i).productDescription));
        }
        mlistAdapter = new ListAdapter(this, mlistToHome);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mlistAdapter);

    }

    //Load product names and prices from internal memory
    public String aws3Read() {

        StringBuilder stringBuilder = new StringBuilder();
        String fileName = "download.txt";

        try {
            File file = new File(getFilesDir(), "download.txt");
            if (file.exists()) {
                Log.d("file exists", "Home file exists");
            } else {
                Log.d("file exists", " Home No file exists");
            }
            FileInputStream fileInputStream = new FileInputStream(file);
            Log.d("LogLocation", getFilesDir().toString());

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

    //Parse procucts JSON text
    public ArrayList<ListObjects> startJSONSetUP() {
        jsonText = aws3Read();
        ArrayList<ListObjects> textList = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(jsonText);
            for (int i = 0; i < jsonText.length(); i++) {
                JSONObject array1 = jsonArray.getJSONObject(i);
                JSONObject obect1 = array1.getJSONObject("product");
                String price1 = obect1.get("productPrice").toString();
                String product1 = obect1.get("productName").toString();
                String description1 = obect1.get("description").toString();

                textList.add(new ListObjects("imageFile1.jpg", product1, price1, description1));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return textList;
    }

    public void homepageToProduct1(String image, String product, String price, String description) {

        Intent intent = new Intent(this, XProduct1.class);
        intent.putExtra("IMAGE", image);
        intent.putExtra("PRODUCT", product);
        intent.putExtra("PRICE", price);
        intent.putExtra("DESCRIPTION", description);
        startActivity(intent);

    }
}