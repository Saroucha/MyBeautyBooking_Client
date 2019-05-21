package com.example.mybeautybooking.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
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
import com.example.mybeautybooking.ClientProfilActivity;
import com.example.mybeautybooking.R;
import com.example.mybeautybooking.SharedPrefManager;
import com.example.mybeautybooking.fragment.ProfilFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ClientProfil extends AppCompatActivity {
    public ArrayList<String> arrayList = new java.util.ArrayList<>();
    ProgressBar progressBar;
    ProgressDialog progressDialog2;


    EditText email_Client;
    EditText firstnameText_Client;
    EditText nameText_Client;
    EditText passText_Client;
    EditText phoneIntClient;
    EditText streetText_Client;
    EditText zipInt_Client;
    EditText cityText_Client;


    Button doneButton,deleteButton;

    //RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
    ClientProfilActivity clientProfilActivity;

    private RequestQueue requestQueue;
    private static final String URL = "http://192.168.43.242/Test-Projet/delete_test.php";
    private static final String URL1 = "http://192.168.43.242/Test-Projet/update.php";
    private StringRequest request;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil_client);

        nameText_Client = (EditText) findViewById(R.id.input_name);
        nameText_Client.setText(SharedPrefManager.getInstance(getApplicationContext()).getKeyUserName());

        firstnameText_Client = (EditText) findViewById(R.id.input_first_name);
        firstnameText_Client.setText(SharedPrefManager.getInstance(getApplicationContext()).getKeyUserFirstname());

        phoneIntClient= (EditText) findViewById(R.id.input_phone);
        phoneIntClient.setText(SharedPrefManager.getInstance(getApplicationContext()).getKeyUserPhone());

        streetText_Client = (EditText) findViewById(R.id.input_street);
        streetText_Client.setText(SharedPrefManager.getInstance(getApplicationContext()).getKeyUserStreet());

        cityText_Client = (EditText) findViewById(R.id.input_city);
        cityText_Client.setText(SharedPrefManager.getInstance(getApplicationContext()).getKeyUserCity());


        zipInt_Client = (EditText) findViewById(R.id.input_zip);
        zipInt_Client.setText(SharedPrefManager.getInstance(getApplicationContext()).getKeyUserZip());

        email_Client = (EditText) findViewById(R.id.input_email);
        email_Client.setText(SharedPrefManager.getInstance(getApplicationContext()).getUserEmail());

        doneButton = (Button)findViewById(R.id.btn_done);
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                update();
            }
        });

        requestQueue = Volley.newRequestQueue(getApplicationContext());


        clientProfilActivity = new ClientProfilActivity();
        deleteButton = (Button)findViewById(R.id.btn_delete);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //confirmDelete();
                confirmDelete();
            }
        });

    }



    private void confirmDelete() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ClientProfil.this);
        alertDialogBuilder.setMessage("Êtes-vous sûr de vouloir supprimer votre compte?");
        alertDialogBuilder.setPositiveButton("Supprimer",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                        //If the user confirms deletion, execute DeleteMovieAsyncTask
                        delete();

                    }
                });

        alertDialogBuilder.setNegativeButton("Annuler", null);

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }





    public void delete () {

        progressDialog2 = new ProgressDialog(ClientProfil.this);
        progressDialog2.setMessage(" Suppression en cours. Veuillez patienter...");
        progressDialog2.setIndeterminate(false);
        progressDialog2.setCancelable(false);
        progressDialog2.show();

        request = new StringRequest(Request.Method.POST,URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    // create json object with php reponse
                    JSONObject jsonObject = new JSONObject(response);

                    /*verification jsonphp reponse*/
                    if (jsonObject.names().get(0).equals("success")) {

                        new android.os.Handler().postDelayed(
                                new Runnable() {
                                    public void run() {
                                        // onLoginSuccess
                                        destroy();
                                        finish();
//                                        progressBar.setVisibility(View.INVISIBLE);
                                        //progressDialog2.dismiss();
                                    }
                                }, 100);
                    }else {destroy();}

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                if (error instanceof TimeoutError) {
                    Toast.makeText(getApplicationContext(), "TimeoutError", Toast.LENGTH_LONG).show();
                }

                if (error instanceof NoConnectionError) {
                    Toast.makeText(getApplicationContext(), "NoConnectionError", Toast.LENGTH_LONG).show();
                }

                if (error instanceof AuthFailureError) {
                    Toast.makeText(getApplicationContext(), "AuthFailureError", Toast.LENGTH_LONG).show();
                } else if (error instanceof ServerError) {
                    Toast.makeText(getApplicationContext(), "ServerError", Toast.LENGTH_LONG).show();
                } else if (error instanceof NetworkError) {
                    Toast.makeText(getApplicationContext(), "NetworkError", Toast.LENGTH_LONG).show();
                } else if (error instanceof ParseError) {
                    Toast.makeText(getApplicationContext(), "ParseError", Toast.LENGTH_LONG).show();
                }

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> hashMap = new HashMap<String, String>();
                hashMap.put("id", SharedPrefManager.getInstance(getApplicationContext()).getKeyUserID().toString());

                return hashMap;
            }
        };

        requestQueue.add(request);


    }

     void destroy(){
        if ( progressDialog2!=null && progressDialog2.isShowing() ){
            progressDialog2.cancel();
        }
    }


     void update () {
         if(validate()){

        request = new StringRequest(Request.Method.POST,URL1, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    // create json object with php reponse
                    JSONObject jsonObject = new JSONObject(response);

                    /*verification jsonphp reponse*/
                    if (jsonObject.names().get(0).equals("success")) {
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
                                        Toast.makeText(getApplicationContext(), "Mise à jour profil", Toast.LENGTH_LONG).show();
                                    }
                                }, 100);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                if (error instanceof TimeoutError) {
                    Toast.makeText(getApplicationContext(), "TimeoutError", Toast.LENGTH_LONG).show();
                }

                if (error instanceof NoConnectionError) {
                    Toast.makeText(getApplicationContext(), "NoConnectionError", Toast.LENGTH_LONG).show();
                }

                if (error instanceof AuthFailureError) {
                    Toast.makeText(getApplicationContext(), "AuthFailureError", Toast.LENGTH_LONG).show();
                } else if (error instanceof ServerError) {
                    Toast.makeText(getApplicationContext(), "ServerError", Toast.LENGTH_LONG).show();
                } else if (error instanceof NetworkError) {
                    Toast.makeText(getApplicationContext(), "NetworkError", Toast.LENGTH_LONG).show();
                } else if (error instanceof ParseError) {
                    Toast.makeText(getApplicationContext(), "ParseError", Toast.LENGTH_LONG).show();
                }

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> hashMap = new HashMap<String, String>();
                hashMap.put("id", SharedPrefManager.getInstance(getApplicationContext()).getKeyUserID().toString());
                hashMap.put("email",email_Client.getText().toString());
                hashMap.put("name",nameText_Client.getText().toString());
                hashMap.put("first_name",firstnameText_Client.getText().toString());
                hashMap.put("phone",phoneIntClient.getText().toString());
                hashMap.put("street",streetText_Client.getText().toString());
                hashMap.put("city",cityText_Client.getText().toString());
                hashMap.put("zip",zipInt_Client.getText().toString());

                return hashMap;
            }
        };

        requestQueue.add(request);

         }
    }

    public boolean validate() {
        boolean valid = true;

        String email = email_Client.getText().toString();
        String name = nameText_Client.getText().toString();
        String firstname = firstnameText_Client.getText().toString();
        String phone = phoneIntClient.getText().toString();
        String street = streetText_Client.getText().toString();
        String city = cityText_Client.getText().toString();
        String zip = zipInt_Client.getText().toString();


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
            phoneIntClient.setError(getString(R.string.error_phone_signup_msg));
            valid = false;
        } else {
            phoneIntClient.setError(null);
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
            zipInt_Client.setError(getString(R.string.error_zip_signup_msg));
            valid = false;
        } else {
            zipInt_Client.setError(null);
        }


        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            email_Client.setError(getString(R.string.error_invalid_email));
            valid = false;
        } else {
            email_Client.setError(null);
        }

        return valid;
    }

}
