package id.overgrowth;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import id.overgrowth.adapter.AdTanamanRekomendasi;
import id.overgrowth.model.MTanah;
import id.overgrowth.model.MTanaman;
import id.overgrowth.utility.AlertDialogManager;
import id.overgrowth.utility.DividerItem;
import id.overgrowth.utility.OkHttpRequest;
import id.overgrowth.utility.RemoteWeatherFetch;
import id.overgrowth.utility.SessionManager;
import id.overgrowth.utility.UrlApi;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MulaiTanamRekomendasiActivity extends AppCompatActivity {
    private ArrayList<MTanaman> tanamanList = new ArrayList<>();
    AlertDialogManager adm = new AlertDialogManager();
    private RecyclerView rvTanaman;
    private AdTanamanRekomendasi adapter;
    private FloatingActionButton fab_info_tanah;
    private Toolbar toolbar;
    private Handler handler;
    private Double suhu;
    private String cuaca;
    private RequestBody requestBody;
    private ProgressDialog progressDialog;
    private AlertDialogManager alert;
    private SessionManager session;
    private HashMap<String, String> user;
    private String idUser;
    private TextView titleToolbar;
    private boolean statusProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mulai_tanam_rekomendasi);
        initView();
        setToolbar();

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Rekomendasi Tanaman berdasarkan Suhu Cuaca");
        progressDialog.setMessage("Loading..");
        progressDialog.setIndeterminate(false);
        progressDialog.setCancelable(true);

        fab_info_tanah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MTanah mTanah = new MTanah(1,"Info Tanah","Coming Soon on the Next Updates");
                adm.showAlertDialog(MulaiTanamRekomendasiActivity.this,mTanah.getNama_tanah(),mTanah.getDeskripsi_tanah());
            }
        });


        if (session.isLoggedIn()){
            user = session.getUserDetails();
            idUser = user.get(SessionManager.KEY_IDUSER);
            cuaca = "Hujan";
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
            pdCanceller.postDelayed(progressRunnable, 20000);//20 detik
            //updateWeatherData();
            getTanamanRekomendasi();
        }

    }

    private void initView() {

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        titleToolbar = (TextView) findViewById(R.id.title_toolbar);
        rvTanaman = (RecyclerView) findViewById(R.id.recyclerview_rekomendasitanaman);
        fab_info_tanah = (FloatingActionButton) findViewById(R.id.fab_info_tanah);
        alert = new AlertDialogManager();
        session = new SessionManager(getBaseContext());

    }

    private void setToolbar() {
        setSupportActionBar(toolbar);
        titleToolbar.setText("Mulai Tanam");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void dataDummy() {
        tanamanList.add(new MTanaman(1,"Chinese Evergreen","Sayuran",2,"Tanaman Langka","chinese-evergreen.png","Cara menanamnya disini","Kemarau"));
        tanamanList.add(new MTanaman(2,"Aloevera","Hias",2,"Tanaman Langka","chinese-evergreen.png","Cara menanamnya disini","Kemarau"));
        tanamanList.add(new MTanaman(3,"Aloevera","Hias",2,"Tanaman Langka","chinese-evergreen.png","Cara menanamnya disini","Kemarau"));
    }

    private void getTanamanRekomendasi() {
        Log.i("iduser:",idUser);
        Log.i("cuaca:",cuaca);

        requestBody = new FormBody.Builder()
                .add("id_user", idUser)
                .add("cuaca_musim_saat_ini", cuaca)
                .build();

        try {
            OkHttpRequest.postDataToServer(UrlApi.urlRekomendasiTanaman, requestBody).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.e("Error : ", e.getMessage());
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    int statusCode;
                    try {
                        Log.i("getdatanews", "response success");

                        JSONObject jsonObject = new JSONObject(response.body().string());
                        statusCode = jsonObject.getInt("statusCode");
                        Log.i("getdatanews", String.valueOf(statusCode));
                        if (statusCode == 200) {
                            JSONArray jsonArray = jsonObject.getJSONArray("item");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jObject = jsonArray.getJSONObject(i);
                                MTanaman mTanaman = new MTanaman();
                                Log.i("getdatanews", jObject.getString("nama_tanaman"));
                                mTanaman.setIdtanaman(jObject.getInt("id_tanaman"));
                                mTanaman.setNamatanaman(jObject.getString("nama_tanaman"));
                                mTanaman.setJenistanaman(jObject.getString("jenis_tanaman"));
                                mTanaman.setLamapanen(jObject.getInt("lama_panen"));
                                mTanaman.setDeskripsi(jObject.getString("deskripsi"));
                                mTanaman.setFototanaman(jObject.getString("foto_tanaman"));
                                mTanaman.setCaramenanam(jObject.getString("cara_menanam"));
                                mTanaman.setCocokdimusim(jObject.getString("cocokdimusim"));
                                tanamanList.add(mTanaman);
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    MulaiTanamRekomendasiActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            progressDialog.dismiss();
                            rvTanaman.setLayoutManager(new LinearLayoutManager(MulaiTanamRekomendasiActivity.this));
                            adapter = new AdTanamanRekomendasi(tanamanList,MulaiTanamRekomendasiActivity.this);
                            rvTanaman.setAdapter(adapter);
                            rvTanaman.setHasFixedSize(true);
                            rvTanaman.setItemAnimator(new DefaultItemAnimator());
                            rvTanaman.addItemDecoration(new DividerItem(MulaiTanamRekomendasiActivity.this));
                        }
                    });
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void updateWeatherData(){
        new Thread(){
            public void run(){
                final JSONObject json = RemoteWeatherFetch.getJSON(MulaiTanamRekomendasiActivity.this);
                if(json == null){
                    handler.post(new Runnable(){
                        public void run(){
                            Toast.makeText(MulaiTanamRekomendasiActivity.this, MulaiTanamRekomendasiActivity.this.getString(R.string.place_not_found), Toast.LENGTH_LONG).show();
                        }
                    });
                } else {
                    handler.post(new Runnable(){
                        public void run(){
                            renderWeather(json);
                        }
                    });
                }
            }
        }.start();
    }

    private void renderWeather(JSONObject json){
        try {
            JSONObject details = json.getJSONArray("weather").getJSONObject(0);
            JSONObject main = json.getJSONObject("main");
            suhu = main.getDouble("temp") - 273.15; // satuan K ke ℃
            if((suhu < 23) && (suhu > 19)) {
                cuaca = "Hujan & Kemarau";
            } else if((suhu > 20) && (suhu < 35)) {
                cuaca = "Kemarau";
            }
            setWeatherIcon(details.getInt("id"),
                    json.getJSONObject("sys").getLong("sunrise") * 1000,
                    json.getJSONObject("sys").getLong("sunset") * 1000);

        }catch(Exception e){
            Log.e("SimpleWeather", "One or more fields not found in the JSON data");
        }
    }

    private void setWeatherIcon(int actualId, long sunrise, long sunset){
        int id = actualId / 100;
        cuaca = "";
        if(actualId == 800){
            long currentTime = new Date().getTime();
            if(currentTime>=sunrise && currentTime<sunset) {
                cuaca = "Kemarau";
            } else {
                cuaca = "Kemarau";
            }
        } else {
            switch(id) {
                case 2 : cuaca = "Hujan"; //petir
                    break;
                case 3 : cuaca = "Hujan"; // gerimis
                    break;
                case 7 : cuaca = "Hujan";//berkabut
                    break;
                case 8 : cuaca = "Hujan";//berawan
                    break;
                case 6 : cuaca = "Hujan";//salju
                    break;
                case 5 : cuaca = "Hujan";//hujan
                    break;
            }
        }
    }
    private void showDialog() {
        final AlertDialog.Builder popDialog = new AlertDialog.Builder(this);
        final LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View Viewlayout = inflater.inflate(R.layout.dialog_coba_lagi,(ViewGroup) findViewById(R.id.layout_dialog_coba_lagi));
        popDialog.setIcon(android.R.drawable.stat_notify_error);
        popDialog.setTitle("Gagal mendapatkan rekomendasi tanaman");
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
