package com.nugrs.pointeproductsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.mysql.cj.jdbc.MysqlDataSource;
import com.stripe.android.ApiResultCallback;
import com.stripe.android.PaymentIntentResult;
import com.stripe.android.Stripe;
import com.stripe.android.model.Address;
import com.stripe.android.model.ConfirmPaymentIntentParams;
import com.stripe.android.model.Customer;
import com.stripe.android.model.PaymentIntent;
import com.stripe.android.model.PaymentMethodCreateParams;
import com.stripe.android.view.CardInputWidget;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.ref.WeakReference;
import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.postgresql.ds.PGSimpleDataSource;
import org.postgresql.jdbc.*;

public class GuestUserPayment extends AppCompatActivity {
    private static final String BACKEND_URL = "https://pointe-products-app.herokuapp.com/";

    private OkHttpClient httpClient = new OkHttpClient();
    private String paymentIntentClientSecret;
    private Stripe stripe;
    private String id;
    private String quantity;
    String json;
    JSONObject json1;
    CartHolder cartHolder;
    JSONObject json2;
    JSONArray list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest_user_payment);

        cartHolder = CartHolder.getInstance();
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");

        startCheckout2();
    }
        public void startCheckout2() {
        //JSON setup for Stripe
        try {
            EditText receipt_email = findViewById(R.id.emailTxBoxGuest);
            json1 = new JSONObject();
            json1.put("checkBoxStatus", "checking");
            json1.put("receipt_email", receipt_email.getText());
            CheckBox checkboxCreateCust = (CheckBox) findViewById(R.id.checkBox);
            json1.put("reviewsde", false);
            json1.put("currency", "usd");
               /* boolean status;
                String statusNum;
                status = checkboxCreateCust.isChecked();
                if(status == true) {
                    statusNum = "Checked";
                }
                else {
                    statusNum = "Not Checked";
                }*/


            //json1.put("customer", "cus_H4dKBmAzeIKtDe");

            EditText shippingAddress = findViewById(R.id.addressTxBox);
            EditText firstName = findViewById(R.id.nameTxBox);
            JSONObject shipping = new JSONObject();
            shipping.put("name", firstName.getText());
            JSONObject address = new JSONObject();
            address.put("line1", shippingAddress.getText());
            shipping.put("address", address);
            json1.put("shipping", shipping);


            Log.d("JSON: ", json1.toString());


            // send check box status to server

            ArrayList<PurchaseClass> purchase = cartHolder.getListArray();
            list = new JSONArray();

            for (PurchaseClass product : purchase) {
                json2 = new JSONObject();
                json2.put("id", product.id);
                json2.put("quantity", 1);

                list.put(json2);
                json = list.toString();
                Log.d("JSON: ", list.toString());
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        Button payButton = findViewById(R.id.payButtonGuest);

       payButton.setOnClickListener(view -> {
           ActivityPayment activityPayment = new ActivityPayment();
           // activityPayment.http(json);
            Log.d("NewJson", json);
       });


    }


}