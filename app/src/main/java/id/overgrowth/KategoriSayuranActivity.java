package id.overgrowth;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import java.util.ArrayList;

import id.overgrowth.adapter.AdKategoriTanamanSayuran;
import id.overgrowth.model.MTanaman;
import id.overgrowth.utility.DividerItem;

public class KategoriSayuranActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private RecyclerView rvSayuran;
    private ArrayList<MTanaman> arrayListSayuran = new ArrayList<>();
    private AdKategoriTanamanSayuran adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kategori_sayuran);
        initView();
        setToolbar();
        adapter = new AdKategoriTanamanSayuran(arrayListSayuran,this);
        rvSayuran.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        rvSayuran.setHasFixedSize(true);
        rvSayuran.setAdapter(adapter);
        rvSayuran.setItemAnimator(new DefaultItemAnimator());
        rvSayuran.addItemDecoration(new DividerItem(this));
        dataDummy();
    }

    private void initView(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        rvSayuran = (RecyclerView) findViewById(R.id.recyclerview_kategorisayuran);

    }
    private void setToolbar(){
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Sayuran");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void dataDummy(){
        arrayListSayuran.add(new MTanaman(1,"Kangkung","Kale","Nama latinnya","Sayuran",8,140,"chinese-evergreen.png"));
        arrayListSayuran.add(new MTanaman(2,"Kangkung","Kale","Nama latinnya","Sayuran",8,140,"chinese-evergreen.png"));
        arrayListSayuran.add(new MTanaman(3,"Kangkung","Kale","Nama latinnya","Sayuran",8,140,"chinese-evergreen.png"));
        arrayListSayuran.add(new MTanaman(4,"Kangkung","Kale","Nama latinnya","Sayuran",8,140,"chinese-evergreen.png"));
        arrayListSayuran.add(new MTanaman(5,"Kangkung","Kale","Nama latinnya","Sayuran",8,140,"chinese-evergreen.png"));
        arrayListSayuran.add(new MTanaman(6,"Kangkung","Kale","Nama latinnya","Sayuran",8,140,"chinese-evergreen.png"));
        arrayListSayuran.add(new MTanaman(7,"Kangkung","Kale","Nama latinnya","Sayuran",8,140,"chinese-evergreen.png"));
        arrayListSayuran.add(new MTanaman(8,"Kangkung","Kale","Nama latinnya","Sayuran",8,140,"chinese-evergreen.png"));
        arrayListSayuran.add(new MTanaman(9,"Kangkung","Kale","Nama latinnya","Sayuran",8,140,"chinese-evergreen.png"));
        arrayListSayuran.add(new MTanaman(10,"Kangkung","Kale","Nama latinnya","Sayuran",8,140,"chinese-evergreen.png"));
        arrayListSayuran.add(new MTanaman(11,"Kangkung","Kale","Nama latinnya","Sayuran",8,140,"chinese-evergreen.png"));
        arrayListSayuran.add(new MTanaman(12,"Kangkung","Kale","Nama latinnya","Sayuran",8,140,"chinese-evergreen.png"));
        arrayListSayuran.add(new MTanaman(13,"Kangkung","Kale","Nama latinnya","Sayuran",8,140,"chinese-evergreen.png"));
        arrayListSayuran.add(new MTanaman(14,"Kangkung","Kale","Nama latinnya","Sayuran",8,140,"chinese-evergreen.png"));
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
