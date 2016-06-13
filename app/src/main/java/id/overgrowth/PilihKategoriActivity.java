package id.overgrowth;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class PilihKategoriActivity extends AppCompatActivity implements View.OnClickListener {
    private Toolbar toolbar;
    private RelativeLayout rlSayuran;
    private RelativeLayout rlBuah;
    private RelativeLayout rlHias;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pilih_kategori_activity);
        initView();
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
    }
    private void setupToolbar(){
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Pilih Kategori");
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.rl_kategori_sayuran :
                Toast.makeText(PilihKategoriActivity.this,"Sayuran",Toast.LENGTH_SHORT).show();
                intent = new Intent(PilihKategoriActivity.this, KategoriSayuranActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_kategori_buah :
                Toast.makeText(PilihKategoriActivity.this,"Buah",Toast.LENGTH_SHORT).show();
                intent = new Intent(PilihKategoriActivity.this, KategoriBuahActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_kategori_hias :
                Toast.makeText(PilihKategoriActivity.this,"Hias",Toast.LENGTH_SHORT).show();
                intent = new Intent(PilihKategoriActivity.this, KategoriHiasActivity.class);
                startActivity(intent);
                break;
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
