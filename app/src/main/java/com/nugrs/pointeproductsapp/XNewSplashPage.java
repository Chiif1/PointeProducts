package com.nugrs.pointeproductsapp;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import com.amplifyframework.AmplifyException;
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.rx.RxAmplify;
import com.amplifyframework.rx.RxStorageCategoryBehavior;
import com.amplifyframework.storage.s3.AWSS3StoragePlugin;
import java.io.File;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

//Splash page
public class XNewSplashPage extends AppCompatActivity {
    int i = 0;
    int b = 0;
    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_x_new_splash_page);
        // Initialized Amplify
        try {
            Amplify.addPlugin(new AWSCognitoAuthPlugin());
            Amplify.addPlugin(new AWSS3StoragePlugin());
            Amplify.configure(this);

            Log.d("MyAmplifyApp", "Initialized Amplify");
        } catch (AmplifyException e) {
            Log.d("MyAmplifyApp", "Could not initialize Amplify", e);
        }

        //Start Product Images downloads
        DownloadImages downloadImages = new DownloadImages();
        downloadImages.run();

        //Start product name, product price,  product id
        DownloadJSONText testingDownolad = new DownloadJSONText();
        testingDownolad.run();
    }

    //Image down load completed
    public void downloadsCompleted() {
        Log.d("Myamplify", "Number is: " + b);
        if (b == 1) { //Change to 1 after adding amplify
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Intent intent = new Intent(this, XHomePage.class);
            startActivity(intent);
        }
        b++;
    }
    //Down load Images
    public class DownloadImages implements Runnable {

        DownloadImages() {
        }

        @Override
        public void run() {
            String one = "imageFile1";
            String two = "imageFile2";

            RxStorageCategoryBehavior storage = RxAmplify.Storage;
            // Multipe procuct image file down load into internal memory
            Single
                    .mergeArray(
                            storage.downloadFile(one + ".jpg", new File(getFilesDir(), one + ".jpg"))
                                    .observeResult(),
                            storage.downloadFile(two + ".jpg", new File(getFilesDir(), two + ".jpg"))
                                    .observeResult()
                    )
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(result -> {
                        Log.i("MyAmplifyApp", "Download OK." + result.getFile().getName().toString());

                        if (i == 1) {
                            downloadsCompleted();
                        }
                        i++;
                    }, failure -> {
                        Log.e("MyAmplifyApp", "Failed.", failure);
                    });

        }
    }
    //Download product details JSON into internal memory
    public class DownloadJSONText implements Runnable {

        DownloadJSONText() {

        }

        @Override
        public void run() {
            int i;
            Amplify.Storage.downloadFile(
                    "Products JSON.json",
                    new File(getFilesDir(), "download.txt"),
                    result -> handler.post(() -> {
                        downloadsCompleted();
                        Log.d("MyAmplifyApp", "Testing download completed");
                    }),
                    error -> Log.e("MyAmplifyApp", "Download Failure", error)
            );
        }
    }
}