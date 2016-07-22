package id.overgrowth;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import id.overgrowth.adapter.AdListTanamanUser;
import id.overgrowth.model.MTanamanUser;
import id.overgrowth.utility.DividerItem;
import id.overgrowth.utility.OkHttpRequest;
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
    private View parentView;
    private RecyclerView recyclerView;
    private AdListTanamanUser adapter;
    private RequestBody requestBody;
    public ListTanamanUserFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dummyTanamanUser();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_list_tanaman_user, container, false);
        parentView = rootView;
        recyclerView = (RecyclerView) parentView.findViewById(R.id.recyclerviewtanamanuser);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getBaseContext()));

        adapter = new AdListTanamanUser(tanamanUserArrayList,getActivity());
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItem(getActivity()));
        recyclerView.setHasFixedSize(true);

        return rootView;
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
        Log.i("iduser:",String.valueOf(1));

        requestBody = new FormBody.Builder()
                .add("id_user", String.valueOf(1))
                .build();

        try {
            OkHttpRequest.postDataToServer(UrlApi.urlTanamanUser, requestBody).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.e("Error : ", e.getMessage());
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    int statusCode;
                    String pesan = null;
                    try {
                        Log.i("getdatanews", "response success");

                        JSONObject jsonObject = new JSONObject(response.body().string());
                        statusCode = jsonObject.getInt("statusCode");
                        pesan = jsonObject.getString("pesan");
                        Log.i("getdatanews", String.valueOf(statusCode));


                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    final String finalPesan = pesan;
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getActivity(), finalPesan, Toast.LENGTH_LONG).show();

                        }
                    });
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
