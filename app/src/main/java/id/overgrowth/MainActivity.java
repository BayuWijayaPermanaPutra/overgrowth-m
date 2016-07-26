package id.overgrowth;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

import id.overgrowth.utility.AlertDialogManager;
import id.overgrowth.utility.InternetCheck;
import id.overgrowth.utility.OkHttpRequest;
import id.overgrowth.utility.SessionManager;
import id.overgrowth.utility.UrlApi;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    DrawerLayout drawer;
    NavigationView navigationView;
    Toolbar mToolbar;
    private String idUser;
    private SessionManager session;
    private HashMap<String, String> user;
    private ImageView fotoUser;
    private TextView namaUser;
    private TextView emailUser;
    private TextView logout;
    private RequestBody requestBody;
    private TextView titleToolbar;
    ProgressDialog progressDialog;
    private boolean statusProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createObjects();
        if(!session.isLoggedIn()){
            Intent login = new Intent(this, LoginActivity.class);
            startActivity(login);
            finish();
            return;
        }
        if(InternetCheck.isNetworkConnected(this)){
            if (InternetCheck.isNetworkAvailable(this)) {
                initView();
                setSupportActionBar(mToolbar);
                getSupportActionBar().setDisplayShowHomeEnabled(true);
                ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                        this, drawer, mToolbar, R.string.drawer_open, R.string.drawer_close);
                drawer.setDrawerListener(toggle);
                toggle.syncState();
                getSupportActionBar().setDisplayShowHomeEnabled(true);
                mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        drawer.openDrawer(GravityCompat.START);
                    }
                });
                //header
                View header = LayoutInflater.from(this).inflate(R.layout.nav_header,null);
                navigationView.addHeaderView(header);

                if (session.isLoggedIn()){
                    user = session.getUserDetails();
                    fotoUser = (ImageView) findViewById(R.id.image_foto_header);
                    emailUser = (TextView) findViewById(R.id.txt_email_header);
                    namaUser = (TextView) findViewById(R.id.txt_nama_header);
                    logout = (TextView) findViewById(R.id.txt_button_logout_header);

                    idUser = user.get(SessionManager.KEY_IDUSER);
                    Picasso.with(getBaseContext()).load(user.get(SessionManager.KEY_URL_FOTO_USER)).into(fotoUser);
                    namaUser.setText(user.get(SessionManager.KEY_NAMAUSER));
                    emailUser.setText(user.get(SessionManager.KEY_EMAILUSER));
                    logout.setOnClickListener(this);
                }
                cekTanamanUser();
            } else {
                //alert.showAlertDialog(this,"Error","Internet tidak bisa diakses!");
                showDialog();
            }
        } else {
           //alert.showAlertDialog(this,"Error","Tidak terkoneksi ke Internet!\nMohon nyalakan paket data atau koneksi WiFi!");
            showDialog();
        }
    }


    private void initView() {
        navigationView = (NavigationView) findViewById(R.id.fragment_navigation_drawer);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        titleToolbar = (TextView) findViewById(R.id.title_toolbar);

    }
    private void createObjects(){
        session = new SessionManager(this);
        progressDialog = new ProgressDialog(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        else {
            finish();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txt_button_logout_header : logoutUserAccount();
                break;
            default: break;
        }
    }

    private void logoutUserAccount() {
        session.logoutUser();
    }

    private void cekTanamanUser(){
        Log.i("iduser:",idUser);
        progressDialog.setTitle("Pengecekkan Data Tanaman Kamu");
        progressDialog.setMessage("Loading..");
        progressDialog.setIndeterminate(false);
        progressDialog.setCancelable(true);
        progressDialog.show();
        statusProgressDialog = true;
        Runnable progressRunnable = new Runnable() {
            @Override
            public void run() {
                progressDialog.cancel();
                if(statusProgressDialog){
                    showDialog();
                }
            }
        };
        Handler pdCanceller = new Handler();
        pdCanceller.postDelayed(progressRunnable, 25000);

        requestBody = new FormBody.Builder()
                .add("id_user", idUser)
                .build();

        try {
            OkHttpRequest.postDataToServer(UrlApi.urlTanamanUser, requestBody).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.e("Error : ", e.getMessage());
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    int statusCode = 0;
                    String pesan = null;
                    try {
                        Log.i("getdatanews", "response success");

                        JSONObject jsonObject = new JSONObject(response.body().string());
                        statusCode = jsonObject.getInt("statusCode");
                        pesan = jsonObject.getString("pesan");

                        Log.i("getdatanews", String.valueOf(statusCode));


                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    final int finalStatusCode = statusCode;
                    final String finalPesan = pesan;
                    MainActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            FragmentManager fragmentManager = getSupportFragmentManager();
                            HomeFragment home = new HomeFragment();
                            ListTanamanUserFragment tanaman_user = new ListTanamanUserFragment();
                            if (statusProgressDialog == true){
                                progressDialog.dismiss();
                                statusProgressDialog = false;
                            }

                            if (finalStatusCode == 200) {
                                titleToolbar.setText("Tanaman Saya");
                                fragmentManager.beginTransaction().replace(R.id.container_body, tanaman_user).commit();
                            } else {
                                Toast.makeText(MainActivity.this, finalPesan, Toast.LENGTH_SHORT).show();
                                titleToolbar.setText("Overgrowth");
                                fragmentManager.beginTransaction().replace(R.id.container_body, home).commit();
                            }
                        }
                    });
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void showDialog() {
        final AlertDialog.Builder popDialog = new AlertDialog.Builder(this);
        final LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View Viewlayout = inflater.inflate(R.layout.dialog_coba_lagi,(ViewGroup) findViewById(R.id.layout_dialog_coba_lagi));
        popDialog.setIcon(android.R.drawable.stat_notify_error);
        popDialog.setTitle("Pengecekkan Data Tanaman Gagal");
        popDialog.setView(Viewlayout);
        popDialog.setCancelable(false);
        // Button OK
        popDialog.setPositiveButton(android.R.string.ok,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = getIntent();
                        finish();
                        startActivity(intent);
                    }
                })
                // Button Cancel
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                                finish();
                            }
                        });
        popDialog.create();
        popDialog.show();
    }
}