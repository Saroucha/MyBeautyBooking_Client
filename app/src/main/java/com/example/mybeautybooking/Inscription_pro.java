package com.example.mybeautybooking;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import net.gotev.uploadservice.UploadService;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.net.ssl.HttpsURLConnection;

public class Inscription_pro extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Button GetImageFromGalleryButton, Enregistrer;
    Spinner staticSpinner;
    ImageView ShowSelectedImage;
    //EDITEXT
    EditText Nom;
    EditText mail;
    EditText adresseDomicile;
    EditText password;
    EditText CodePostale;
    EditText Distance;
    EditText NomEntreprise;
    //EditText SiegeSocial;
    EditText RegistreSocial;
    EditText Description;
    EditText mobile;

    //TEXTVIEW
    TextView TxtNom;
    TextView TxtMail;
    TextView TxtCatego;
    TextView TxtMDP;
    TextView TxtAdresse;
    TextView TxtPostale;
    TextView TxtDistance;
    TextView TxtNomEntreprise;
    TextView TxtRegistre;
   // TextView TxtSiege;
    TextView TxtDescription;
    TextView TxtTel;
    Bitmap FixBitmap;
// déclaration des constante POST
    String ImageTag = "image_tag" ;
    String ImageName = "image_data" ;
    String nom = "NomP";
    String email="Email";
    String motDepasse="Password";
    String adresse="Adresse";
    String codePostale="Postale";
    String distance="Distance";
    String Entreprise="NomEntreprise";
    String categorie="Categorie";
   // String Siege="Siege";

    String Registre="Registre";
    String Descrip="Description";
    String tel="Telephone";


    ProgressDialog progressDialog ;

    ByteArrayOutputStream byteArrayOutputStream ;

    byte[] byteArray ;

    String ConvertImage ;

    String NomPro;
    String emailPro;
    String passwordPro;
    String adressePro;
    String postalePro;
    String distancePro;
    String NomEntreprisePro;
    String Spin;
    String registrePro;
    String descriptionPro;
    String mobilePro;
    String[] categoriesNames={"Catégorie","Coiffure","Onglerie","Epilation","Bien-être"};
    public final Pattern POSTALE = Pattern.compile("^(([0-8][0-9])|(9[0-5])|(2[ab]))[0-9]{3}$");
    public final Pattern Siret = Pattern.compile("^[0-9]{14}$");
    public final Pattern EMAIL_ADDRESS_PATTERN = Pattern.compile( "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+");
    public final Pattern TELEPHONE=Pattern.compile("^\\+?\\(?[0-9]{1,3}\\)? ?-?[0-9]{1,3} ?-?[0-9]{3,5} ?-?[0-9]{4}( ?-?[0-9]{3})?");
    HttpURLConnection httpURLConnection ;
    URL url;
    OutputStream outputStream;
    BufferedWriter bufferedWriter ;
    int RC ;
    BufferedReader bufferedReader ;
    StringBuilder stringBuilder;
    boolean check = true;
    private int GALLERY = 1, CAMERA = 2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscription_pro);
        // les instances Button, Image view and spinner
        GetImageFromGalleryButton = (Button)findViewById(R.id.buttonSelect);
        Enregistrer = (Button)findViewById(R.id.buttonUpload);
        ShowSelectedImage = (ImageView)findViewById(R.id.imageView);
        staticSpinner = (Spinner) findViewById(R.id.simpleSpinner);
        staticSpinner.setOnItemSelectedListener(this);

        // get ID EDITTEXT
        Nom=(EditText)findViewById(R.id.NomC);
        mail=(EditText)findViewById(R.id.mail);
        adresseDomicile=(EditText)findViewById(R.id.adresseDomicile);
        CodePostale=(EditText)findViewById(R.id.adressePostale);
        Distance=(EditText)findViewById(R.id.distance);
        password=(EditText)findViewById(R.id.mdp);
        NomEntreprise=(EditText)findViewById(R.id.nomEntreprise);
        RegistreSocial=(EditText)findViewById(R.id.registreCommercial);
        Description=(EditText)findViewById(R.id.description);
        mobile=(EditText)findViewById(R.id.tel);

        // GET ID TEXTVIEW
        TxtNom=(TextView)findViewById(R.id.txtNom);
        TxtAdresse=(TextView)findViewById(R.id.txtAdresse);
        TxtMail=(TextView)findViewById(R.id.txtMail);
        TxtPostale=(TextView)findViewById(R.id.txtPostale);
        TxtMDP=(TextView)findViewById(R.id.txtMdp);
        TxtNomEntreprise=(TextView)findViewById(R.id.txtNomEntreprise);
        TxtDistance=(TextView)findViewById(R.id.txtDistance);
        TxtRegistre=(TextView)findViewById(R.id.txtRegistre);
        TxtCatego=(TextView)findViewById(R.id.txtCatego);
        TxtTel=(TextView)findViewById(R.id.txtTel);
        TxtDescription=(TextView)findViewById(R.id.txtDescription);

        byteArrayOutputStream = new ByteArrayOutputStream();

        //Creating the ArrayAdapter instance having the bank name list
        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,categoriesNames);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        staticSpinner.setAdapter(aa);


        // button's action
        GetImageFromGalleryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPictureDialog();
            }
        });

        Enregistrer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               NomPro=Nom.getText().toString().trim();
                emailPro=mail.getText().toString().trim();
                passwordPro=password.getText().toString().trim();
                adressePro=adresseDomicile.getText().toString().trim();
                postalePro=CodePostale.getText().toString().trim();
                distancePro=Distance.getText().toString().trim();
                NomEntreprisePro=NomEntreprise.getText().toString().trim();
                registrePro=RegistreSocial.getText().toString().trim();
                descriptionPro=Description.getText().toString().trim();
                mobilePro=mobile.getText().toString().trim();
                Spin = staticSpinner.getSelectedItem().toString();
if(NomPro.isEmpty()|| emailPro.isEmpty()|| passwordPro.isEmpty() || Spin.equals("Catégorie")|| adresseDomicile.getText().toString().trim().equals("") || CodePostale.getText().toString().trim().equals("")|| Distance.getText().toString().equals("") || NomEntreprise.getText().toString().equals("") || RegistreSocial.getText().toString().equals("")|| Description.getText().toString().trim().equals("")|| mobile.getText().toString().trim().equals("")){
    TxtCatego.setVisibility(View.VISIBLE);
    TxtCatego.setText("Choisissez une catégorie !!");
    TxtNom.setVisibility(View.VISIBLE);
    TxtNom.setText("Insérer votre nom complet");
    TxtMail.setVisibility(View.VISIBLE);
    TxtMail.setText("Insérer votre mail ");
    TxtMDP.setVisibility(View.VISIBLE);
    TxtMDP.setText("Insérer votre mot de passe ");
    TxtAdresse.setVisibility(View.VISIBLE);
    TxtAdresse.setText("Insérer votre adresse");
    TxtPostale.setVisibility(View.VISIBLE);
    TxtPostale.setText("Insérer votre code postale");
    TxtDistance.setVisibility(View.VISIBLE);
    TxtDistance.setText("Insérer la distance de votre ville");
    TxtNomEntreprise.setVisibility(View.VISIBLE);
    TxtNomEntreprise.setText("Insérer le nom de votre entreprise");
    TxtRegistre.setVisibility(View.VISIBLE);
    TxtRegistre.setText("Insérer le n°Siret");
    TxtDescription.setVisibility(View.VISIBLE);
    TxtDescription.setText("Insérer une description de votre entreprise");

        }else{
                    UploadImageToServer();
                    sendEmail();
                    Intent intent = new Intent(Inscription_pro.this, HomePage.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(intent);

                }

  // }
}
                });

        if (ContextCompat.checkSelfPermission(Inscription_pro.this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{android.Manifest.permission.CAMERA},
                        5);
            }
        }
    }

    //Performing action onItemSelected and onNothing selected
    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position,long id) {
        Toast.makeText(getApplicationContext(), categoriesNames[position], Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
// TODO Auto-generated method stub

    }
    private void showPictureDialog(){
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(this);
        pictureDialog.setTitle("Select Action");
        String[] pictureDialogItems = {
                "Photo Gallery",
                "Camera" };
        pictureDialog.setItems(pictureDialogItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                choosePhotoFromGallary();
                                ShowSelectedImage.setVisibility(View.VISIBLE);
                                break;
                            case 1:
                                takePhotoFromCamera();
                                ShowSelectedImage.setVisibility(View.VISIBLE);
                                break;
                        }
                    }
                });
        pictureDialog.show();
    }

    public void choosePhotoFromGallary() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(galleryIntent, GALLERY);
    }

    private void takePhotoFromCamera() {
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == this.RESULT_CANCELED) {
            return;
        }
        if (requestCode == GALLERY) {
            if (data != null) {
                Uri contentURI = data.getData();
                try {
                    FixBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);
                    // String path = saveImage(bitmap);
                    //Toast.makeText(MainActivity.this, "Image Saved!", Toast.LENGTH_SHORT).show();
                    ShowSelectedImage.setImageBitmap(FixBitmap);
                   Enregistrer.setVisibility(View.VISIBLE);

                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(Inscription_pro.this, "Failed!", Toast.LENGTH_SHORT).show();
                }
            }

        } else if (requestCode == CAMERA) {
            FixBitmap = (Bitmap) data.getExtras().get("data");
            ShowSelectedImage.setImageBitmap(FixBitmap);
            Enregistrer.setVisibility(View.VISIBLE);
            //  saveImage(thumbnail);
            //Toast.makeText(ShadiRegistrationPart5.this, "Image Saved!", Toast.LENGTH_SHORT).show();
        }
    }

// validation email
    private boolean checkEmail(String email) {
        return EMAIL_ADDRESS_PATTERN.matcher(email).matches();

    }

    // validation siret
    private boolean checkSiret(String siret) {
        return Siret.matcher(siret).matches();

    }
// validation telephone
    private boolean checkTelephone(String tel) {
        return TELEPHONE.matcher(tel).matches();

    }

    // validation postale
    private boolean checkPostale(String postale) {
        return POSTALE.matcher(postale).matches();

    }

    public void UploadImageToServer(){
        FixBitmap.compress(Bitmap.CompressFormat.JPEG, 40, byteArrayOutputStream);
        byteArray = byteArrayOutputStream.toByteArray();
        ConvertImage = Base64.encodeToString(byteArray, Base64.DEFAULT);
        class AsyncTaskUploadClass extends AsyncTask<Void,Void,String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog = ProgressDialog.show(Inscription_pro.this,"Image is Uploading","Please Wait",false,false);
            }

            @Override
            protected void onPostExecute(String string1) {

                super.onPostExecute(string1);

                progressDialog.dismiss();

                Toast.makeText(Inscription_pro.this,string1,Toast.LENGTH_LONG).show();

            }


            @Override
            protected String doInBackground(Void... params) {

                ImageProcessClass imageProcessClass = new ImageProcessClass();

                HashMap<String,String> HashMapParams = new HashMap<String,String>();
                HashMapParams.put(nom, NomPro);
                HashMapParams.put(email,emailPro);
                HashMapParams.put(adresse, adressePro);
                HashMapParams.put(motDepasse, passwordPro);
                HashMapParams.put(codePostale, postalePro);
                HashMapParams.put(distance,distancePro);
                HashMapParams.put(Entreprise, NomEntreprisePro);
                HashMapParams.put(Registre, registrePro);
                HashMapParams.put(Descrip, descriptionPro);
                HashMapParams.put(tel,mobilePro);
                HashMapParams.put(categorie,Spin);
                HashMapParams.put(ImageTag, NomPro);
                HashMapParams.put(ImageName, ConvertImage);

                String FinalData = imageProcessClass.ImageHttpRequest("http://192.168.43.79/beautybooking/EnregistrementPro.php", HashMapParams);

                return FinalData;

            }
        }
        AsyncTaskUploadClass AsyncTaskUploadClassOBJ = new AsyncTaskUploadClass();
        AsyncTaskUploadClassOBJ.execute();
    }


    public class ImageProcessClass{

        public String ImageHttpRequest(String requestURL,HashMap<String, String> PData) {

            StringBuilder stringBuilder = new StringBuilder();

            try {
                url = new URL(requestURL);

                httpURLConnection = (HttpURLConnection) url.openConnection();

                httpURLConnection.setReadTimeout(20000);

                httpURLConnection.setConnectTimeout(20000);

                httpURLConnection.setRequestMethod("POST");

                httpURLConnection.setDoInput(true);

                httpURLConnection.setDoOutput(true);

                outputStream = httpURLConnection.getOutputStream();

                bufferedWriter = new BufferedWriter(

                        new OutputStreamWriter(outputStream, "UTF-8"));

                bufferedWriter.write(bufferedWriterDataFN(PData));

                bufferedWriter.flush();

                bufferedWriter.close();

                outputStream.close();

                RC = httpURLConnection.getResponseCode();

                if (RC == HttpsURLConnection.HTTP_OK) {

                    bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));

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

            stringBuilder = new StringBuilder();

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

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 5) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Now user should be able to use camera

            }
            else {

                Toast.makeText(Inscription_pro.this, "Unable to use Camera..Please Allow us to use Camera", Toast.LENGTH_LONG).show();

            }
        }
    }
    //envoi du mail à l admin
    private void sendEmail() {
        //ici faut mettre l email de l admin concerné
        String email = "zenzounasaroucha@gmail.com";
        //Envoi du mail à l'admin
        GmailSender sm = new GmailSender(this, email, "[Professionnel] nouveau dossier ", "Vous avez reçu un nouveau dossier d'un professionnel"+ '\n'+'\b'+emailPro.toString()+'\b'+'\n' +"à traiter dans les meilleurs delais"+'\n'+"le nom professionnel: "+NomPro.toString()+'\n'+"pour accèder aux dossiers des professionnels copier ce lien: http://localhost/beautybooking/Professionnels.php");
       //Envoi du mail au pro enregistré
        GmailSender smm=new GmailSender(this,emailPro,"Dossier professionnel","Votre dossier est en cours de traitement veuillez vous connecter d'ici 10 jours");
        //Executing sendmail to send email
        sm.execute();
        smm.execute();
    }
}
