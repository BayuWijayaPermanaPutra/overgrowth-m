package id.overgrowth;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import id.overgrowth.utility.AlertDialogManager;
import id.overgrowth.utility.InternetCheck;

public class MulaiTanamPilihanActivity extends AppCompatActivity {
    Toolbar toolbar;
    Button btnPilihTanaman;
    private AlertDialogManager alert;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mulai_tanam_pilihan);
        initView();
        setToolbar();
        alert = new AlertDialogManager();
        btnPilihTanaman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(InternetCheck.isNetworkConnected(MulaiTanamPilihanActivity.this)){
                    if (InternetCheck.isNetworkAvailable(MulaiTanamPilihanActivity.this)){
                        Intent intent = new Intent(MulaiTanamPilihanActivity.this,MulaiTanamRekomendasiActivity.class);
                        startActivity(intent);
                    } else {
                        alert.showAlertDialog(MulaiTanamPilihanActivity.this,"Error","Internet tidak bisa diakses!");
                    }
                } else {
                    alert.showAlertDialog(MulaiTanamPilihanActivity.this,"Error","Tidak terkoneksi ke Internet!\nMohon nyalakan paket data atau koneksi WiFi!");
                }

            }
        });
    }
    private void initView(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        btnPilihTanaman = (Button) findViewById(R.id.button_pilih_tanaman);
    }
    private void setToolbar(){
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Mulai Tanam");
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
