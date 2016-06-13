package id.overgrowth;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.LinkedList;

import id.overgrowth.adapter.AdTanamanRekomendasi;
import id.overgrowth.model.MTanah;
import id.overgrowth.model.MTanaman;
import id.overgrowth.utility.DividerItem;
import id.overgrowth.utility.AlertDialogManager;

public class MulaiTanamRekomendasiActivity extends AppCompatActivity {
    private LinkedList<MTanaman> tanamanArrayList = new LinkedList<>();
    AlertDialogManager adm = new AlertDialogManager();
    private RecyclerView rvTanaman;
    private AdTanamanRekomendasi adapter;
    private FloatingActionButton fab_info_tanah;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mulai_tanam_rekomendasi);
        initView();
        rvTanaman.setLayoutManager(new LinearLayoutManager(this.getBaseContext()));
        adapter = new AdTanamanRekomendasi(tanamanArrayList,this);
        rvTanaman.setAdapter(adapter);
        rvTanaman.setItemAnimator(new DefaultItemAnimator());
        rvTanaman.setHasFixedSize(true);
        rvTanaman.addItemDecoration(new DividerItem(this));
        dataDummy();

        fab_info_tanah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MTanah mTanah = new MTanah(1,"Tanah Regosol","Tanah regosol, adalah tanah dengan ciri-ciri antara lain: kasar, teksturnya berbutir, warna sedikit abu-abu hingga kekuningan, mengandung bahan organik dalam jumlah yang sedikit.");
                adm.showAlertDialog(MulaiTanamRekomendasiActivity.this,mTanah.getNama_tanah(),mTanah.getDeskripsi_tanah());
            }
        });
    }

    private void initView(){
        rvTanaman = (RecyclerView) findViewById(R.id.recyclerview_rekomendasitanaman);
        fab_info_tanah = (FloatingActionButton) findViewById(R.id.fab_info_tanah);
    }

    public void dataDummy() {
        tanamanArrayList.addLast(new MTanaman(1,"Chinese Evergreen","Chinese Evergreen","Nama latinnya","Sayuran",8,400,"chinese-evergreen.png"));
        tanamanArrayList.addLast(new MTanaman(2,"Aloevera","Aloevera","Nama latinnya","Hias",8,350,"aloevera.png"));
        tanamanArrayList.addLast(new MTanaman(3,"Aloevera","Aleovera","Nama latinnya","Hias",8,350,"aloevera.png"));
    }
}
