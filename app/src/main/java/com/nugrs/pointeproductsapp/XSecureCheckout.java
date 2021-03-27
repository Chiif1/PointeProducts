package com.nugrs.pointeproductsapp;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class XSecureCheckout extends AppCompatActivity {
    EditText password2;
    TextView emailMatch;
    JSONObject jsonObject;
    private OkHttpClient httpClient = new OkHttpClient();
    EditText userEmail;
    EditText userPassword;
    private static final String BACKEND_URL = "https://pointe-products-app.herokuapp.com/";
    JSONObject jsonObject1 = null;
    String resultLogin = null;
    String name = null;
    String line1 = null;
    String line2 = null;
    String city = null;
    String state = null;
    String postal_code = null;
    String phone = null;
    String nameShipping = null;
    String line1Shipping = null;
    String line2Shipping = null;
    String cityShipping = null;
    String stateShipping = null;
    String postal_codeShipping = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_x_secure_checkout);

        userEmail = findViewById(R.id.XemailUser);
        userPassword = findViewById(R.id.XpasswordUser);

        Button checkOutbutton = findViewById(R.id.Xguest_checkout);
        checkOutbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentGuest = new Intent(XSecureCheckout.this, XGuestUserPayment.class);
                startActivity(intentGuest);
            }
        });

        Button existingUserButton = findViewById(R.id.XexistingUser_checkoutBt);
        existingUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkPasswordMatch(view);
            }
        });

        TextView createCustomerLink = findViewById(R.id.XcreateCustomerLink);
        createCustomerLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //navControllerCreateCustomer.navigate(R.id.action_secureCheckout_to_createCustomer);
                Intent intent = new Intent(XSecureCheckout.this, XCreateCustomer.class);
                startActivity(intent);
            }
        });
    }

    public void checkPasswordMatch(View view) {

        //Stripe request body
        String loginstatus;
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("emailSide", userEmail.getText());
            jsonObject.put("passwordSide", userPassword.getText());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");

        RequestBody body = RequestBody.create(jsonObject.toString(), mediaType);
        Request request = new Request.Builder()
                .url(BACKEND_URL + "password_auth")
                .post(body)
                .build();

        httpClient.newCall(request)
                .enqueue(new Callback() {
                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e) {

                    }

                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {

                        if (response.isSuccessful()) {
                            String myresponse = response.body().string();

                            try {
                                jsonObject1 = new JSONObject(myresponse);
                                resultLogin = jsonObject1.getString("login_status");
                                System.out.println("response: " + resultLogin);

                                name = jsonObject1.getString("name");
                                line1 = jsonObject1.getString("line1");
                                line2 = jsonObject1.getString("line2");
                                city = jsonObject1.getString("city");
                                state = jsonObject1.getString("state");
                                postal_code = jsonObject1.getString("postal_code");
                                phone = jsonObject1.getString("phone");

                                nameShipping = jsonObject1.getString("nameShipping");
                                line1Shipping = jsonObject1.getString("line1Shipping");
                                line2Shipping = jsonObject1.getString("line2Shipping");
                                cityShipping = jsonObject1.getString("cityShipping");
                                stateShipping = jsonObject1.getString("stateShipping");
                                postal_codeShipping = jsonObject1.getString("postal_codeShipping");

                                System.out.println("TestBack: " + jsonObject1);
                                System.out.println("Loin Result: " + resultLogin);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    // if (response.isSuccessful()) {
                                    if (resultLogin.equals("true")) {
                                        Log.d("Before Nav>>>>>", "..........");
                                        //navController2.navigate(R.id.action_secureCheckout_to_existingCustomerPayment);
                                        Intent intent = new Intent(XSecureCheckout.this, XExistingCustomerPayment.class);
                                        intent.putExtra("NAME", name);
                                        intent.putExtra("LINE1", line1);
                                        intent.putExtra("LINE2", line2);
                                        intent.putExtra("CITY", city);
                                        intent.putExtra("STATE", state);
                                        intent.putExtra("POSTAL_CODE", postal_code);
                                        intent.putExtra("PHONE", phone);
                                        intent.putExtra("NAME_SHIPPING", nameShipping);
                                        intent.putExtra("LINE1_SHIPPING", line1Shipping);
                                        intent.putExtra("LINE2_SHIPPING", line2Shipping);
                                        intent.putExtra("CITY_SHIPPING", cityShipping);
                                        intent.putExtra("STATE_SHIPPING", stateShipping);
                                        intent.putExtra("POSTAL_CODE_SHIPPING", postal_codeShipping);
                                        startActivity(intent);
                                    }
                                }
                            });
                        }
                    }
                });
    }

}