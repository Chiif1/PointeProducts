package com.nugrs.pointeproductsapp;


import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.amplifyframework.AmplifyException;
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.storage.s3.AWSS3StoragePlugin;

import java.io.BufferedReader;
import java.io.File;

/*import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amplifyframework.AmplifyException;
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.AWSDataStorePlugin;
import com.amplifyframework.storage.options.StorageDownloadFileOptions;
import com.amplifyframework.storage.s3.AWSS3StoragePlugin;*/

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;

import static android.graphics.Bitmap.CompressFormat.JPEG;

public class MainActivity extends AppCompatActivity {
    String[] resourceProduct;
    String[] resourcePrice;
    String[] images;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


       try {
           Amplify.addPlugin(new AWSCognitoAuthPlugin());
           Amplify.addPlugin(new AWSS3StoragePlugin());
            Amplify.configure(getApplicationContext());

            Log.d("MyAmplifyApp", "Initialized Amplify");
        } catch (AmplifyException e) {
            Log.d("MyAmplifyApp", "Could not initialize Amplify", e);
        }
        uploadFile();
        //downLoadfile();
    }

    public String formatPrice(String price) {
        String priceIn = price;
        Double stringToDouble = Double.parseDouble(priceIn);
        String formatString = String.format("%s%.2f", "$", stringToDouble);
        return formatString;
    }

    private void uploadFile() {
        String cameraDescription ="ILCE6600/B Camera bodySEL18135 18-135mm LensNP-FZ100 Lithium BatteryAC-UUD12 AC ChargerUSB cableBody";
        String basketDescription ="Natural bamboo meets a classic chevron pattern. The result? Storage that lets you stow away the clutter";

        File exampleFile = new File(getApplicationContext().getFilesDir(), "ExampleKey");
        String addJSON = "[{\"product\":{\"id\":\"price_1HJQuYHPIbErS1OWgUZ5ed5Q\",\"productName\":\"Basket\",\"productPrice\":\"35\",\"description\":\"Natural bamboo meets a classic chevron pattern. The result? Storage that lets you stow away the clutter\"}},{\"product\":{\"id\":\"price_1HJQuYHPIbErS1OWXYc8E6pm\",\"productName\":\"Camera\",\"productPrice\":\"250\",\"description\":\"ILCE6600/B Camera bodySEL18135 18-135mm LensNP-FZ100 Lithium BatteryAC-UUD12 AC ChargerUSB cableBody\"}}]";
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(exampleFile));
            writer.append(addJSON);
            //writer.append("Example file contents");
            writer.close();
        } catch (Exception exception) {
            Log.e("MyAmplifyApp", "Upload failed", exception);
        }

        Amplify.Storage.uploadFile(
                "Products JSON.json",
                exampleFile,
                result -> Log.i("MyAmplifyApp", "Successfully uploaded: " + result.getKey()),
                storageFailure -> Log.e("MyAmplifyApp", "Upload failed", storageFailure)
        );

        Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.boot);

        File imageFile = new File(getApplicationContext().getFilesDir(), "imageFile.jpg");

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(imageFile);

            bm.compress(Bitmap.CompressFormat.JPEG, 100, fos);

            fos.flush();
            fos.close();

        } catch (IOException e) {
            Log.i("MyAmplifyApp", "File Created");
            Log.e("apperror", e.getMessage());
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
        Amplify.Storage.uploadFile(
                "imageFile2.jpg",
                imageFile,
                result -> Log.i("MyAmplifyApp", "Image Successfully uploaded: " + result.getKey()),
                storageFailure -> Log.e("MyAmplifyApp", "Image Upload failed", storageFailure)
        );

        File fileOfImage = new File(getApplicationContext().getFilesDir(), "image2" + "jpg");


        if (fileOfImage.exists()) {
            Log.d("Image file exists", "From drawable file exists");
        } else {
            Log.d("Image file exists", "From drawable No file exists");
        }
    }
}