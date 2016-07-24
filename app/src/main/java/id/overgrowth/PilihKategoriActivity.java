package id.overgrowth;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import id.overgrowth.utility.AlertDialogManager;
import id.overgrowth.utility.InternetCheck;

public class PilihKategoriActivity extends AppCompatActivity implements View.OnClickListener {
    private Toolbar toolbar;
    private RelativeLayout rlSayuran;
    private RelativeLayout rlBuah;
    private RelativeLayout rlHias;
    private AlertDialogManager alert;
    private TextView titleToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pilih_kategori_activity);
        initView();
        alert = new AlertDialogManager();
        setupToolbar();
        rlSayuran.setOnClickListener(this);
        rlBuah.setOnClickListener(this);
        rlHias.setOnClickListener(this);
    }
    private void initView(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        rlSayuran = (RelativeLayout) findViewById(R.id.rl_kategori_sayuran);
        rlBuah = (RelativeLayout) findViewById(R.id.rl_kategori_buah);
        rlHias = (RelativeLayout) findViewById(R.id.rl_kategori_hias);
        titleToolbar = (TextView) findViewById(R.id.title_toolbar);
    }
    private void setupToolbar(){
        titleToolbar.setText("Pilih Kategori");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        Bundle b = new Bundle();
        if(InternetCheck.isNetworkConnected(PilihKategoriActivity.this)){
            if (InternetCheck.isNetworkAvailable(PilihKategoriActivity.this)){
                switch (view.getId()) {
                    case R.id.rl_kategori_sayuran :
                        intent = new Intent(PilihKategoriActivity.this, ListTanamanActivity.class);
                        b.putString("jenis_tanaman","Sayuran");
                        intent.putExtras(b);
                        startActivity(intent);
                        break;
                    case R.id.rl_kategori_buah :
                        intent = new Intent(PilihKategoriActivity.this, ListTanamanActivity.class);
                        b.putString("jenis_tanaman","Buah");
                        intent.putExtras(b);
                        startActivity(intent);
                        break;
                    case R.id.rl_kategori_hias :
                        intent = new Intent(PilihKategoriActivity.this, ListTanamanActivity.class);
                        b.putString("jenis_tanaman","Hias");
                        intent.putExtras(b);
                        startActivity(intent);
                        break;
                }
            } else {

                alert.showAlertDialog(this,"Error","Internet tidak bisa diakses!");
            }
        } else {
            alert.showAlertDialog(this,"Error","Tidak terkoneksi ke Internet!\nMohon nyalakan paket data atau koneksi WiFi!");
        }

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
