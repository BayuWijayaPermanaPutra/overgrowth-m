package id.overgrowth.adapter;

import android.content.Context;
import android.support.v7.internal.widget.ActivityChooserView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;

import id.overgrowth.R;
import id.overgrowth.model.MTanaman;

/**
 * Created by bayu_wpp on 5/28/2016.
 */
public class AdKategoriTanamanSayuran extends RecyclerView.Adapter<AdKategoriTanamanSayuran.ViewHolder>{
    private ArrayList<MTanaman> kategoriSayuran;
    private Context context;
    private final String urlPhoto = "http://sharingdisini.com/wp-content/uploads/2012/11/";

    public AdKategoriTanamanSayuran(ArrayList<MTanaman> kategoriSayuran, Context context) {
        this.kategoriSayuran = kategoriSayuran;
        this.context = context;
    }

    @Override
    public AdKategoriTanamanSayuran.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_list_kategori_sayuran,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AdKategoriTanamanSayuran.ViewHolder holder, int position) {
        MTanaman tanaman = kategoriSayuran.get(position);
        holder.namaSayuran.setText(tanaman.getNamatanaman());
        Context context = ((ViewHolder) holder).imageSayuran.getContext();
        Picasso.with(context).load(urlPhoto+tanaman.getFototanaman()).into(((ViewHolder) holder).imageSayuran);
    }

    @Override
    public int getItemCount() {
        return kategoriSayuran.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageSayuran;
        TextView namaSayuran;
        public ViewHolder(View itemView) {
            super(itemView);
            imageSayuran = (ImageView) itemView.findViewById(R.id.image_foto_kategori_sayuran);
            namaSayuran = (TextView) itemView.findViewById(R.id.textv_namasayuran_kategori);
        }
    }
}
