package id.overgrowth.utility;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import java.util.HashMap;

import id.overgrowth.LoginActivity;
import id.overgrowth.MainActivity;

/**
 * Created by bayu_wpp on 6/20/2016.
 */
public class SessionManager {
    private SharedPreferences pref;

    private SharedPreferences.Editor editor;

    private Context mContext;

    private static final String PREF_NAME = "OvergrowthPref";

    private static final String IS_LOGIN = "IsLoggedIn";

    private static final String ALARM = "Alarm";

    private static final String IS_HAVE_PLANTS = "IsHavePlants";

    public static final String KEY_IDUSER = "iduser";

    public static final String KEY_NAMAUSER = "namauser";

    public static final String KEY_EMAILUSER = "emailuser";

    public static final String KEY_URL_FOTO_USER = "urlUser";

    public SessionManager(Context context) {
        mContext = context;
        pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = pref.edit();
    }

    public void createLoginSession(String idUser, String nama, String email, String urlUser) {
        editor.putBoolean(IS_LOGIN, true);
        editor.putString(KEY_IDUSER, idUser);
        editor.putString(KEY_NAMAUSER, nama);
        editor.putString(KEY_EMAILUSER, email);
        editor.putString(KEY_URL_FOTO_USER, urlUser);
        editor.commit();
    }


    public void addProfile(String idUser, String nama, String email, String urlUser) {
        editor.putString(KEY_IDUSER,idUser);
        editor.putString(KEY_NAMAUSER,nama);
        editor.putString(KEY_EMAILUSER,email);
        editor.putString(KEY_URL_FOTO_USER,urlUser);
        editor.commit();
    }


    public void logoutUser() {
        editor.clear();
        editor.commit();

        Intent i = new Intent(mContext, LoginActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(i);
    }

    public HashMap<String, String> getUserDetails() {
        HashMap<String, String> user = new HashMap<>();
        user.put(KEY_IDUSER, pref.getString(KEY_IDUSER, null));
        user.put(KEY_NAMAUSER, pref.getString(KEY_NAMAUSER, null));
        user.put(KEY_EMAILUSER, pref.getString(KEY_EMAILUSER, null));
        user.put(KEY_URL_FOTO_USER, pref.getString(KEY_URL_FOTO_USER,null));
        return user;
    }

    public boolean isLoggedIn() {
        return pref.getBoolean(IS_LOGIN, false);
    }

    public boolean alarmAktif() {
        return pref.getBoolean(ALARM, false);//inisialisasi
    }

    public void setAlarm() {
        editor.putBoolean(ALARM, true);
        editor.commit();
    }
    public void unsetAlarm() {
        editor.remove(ALARM);
        editor.commit();
    }

}
