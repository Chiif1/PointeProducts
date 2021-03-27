package com.nugrs.pointeproductsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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

public class CreateCustomer extends AppCompatActivity {
    private static final String BACKEND_URL = "https://pointe-products-app.herokuapp.com/";
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
    JSONObject json1 = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_customer);

        OkHttpClient httpClient = new OkHttpClient();

        MediaType mediaType = MediaType.get("application/json; charset=utf-8");

        Button createCustmerButton = findViewById(R.id.payNowCreateCustomer);

        createCustmerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("create cust btn", "create cust button pressed");

                EditText customer  = findViewById(R.id.nameTxBoxCreateCustomer);
                EditText address1  = findViewById(R.id.addressTxBoxCreateCustomer1);
                EditText address2  = findViewById(R.id.addressTxBoxCreateCustomer2);
                EditText city  = findViewById(R.id.cityTxBoxCreateCustomer);
                EditText state  = findViewById(R.id.stateTxBoxCraeteCustomer);
                EditText postal_code = findViewById(R.id.zipcodeTxBoxCreateCustomer);
                EditText email  = findViewById(R.id.emailTxBoxCreateCustomer);
                EditText phone  = findViewById(R.id.phoneTxBoxCreateCustomer);


                json1 = new JSONObject();
                try {
                    //json1.put("checkBoxStatus", "checking");
                    json1.put("name", customer.getText());
                    json1.put("email", email.getText());
                    json1.put("phone", phone.getText());

                    JSONObject addressJSON = new JSONObject();
                    addressJSON.put("line1", address1.getText());
                    addressJSON.put("line2", address2.getText());
                    addressJSON.put("city", city.getText());
                    addressJSON.put("state", state.getText());
                    addressJSON.put("postal_code", postal_code.getText());
                    addressJSON.put("currency", "usd");

                    json1.put("address", addressJSON);

                } catch (JSONException e) {
                    e.printStackTrace();
                }


                EditText nameShipping  = findViewById(R.id.nameTxBoxCreateCustomer);
                EditText address1Shipping  = findViewById(R.id.addressTxBoxShippingCreateCustomer1);
                EditText address2Shipping  = findViewById(R.id.addressTxBoxCreateCustomerShipping2);
                EditText cityShipping  = findViewById(R.id.cityTxBoxCreateCustomerShipping);
                EditText stateShipping  = findViewById(R.id.stateTxBoxShippingCreateCustomer);
                EditText zipcodeShipping  = findViewById(R.id.zipcodeTxBoxCreateCustomer);

                JSONObject shipping = new JSONObject();
                try {
                    shipping.put("name", nameShipping.getText());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                JSONObject addressShippingJSON = new JSONObject();
                try {
                    addressShippingJSON.put("line1", address1Shipping.getText());
                    addressShippingJSON.put("line2", address2Shipping.getText());
                    addressShippingJSON.put("city", cityShipping.getText());
                    addressShippingJSON.put("state", stateShipping.getText());
                    addressShippingJSON.put("postal_code", zipcodeShipping.getText());

                    shipping.put("address", addressShippingJSON);
                    json1.put("shipping", shipping);
                    Log.d("JSON: ", json1.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                RequestBody body = RequestBody.create(json1.toString(), mediaType);
                Request request = new Request.Builder()
                        .url(BACKEND_URL + "create_customer")
                        .post(body)
                        .build();

                httpClient.newCall(request)
                        .enqueue(new Callback() {
                            @Override
                            public void onFailure(@NotNull Call call, @NotNull IOException e) {

                            }

                            @Override
                            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {

                            }
                        });

            }
        });


                }


}