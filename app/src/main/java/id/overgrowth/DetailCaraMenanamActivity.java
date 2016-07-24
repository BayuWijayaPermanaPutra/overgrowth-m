package id.overgrowth;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

public class DetailCaraMenanamActivity extends AppCompatActivity {
    private TextView textVCaraMenanam;
    private Toolbar toolbar;
    private TextView titleToolbar;
    private String caraMenanam;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_cara_menanam);
        initView();
        setToolbar();
        caraMenanam = getIntent().getExtras().getString("caraMenanam");
        if(caraMenanam != null){
            textVCaraMenanam.setText(caraMenanam);
        }

    }
    private void initView(){
        textVCaraMenanam = (TextView) findViewById(R.id.textv_cara_menanam_detail_tanamanuser);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        titleToolbar = (TextView) findViewById(R.id.title_toolbar);
    }
    private void setToolbar(){
        setSupportActionBar(toolbar);
        titleToolbar.setText("Cara Menanam");
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
