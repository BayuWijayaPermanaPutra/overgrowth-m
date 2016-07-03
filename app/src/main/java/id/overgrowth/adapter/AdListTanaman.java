package id.overgrowth.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import id.overgrowth.DetailTanamanPilihActivity;
import id.overgrowth.R;
import id.overgrowth.model.MTanaman;

/**
 * Created by bayu_wpp on 5/28/2016.
 */
public class AdListTanaman extends RecyclerView.Adapter<AdListTanaman.ViewHolder> {
    private ArrayList<MTanaman> arrayBuah;
    private Context context;
    private final String urlPhoto = "http://sharingdisini.com/wp-content/uploads/2012/11/";

    public AdListTanaman(ArrayList<MTanaman> arrayBuah, Context context) {
        this.arrayBuah = arrayBuah;
        this.context = context;
    }

    @Override
    public AdListTanaman.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_list_tanaman,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(AdListTanaman.ViewHolder holder, int position) {
        MTanaman tanaman = arrayBuah.get(position);
        Context context = ((ViewHolder) holder).imageBuah.getContext();
        Picasso.with(context).load(urlPhoto+tanaman.getFototanaman()).into(((ViewHolder)holder).imageBuah);
        holder.namaBuah.setText(tanaman.getNamatanaman());
    }

    @Override
    public int getItemCount() {
        return arrayBuah.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView namaBuah;
        ImageView imageBuah;
        public ViewHolder(View itemView) {
            super(itemView);
            context = itemView.getContext();
            imageBuah = (ImageView) itemView.findViewById(R.id.image_foto_kategori_buah);
            namaBuah = (TextView) itemView.findViewById(R.id.textv_namabuah_kategori);

            final Intent[] intent = new Intent[1];
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    intent[0] = new Intent(view.getContext(), DetailTanamanPilihActivity.class);
                    context.startActivity(intent[0]);
                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    intent[0] = new Intent(view.getContext(),DetailTanamanPilihActivity.class);
                    context.startActivity(intent[0]);
                    return true;
                }
            });
        }
    }
}
