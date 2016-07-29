package id.overgrowth;

import android.animation.ObjectAnimator;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
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
import okhttp3.Response;

public class DetailTanamanUserBelumTanamActivity extends AppCompatActivity {
    private static final String TAG_ID = "id_tanaman_user";
    private static final String TAG_NAMA = "nama_tanaman";
    private static final String TAG_GAMBAR = "foto_tanaman";
    private static final String TAG_CARA_MENANAM = "cara_menanam";
    private Toolbar toolbar;
    private TextView titleToolbar;
    public static int id_tanaman_user;
    private ImageView imgVTanaman;
    private TextView txtVNama, txtVCaraMenanam, txtVBacaSelengkapnya, txtVCaraMenanamTitle, txtVPunyaBibit;
    private Button btnTanamSekarang;
    private AlertDialogManager alert;    
    private SessionManager session;
    private HashMap<String, String> user;
    private String idUser;
    FormBody formBody;
    private String urlGambar, namaTanaman, caraMenanam;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_tanaman_user_belum_tanam);
        createObjects();


        if(InternetCheck.isNetworkConnected(DetailTanamanUserBelumTanamActivity.this)){
            if (InternetCheck.isNetworkAvailable(DetailTanamanUserBelumTanamActivity.this)){
                if (session.isLoggedIn()){
                    user = session.getUserDetails();
                    idUser = user.get(SessionManager.KEY_IDUSER);
                    id_tanaman_user = getIntent().getExtras().getInt("id_tanaman_user");
                    if(id_tanaman_user != 0) {
                        formBody = new FormBody.Builder()
                                .add("id_user", idUser)
                                .add("id_tanaman_user", String.valueOf(id_tanaman_user))
                                .build();
                        try {
                            OkHttpRequest.postDataToServer(UrlApi.urlTanamanUser,formBody).enqueue(new Callback() {
                                @Override
                                public void onFailure(Call call, IOException e) {
                                    e.printStackTrace();
                                    Log.e("getdatatanamanuser", e.getMessage());
                                }

                                @Override
                                public void onResponse(Call call, Response response) throws IOException {
                                    int statusCode = 0;
                                    try {
                                        String result = response.body().string();
                                        Log.i("Hasil JSON : ", result);
                                        Log.i("getdatatanamanuser", "response detail news success");

                                        JSONObject jsonObject = new JSONObject(result);
                                        statusCode = jsonObject.getInt("statusCode");
                                        Log.i("getdatatanamanuser", String.valueOf(statusCode));
                                        if (statusCode == 200) {
                                            JSONArray jsonArray = jsonObject.getJSONArray("item");
                                            for (int i = 0; i < jsonArray.length(); i++) {
                                                JSONObject jObject = jsonArray.getJSONObject(i);

                                                Log.i("getID_tanamanuser", jObject.getString(TAG_ID));
                                                Log.i("getdatatanamanuser", jObject.getString(TAG_NAMA));
                                                namaTanaman = jObject.getString(TAG_NAMA);
                                                urlGambar = UrlApi.urlGambarTanaman+jObject.getString(TAG_GAMBAR);
                                                caraMenanam = jObject.getString(TAG_CARA_MENANAM);
                                            }

                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }

                                    final int finalStatusCode = statusCode;
                                    DetailTanamanUserBelumTanamActivity.this.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {

                                            if(finalStatusCode == 200) {

                                                initView();

                                                setToolbar(namaTanaman);
                                                txtVNama.setText(namaTanaman);
                                                Picasso.with(getBaseContext()).load(urlGambar).into(imgVTanaman);
                                                txtVCaraMenanam.setText(caraMenanam);
                                                btnTanamSekarang.setVisibility(View.VISIBLE);
                                                txtVBacaSelengkapnya.setVisibility(View.VISIBLE);
                                                txtVCaraMenanamTitle.setVisibility(View.VISIBLE);
                                                txtVPunyaBibit.setVisibility(View.VISIBLE);
                                                btnTanamSekarang.setOnClickListener(new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View view) {
                                                        prosesTanamTanaman();
                                                    }
                                                });


                                                txtVBacaSelengkapnya.setOnClickListener(new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View view) {
                                                        Bundle b = new Bundle();
                                                        Intent intent = new Intent(DetailTanamanUserBelumTanamActivity.this, DetailCaraMenanamActivity.class);
                                                        b.putString("caraMenanam",caraMenanam);
                                                        intent.putExtras(b);
                                                        startActivity(intent);
                                                    }
                                                });
                                            }

                                        }
                                    });
                                }
                            });
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                }
            } else {
                alert.showAlertDialog(this,"Error","Internet tidak bisa diakses!");
            }
        } else {
            alert.showAlertDialog(this,"Error","Tidak terkoneksi ke Internet!\nMohon nyalakan paket data atau koneksi WiFi!");
        }
            
    }
    private void initView() {
        imgVTanaman = (ImageView) findViewById(R.id.image_foto_detail_tanamanuser);
        txtVNama = (TextView) findViewById(R.id.textv_namadetail_tanamanuser);
        txtVCaraMenanam = (TextView) findViewById(R.id.textv_cara_menanam_tanamanuser);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        titleToolbar = (TextView) findViewById(R.id.title_toolbar);

        btnTanamSekarang = (Button) findViewById(R.id.button_tanam_sekarang_detail_tanamanuser);
        txtVBacaSelengkapnya = (TextView) findViewById(R.id.textv_baca_selengkapnya_detail_tanamanuser);
        txtVCaraMenanamTitle = (TextView) findViewById(R.id.text_cara_menanam_title_belum_tanam);
        txtVPunyaBibit = (TextView) findViewById(R.id.text_punyabibit_belum_tanam);

    }
    private void createObjects(){
        alert = new AlertDialogManager();
        session = new SessionManager(this);
    }
    private void setToolbar(String title){
        setSupportActionBar(toolbar);
        titleToolbar.setText(title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_overflow_detailtanaman_user, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.menu_hapustanaman_overflow:
                prosesHapusTanaman();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void prosesTanamTanaman(){

        formBody = new FormBody.Builder()
                .add("id_user", idUser)
                .add("id_tanaman_user", String.valueOf(id_tanaman_user))
                .build();
        try {
            OkHttpRequest.postDataToServer(UrlApi.urlTanamTanamanUser,formBody).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    e.printStackTrace();
                    Log.e("errortanamtanaman", e.getMessage());
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    int statusCode;
                    String message = null;
                    try {
                        Log.i("getdatatanamtanaman", "response detail tanaman success");

                        JSONObject jsonObject = new JSONObject(response.body().string());
                        statusCode = jsonObject.getInt("statusCode");
                        message = jsonObject.getString("message");
                        Log.i("gettanamtanamanstatus", String.valueOf(statusCode));

                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    final String finalMessage = message;
                    final Intent[] intent = new Intent[1];
                    DetailTanamanUserBelumTanamActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(DetailTanamanUserBelumTanamActivity.this, finalMessage, Toast.LENGTH_LONG).show();
                            intent[0] = new Intent(DetailTanamanUserBelumTanamActivity.this, MainActivity.class);
                            startActivity(intent[0]);
                            finish();
                        }
                    });
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void prosesHapusTanaman() {
        final AlertDialog.Builder popDialog = new AlertDialog.Builder(this);
        final LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View Viewlayout = inflater.inflate(R.layout.dialog_hapus_tanaman,(ViewGroup) findViewById(R.id.layout_dialog_hapus_tanaman));
        popDialog.setIcon(R.mipmap.ic_alert);
        popDialog.setTitle("Hapus Tanaman");
        popDialog.setView(Viewlayout);
        popDialog.setCancelable(false);
        // Button OK
        popDialog.setPositiveButton(android.R.string.ok,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        formBody = new FormBody.Builder()
                                .add("id_user", idUser)
                                .add("id_tanaman_user", String.valueOf(id_tanaman_user))
                                .build();
                        try {
                            OkHttpRequest.postDataToServer(UrlApi.urlHapusTanaman,formBody).enqueue(new Callback() {
                                @Override
                                public void onFailure(Call call, IOException e) {
                                    e.printStackTrace();
                                    Log.e("errorhapustanaman", e.getMessage());
                                }

                                @Override
                                public void onResponse(Call call, Response response) throws IOException {
                                    int statusCode;
                                    String message = null;
                                    try {
                                        Log.i("getresponsehapustanaman", "response detail news success");

                                        JSONObject jsonObject = new JSONObject(response.body().string());
                                        statusCode = jsonObject.getInt("statusCode");
                                        message = jsonObject.getString("message");
                                        Log.i("gethapustanamanstatus", String.valueOf(statusCode));

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }

                                    final String finalMessage = message;
                                    final Intent[] intent = new Intent[1];
                                    DetailTanamanUserBelumTanamActivity.this.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(DetailTanamanUserBelumTanamActivity.this, finalMessage, Toast.LENGTH_LONG).show();
                                            intent[0] = new Intent(DetailTanamanUserBelumTanamActivity.this, MainActivity.class);
                                            startActivity(intent[0]);
                                            finish();
                                        }
                                    });
                                }
                            });
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                })
                // Button Cancel
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
        popDialog.create();
        popDialog.show();
    }
}
