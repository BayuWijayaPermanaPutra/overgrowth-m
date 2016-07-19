package id.overgrowth;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import id.overgrowth.adapter.AdListTanaman;
import id.overgrowth.model.MTanaman;
import id.overgrowth.utility.AlertDialogManager;
import id.overgrowth.utility.DividerItem;
import id.overgrowth.utility.OkHttpRequest;
import id.overgrowth.utility.SessionManager;
import id.overgrowth.utility.UrlApi;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ListTanamanActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private RecyclerView rvListTanaman;
    private ArrayList<MTanaman> arrayTanaman = new ArrayList<>();
    private AdListTanaman adapterTanaman;
    public static String jenis_tanaman = null;
    private SessionManager session;
    private HashMap<String, String> user;
    private String idUser;
    private ProgressDialog progressDialog;
    private AlertDialogManager alert;
    private RequestBody requestBody;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_tanaman);
        initView();

        jenis_tanaman = getIntent().getExtras().getString("jenis_tanaman");
        setToolbar(jenis_tanaman);

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("List Tanaman "+jenis_tanaman);
        progressDialog.setMessage("Loading..");
        progressDialog.setIndeterminate(false);
        progressDialog.setCancelable(false);

        if (session.isLoggedIn()){
            user = session.getUserDetails();
            idUser = user.get(SessionManager.KEY_IDUSER);
            progressDialog.show();
            getListKategoriTanaman();
        }

    }

    private void initView(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        rvListTanaman = (RecyclerView) findViewById(R.id.recyclerview_list_tanaman);
        alert = new AlertDialogManager();
        session = new SessionManager(getBaseContext());
    }
    private void setToolbar(String title){
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    private void dataDummy(){
        arrayTanaman.add(new MTanaman(1,"Mangga","Buah","2016-15-2016","2","Tanaman Langka","chinese-evergreen.png","Kemarau"));
        arrayTanaman.add(new MTanaman(1,"Mangga","Buah","2016-15-2016","2","Tanaman Langka","chinese-evergreen.png","Kemarau"));
        arrayTanaman.add(new MTanaman(1,"Mangga","Buah","2016-15-2016","2","Tanaman Langka","chinese-evergreen.png","Kemarau"));
        arrayTanaman.add(new MTanaman(1,"Mangga","Buah","2016-15-2016","2","Tanaman Langka","chinese-evergreen.png","Kemarau"));
        arrayTanaman.add(new MTanaman(1,"Mangga","Buah","2016-15-2016","2","Tanaman Langka","chinese-evergreen.png","Kemarau"));
        arrayTanaman.add(new MTanaman(1,"Mangga","Buah","2016-15-2016","2","Tanaman Langka","chinese-evergreen.png","Kemarau"));
        arrayTanaman.add(new MTanaman(1,"Mangga","Buah","2016-15-2016","2","Tanaman Langka","chinese-evergreen.png","Kemarau"));
        arrayTanaman.add(new MTanaman(1,"Mangga","Buah","2016-15-2016","2","Tanaman Langka","chinese-evergreen.png","Kemarau"));
        arrayTanaman.add(new MTanaman(1,"Mangga","Buah","2016-15-2016","2","Tanaman Langka","chinese-evergreen.png","Kemarau"));
        arrayTanaman.add(new MTanaman(1,"Mangga","Buah","2016-15-2016","2","Tanaman Langka","chinese-evergreen.png","Kemarau"));
        arrayTanaman.add(new MTanaman(1,"Mangga","Buah","2016-15-2016","2","Tanaman Langka","chinese-evergreen.png","Kemarau"));
        arrayTanaman.add(new MTanaman(1,"Mangga","Buah","2016-15-2016","2","Tanaman Langka","chinese-evergreen.png","Kemarau"));
        arrayTanaman.add(new MTanaman(1,"Mangga","Buah","2016-15-2016","2","Tanaman Langka","chinese-evergreen.png","Kemarau"));
        arrayTanaman.add(new MTanaman(1,"Mangga","Buah","2016-15-2016","2","Tanaman Langka","chinese-evergreen.png","Kemarau"));
        arrayTanaman.add(new MTanaman(1,"Mangga","Buah","2016-15-2016","2","Tanaman Langka","chinese-evergreen.png","Kemarau"));
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

    private void getListKategoriTanaman() {
        Log.i("iduser:",idUser);
        Log.i("jenis_tanaman:",jenis_tanaman);

        requestBody = new FormBody.Builder()
                .add("id_user", idUser)
                .add("jenis_tanaman", jenis_tanaman)
                .build();

        try {
            OkHttpRequest.postDataToServer(UrlApi.urlTanaman, requestBody).enqueue(new Callback() {
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
                                Log.i("getdatalistkategori", jObject.getString("nama_tanaman"));
                                mTanaman.setIdtanaman(jObject.getInt("id_tanaman"));
                                mTanaman.setNamatanaman(jObject.getString("nama_tanaman"));
                                mTanaman.setJenistanaman(jObject.getString("jenis_tanaman"));
                                mTanaman.setAwalpanen(jObject.getString("awal_panen"));
                                mTanaman.setLamapanen(jObject.getString("lama_panen"));
                                mTanaman.setDeskripsi(jObject.getString("deskripsi"));
                                mTanaman.setFototanaman(jObject.getString("foto_tanaman"));
                                mTanaman.setCocokdimusim(jObject.getString("cocokdimusim"));
                                arrayTanaman.add(mTanaman);
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    ListTanamanActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            progressDialog.dismiss();
                            rvListTanaman.setLayoutManager(new LinearLayoutManager(ListTanamanActivity.this));
                            adapterTanaman = new AdListTanaman(arrayTanaman,ListTanamanActivity.this);
                            rvListTanaman.setAdapter(adapterTanaman);
                            rvListTanaman.setHasFixedSize(true);
                            rvListTanaman.setItemAnimator(new DefaultItemAnimator());
                            rvListTanaman.addItemDecoration(new DividerItem(ListTanamanActivity.this));
                        }
                    });
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
