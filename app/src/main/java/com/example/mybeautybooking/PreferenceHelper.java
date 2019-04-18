package com.example.mybeautybooking;

/**
 * Created by BOURAHLA sarah
 */
import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceHelper {

    private final String INTRO = "intro";
    private final String NAME = "NomP";
    private final String Email = "Email";
    private final String adresseDomicile="Adresse";
    private final String postale="Postale";
    public  final String distance="Distance";
    private final String nomEntreprise="NomEntreprise";
    private final String registre="Registre";
    private final String description="Description";
    private final String telephone="Telephone";
    private final String Image="image_path";
    private SharedPreferences app_prefs;
    private Context context;

    public PreferenceHelper(Context context) {
        app_prefs = context.getSharedPreferences("shared",
                Context.MODE_PRIVATE);
        this.context = context;
    }

    public void putIsLogin(boolean loginorout) {
        SharedPreferences.Editor edit = app_prefs.edit();
        edit.putBoolean(INTRO, loginorout);
        edit.commit();
    }
    public boolean getIsLogin() {
        return app_prefs.getBoolean(INTRO, false);
    }

    public void putadresseDomicile(String loginorout) {
        SharedPreferences.Editor edit = app_prefs.edit();
        edit.putString(adresseDomicile, loginorout);
        edit.commit();
    }
    public String getadresseDomicile() {
        return app_prefs.getString(adresseDomicile, "");
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
