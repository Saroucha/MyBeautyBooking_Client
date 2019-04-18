package com.example.mybeautybooking;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;


public class Profile_Pro extends AppCompatActivity {
    //Déclaration des variables et constantes
    private EditText tvname, tvemail, adresse,telephone,registre,distance,description,nomEntreprise,postale;
    private Button btnlogout,btnDelete;
    private ImageView picture;
    private static final String KEY_MOVIE_ID = "id";
    private static final String KEY_SUCCESS = "success";
    String ID;
    private String imagesJSON;
    HashMap<String,String> hashMap = new HashMap<>();
    ProgressDialog progressDialog2;
    private PreferenceHelper preferenceHelper;
    String finalResult ;
    String IdHolder;
    private static final String JSON_ARRAY ="result";
    private static final String IMAGE_URL = "url";
    private JSONArray arrayImages= null;

    private int TRACK = 0;
    int success;
    public  String HttpUrlDeleteRecord = "http://10.192.129.78:80/beautybooking/deletePro.php";
    HttpParse httpParse = new HttpParse();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile__pro);

        //Initialisation des objets
        preferenceHelper = new PreferenceHelper(this);

        tvname = (EditText) findViewById(R.id.name);
        tvemail = (EditText) findViewById(R.id.email);
        adresse = (EditText) findViewById(R.id.adresse);
        postale = (EditText) findViewById(R.id.postale);
        distance = (EditText) findViewById(R.id.distance);
        telephone = (EditText) findViewById(R.id.telephone);
        description = (EditText) findViewById(R.id.description);
        nomEntreprise = (EditText) findViewById(R.id.nomEntreprise);
        registre = (EditText) findViewById(R.id.registre);
        picture = (ImageView) findViewById(R.id.pic);
        btnlogout = (Button) findViewById(R.id.logout);
        btnDelete=(Button)findViewById(R.id.Deleteaccount);
        tvname.setText(preferenceHelper.getName());
        tvemail.setText(preferenceHelper.getHobby());
        adresse.setText(preferenceHelper.getadresseDomicile());
        postale.setText(preferenceHelper.getPostale());
        distance.setText(preferenceHelper.getDistance());
        telephone.setText(preferenceHelper.getTelephone());
        description.setText(preferenceHelper.getdescription());
        nomEntreprise.setText(preferenceHelper.getNomEntreprise());
        registre.setText(preferenceHelper.getregistre());

        RequestOptions requestOptions = new RequestOptions().centerCrop().placeholder(R.drawable.loading_shape).error(R.drawable.loading_shape);

        Glide.with(this).load(preferenceHelper.getImage()).apply(requestOptions).into(picture);
        Intent intent = getIntent();
        ID=intent.getStringExtra(KEY_MOVIE_ID);
        IdHolder = getIntent().getStringExtra("id");



        btnlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                preferenceHelper.putIsLogin(false);
                Intent intent = new Intent(Profile_Pro.this, Login_pro.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                Profile_Pro.this.finish();
            }
        });

        // Add Click listener on Delete button.
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                confirmDelete();
            }
        });

    }

    private void confirmDelete() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                Profile_Pro.this);
        alertDialogBuilder.setMessage("Are you sure, you want to delete this movie?");
        alertDialogBuilder.setPositiveButton("Delete",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                            //If the user confirms deletion, execute DeleteMovieAsyncTask
                            new DeleteMovieAsyncTask().execute();

                    }
                });

        alertDialogBuilder.setNegativeButton("Cancel", null);

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
    private class DeleteMovieAsyncTask extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Display progress bar
            progressDialog2 = new ProgressDialog(Profile_Pro.this);
            progressDialog2.setMessage("Deleting Movie. Please wait...");
            progressDialog2.setIndeterminate(false);
            progressDialog2.setCancelable(false);
            progressDialog2.show();
        }

        @Override
        protected String doInBackground(String... params) {
            HttpParse httpJsonParser = new HttpParse();
            Map<String, String> httpParams = new HashMap<>();
            //Set movie_id parameter in request
            httpParams.put(KEY_MOVIE_ID, ID);
            JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                    HttpUrlDeleteRecord , "POST", httpParams);
            try {
                success = jsonObject.getInt(KEY_SUCCESS);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(String result) {
            progressDialog2.dismiss();
            runOnUiThread(new Runnable() {
                public void run() {
                    if (success == 1) {
                        //Display success message
                        Toast.makeText(Profile_Pro.this,
                                "Movie Deleted", Toast.LENGTH_LONG).show();
                        Intent i = getIntent();
                        //send result code 20 to notify about movie deletion
                        setResult(20, i);
                        finish();

                    } else {
                        Toast.makeText(Profile_Pro.this,
                                "Some error occurred while deleting movie",
                                Toast.LENGTH_LONG).show();

                    }
                }
            });

        }
    }
    @Override
    public void onDestroy(){
        super.onDestroy();
        if ( progressDialog2!=null && progressDialog2.isShowing() ){
            progressDialog2.cancel();
        }
    }





}
