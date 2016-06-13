package id.overgrowth;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import java.util.ArrayList;

import id.overgrowth.adapter.AdKategoriTanamanBuah;
import id.overgrowth.model.MTanaman;
import id.overgrowth.utility.DividerItem;

public class KategoriBuahActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private RecyclerView rvKategoriBuah;
    private ArrayList<MTanaman> arrayBuah = new ArrayList<>();
    private AdKategoriTanamanBuah adapterBuah;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kategori_buah);
        initView();
        setToolbar();
        adapterBuah = new AdKategoriTanamanBuah(arrayBuah,this);
        rvKategoriBuah.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        rvKategoriBuah.setAdapter(adapterBuah);
        rvKategoriBuah.setHasFixedSize(true);
        rvKategoriBuah.setItemAnimator(new DefaultItemAnimator());
        rvKategoriBuah.addItemDecoration(new DividerItem(this));
        dataDummy();
    }

    private void initView(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        rvKategoriBuah = (RecyclerView) findViewById(R.id.recyclerview_kategoribuah);

    }
    private void setToolbar(){
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Buah");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    private void dataDummy(){
        arrayBuah.add(new MTanaman(1,"Mangga","Manggo","LatinMangga","Buah",8,140,"chinese-evergreen.png"));
        arrayBuah.add(new MTanaman(2,"Mangga","Manggo","LatinMangga","Buah",8,140,"chinese-evergreen.png"));
        arrayBuah.add(new MTanaman(3,"Mangga","Manggo","LatinMangga","Buah",8,140,"chinese-evergreen.png"));
        arrayBuah.add(new MTanaman(4,"Mangga","Manggo","LatinMangga","Buah",8,140,"chinese-evergreen.png"));
        arrayBuah.add(new MTanaman(5,"Mangga","Manggo","LatinMangga","Buah",8,140,"chinese-evergreen.png"));
        arrayBuah.add(new MTanaman(6,"Mangga","Manggo","LatinMangga","Buah",8,140,"chinese-evergreen.png"));
        arrayBuah.add(new MTanaman(7,"Mangga","Manggo","LatinMangga","Buah",8,140,"chinese-evergreen.png"));
        arrayBuah.add(new MTanaman(8,"Mangga","Manggo","LatinMangga","Buah",8,140,"chinese-evergreen.png"));
        arrayBuah.add(new MTanaman(9,"Mangga","Manggo","LatinMangga","Buah",8,140,"chinese-evergreen.png"));
        arrayBuah.add(new MTanaman(10,"Mangga","Manggo","LatinMangga","Buah",8,140,"chinese-evergreen.png"));
        arrayBuah.add(new MTanaman(11,"Mangga","Manggo","LatinMangga","Buah",8,140,"chinese-evergreen.png"));
        arrayBuah.add(new MTanaman(12,"Mangga","Manggo","LatinMangga","Buah",8,140,"chinese-evergreen.png"));
        arrayBuah.add(new MTanaman(13,"Mangga","Manggo","LatinMangga","Buah",8,140,"chinese-evergreen.png"));
        arrayBuah.add(new MTanaman(14,"Mangga","Manggo","LatinMangga","Buah",8,140,"chinese-evergreen.png"));
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
