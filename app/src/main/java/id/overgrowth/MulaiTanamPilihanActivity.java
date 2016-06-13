package id.overgrowth;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MulaiTanamPilihanActivity extends AppCompatActivity {
    Toolbar toolbar;
    Button btnPilihTanaman;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mulai_tanam_pilihan);
        initView();
        setToolbar();
        btnPilihTanaman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MulaiTanamPilihanActivity.this,MulaiTanamRekomendasiActivity.class);
                startActivity(intent);
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
