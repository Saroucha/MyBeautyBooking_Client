package com.example.mybeautybooking;

/**
 * Created by BOURAHLA sarah
 */
import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;

public class PreferenceHelper {

    private final String INTRO = "intro";
    private final String NAME = "NomP";
    public final String ID = "id";


    public final String Email = "Email";
    public final String adresseDomicile="Adresse";
    private final String postale="Postale";
    public  final String distance="Distance";
    private final String nomEntreprise="NomEntreprise";
    private final String registre="Registre";
    public final String description="Description";
    public final String telephone="Telephone";
    private final String Image="image_path";
    private final String LOGINN="IS_LOGIN";
    private SharedPreferences app_prefs;
   public  SharedPreferences.Editor editor;
    private Context context;

    public PreferenceHelper(Context context) {
        this.context = context;
        app_prefs = context.getSharedPreferences("shared",
                Context.MODE_PRIVATE);
        editor=app_prefs.edit();

    }

    public void putIsLogin(boolean loginorout) {
        SharedPreferences.Editor edit = app_prefs.edit();
        edit.putBoolean(INTRO, loginorout);
        edit.commit();
    }

    public void createSession(String Ville, String Tel, String Distance, String Description, String Id){
        editor.putBoolean(LOGINN,true);
        editor.putString(adresseDomicile,Ville);
        editor.putString(telephone,Tel);
        editor.putString(distance,Distance);
        editor.putString(description,Description);
        editor.putString(ID,Id);
        editor.apply();

    }

    public void createSessionLOGIN(String email, String id){
        editor.putBoolean(LOGINN,true);
        editor.putString(Email,email);
        editor.putString(ID,id);
        editor.apply();

    }
    public boolean getIsLogin() {
        return app_prefs.getBoolean(INTRO, false);
    }

    public HashMap<String,String> getUserDetail(){
        HashMap<String,String> user = new HashMap<>();
        user.put(NAME,app_prefs.getString(NAME,null));
        user.put(Email,app_prefs.getString(Email,null));
       user.put(ID,app_prefs.getString(ID,null));

        return user;
    }
    public void putadresseDomicile(String loginorout) {
        SharedPreferences.Editor edit = app_prefs.edit();
        edit.putString(adresseDomicile, loginorout);
        edit.commit();
    }
    public String getadresseDomicile() {
        return app_prefs.getString(adresseDomicile, "");
    }


   public String getID() {
       return app_prefs.getString(ID, "");
   }

    public void putID(String loginorout) {
        SharedPreferences.Editor edit = app_prefs.edit();
        edit.putString(ID, loginorout);
        edit.commit();
    }

    public void putName(String loginorout) {
        SharedPreferences.Editor edit = app_prefs.edit();
        edit.putString(NAME, loginorout);
        edit.commit();
    }
    public String getName() {
        return app_prefs.getString(NAME, "");
    }

    public void putImage(String loginorout) {
        SharedPreferences.Editor edit = app_prefs.edit();
        edit.putString(Image, loginorout);
        edit.commit();
    }
    public String getImage() {
        return app_prefs.getString(Image, "");
    }




    public void putHobby(String loginorout) {
        SharedPreferences.Editor edit = app_prefs.edit();
        edit.putString(Email, loginorout);
        edit.commit();
    }
    public String getHobby() {
        return app_prefs.getString(Email, "");
    }


    public void putPostale(String loginorout) {
        SharedPreferences.Editor edit = app_prefs.edit();
        edit.putString(postale, loginorout);
        edit.commit();
    }
    public String getPostale() {
        return app_prefs.getString(postale, "");
    }


    public void putDistance(String loginorout) {
        SharedPreferences.Editor edit = app_prefs.edit();
        edit.putString(distance, loginorout);
        edit.commit();
    }
    public String getDistance() {
        return app_prefs.getString(distance, "");
    }




    public void putNomEntreprise(String loginorout) {
        SharedPreferences.Editor edit = app_prefs.edit();
        edit.putString(nomEntreprise, loginorout);
        edit.commit();
    }
    public String getNomEntreprise() {
        return app_prefs.getString(nomEntreprise, "");
    }




    public String getiMAGE() {
        return app_prefs.getString(Image, "");
    }


    public void putiMAGE(String loginorout) {
        SharedPreferences.Editor edit = app_prefs.edit();
        edit.putString(Image, loginorout);
        edit.commit();
    }


    public void putregistre(String loginorout) {
        SharedPreferences.Editor edit = app_prefs.edit();
        edit.putString(registre, loginorout);
        edit.commit();
    }
    public String getregistre() {
        return app_prefs.getString(registre, "");
    }

    public void putdescription(String loginorout) {
        SharedPreferences.Editor edit = app_prefs.edit();
        edit.putString(description, loginorout);
        edit.commit();
    }
    public String getdescription() {
        return app_prefs.getString(description, "");
    }

    public void putTelephone(String loginorout) {
        SharedPreferences.Editor edit = app_prefs.edit();
        edit.putString(telephone, loginorout);
        edit.commit();
    }
    public String getTelephone() {
        return app_prefs.getString(telephone, "");
    }


}
