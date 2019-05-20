package com.example.mybeautybooking;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;


public class Profile_Pro extends AppCompatActivity {
    //Déclaration des variables et constantes
    private EditText tvname, tvemail, adresse,telephone,registre,distance,description,nomEntreprise,postale;
    private Button btnlogout,btnDelete,btnModifier;
    private ImageView picture;
    private TextView txtDescriptions,txtUsername,txtTel,txtEmail,txtAdresse,txtPostale,txtDistance,txtEntreprise,txtDesc,txtRegistre;
    private static final String KEY_MOVIE_ID = "id";
    private static final String KEY_SUCCESS = "success";
    String ID;
    ProgressDialog progressDialog2;
    private PreferenceHelper preferenceHelper;
    String IdHolder;
    String getID;
    private int state =0;
    private int TRACK = 0;
    int success;
    public  String HttpUrlDeleteRecord = "http://10.192.129.78:80/beautybooking/deletePro.php";
    public  String URL_EDIT = "http://192.168.43.79:80/beautybooking/UpdatePro.php";

    HttpParse httpParse = new HttpParse();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile__pro);

        //Initialisation des objets
        preferenceHelper = new PreferenceHelper(this);

      //Edittext
        tvname = (EditText) findViewById(R.id.name);
        tvemail = (EditText) findViewById(R.id.email);
        adresse = (EditText) findViewById(R.id.adresse);
        postale = (EditText) findViewById(R.id.postale);
        distance = (EditText) findViewById(R.id.distance);
        telephone = (EditText) findViewById(R.id.telephone);
        description = (EditText) findViewById(R.id.description);
        nomEntreprise = (EditText) findViewById(R.id.nomEntreprise);
        registre = (EditText) findViewById(R.id.registre);

        // TextView
        //c'est le textview de l'affichage droit
        txtDescriptions=(TextView)findViewById(R.id.txtdescriptions);
        //c'est le textview de l'affichage gauche
        txtUsername=(TextView)findViewById(R.id.username);
        txtTel=(TextView)findViewById(R.id.Telephone);
        txtEmail=(TextView)findViewById(R.id.emaail);
        txtAdresse=(TextView)findViewById(R.id.adr);
        txtPostale=(TextView)findViewById(R.id.POSTA);
        txtDistance=(TextView)findViewById(R.id.ALENTOUR);
        txtEntreprise=(TextView)findViewById(R.id.entre);
        txtDesc=(TextView)findViewById(R.id.desc);
        txtRegistre=(TextView)findViewById(R.id.registres);


        //Boutons
        btnModifier=(Button)findViewById(R.id.modifier);
        btnlogout = (Button) findViewById(R.id.logout);
        btnDelete=(Button)findViewById(R.id.Deleteaccount);

        //ImageView
        picture = (ImageView) findViewById(R.id.pic);

        //insert data into the textview or edittext
        tvname.setText(preferenceHelper.getName());
        tvemail.setText(preferenceHelper.getHobby());
        adresse.setText(preferenceHelper.getadresseDomicile());
        postale.setText(preferenceHelper.getPostale());
        distance.setText(preferenceHelper.getDistance());
        telephone.setText(preferenceHelper.getTelephone());
        //description.setText(preferenceHelper.getdescription());
        txtDescriptions.setText(preferenceHelper.getdescription());
        nomEntreprise.setText(preferenceHelper.getNomEntreprise());
        registre.setText(preferenceHelper.getregistre());

        // hypertext description
        txtDescriptions.setText(Html.fromHtml(txtDescriptions.getText().toString()));
        txtDescriptions.setMovementMethod(LinkMovementMethod.getInstance());

        RequestOptions requestOptions = new RequestOptions().centerCrop().placeholder(R.drawable.loading_shape).error(R.drawable.loading_shape);

       //get image and insert it into the imageview
        Glide.with(this).load(preferenceHelper.getImage()).apply(requestOptions).into(picture);
        Intent intent = getIntent();
        ID=intent.getStringExtra(KEY_MOVIE_ID);
        IdHolder = getIntent().getStringExtra("id");


        //action's button
        //Logout button
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
        // Add Click listener on update button
        btnModifier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String bText = btnModifier.getText().toString();

                if (bText.equals("UPDATE")) {

                    //cacher le nom
                    txtUsername.setVisibility(View.GONE);
                    tvname.setVisibility(View.GONE);

                    //j'active la modification au tel
                    telephone.setEnabled(true);

                    //cacher Email
                    txtEmail.setVisibility(View.GONE);
                    tvemail.setVisibility(View.GONE);

                    //J'active la ville
                    adresse.setEnabled(true);

                    //j'active le postale
                    postale.setEnabled(true);

                    //j'active la distance
                    distance.setEnabled(true);

                    //je cache l'entreprise
                    txtEntreprise.setVisibility(View.GONE);
                    nomEntreprise.setVisibility(View.GONE);

                    // j'active description
                    txtDescriptions.setVisibility(View.GONE);
                    description.setVisibility(View.VISIBLE);
                    description.setText(txtDescriptions.getText().toString());
                    description.setEnabled(true);

                    //je cache registre
                    txtRegistre.setVisibility(View.GONE);
                    registre.setVisibility(View.GONE);

                    btnModifier.setText("Save");
                }
                else {
                    Toast.makeText(Profile_Pro.this, "ok", Toast.LENGTH_SHORT).show();

                    //afficher nom
                    txtUsername.setVisibility(View.VISIBLE);
                    tvname.setVisibility(View.VISIBLE);

                    // je desactive la modification du tel
                    telephone.setEnabled(false);

                    //afficher Email
                    txtEmail.setVisibility(View.VISIBLE);
                    tvemail.setVisibility(View.VISIBLE);

                    //je desactive la modification de la ville
                    adresse.setEnabled(false);

                    //je desactive la modification du postale
                    postale.setEnabled(false);

                    //je desactive la modification de distance
                    distance.setEnabled(false);

                    //je cache l'entreprise
                    txtEntreprise.setVisibility(View.VISIBLE);
                    nomEntreprise.setVisibility(View.VISIBLE);

                    //je desactive la modification de description
                    description.setEnabled(false);
                    description.setVisibility(View.GONE);
                    txtDescriptions.setVisibility(View.VISIBLE);
                    txtDescriptions.setText(description.getText().toString().trim());

                    //j'affiche registre
                    txtRegistre.setVisibility(View.VISIBLE);
                    registre.setVisibility(View.VISIBLE);

                    saveDetail();
                    btnModifier.setText("UPDATE");
                }
            }


        });

    }

    private void confirmDelete() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                Profile_Pro.this);
        alertDialogBuilder.setMessage("Are you sure, you want to delete this account?");
        alertDialogBuilder.setPositiveButton("Delete",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                        Intent intent = new Intent(Profile_Pro.this, Login_pro.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        Profile_Pro.this.finish();

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

    public void saveDetail(){
    final  String Ville = adresse.getText().toString().trim();
    final  String POSTALE = postale.getText().toString().trim();
    final  String Distance = distance.getText().toString().trim();
    final  String Tel = telephone.getText().toString().trim();
    final  String Description = description.getText().toString().trim();
    final String id = getID;
    final ProgressDialog progressDialog= new ProgressDialog(this);
    progressDialog.setMessage("Saving...");
    progressDialog.show();
    StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_EDIT,
            new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    progressDialog.dismiss();
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        String success = jsonObject.getString("success");
                        if(success.equals("1")){
                            Toast.makeText(Profile_Pro.this,"success",Toast.LENGTH_SHORT).show();
                    preferenceHelper.createSession(Ville,Tel,Distance,Description,id);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
            Toast.makeText(Profile_Pro.this,"error"+error.toString(),Toast.LENGTH_SHORT).show();
       Log.i(error.toString(),"error");
       System.out.println("error ;" + error.toString());
        }
    })
    {
        @Override
        protected Map<String, String> getParams() throws AuthFailureError {
            Map<String,String>params = new HashMap<>();
            params.put("Adresse",Ville);
            params.put("Telephone",Tel);
            params.put("Distance",Distance);
            params.put("Description",Description);
            params.put("Postale",POSTALE);

            return params;
        }
    };

    RequestQueue requestQueue = Volley.newRequestQueue(this);
    requestQueue.add(stringRequest);
}

    public void save(){

        final  String Ville = adresse.getText().toString().trim();
        final  String Distance = distance.getText().toString().trim();
        final String Tel = telephone.getText().toString().trim();
        final   String Description = description.getText().toString().trim();
        class AsyncTaskUploadClass extends AsyncTask<Void,Void,String> {

            @Override
            protected void onPreExecute() {

                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String string1) {

                super.onPostExecute(string1);


                Toast.makeText(Profile_Pro.this,string1,Toast.LENGTH_LONG).show();

            }


            @Override
            protected String doInBackground(Void... params) {


                HashMap<String,String> HashMapParams = new HashMap<String,String>();
                HashMapParams.put(preferenceHelper.adresseDomicile, Ville);
                HashMapParams.put(preferenceHelper.distance,Distance);
                HashMapParams.put(preferenceHelper.telephone, Tel);
                HashMapParams.put(preferenceHelper.description, Description);


                ImageProcessClass imageProcessClass = new ImageProcessClass();
                String FinalData = imageProcessClass.ImageHttpRequest("http://192.168.43.79/beautybooking/UpdatePro.php", HashMapParams);

                return FinalData;

            }
        }
        AsyncTaskUploadClass AsyncTaskUploadClassOBJ = new AsyncTaskUploadClass();
        AsyncTaskUploadClassOBJ.execute();
    }
    public class ImageProcessClass{
        boolean check = true;
        int RC;
        public String ImageHttpRequest(String requestURL,HashMap<String, String> PData) {

            StringBuilder stringBuilder = new StringBuilder();

            try {
               URL url = new URL(requestURL);

               HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

                httpURLConnection.setReadTimeout(20000);

                httpURLConnection.setConnectTimeout(20000);

                httpURLConnection.setRequestMethod("POST");

                httpURLConnection.setDoInput(true);

                httpURLConnection.setDoOutput(true);

               OutputStream outputStream = httpURLConnection.getOutputStream();

               BufferedWriter bufferedWriter = new BufferedWriter(

                        new OutputStreamWriter(outputStream, "UTF-8"));

                bufferedWriter.write(bufferedWriterDataFN(PData));

                bufferedWriter.flush();

                bufferedWriter.close();

                outputStream.close();

                RC = httpURLConnection.getResponseCode();

                if (RC == HttpsURLConnection.HTTP_OK) {

                  BufferedReader  bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));

                    stringBuilder = new StringBuilder();

                    String RC2;

                    while ((RC2 = bufferedReader.readLine()) != null){

                        stringBuilder.append(RC2);
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            return stringBuilder.toString();
        }

        private String bufferedWriterDataFN(HashMap<String, String> HashMapParams) throws UnsupportedEncodingException {

           StringBuilder stringBuilder = new StringBuilder();

            for (Map.Entry<String, String> KEY : HashMapParams.entrySet()) {
                if (check)
                    check = false;
                else
                    stringBuilder.append("&");

                stringBuilder.append(URLEncoder.encode(KEY.getKey(), "UTF-8"));

                stringBuilder.append("=");

                stringBuilder.append(URLEncoder.encode(KEY.getValue(), "UTF-8"));
            }

            return stringBuilder.toString();
        }

    }



}
