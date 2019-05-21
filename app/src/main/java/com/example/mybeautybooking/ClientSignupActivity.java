package com.example.mybeautybooking;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;


public class ClientSignupActivity extends AppCompatActivity {
    private static final String TAG = "SignupActivity";

    ProgressBar progressBar;


    EditText nameText_Client;
    EditText firstnameText_Client;
    EditText phoneText_Client;
    EditText streetText_Client;
    EditText zipText_Client;
    EditText cityText_Client;
    EditText emailText_Client;
    EditText passwordText_Client;
    Button signupButton_Client;
    TextView loginLink_Client;

    private RequestQueue requestQueue;
    private static final String URL = "http://192.168.43.242/projet/client_add.php";
    private StringRequest request;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_signup);


        progressBar = (ProgressBar) findViewById(R.id.progressbar);
//        progressBar.setVisibility(View.INVISIBLE);

        nameText_Client = (EditText) findViewById(R.id.input_name);
        firstnameText_Client = (EditText) findViewById(R.id.input_first_name);
        phoneText_Client = (EditText) findViewById(R.id.input_phone);
        streetText_Client = (EditText) findViewById(R.id.input_street);
        zipText_Client = (EditText) findViewById(R.id.input_zip);
        cityText_Client = (EditText) findViewById(R.id.input_city);
        emailText_Client = (EditText) findViewById(R.id.input_email);
        passwordText_Client = (EditText) findViewById(R.id.input_password);
        signupButton_Client = (Button) findViewById(R.id.btn_signup);
        signupButton_Client.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });

        requestQueue = Volley.newRequestQueue(this);


        loginLink_Client = (TextView) findViewById(R.id.link_login);
        loginLink_Client.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ClientLoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
            }
        });

    }




    public void signup() {
        Log.d(TAG, "Signup");

        if (!validate()) {
            onSignupFailed();
            return;
        }

        signupButton_Client.setEnabled(false);

//        progressBar = (ProgressBar) findViewById(R.id.progressbar);
//        progressBar.setVisibility(View.VISIBLE);

        // TODO: Implement signup logic here.

        request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    // create json object with php reponse
                    JSONObject jsonObject = new JSONObject(response);

                    /*verification jsonphp reponse*/
                    if(jsonObject.names().get(0).equals("success")){
                        SharedPrefManager.getInstance(getApplicationContext())
                                .userLogin(
                                        jsonObject.getInt("id"),
                                        jsonObject.getString("name"),
                                        jsonObject.getString("firstname"),
                                        jsonObject.getString("email"),
                                        jsonObject.getString("password"),
                                        jsonObject.getString("phone"),
                                        jsonObject.getString("street"),
                                        jsonObject.getString("zip"),
                                        jsonObject.getString("city")

                                );
                        new android.os.Handler().postDelayed(
                                new Runnable() {
                                    public void run() {
                                        // onSignUpSuccess
                                        onSignupSuccess();
                                        //progressBar.setVisibility(View.INVISIBLE);
                                    }
                                }, 1000);
                    }else if(jsonObject.names().get(0).equals("exist")){

                        new android.os.Handler().postDelayed(
                                new Runnable() {
                                    public void run() {
                                        // client exist
                                      //  progressBar.setVisibility(View.INVISIBLE);
                                    }
                                }, 1000);
                        Toast.makeText(getBaseContext(), R.string.error_exist_email, Toast.LENGTH_LONG).show();
                    }
                    else{
                        new android.os.Handler().postDelayed(
                                new Runnable() {
                                    public void run() {
                                        //  onSignUpFailed
                                        onSignupFailed();
                                     //   progressBar.setVisibility(View.INVISIBLE);
                                    }
                                }, 1000);                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() { /*volley error*/
            @Override
            public void onErrorResponse(VolleyError error) {

                if (error instanceof TimeoutError) { Toast.makeText(getApplicationContext(), "TimeoutError", Toast.LENGTH_LONG).show();}

                if ( error instanceof NoConnectionError) { Toast.makeText(getApplicationContext(), "NoConnectionError" ,Toast.LENGTH_LONG).show();}

                if (error instanceof AuthFailureError) {
                    Toast.makeText(getApplicationContext(), "AuthFailureError" ,Toast.LENGTH_LONG).show();}
                else if (error instanceof ServerError) {
                    Toast.makeText(getApplicationContext(), "ServerError", Toast.LENGTH_LONG).show(); }

                else if (error instanceof NetworkError) {
                    Toast.makeText(getApplicationContext(), "NetworkError", Toast.LENGTH_LONG).show(); }

                else if  (error instanceof ParseError) {
                    Toast.makeText(getApplicationContext(), "ParseError" ,Toast.LENGTH_LONG).show();}

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> hashMap = new HashMap<String, String>();
                hashMap.put("email",emailText_Client.getText().toString());
                hashMap.put("password",passwordText_Client.getText().toString());
                hashMap.put("name",nameText_Client.getText().toString());
                hashMap.put("first_name",firstnameText_Client.getText().toString());
                hashMap.put("phone",phoneText_Client.getText().toString());
                hashMap.put("street",streetText_Client.getText().toString());
                hashMap.put("city",cityText_Client.getText().toString());
                hashMap.put("zip",zipText_Client.getText().toString());

                return hashMap;
            }
        };

        requestQueue.add(request);

    }

    /*access to client profil*/
    public void onSignupSuccess() {
        signupButton_Client.setEnabled(true);
        setResult(RESULT_OK, null);
        Intent intent = new Intent(ClientSignupActivity.this, ClientProfilActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
        finish();
    }

    public void onSignupFailed() {
        Toast.makeText(getBaseContext(), R.string.login_failed_msg, Toast.LENGTH_LONG).show();

        signupButton_Client.setEnabled(true);
    }

    /* verif if the fields are validate*/
    public boolean validate() {
        boolean valid = true;

        String name = nameText_Client.getText().toString();
        String firstname = firstnameText_Client.getText().toString();
        String phone = phoneText_Client.getText().toString();
        String street = streetText_Client.getText().toString();
        String city = cityText_Client.getText().toString();
        String zip = zipText_Client.getText().toString();
        String email = emailText_Client.getText().toString();
        String password = passwordText_Client.getText().toString();

        if (name.isEmpty() || name.length() < 3) {
            nameText_Client.setError(getString(R.string.error_name_signup_msg));
            valid = false;
        } else {
            nameText_Client.setError(null);
        }

        if (firstname.isEmpty() || firstname.length() < 3) {
            firstnameText_Client.setError(getString(R.string.error_firstname_signup_msg));
            valid = false;
        } else {
            firstnameText_Client.setError(null);
        }
//        || !android.util.Patterns.PHONE.matcher(phone).matches()
        if (phone.isEmpty() || !Patterns.PHONE.matcher(phone).matches() ) {
            phoneText_Client.setError(getString(R.string.error_phone_signup_msg));
            valid = false;
        } else {
            phoneText_Client.setError(null);
        }

        if (street.isEmpty() || street.length() < 5) {
            streetText_Client.setError(getString(R.string.error_street_signup_msg));
            valid = false;
        } else {
            streetText_Client.setError(null);
        }

        if (city.isEmpty() || city.length() < 3) {
            cityText_Client.setError(getString(R.string.error_city_signup_msg));
            valid = false;
        } else {
            cityText_Client.setError(null);
        }

        if (zip.isEmpty() || zip.length() != 5 ) {
            zipText_Client.setError(getString(R.string.error_zip_signup_msg));
            valid = false;
        } else {
            zipText_Client.setError(null);
        }


        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailText_Client.setError(getString(R.string.error_invalid_email));
            valid = false;
        } else {
            emailText_Client.setError(null);
        }

        if (password.isEmpty() || password.length() < 8) {
            passwordText_Client.setError(getString(R.string.error_invalid_password));
            valid = false;
        } else {
            passwordText_Client.setError(null);
        }

        return valid;
    }
}
