package id.overgrowth;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import id.overgrowth.utility.AlertDialogManager;
import id.overgrowth.utility.InternetCheck;
import id.overgrowth.utility.OkHttpRequest;
import id.overgrowth.utility.SessionManager;
import id.overgrowth.utility.UrlApi;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Response;

public class DetailTanamanUserSudahTanamActivity extends AppCompatActivity {
    private static final String TAG_ID = "id_tanaman_user";
    private static final String TAG_NAMA = "nama_tanaman";
    private static final String TAG_GAMBAR = "foto_tanaman";
    private static final String TAG_COCOK_DI_MUSIM = "cocokdimusim";
    private static final String TAG_LAMA_PANEN = "lama_panen";
    private static final String TAG_TANGGAL_TANAM = "waktu_menanam";
    private static final String TAG_DESKRIPSI = "deskripsi";

    private String namaTanaman, urlGambar, tanggalDitanam, lamaMenujuPanen, lamaPanen, cocokDiMusim, deskripsi;
    private int lama_panenInt;
    private long hitungLamaPanen;
    private long hitungSelisihHari;
    public static int id_tanaman_user;
    private TextView textVnamaTanaman, textVlamaMenujuPanen, textVlamaPanen, textVcocokDiMusim, textVtitleDeskripsi, textVDeskripsiSingkat, textVTitiktitik, textVBacaSelengkapnya;
    private ImageView imageTanaman;
    private Toolbar toolbar;
    private HashMap<String, String> user;
    private String idUser;
    private TextView titleToolbar;
    FormBody formBody;
    private AlertDialogManager alert;
    private SessionManager session;
    private Button buttonPanenTanaman;
    private LinearLayout linearLayoutInfoCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_tanaman_user_sudah_tanam);
        id_tanaman_user = getIntent().getExtras().getInt("id_tanaman_user");
        createObjects();
        if(InternetCheck.isNetworkConnected(DetailTanamanUserSudahTanamActivity.this)){
            if (InternetCheck.isNetworkAvailable(DetailTanamanUserSudahTanamActivity.this)){
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
                                    int statusCode;
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
                                                tanggalDitanam = jObject.getString(TAG_TANGGAL_TANAM);

                                                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                                                Calendar c = Calendar.getInstance();

                                                String hariIniFormatted = dateFormat.format(c.getTime());
                                                hitungSelisihHari = 0;
                                                Date waktuMenanam = new Date();
                                                Date hariIni = new Date();
                                                try {
                                                    waktuMenanam = dateFormat.parse(tanggalDitanam);
                                                    hariIni = dateFormat.parse(hariIniFormatted);
                                                } catch (ParseException e) {
                                                    // TODO Auto-generated catch block
                                                    e.printStackTrace();
                                                }
                                                hitungSelisihHari = Math.abs(hariIni.getTime() - waktuMenanam.getTime());

                                                lama_panenInt = 0;

                                                try {
                                                    lama_panenInt = Integer.parseInt(jObject.getString(TAG_LAMA_PANEN));
                                                } catch(NumberFormatException nfe) {
                                                    System.out.println("Could not parse " + nfe);
                                                }

                                                hitungLamaPanen = lama_panenInt - TimeUnit.MILLISECONDS.toDays(hitungSelisihHari);

                                                if(hitungLamaPanen < 1){
                                                    lamaMenujuPanen = "Sudah panen";
                                                }
                                                else {
                                                    lamaMenujuPanen = String.valueOf(hitungLamaPanen) +" hari menuju panen";
                                                }
                                                deskripsi = jObject.getString(TAG_DESKRIPSI);
                                                lamaPanen= jObject.getString(TAG_LAMA_PANEN)+" hari";
                                                cocokDiMusim = jObject.getString(TAG_COCOK_DI_MUSIM);
                                                if(cocokDiMusim.equals("Hujan & Kemarau")){
                                                    cocokDiMusim = "Semua Musim";
                                                }
                                            }

                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }

                                    DetailTanamanUserSudahTanamActivity.this.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            initView();
                                            setToolbar(namaTanaman);
                                            textVnamaTanaman.setText(namaTanaman);
                                            Picasso.with(getBaseContext()).load(urlGambar).into(imageTanaman);
                                            textVlamaMenujuPanen.setText(lamaMenujuPanen);
                                            textVlamaPanen.setText(lamaPanen);
                                            textVcocokDiMusim.setText(cocokDiMusim);
                                            linearLayoutInfoCard.setVisibility(View.VISIBLE);
                                            textVDeskripsiSingkat.setText(deskripsi);
                                            textVtitleDeskripsi.setVisibility(View.VISIBLE);
                                            textVDeskripsiSingkat.setVisibility(View.VISIBLE);
                                            textVTitiktitik.setVisibility(View.VISIBLE);
                                            textVBacaSelengkapnya.setVisibility(View.VISIBLE);
                                            textVBacaSelengkapnya.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {
                                                    Bundle b = new Bundle();
                                                    Intent intent = new Intent(DetailTanamanUserSudahTanamActivity.this, DetailDeskripsiTanamanUserActivity.class);
                                                    b.putString("deskripsiTanaman",deskripsi);
                                                    intent.putExtras(b);
                                                    startActivity(intent);
                                                }
                                            });
                                            if (hitungLamaPanen < 1) {
                                                buttonPanenTanaman.setVisibility(View.VISIBLE);
                                                buttonPanenTanaman.setOnClickListener(new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View view) {
                                                        prosesPanenTanaman();
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_overflow_detailtanaman_user, menu);
        return true;
    }


    private void initView(){
        textVnamaTanaman = (TextView) findViewById(R.id.textv_namadetail_tanamanuser_sudahtanam);
        imageTanaman = (ImageView) findViewById(R.id.image_foto_detail_tanamanuser_sudahtanam);
        textVlamaMenujuPanen = (TextView) findViewById(R.id.textv_lama_menuju_panen_detailtanamanuser);
        textVlamaPanen = (TextView) findViewById(R.id.textv_lama_panen_tanaman_hari_tanamanuser);
        textVcocokDiMusim = (TextView) findViewById(R.id.textv_tanamancocokdimusim_tanamanuser);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        titleToolbar = (TextView) findViewById(R.id.title_toolbar);
        buttonPanenTanaman = (Button) findViewById(R.id.button_panen_tanamanuser);
        linearLayoutInfoCard = (LinearLayout) findViewById(R.id.linearlayout_info_detailtanaman_sudahtanam);

        textVtitleDeskripsi = (TextView) findViewById(R.id.text_deskripsititle_tanaman_detailsudahtanam);
        textVDeskripsiSingkat = (TextView) findViewById(R.id.textv_deskripsi_tanaman_sudahtanam);
        textVTitiktitik = (TextView) findViewById(R.id.text_titiktitik_detail_sudahtanam);
        textVBacaSelengkapnya = (TextView) findViewById(R.id.textv_baca_selengkapnya_sudahtanam);
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
                                    DetailTanamanUserSudahTanamActivity.this.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(DetailTanamanUserSudahTanamActivity.this, finalMessage, Toast.LENGTH_LONG).show();
                                            intent[0] = new Intent(DetailTanamanUserSudahTanamActivity.this, MainActivity.class);
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

    private void prosesPanenTanaman() {
        formBody = new FormBody.Builder()
                .add("id_user", idUser)
                .add("id_tanaman_user", String.valueOf(id_tanaman_user))
                .build();
        try {
            OkHttpRequest.postDataToServer(UrlApi.urlPanenTanamanUser,formBody).enqueue(new Callback() {
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
                        Log.i("getresponsepenentanaman", "response detail news success");

                        JSONObject jsonObject = new JSONObject(response.body().string());
                        statusCode = jsonObject.getInt("statusCode");
                        message = jsonObject.getString("message");
                        Log.i("getpenentanamanstatus", String.valueOf(statusCode));

                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    final String finalMessage = message;
                    final Intent[] intent = new Intent[1];
                    DetailTanamanUserSudahTanamActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(DetailTanamanUserSudahTanamActivity.this, finalMessage, Toast.LENGTH_LONG).show();
                            intent[0] = new Intent(DetailTanamanUserSudahTanamActivity.this, MainActivity.class);
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
}

