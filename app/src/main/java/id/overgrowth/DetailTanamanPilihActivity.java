package id.overgrowth;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

import id.overgrowth.utility.AlertDialogManager;
import id.overgrowth.utility.OkHttpRequest;
import id.overgrowth.utility.SessionManager;
import id.overgrowth.utility.UrlApi;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Response;

public class DetailTanamanPilihActivity extends AppCompatActivity {

    public static int id_tanaman = 0;
    private Toolbar toolbar;
    private String nama;
    private String cocokDiMusim;
    private int lamaPanen;
    private String urlGambar;
    private String deskripsi, caraMenanam;

    private TextView txtVNama, txtVCocokDiMusim, txtVLamaPanen, txtVDeskripsi, txtVBacaSelengkapnya;
    private ImageView imageVGambar;
    private Button buttonPilih;
    FormBody formBody;
    private SessionManager session;
    private HashMap<String, String> user;
    private String idUser;
    private static final String TAG_ID = "id_tanaman";
    private static final String TAG_NAMA = "nama_tanaman";
    private static final String TAG_GAMBAR = "foto_tanaman";
    private static final String TAG_COCOK_DI_MUSIM = "cocokdimusim";
    private static final String TAG_LAMA_PANEN = "lama_panen";
    private static final String TAG_DESKRIPSI = "deskripsi";
    private static final String TAG_CARA_MENANAM = "cara_menanam";
    private AlertDialogManager alert;
    private TextView titleToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_tanaman_pilih);
        initView();
        setToolbar();
        session = new SessionManager(getBaseContext());
        alert = new AlertDialogManager();
        id_tanaman = getIntent().getExtras().getInt("id_tanaman");
        if (session.isLoggedIn()) {
            user = session.getUserDetails();
            idUser = user.get(SessionManager.KEY_IDUSER);
        }
        if(id_tanaman != 0) {
            formBody = new FormBody.Builder()
                    .add("id_user", idUser)
                    .add("id_tanaman", String.valueOf(id_tanaman))
                    .build();
            try {
                OkHttpRequest.postDataToServer(UrlApi.urlTanaman,formBody).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        e.printStackTrace();
                        Log.e("getdatanews", e.getMessage());
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        int statusCode;
                        try {
                            String result = response.body().string();
                            Log.i("Hasil JSON : ", result);
                            Log.i("getdatanews", "response detail news success");

                            JSONObject jsonObject = new JSONObject(result);
                            statusCode = jsonObject.getInt("statusCode");
                            Log.i("getdatanews", String.valueOf(statusCode));
                            if (statusCode == 200) {
                                JSONArray jsonArray = jsonObject.getJSONArray("item");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jObject = jsonArray.getJSONObject(i);

                                    Log.i("get ID detail datanews", jObject.getString(TAG_ID));
                                    Log.i("getdatatanaman", jObject.getString(TAG_NAMA));

                                    urlGambar = UrlApi.urlGambarTanaman+jObject.getString(TAG_GAMBAR);
                                    nama = jObject.getString(TAG_NAMA);
                                    lamaPanen = jObject.getInt(TAG_LAMA_PANEN);
                                    deskripsi = jObject.getString(TAG_DESKRIPSI);
                                    caraMenanam = jObject.getString(TAG_CARA_MENANAM);
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

                        DetailTanamanPilihActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                txtVNama.setText(nama);
                                Picasso.with(getBaseContext()).load(urlGambar).into(imageVGambar);
                                txtVLamaPanen.setText(String.valueOf(lamaPanen)+" hari");
                                txtVCocokDiMusim.setText(cocokDiMusim);
                                String desShow = deskripsi + ".<br> Cara Menanam : " + caraMenanam;
                                txtVDeskripsi.setText(Html.fromHtml(desShow.replaceAll("\n","<br>")));
                                buttonPilih.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        prosesPilihTanaman();
                                    }
                                });

                                txtVBacaSelengkapnya.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Bundle b = new Bundle();
                                        Intent intent = new Intent(DetailTanamanPilihActivity.this, DetailDeskripsiTanamanActivity.class);
                                        b.putString("deskripsiTanaman",deskripsi);
                                        b.putString("caraMenanam",caraMenanam);
                                        intent.putExtras(b);
                                        startActivity(intent);
                                    }
                                });
                            }
                        });
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }


    private void initView(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        titleToolbar = (TextView) findViewById(R.id.title_toolbar);
        txtVNama = (TextView) findViewById(R.id.textv_namadetail_pilih);
        imageVGambar = (ImageView) findViewById(R.id.image_foto_detailtanaman_pilih);

        txtVLamaPanen = (TextView) findViewById(R.id.textv_lama_panen_tanaman_hari_pilih);
        txtVCocokDiMusim = (TextView) findViewById(R.id.textv_tanamancocokdimusim_pilih);
        txtVDeskripsi = (TextView) findViewById(R.id.textv_deskripsi_tanaman_pilih);
        txtVBacaSelengkapnya = (TextView) findViewById(R.id.textv_baca_selengkapnya_pilih);
        buttonPilih = (Button) findViewById(R.id.button_pilih_tanaman_detail_pilih);
    }

    private void setToolbar(){
        setSupportActionBar(toolbar);
        titleToolbar.setText("Detail Tanaman");
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

    private void prosesPilihTanaman(){
        formBody = new FormBody.Builder()
                .add("id_user", idUser)
                .add("id_tanaman", String.valueOf(id_tanaman))
                .build();
        try {
            OkHttpRequest.postDataToServer(UrlApi.urlPilihTanaman,formBody).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    e.printStackTrace();
                    Log.e("errorpilihtanaman", e.getMessage());
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    int statusCode;
                    String message = null;
                    try {
                        Log.i("getdatapilihtanaman", "response detail news success");

                        JSONObject jsonObject = new JSONObject(response.body().string());
                        statusCode = jsonObject.getInt("statusCode");
                        message = jsonObject.getString("message");
                        Log.i("getpilihtanamanstatus", String.valueOf(statusCode));

                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    final String finalMessage = message;
                    final Intent[] intent = new Intent[1];
                    DetailTanamanPilihActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(DetailTanamanPilihActivity.this, finalMessage, Toast.LENGTH_LONG).show();
                            intent[0] = new Intent(DetailTanamanPilihActivity.this, MainActivity.class);
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
