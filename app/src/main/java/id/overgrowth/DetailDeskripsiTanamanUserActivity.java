package id.overgrowth;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

public class DetailDeskripsiTanamanUserActivity extends AppCompatActivity {
    private TextView textVdeskripsiTanaman;
    private TextView titleToolbar;
    private Toolbar toolbar;
    public static String deskripsiTanaman = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_deskripsi_tanaman_user);
        initView();
        setToolbar();
        deskripsiTanaman = getIntent().getExtras().getString("deskripsiTanaman");
        textVdeskripsiTanaman.setText(deskripsiTanaman);
    }

    private void initView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        titleToolbar = (TextView) findViewById(R.id.title_toolbar);
        textVdeskripsiTanaman = (TextView) findViewById(R.id.textv_deskripsi_tanaman_user_detail);
    }
    private void setToolbar(){
        setSupportActionBar(toolbar);
        titleToolbar.setText("Deskripsi Tanaman");
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
