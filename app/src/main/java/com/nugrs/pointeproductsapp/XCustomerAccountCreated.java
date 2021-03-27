package com.nugrs.pointeproductsapp;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class XCustomerAccountCreated extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_x_customer_account_created);

        Button backToLoginBt = findViewById(R.id.XbackToLoginButton);
        backToLoginBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(XCustomerAccountCreated.this, XSecureCheckout.class);
                startActivity(intent);

            }
        });
    }
}