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
    private TextView txtVNama, txtVCaraMenanam, txtVBacaSelengkapnya;
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
                initView();

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
                                                caraMenanam = Html.fromHtml(jObject.getString(TAG_CARA_MENANAM)).toString();
                                            }

                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }

                                    DetailTanamanUserBelumTanamActivity.this.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            setToolbar(namaTanaman);
                                            txtVNama.setText(namaTanaman);
                                            Picasso.with(getBaseContext()).load(urlGambar).into(imgVTanaman);
                                            txtVCaraMenanam.setText(caraMenanam);
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
        txtVBacaSelengkapnya = (TextView) findViewById(R.id.textv_baca_selengkapnya_detail_tanamanuser);
        btnTanamSekarang = (Button) findViewById(R.id.button_tanam_sekarang_detail_tanamanuser);
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

    private void prosesTanamTanaman(){

    }
}
