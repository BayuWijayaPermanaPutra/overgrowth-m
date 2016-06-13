package id.overgrowth;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import java.util.ArrayList;

import id.overgrowth.adapter.AdKategoriTanamanHias;
import id.overgrowth.model.MTanaman;
import id.overgrowth.utility.DividerItem;

public class KategoriHiasActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private ArrayList<MTanaman> arrayHias = new ArrayList<>();
    private RecyclerView rvTanamanHias;
    private AdKategoriTanamanHias adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kategori_hias);
        initView();
        setToolbar();
        adapter = new AdKategoriTanamanHias(arrayHias,this);
        rvTanamanHias.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        rvTanamanHias.setAdapter(adapter);
        rvTanamanHias.setItemAnimator(new DefaultItemAnimator());
        rvTanamanHias.setHasFixedSize(true);
        rvTanamanHias.addItemDecoration(new DividerItem(this));
        dataDummy();
    }
    private void initView(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        rvTanamanHias = (RecyclerView) findViewById(R.id.recyclerview_kategorihias);
    }
    private void setToolbar(){
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Tanaman Hias");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    private void dataDummy(){
        arrayHias.add(new MTanaman(1,"Daisy","Daisy","Nama latinnya","Hias",9,160,"chinese-evergreen.png"));
        arrayHias.add(new MTanaman(2,"Daisy","Daisy","Nama latinnya","Hias",9,160,"chinese-evergreen.png"));
        arrayHias.add(new MTanaman(3,"Daisy","Daisy","Nama latinnya","Hias",9,160,"chinese-evergreen.png"));
        arrayHias.add(new MTanaman(4,"Daisy","Daisy","Nama latinnya","Hias",9,160,"chinese-evergreen.png"));
        arrayHias.add(new MTanaman(5,"Daisy","Daisy","Nama latinnya","Hias",9,160,"chinese-evergreen.png"));
        arrayHias.add(new MTanaman(6,"Daisy","Daisy","Nama latinnya","Hias",9,160,"chinese-evergreen.png"));
        arrayHias.add(new MTanaman(7,"Daisy","Daisy","Nama latinnya","Hias",9,160,"chinese-evergreen.png"));
        arrayHias.add(new MTanaman(8,"Daisy","Daisy","Nama latinnya","Hias",9,160,"chinese-evergreen.png"));
        arrayHias.add(new MTanaman(9,"Daisy","Daisy","Nama latinnya","Hias",9,160,"chinese-evergreen.png"));
        arrayHias.add(new MTanaman(10,"Daisy","Daisy","Nama latinnya","Hias",9,160,"chinese-evergreen.png"));
        arrayHias.add(new MTanaman(11,"Daisy","Daisy","Nama latinnya","Hias",9,160,"chinese-evergreen.png"));
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
