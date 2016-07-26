package id.overgrowth;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

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

public class DetailTanamanUserSudahTanamActivity extends AppCompatActivity {
    private static final String TAG_ID = "id_tanaman_user";
    private static final String TAG_NAMA = "nama_tanaman";
    private static final String TAG_GAMBAR = "foto_tanaman";
    private static final String TAG_COCOK_DI_MUSIM = "cocokdimusim";
    private static final String TAG_LAMA_PANEN = "lama_panen";
    private String namaTanaman, urlGambar, lamaMenujuPanen, lamaPanen, cocokDiMusim;

    public static int id_tanaman_user;
    private TextView textVnamaTanaman, textVlamaMenujuPanen, textVlamaPanen, textVcocokDiMusim;
    private ImageView imageTanaman;
    private Toolbar toolbar;
    private HashMap<String, String> user;
    private String idUser;
    private TextView titleToolbar;
    FormBody formBody;
    private AlertDialogManager alert;
    private SessionManager session;

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
                                                lamaMenujuPanen = jObject.getString(TAG_LAMA_PANEN)+" hari menuju panen";
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


    private void initView(){
        textVnamaTanaman = (TextView) findViewById(R.id.textv_namadetail_tanamanuser_sudahtanam);
        imageTanaman = (ImageView) findViewById(R.id.image_foto_detail_tanamanuser_sudahtanam);
        textVlamaMenujuPanen = (TextView) findViewById(R.id.textv_lama_menuju_panen_detailtanamanuser);
        textVlamaPanen = (TextView) findViewById(R.id.textv_lama_panen_tanaman_hari_tanamanuser);
        textVcocokDiMusim = (TextView) findViewById(R.id.textv_tanamancocokdimusim_tanamanuser);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        titleToolbar = (TextView) findViewById(R.id.title_toolbar);
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
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
