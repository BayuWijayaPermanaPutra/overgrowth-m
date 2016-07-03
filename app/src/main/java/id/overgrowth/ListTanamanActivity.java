package id.overgrowth;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.Date;

import id.overgrowth.adapter.AdListTanaman;
import id.overgrowth.model.MTanaman;
import id.overgrowth.utility.DividerItem;

public class ListTanamanActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private RecyclerView rvListTanaman;
    private ArrayList<MTanaman> arrayTanaman = new ArrayList<>();
    private AdListTanaman adapterTanaman;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_tanaman);
        initView();
        setToolbar();
        adapterTanaman = new AdListTanaman(arrayTanaman,this);
        rvListTanaman.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        rvListTanaman.setAdapter(adapterTanaman);
        rvListTanaman.setHasFixedSize(true);
        rvListTanaman.setItemAnimator(new DefaultItemAnimator());
        rvListTanaman.addItemDecoration(new DividerItem(this));
        dataDummy();
    }

    private void initView(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        rvListTanaman = (RecyclerView) findViewById(R.id.recyclerview_list_tanaman);
    }
    private void setToolbar(){
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Buah");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    private void dataDummy(){
        arrayTanaman.add(new MTanaman(1,"Mangga","Buah","2016-15-2016","2","chinese-evergreen.png","Kemarau"));
        arrayTanaman.add(new MTanaman(1,"Mangga","Buah","2016-15-2016","2","chinese-evergreen.png","Kemarau"));
        arrayTanaman.add(new MTanaman(1,"Mangga","Buah","2016-15-2016","2","chinese-evergreen.png","Kemarau"));
        arrayTanaman.add(new MTanaman(1,"Mangga","Buah","2016-15-2016","2","chinese-evergreen.png","Kemarau"));
        arrayTanaman.add(new MTanaman(1,"Mangga","Buah","2016-15-2016","2","chinese-evergreen.png","Kemarau"));
        arrayTanaman.add(new MTanaman(1,"Mangga","Buah","2016-15-2016","2","chinese-evergreen.png","Kemarau"));
        arrayTanaman.add(new MTanaman(1,"Mangga","Buah","2016-15-2016","2","chinese-evergreen.png","Kemarau"));
        arrayTanaman.add(new MTanaman(1,"Mangga","Buah","2016-15-2016","2","chinese-evergreen.png","Kemarau"));
        arrayTanaman.add(new MTanaman(1,"Mangga","Buah","2016-15-2016","2","chinese-evergreen.png","Kemarau"));
        arrayTanaman.add(new MTanaman(1,"Mangga","Buah","2016-15-2016","2","chinese-evergreen.png","Kemarau"));
        arrayTanaman.add(new MTanaman(1,"Mangga","Buah","2016-15-2016","2","chinese-evergreen.png","Kemarau"));
        arrayTanaman.add(new MTanaman(1,"Mangga","Buah","2016-15-2016","2","chinese-evergreen.png","Kemarau"));
        arrayTanaman.add(new MTanaman(1,"Mangga","Buah","2016-15-2016","2","chinese-evergreen.png","Kemarau"));
        arrayTanaman.add(new MTanaman(1,"Mangga","Buah","2016-15-2016","2","chinese-evergreen.png","Kemarau"));
        arrayTanaman.add(new MTanaman(1,"Mangga","Buah","2016-15-2016","2","chinese-evergreen.png","Kemarau"));
        arrayTanaman.add(new MTanaman(1,"Mangga","Buah","2016-15-2016","2","chinese-evergreen.png","Kemarau"));
        arrayTanaman.add(new MTanaman(1,"Mangga","Buah","2016-15-2016","2","chinese-evergreen.png","Kemarau"));
        arrayTanaman.add(new MTanaman(1,"Mangga","Buah","2016-15-2016","2","chinese-evergreen.png","Kemarau"));
        arrayTanaman.add(new MTanaman(1,"Mangga","Buah","2016-15-2016","2","chinese-evergreen.png","Kemarau"));
        arrayTanaman.add(new MTanaman(1,"Mangga","Buah","2016-15-2016","2","chinese-evergreen.png","Kemarau"));
        arrayTanaman.add(new MTanaman(1,"Mangga","Buah","2016-15-2016","2","chinese-evergreen.png","Kemarau"));
        arrayTanaman.add(new MTanaman(1,"Mangga","Buah","2016-15-2016","2","chinese-evergreen.png","Kemarau"));
        arrayTanaman.add(new MTanaman(1,"Mangga","Buah","2016-15-2016","2","chinese-evergreen.png","Kemarau"));
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
