package id.overgrowth;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.util.HashMap;

import id.overgrowth.fragment.HomeFragment;
import id.overgrowth.fragment.ListTanamanUserFragment;
import id.overgrowth.utility.InternetCheck;
import id.overgrowth.utility.OkHttpRequest;
import id.overgrowth.utility.SessionManager;
import id.overgrowth.utility.UrlApi;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    private Toolbar mToolbar;
    private String idUser;
    private SessionManager session;
    private HashMap<String, String> user;
    private RequestBody requestBody;
    private TextView titleToolbar;
    ProgressDialog progressDialog;
    private boolean statusProgressDialog;
    AlertDialog.Builder popDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createObjects();
        if (!session.isLoggedIn()) {
            Intent login = new Intent(this, LoginActivity.class);
            startActivity(login);
            finish();
            return;
        }
        if (InternetCheck.isNetworkConnected(this)) {
            if (InternetCheck.isNetworkAvailable(this)) {
                initView();
                setSupportActionBar(mToolbar);

                if (session.isLoggedIn()) {
                    user = session.getUserDetails();
                    idUser = user.get(SessionManager.KEY_IDUSER);
                }
                progressDialog.setTitle("Memeriksa Data Tanaman Kamu");
                progressDialog.setMessage("Loading..");
                progressDialog.setIndeterminate(false);
                progressDialog.setCancelable(false);
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
                pdCanceller.postDelayed(progressRunnable, 26000);

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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        progressDialog.dismiss();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_overflow_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_rating_overflow:
                final String appPackageName = getPackageName(); // getPackageName() from Context or Activity object
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                } catch (android.content.ActivityNotFoundException anfe) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                }
                return true;
            case R.id.menu_logout_overflow:
                logoutUserAccount();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void initView() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        titleToolbar = (TextView) findViewById(R.id.title_toolbar);

    }
    private void createObjects(){
        session = new SessionManager(MainActivity.this);
        progressDialog = new ProgressDialog(MainActivity.this);
    }

    private void logoutUserAccount() {
        session.logoutUser();
    }

    private void cekTanamanUser(){
        Log.i("iduser:",idUser);


        requestBody = new FormBody.Builder()
                .add("id_user", idUser)
                .build();

        try {
            OkHttpRequest.postDataToServer(UrlApi.urlTanamanUser, requestBody).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    e.printStackTrace();
                    finish();
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
        popDialog = new AlertDialog.Builder(this);
        final LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View Viewlayout = inflater.inflate(R.layout.dialog_coba_lagi,(ViewGroup) findViewById(R.id.layout_dialog_coba_lagi));
        popDialog.setIcon(R.mipmap.ic_alert);
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