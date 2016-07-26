package id.overgrowth;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.MenuItem;
import android.widget.TextView;

public class DetailDeskripsiTanamanActivity extends AppCompatActivity {
    private TextView textVdeskripsiTanaman;
    private TextView textVcaraMenanam;
    public static String deskripsiTanaman = null;
    public static String caraMenanam = null;
    private TextView titleToolbar;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_deskripsi_tanaman);
        initView();
        setToolbar();
        deskripsiTanaman = getIntent().getExtras().getString("deskripsiTanaman");
        deskripsiTanaman.replaceAll("\n","<br />");
        caraMenanam = getIntent().getExtras().getString("caraMenanam");
        caraMenanam.replaceAll("\n","<br />");
        textVdeskripsiTanaman.setText(Html.fromHtml(deskripsiTanaman));
        textVcaraMenanam.setText(Html.fromHtml(caraMenanam));
    }

    private void initView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        titleToolbar = (TextView) findViewById(R.id.title_toolbar);
        textVcaraMenanam = (TextView) findViewById(R.id.textv_cara_menanam_detail);
        textVdeskripsiTanaman = (TextView) findViewById(R.id.textv_deskripsi_tanaman_detail);
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
