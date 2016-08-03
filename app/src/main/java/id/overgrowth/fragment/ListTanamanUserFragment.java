package id.overgrowth.fragment;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import id.overgrowth.MulaiTanamPilihanActivity;
import id.overgrowth.PilihKategoriActivity;
import id.overgrowth.R;
import id.overgrowth.adapter.AdListTanamanUser;
import id.overgrowth.model.TanamanUser;
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
    private ArrayList<TanamanUser> tanamanUserArrayList = new ArrayList<>();
    private RecyclerView recyclerView;
    private AdListTanamanUser adapter;
    private RequestBody requestBody;
    private HashMap<String, String> user;
    private String idUser;
    private SessionManager session;
    private FloatingActionButton fab1;
    private FloatingActionButton fab2;
    private FrameLayout frameListTUser;
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
        final View rootView = inflater.inflate(R.layout.fragment_list_tanaman_user, container, false);

        initView(rootView);
        setObject();
        if (session.isLoggedIn()){
            user = session.getUserDetails();
            idUser = user.get(SessionManager.KEY_IDUSER);
            progressDialog.setTitle("Load Data Tanaman Kamu");
            progressDialog.setMessage("Loading..");
            progressDialog.setIndeterminate(false);
            progressDialog.setCancelable(false);
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
            getTanamanUser();
        }

        final FrameLayout frameLayout = (FrameLayout) rootView.findViewById(R.id.frame_layout_tanaman_user);
        frameLayout.getBackground().setAlpha(0);
        final FloatingActionsMenu fabMenu = (FloatingActionsMenu) rootView.findViewById(R.id.material_design_android_floating_action_menu);
        fabMenu.setOnFloatingActionsMenuUpdateListener(new FloatingActionsMenu.OnFloatingActionsMenuUpdateListener() {
            @Override
            public void onMenuExpanded() {
                frameLayout.getBackground().setAlpha(240);
                frameLayout.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        fabMenu.collapse();
                        return true;
                    }
                });
            }

            @Override
            public void onMenuCollapsed() {
                frameLayout.getBackground().setAlpha(0);
                frameLayout.setOnTouchListener(null);
            }
        });



        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fabMenu.collapse();
                intent = new Intent(getActivity(),PilihKategoriActivity.class);
                startActivity(intent);
            }
        });

        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fabMenu.collapse();
                intent = new Intent(getActivity(),MulaiTanamPilihanActivity.class);
                startActivity(intent);
            }
        });

        return rootView;
    }

    private void initView(View rootView) {
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerviewtanamanuser);
        fab1 = (FloatingActionButton) rootView.findViewById(R.id.material_design_floating_action_menu_item1);
        fab2 = (FloatingActionButton) rootView.findViewById(R.id.material_design_floating_action_menu_item2);

        frameListTUser = (FrameLayout) rootView.findViewById(R.id.frame_list_tanamanuser);
        progressDialog = new ProgressDialog(getActivity());
    }
    private void setObject(){
        session = new SessionManager(getActivity());
    }


    private void getTanamanUser(){
        Log.i("iduser:",idUser);

        requestBody = new FormBody.Builder()
                .add("id_user", idUser)
                .build();

        try {
            OkHttpRequest.postDataToServer(UrlApi.urlTanamanUser, requestBody).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    e.printStackTrace();
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
                                TanamanUser tanamanUser = new TanamanUser();
                                tanamanUser.setIdTanamanUser(jObject.getInt("id_tanaman_user"));
                                tanamanUser.setNamaTanaman(jObject.getString("nama_tanaman"));
                                tanamanUser.setFotoTanaman(jObject.getString("foto_tanaman"));
                                tanamanUser.setWaktuMenanam(jObject.getString("waktu_menanam"));
                                tanamanUserArrayList.add(tanamanUser);
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
        popDialog.setIcon(R.mipmap.ic_alert);
        popDialog.setTitle("Gagal Load Data Tanaman Kamu");
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
