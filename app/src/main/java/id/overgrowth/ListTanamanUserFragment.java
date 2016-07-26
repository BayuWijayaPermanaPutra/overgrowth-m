package id.overgrowth;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import id.overgrowth.adapter.AdListTanamanUser;
import id.overgrowth.model.MTanamanUser;
import id.overgrowth.utility.DividerItem;
import id.overgrowth.utility.OkHttpRequest;
import id.overgrowth.utility.SessionManager;
import id.overgrowth.utility.UrlApi;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.RequestBody;
import okhttp3.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class ListTanamanUserFragment extends Fragment {
    private ArrayList<MTanamanUser> tanamanUserArrayList = new ArrayList<>();
    private RecyclerView recyclerView;
    private AdListTanamanUser adapter;
    private RequestBody requestBody;
    private HashMap<String, String> user;
    private String idUser;
    private SessionManager session;
    private FloatingActionButton fabMain;
    private FloatingActionButton fab1;
    private FloatingActionButton fab2;
    private TextView text_fab1;
    private TextView text_fab2;
    private boolean FAB_Status = false;
    Animation show_fab_1;
    Animation hide_fab_1;
    Animation show_fab_2;
    Animation hide_fab_2;
    Animation show_text_fab1;
    Animation hide_text_fab1;
    Animation show_text_fab2;
    Animation hide_text_fab2;

    Intent intent;
    ProgressDialog progressDialog;
    private boolean statusProgressDialog;

    public ListTanamanUserFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_list_tanaman_user, container, false);

        initView(rootView);
        setObject();
        if (session.isLoggedIn()){
            user = session.getUserDetails();
            idUser = user.get(SessionManager.KEY_IDUSER);
            getTanamanUser();
        }



        fabMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (FAB_Status == false) {
                    expandFAB();
                    expandTextFAB();
                    FAB_Status = true;
                } else {
                    hideFAB();
                    hideTextFAB();
                    FAB_Status = false;
                }
            }
        });
        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (FAB_Status == false) {
                    expandFAB();
                    expandTextFAB();
                    FAB_Status = true;
                } else {
                    hideFAB();
                    hideTextFAB();
                    FAB_Status = false;
                }
                intent = new Intent(getActivity(),PilihKategoriActivity.class);
                startActivity(intent);
            }
        });

        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (FAB_Status == false) {
                    expandFAB();
                    expandTextFAB();
                    FAB_Status = true;
                } else {
                    hideFAB();
                    hideTextFAB();
                    FAB_Status = false;
                }
                intent = new Intent(getActivity(),MulaiTanamPilihanActivity.class);
                startActivity(intent);
            }
        });
        return rootView;
    }

    private void initView(View rootView) {
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerviewtanamanuser);
        fabMain = (FloatingActionButton) rootView.findViewById(R.id.fab_add_tanaman_user);
        fab1 = (FloatingActionButton) rootView.findViewById(R.id.fab_1);
        fab2 = (FloatingActionButton) rootView.findViewById(R.id.fab_2);
        text_fab1 = (TextView) rootView.findViewById(R.id.text_pilih_langsung_fab);
        text_fab2 = (TextView) rootView.findViewById(R.id.text_rekomendasi_fab);
        //Animations
        show_fab_1 = AnimationUtils.loadAnimation(getActivity(), R.anim.fab1_show);
        hide_fab_1 = AnimationUtils.loadAnimation(getActivity(), R.anim.fab1_hide);
        show_fab_2 = AnimationUtils.loadAnimation(getActivity(), R.anim.fab2_show);
        hide_fab_2 = AnimationUtils.loadAnimation(getActivity(), R.anim.fab2_hide);

        show_text_fab1 = AnimationUtils.loadAnimation(getActivity(),R.anim.text_fab1_show);
        hide_text_fab1 = AnimationUtils.loadAnimation(getActivity(),R.anim.text_fab1_hide);
        show_text_fab2 = AnimationUtils.loadAnimation(getActivity(),R.anim.text_fab2_show);
        hide_text_fab2 = AnimationUtils.loadAnimation(getActivity(),R.anim.text_fab2_hide);

        progressDialog = new ProgressDialog(getActivity());
    }
    private void setObject(){
        session = new SessionManager(getActivity());
    }

    private void expandFAB(){
        //Floating Action Button 1
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) fab1.getLayoutParams();
        layoutParams.rightMargin += (int) (fab1.getWidth() * 1.0);
        layoutParams.bottomMargin += (int) (fab1.getHeight() * 0.25);
        fab1.setLayoutParams(layoutParams);
        fab1.startAnimation(show_fab_1);
        fab1.setClickable(true);

        //Floating Action Button 2
        FrameLayout.LayoutParams layoutParams2 = (FrameLayout.LayoutParams) fab2.getLayoutParams();
        layoutParams2.rightMargin += (int) (fab2.getWidth() * 0.5);
        layoutParams2.bottomMargin += (int) (fab2.getHeight() * 0.8);
        fab2.setLayoutParams(layoutParams2);
        fab2.startAnimation(show_fab_2);
        fab2.setClickable(true);
    }
    private void hideFAB(){
        //Floating Action Button 1
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) fab1.getLayoutParams();
        layoutParams.rightMargin -= (int) (fab1.getWidth() * 1.0);
        layoutParams.bottomMargin -= (int) (fab1.getHeight() * 0.25);
        fab1.setLayoutParams(layoutParams);
        fab1.startAnimation(hide_fab_1);
        fab1.setClickable(false);

        //Floating Action Button 2
        FrameLayout.LayoutParams layoutParams2 = (FrameLayout.LayoutParams) fab2.getLayoutParams();
        layoutParams2.rightMargin -= (int) (fab2.getWidth() * 0.5);
        layoutParams2.bottomMargin -= (int) (fab2.getHeight() * 0.8);
        fab2.setLayoutParams(layoutParams2);
        fab2.startAnimation(hide_fab_2);
        fab2.setClickable(false);
    }

    private void expandTextFAB(){
        //Floating Action Button 1
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) text_fab1.getLayoutParams();
        layoutParams.rightMargin += (int) (text_fab1.getWidth() * 2.0);
        layoutParams.bottomMargin += (int) (text_fab1.getHeight() * 1.3);
        text_fab1.setLayoutParams(layoutParams);
        text_fab1.startAnimation(show_fab_1);
        text_fab1.setClickable(true);

        //Floating Action Button 2
        FrameLayout.LayoutParams layoutParams2 = (FrameLayout.LayoutParams) text_fab2.getLayoutParams();
        layoutParams2.rightMargin += (int) (text_fab2.getWidth() * 1.0);
        layoutParams2.bottomMargin += (int) (text_fab2.getHeight() * 3.7);
        text_fab2.setLayoutParams(layoutParams2);
        text_fab2.startAnimation(show_fab_2);
        text_fab2.setClickable(true);
    }

    private void hideTextFAB(){
        //Floating Action Button 1
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) text_fab1.getLayoutParams();
        layoutParams.rightMargin -= (int) (text_fab1.getWidth() * 2.0);
        layoutParams.bottomMargin -= (int) (text_fab1.getHeight() * 1.3);
        text_fab1.setLayoutParams(layoutParams);
        text_fab1.startAnimation(hide_fab_1);
        text_fab1.setClickable(false);

        //Floating Action Button 2
        FrameLayout.LayoutParams layoutParams2 = (FrameLayout.LayoutParams) text_fab2.getLayoutParams();
        layoutParams2.rightMargin -= (int) (text_fab2.getWidth() * 1.0);
        layoutParams2.bottomMargin -= (int) (text_fab2.getHeight() * 3.7);
        text_fab2.setLayoutParams(layoutParams2);
        text_fab2.startAnimation(hide_fab_2);
        text_fab2.setClickable(false);
    }

    public void dummyTanamanUser(){
        tanamanUserArrayList.add(new MTanamanUser(1,1,"Pepaya","Buah",43,"Tanaman yang sehat","1. Tanam di atas tanah","pepaya.jpg","Hujan","",""));
        tanamanUserArrayList.add(new MTanamanUser(1,1,"Pepaya","Buah",43,"Tanaman yang sehat","1. Tanam di atas tanah","pepaya.jpg","Hujan","",""));
        tanamanUserArrayList.add(new MTanamanUser(1,1,"Pepaya","Buah",43,"Tanaman yang sehat","1. Tanam di atas tanah","pepaya.jpg","Hujan","",""));
        tanamanUserArrayList.add(new MTanamanUser(1,1,"Pepaya","Buah",43,"Tanaman yang sehat","1. Tanam di atas tanah","pepaya.jpg","Hujan","",""));
        tanamanUserArrayList.add(new MTanamanUser(1,1,"Pepaya","Buah",43,"Tanaman yang sehat","1. Tanam di atas tanah","pepaya.jpg","Hujan","",""));
        tanamanUserArrayList.add(new MTanamanUser(1,1,"Pepaya","Buah",43,"Tanaman yang sehat","1. Tanam di atas tanah","pepaya.jpg","Hujan","",""));
    }

    private void getTanamanUser(){
        Log.i("iduser:",idUser);

        progressDialog.setTitle("Load Data Tanaman Kamu");
        progressDialog.setMessage("Loading..");
        progressDialog.setIndeterminate(false);
        progressDialog.setCancelable(true);
        progressDialog.show();
        statusProgressDialog = true;
        Runnable progressRunnable = new Runnable() {
            @Override
            public void run() {
                progressDialog.cancel();
                if(statusProgressDialog){
                    showDialog();
                }
            }
        };
        Handler pdCanceller = new Handler();
        pdCanceller.postDelayed(progressRunnable, 22000);

        requestBody = new FormBody.Builder()
                .add("id_user", idUser)
                .build();

        try {
            OkHttpRequest.postDataToServer(UrlApi.urlTanamanUser, requestBody).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.e("Error : ", e.getMessage());
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    int statusCode = 0;
                    String pesan = null;
                    try {
                        Log.i("getdatanews", "response success");

                        JSONObject jsonObject = new JSONObject(response.body().string());
                        statusCode = jsonObject.getInt("statusCode");
                        pesan = jsonObject.getString("pesan");
                        if(pesan.equals("Kamu memiliki tanaman di galeri!")){
                            JSONArray jsonArray = jsonObject.getJSONArray("item");
                            for (int i = 0; i < jsonArray.length(); i++){
                                JSONObject jObject = jsonArray.getJSONObject(i);
                                MTanamanUser mTanamanUser = new MTanamanUser();
                                mTanamanUser.setIdTanamanUser(jObject.getInt("id_tanaman_user"));
                                mTanamanUser.setNamaTanaman(jObject.getString("nama_tanaman"));
                                mTanamanUser.setFotoTanaman(jObject.getString("foto_tanaman"));
                                mTanamanUser.setWaktuMenanam(jObject.getString("waktu_menanam"));
                                tanamanUserArrayList.add(mTanamanUser);
                            }

                        }
                        Log.i("getdatanews", String.valueOf(statusCode));


                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    final int finalStatusCode = statusCode;
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (statusProgressDialog == true){
                                progressDialog.dismiss();
                                statusProgressDialog = false;
                            }
                            if (finalStatusCode == 200) {
                                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getBaseContext()));
                                adapter = new AdListTanamanUser(tanamanUserArrayList,getActivity());
                                recyclerView.setAdapter(adapter);
                                recyclerView.setItemAnimator(new DefaultItemAnimator());
                                recyclerView.addItemDecoration(new DividerItem(getActivity()));
                                recyclerView.setHasFixedSize(true);
                                recyclerView.setOnTouchListener(new View.OnTouchListener() {
                                    @Override
                                    public boolean onTouch(View v, MotionEvent event) {
                                        if (FAB_Status) {
                                            hideFAB();
                                            hideTextFAB();
                                            FAB_Status = false;
                                        }
                                        return false;
                                    }
                                });
                            }

                        }
                    });
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void showDialog() {
        final AlertDialog.Builder popDialog = new AlertDialog.Builder(getActivity());
        final LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View Viewlayout = inflater.inflate(R.layout.dialog_coba_lagi,(ViewGroup) getActivity().findViewById(R.id.layout_dialog_coba_lagi));
        popDialog.setIcon(android.R.drawable.stat_notify_error);
        popDialog.setTitle("Gagal koneksi ke Internet");
        popDialog.setView(Viewlayout);
        popDialog.setCancelable(false);
        // Button OK
        popDialog.setPositiveButton(android.R.string.ok,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = getActivity().getIntent();
                        getActivity().finish();
                        startActivity(intent);
                    }
                })
                // Button Cancel
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                                getActivity().finish();
                            }
                        });
        popDialog.create();
        popDialog.show();
    }
}
