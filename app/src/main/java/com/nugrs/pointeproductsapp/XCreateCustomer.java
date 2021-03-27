package com.nugrs.pointeproductsapp;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
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

public class XCreateCustomer extends AppCompatActivity implements TextWatcher {
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
    EditText confirmPassword;
    EditText password;
    TextView checkPasword;
    Button backToLoginBt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_x_create_customer);

        password = findViewById(R.id.XpasswordTxBoxCreateCustomer);

        confirmPassword = findViewById(R.id.XconfirmPasswordTxBoxCreateCustomer);
        confirmPassword.addTextChangedListener(this);
        checkPasword = findViewById(R.id.XcheckPasswordMatchCreateCustomer);


        OkHttpClient httpClient = new OkHttpClient();

        MediaType mediaType = MediaType.get("application/json; charset=utf-8");


        Button createCustmerButton = findViewById(R.id.XpayNowCreateCustomer);
        createCustmerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("create cust btn", "create cust button pressed");

                EditText customer = findViewById(R.id.XnameTxBoxCreateCustomer);
                EditText address1 = findViewById(R.id.XaddressTxBoxCreateCustomer1);
                EditText address2 = findViewById(R.id.XaddressTxBoxCreateCustomer2);
                EditText city = findViewById(R.id.XcityTxBoxCreateCustomer);
                EditText state = findViewById(R.id.XstateTxBoxCraeteCustomer);
                EditText postal_code = findViewById(R.id.XzipcodeTxBoxCreateCustomer);
                EditText email = findViewById(R.id.XemailTxBoxCreateCustomer);
                EditText phone = findViewById(R.id.XphoneTxBoxCreateCustomer);


                json1 = new JSONObject();
                try {
                    //json1.put("checkBoxStatus", "checking");
                    json1.put("name", customer.getText());
                    json1.put("email", email.getText());
                    json1.put("phone", phone.getText());
                    json1.put("password", password.getText());

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


                EditText nameShipping = findViewById(R.id.XnameTxBoxCreateCustomer);
                EditText address1Shipping = findViewById(R.id.XaddressTxBoxShippingCreateCustomer1);
                EditText address2Shipping = findViewById(R.id.XaddressTxBoxCreateCustomerShipping2);
                EditText cityShipping = findViewById(R.id.XcityTxBoxCreateCustomerShipping);
                EditText stateShipping = findViewById(R.id.XstateTxBoxShippingCreateCustomer);
                EditText zipcodeShipping = findViewById(R.id.XzipcodeTxBoxCreateCustomer);

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
                                if (response.isSuccessful()) {
                                    Intent intent = new Intent(XCreateCustomer.this, XCustomerAccountCreated.class);
                                    startActivity(intent);
                                }
                            }
                        });

            }
        });


    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        if (!confirmPassword.equals(password)) {
            checkPasword.setVisibility(View.VISIBLE);
            Log.d("v", "Make Visible");
        } else if (confirmPassword.equals(password)) {
            Toast.makeText(this, "before change", Toast.LENGTH_SHORT).show();
            checkPasword.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//String text = editable.toString();

        Toast.makeText(this, "on change", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void afterTextChanged(Editable editable) {
        String text = editable.toString();

        if (!text.equals(password.getText().toString())) {
            Log.d("Check confirm Password", text);
            Log.d("Check Password", password.getText().toString());

            checkPasword.setVisibility(View.VISIBLE);
            Log.d("See Text", "Visible");
        } else {
            checkPasword.setVisibility(View.INVISIBLE);
            Log.d("See Text", "Not Visible");

            Toast.makeText(this, "after change", Toast.LENGTH_SHORT).show();

        }
    }

}