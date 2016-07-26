package id.overgrowth.adapter;

import android.content.Context;
import android.content.Intent;
import android.opengl.Visibility;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import id.overgrowth.DetailTanamanPilihActivity;
import id.overgrowth.DetailTanamanUserBelumTanamActivity;
import id.overgrowth.DetailTanamanUserSudahTanamActivity;
import id.overgrowth.MainActivity;
import id.overgrowth.R;
import id.overgrowth.model.MTanamanUser;
import id.overgrowth.utility.OkHttpRequest;
import id.overgrowth.utility.SessionManager;
import id.overgrowth.utility.UrlApi;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Response;

/**
 * Created by bayu_ on 7/20/2016.
 */
public class AdListTanamanUser extends RecyclerView.Adapter<AdListTanamanUser.ViewHolder> {
    private ArrayList<MTanamanUser> arrayTanamanUser;
    private Context context;
    FormBody formBody;
    private SessionManager session;
    private HashMap<String, String> user;

    private String idUser;

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
        if (tanaman.getWaktuMenanam() == "null"){
            holder.buttonTanam.setVisibility(View.VISIBLE);
        } else {
            holder.buttonHapus.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return arrayTanamanUser.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView namaTanaman;
        ImageView imageTanaman;
        Button buttonTanam,buttonHapus;

        public ViewHolder(View itemView) {
            super(itemView);
            context = itemView.getContext();
            imageTanaman = (ImageView) itemView.findViewById(R.id.image_foto_tanaman_user);
            namaTanaman = (TextView) itemView.findViewById(R.id.textv_nama_tanaman_user);
            buttonTanam = (Button) itemView.findViewById(R.id.button_tanam_tanaman_user);
            buttonHapus = (Button) itemView.findViewById(R.id.button_hapus_tanaman_user);

            session = new SessionManager(context);

            if (session.isLoggedIn()){
                user = session.getUserDetails();
                idUser = user.get(SessionManager.KEY_IDUSER);
            }
/*
            idTanamanUser = arrayTanamanUser.get(getAdapterPosition()).getIdTanamanUser();
*/
            final Intent[] intent = new Intent[1];
            final Bundle b = new Bundle();


            buttonTanam.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    prosesTanamTanaman();
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(arrayTanamanUser.get(getAdapterPosition()).getWaktuMenanam()=="null"){
                        intent[0] = new Intent(view.getContext(), DetailTanamanUserBelumTanamActivity.class);
                        DetailTanamanUserBelumTanamActivity.id_tanaman_user = arrayTanamanUser.get(getAdapterPosition()).getIdTanamanUser();
                    } else {
                        intent[0] = new Intent(view.getContext(), DetailTanamanUserSudahTanamActivity.class);
                        DetailTanamanUserSudahTanamActivity.id_tanaman_user = arrayTanamanUser.get(getAdapterPosition()).getIdTanamanUser();
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
                    } else {
                        intent[0] = new Intent(view.getContext(), DetailTanamanUserSudahTanamActivity.class);
                        DetailTanamanUserSudahTanamActivity.id_tanaman_user = arrayTanamanUser.get(getAdapterPosition()).getIdTanamanUser();
                    }
                    b.putInt("id_tanaman_user",arrayTanamanUser.get(getAdapterPosition()).getIdTanamanUser());
                    intent[0].putExtras(b);
                    context.startActivity(intent[0]);
                    return true;
                }
            });
        }
    }

    private void prosesTanamTanaman(){
        /*
        formBody = new FormBody.Builder()
                .add("id_user", idUser)
                .add("id_tanaman_user", String.valueOf(idTanamanUser))
                .build();
        try {
            OkHttpRequest.postDataToServer(UrlApi.urlTanamTanamanUser,formBody).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    e.printStackTrace();
                    Log.e("errortanamtanaman", e.getMessage());
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    int statusCode;
                    String message = null;
                    try {
                        Log.i("getdatatanamtanaman", "response detail tanaman success");

                        JSONObject jsonObject = new JSONObject(response.body().string());
                        statusCode = jsonObject.getInt("statusCode");
                        message = jsonObject.getString("message");
                        Log.i("gettanamtanamanstatus", String.valueOf(statusCode));

                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    final String finalMessage = message;
                    final Intent[] intent = new Intent[1];
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(DetailTanamanUserBelumTanamActivity.this, finalMessage, Toast.LENGTH_LONG).show();
                            intent[0] = new Intent(DetailTanamanUserBelumTanamActivity.this, MainActivity.class);
                            startActivity(intent[0]);
                            finish();
                        }
                    });
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        */
    }
}
