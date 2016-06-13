package id.overgrowth.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import id.overgrowth.R;
import id.overgrowth.model.MTanaman;

/**
 * Created by bayu_wpp on 5/28/2016.
 */
public class AdKategoriTanamanHias extends RecyclerView.Adapter<AdKategoriTanamanHias.ViewHolder> {
    private ArrayList<MTanaman> arrayHias;
    private Context context;
    private final String urlPhoto = "http://sharingdisini.com/wp-content/uploads/2012/11/";

    public AdKategoriTanamanHias(ArrayList<MTanaman> arrayHias, Context context) {
        this.arrayHias = arrayHias;
        this.context = context;
    }

    @Override
    public AdKategoriTanamanHias.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_list_kategori_hias,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(AdKategoriTanamanHias.ViewHolder holder, int position) {
        MTanaman tanaman = arrayHias.get(position);
        Context context = ((ViewHolder) holder).imageHias.getContext();
        Picasso.with(context).load(urlPhoto+tanaman.getFototanaman()).into(((ViewHolder) holder).imageHias);
        holder.namaHias.setText(tanaman.getNamatanaman());
    }

    @Override
    public int getItemCount() {
        return arrayHias.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageHias;
        TextView namaHias;
        public ViewHolder(View itemView) {
            super(itemView);
            imageHias = (ImageView) itemView.findViewById(R.id.image_foto_kategori_hias);
            namaHias = (TextView) itemView.findViewById(R.id.textv_namahias_kategori);
        }
    }
}
