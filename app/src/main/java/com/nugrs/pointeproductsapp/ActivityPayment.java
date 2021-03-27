package com.nugrs.pointeproductsapp;

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

public class ActivityPayment extends AppCompatActivity {
    /**
     * This example collects card payments, implementing the guide here: https://stripe.com/docs/payments/accept-a-payment#android
     * <p>
     * To run this app, follow the steps here: https://github.com/stripe-samples/accept-a-card-payment#how-to-run-locally
     */
    // 10.0.2.2 is the Android emulator's alias to localhost
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
    MediaType mediaType = MediaType.get("application/json; charset=utf-8");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        cartHolder = CartHolder.getInstance();

        startCheckout();

    }

    private void startCheckout() {

        // Create a PaymentIntent by calling the sample server's /create-payment-intent endpoint.

       /* String json = "{"
                + "\"currency\":\"usd\","
                + "\"items\":["
               + "{\"id\":\"shirt-small-woman\"," + "\"quantity\":1}"
                 + "{\"id\":\"boot\"," + "\"quantity\":1}"
                + "]"
                + "}";*/

        Button payButton = findViewById(R.id.payButtonGuest);
            payButton.setOnClickListener((View view) -> {

            //JSON setup for Stripe
            try {
                EditText receipt_email  = findViewById(R.id.emailTxBoxGuest);
                CheckBox checkboxCreateCust = (CheckBox) findViewById(R.id.checkBoxGuest);
                EditText customer  = findViewById(R.id.nameTxBoxExistingCustomer);

                json1 = new JSONObject();
                json1.put("name", "Guest");
                json1.put("checkBoxStatus", "checking");
                json1.put("receipt_email", receipt_email.getText());
                json1.put("reviewsd", true);
                json1.put("currency", "usd");

                EditText name = findViewById(R.id.nameRecipientGuesShippingTxBox);
                EditText shippingAddress = findViewById(R.id.addressTxGuestShipping1);
                EditText shippingAddress2 = findViewById(R.id.addressTxBoxGuestShipping2);
                EditText shippingCity = findViewById(R.id.cityTxBoxGuestShipping);
                EditText shippingState = findViewById(R.id.stateTxBoxGuestShipping);
                EditText shippingzipcode = findViewById(R.id.zipcodeTxBoxGuestShipping);


                JSONObject shipping = new JSONObject();
                shipping.put("name", name.getText());

                JSONObject address = new JSONObject();
                address.put("line1", shippingAddress.getText());
                address.put("line2", shippingAddress2.getText());
                address.put("city", shippingCity.getText());
                address.put("state", shippingState.getText());
                address.put("postal_code", shippingzipcode.getText());

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


                json1.put("items", list);
                json = json1.toString();
                Log.d("JSON: ", json1.toString());

            } catch (JSONException e) {
                e.printStackTrace();
            }

            //Stripe request body
            RequestBody body = RequestBody.create(json, mediaType);
            Request request = new Request.Builder()
                    .url(BACKEND_URL + "payment_intents")
                    .post(body)
                    .build();

            httpClient.newCall(request)
                    .enqueue(new PayCallback(this));

            Log.d("HTTP send", "HTTp send");



        });

    }


    /*public void http(String json2) {
        Log.d("JSon received", "drerejrejejl");
        RequestBody body = RequestBody.create(json2, mediaType);
        Request request = new Request.Builder()
                .url(BACKEND_URL + "payment_intents")
                .post(body)
                .build();
        Log.d("HTTP send", "HTTp send");

        httpClient.newCall(request)
                .enqueue(new PayCallback(this));



    }*/

    public void cardSend(){

        CardInputWidget cardInputWidget = findViewById(R.id.cardInputWidgetGuest);

        PaymentMethodCreateParams params = cardInputWidget.getPaymentMethodCreateParams();
        if (params != null) {
            ConfirmPaymentIntentParams confirmParams = ConfirmPaymentIntentParams
                    .createWithPaymentMethodCreateParams(params, paymentIntentClientSecret);
            stripe.confirmPayment(this, confirmParams);
        }

    }

    void displayAlert(@NonNull String title,
                      @Nullable String message,
                      boolean restartDemo) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message);
        if (restartDemo) {
            builder.setPositiveButton("Restart demo",
                    (DialogInterface dialog, int index) -> {
                        CardInputWidget cardInputWidget = findViewById(R.id.cardInputWidget);
                        cardInputWidget.clear();
                        startCheckout();
                    });
        } else {
            builder.setPositiveButton("Ok", null);
        }
        builder.create().show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Handle the result of stripe.confirmPayment
        stripe.onPaymentResult(requestCode, data, new PaymentResultCallback(this));
    }

    void onPaymentSuccess(@NonNull final Response response) throws IOException {

        Gson gson = new Gson();
        Type type = new TypeToken<Map<String, Object>>() {
        }.getType();
        Map<String, Object> responseMap = gson.fromJson(
                Objects.requireNonNull(response.body()).string(),
                type
        );

        String intent = responseMap.get("paymentIntent").toString();

        try {
            JSONObject jsonclientSecret = new JSONObject(responseMap);
            Log.d("Json Client parse", jsonclientSecret.toString());
            JSONObject paymentIntent = jsonclientSecret.getJSONObject("paymentIntent");
            String client_secret = paymentIntent.getString("client_secret").toString();
            Log.d("Client Final: ", client_secret.toString());

           // Log.d("publishable key res", responseMap.get("publishable_Key").toString());
            // For added security, our sample app gets the publishable key from the server
            String stripePublishableKey = responseMap.get("publishable_Key").toString();
            paymentIntentClientSecret = client_secret.toString();
            stripe = new Stripe(
                    getApplicationContext(),
                    Objects.requireNonNull(stripePublishableKey)
            );
        } catch (JSONException e) {
            e.printStackTrace();
        }
        // The response from the server includes the Stripe publishable key and
        // PaymentIntent details.
        // Configure the SDK with your Stripe publishable key so that it can make requests to the Stripe API
    }


    private  final class PayCallback implements Callback { //was static
        @NonNull
        private final WeakReference<ActivityPayment> activityRef;

        PayCallback(@NonNull ActivityPayment activity) {
            activityRef = new WeakReference<>(activity);
        }

        @Override
        public void onFailure(@NonNull Call call, @NonNull IOException e) {
            final ActivityPayment activity = activityRef.get();
            if (activity == null) {
                return;
            }

            activity.runOnUiThread(() ->
                    Toast.makeText(
                            activity, "Error:  " + "on failure" + e.toString(), Toast.LENGTH_LONG
                    ).show()

            );

        }

        @Override
        public void onResponse(@NonNull Call call, @NonNull final Response response)
                throws IOException {
            final ActivityPayment activity = activityRef.get();
            if (activity == null) {
                return;
            }

            if (!response.isSuccessful()) {
                Log.d("responseError", response.toString());

                activity.runOnUiThread(() ->
                        Toast.makeText(
                                activity, "Error: " + "Not successful" + response.toString(), Toast.LENGTH_LONG
                        ).show()
                );
            } else {
                activity.onPaymentSuccess(response);
                Log.d("onRespnse Response", response.body().toString());
                cardSend();
            }
        }
    }

    private static final class PaymentResultCallback
            implements ApiResultCallback<PaymentIntentResult> {
        @NonNull
        private final WeakReference<ActivityPayment> activityRef;

        PaymentResultCallback(@NonNull ActivityPayment activity) {
            activityRef = new WeakReference<>(activity);
        }

        @Override
        public void onSuccess(@NonNull PaymentIntentResult result) {
            final ActivityPayment activity = activityRef.get();
            if (activity == null) {
                return;
            }

            PaymentIntent paymentIntent = result.getIntent();
            PaymentIntent.Status status = paymentIntent.getStatus();
            if (status == PaymentIntent.Status.Succeeded) {
                // Payment completed successfully
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                activity.displayAlert(
                        "Payment Completed",
                        "",
                        true
                );
                // gson.toJson(paymentIntent)
            } else if (status == PaymentIntent.Status.RequiresPaymentMethod) {
                // Payment failed – allow retrying using a different payment method
                activity.displayAlert(
                        "Payment failed",
                        Objects.requireNonNull(paymentIntent.getLastPaymentError()).getMessage(),
                        false
                );
            }
        }

        @Override
        public void onError(@NonNull Exception e) {
            final ActivityPayment activity = activityRef.get();
            if (activity == null) {
                return;
            }
            // Payment request failed – allow retrying using the same payment method
            activity.displayAlert("Error", "On Error" + e.toString(), false);
        }
    }
}