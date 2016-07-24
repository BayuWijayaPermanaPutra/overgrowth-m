package id.overgrowth.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import id.overgrowth.DetailTanamanPilihActivity;
import id.overgrowth.DetailTanamanUserBelumTanamActivity;
import id.overgrowth.R;
import id.overgrowth.model.MTanamanUser;
import id.overgrowth.utility.UrlApi;

/**
 * Created by bayu_ on 7/20/2016.
 */
public class AdListTanamanUser extends RecyclerView.Adapter<AdListTanamanUser.ViewHolder> {
    private ArrayList<MTanamanUser> arrayTanamanUser;
    private Context context;

    public AdListTanamanUser(ArrayList<MTanamanUser> arrayBuah, Context context) {
        this.arrayTanamanUser = arrayBuah;
        this.context = context;
    }

    @Override
    public AdListTanamanUser.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_list_tanaman_user,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(AdListTanamanUser.ViewHolder holder, int position) {
        MTanamanUser tanaman = arrayTanamanUser.get(position);
        Context context = ((ViewHolder) holder).imageTanaman.getContext();
        Picasso.with(context).load(UrlApi.urlGambarTanaman+tanaman.getFotoTanaman()).into(((ViewHolder)holder).imageTanaman);
        holder.namaTanaman.setText(tanaman.getNamaTanaman());
    }

    @Override
    public int getItemCount() {
        return arrayTanamanUser.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView namaTanaman;
        ImageView imageTanaman;
        public ViewHolder(View itemView) {
            super(itemView);
            context = itemView.getContext();
            imageTanaman = (ImageView) itemView.findViewById(R.id.image_foto_tanaman_user);
            namaTanaman = (TextView) itemView.findViewById(R.id.textv_nama_tanaman_user);


            final Intent[] intent = new Intent[1];
            final Bundle b = new Bundle();
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(arrayTanamanUser.get(getAdapterPosition()).getWaktuMenanam()=="null"){
                        intent[0] = new Intent(view.getContext(), DetailTanamanUserBelumTanamActivity.class);
                        DetailTanamanUserBelumTanamActivity.id_tanaman_user = arrayTanamanUser.get(getAdapterPosition()).getIdTanamanUser();
                    }
                    b.putInt("id_tanaman_user",arrayTanamanUser.get(getAdapterPosition()).getIdTanamanUser());
                    intent[0].putExtras(b);
                    context.startActivity(intent[0]);
                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    if(arrayTanamanUser.get(getAdapterPosition()).getWaktuMenanam()=="null"){
                        intent[0] = new Intent(view.getContext(),DetailTanamanUserBelumTanamActivity.class);
                        DetailTanamanUserBelumTanamActivity.id_tanaman_user = arrayTanamanUser.get(getAdapterPosition()).getIdTanamanUser();
                    }
                    b.putInt("id_tanaman_user",arrayTanamanUser.get(getAdapterPosition()).getIdTanamanUser());
                    intent[0].putExtras(b);
                    context.startActivity(intent[0]);
                    return true;
                }
            });
        }
    }
}
