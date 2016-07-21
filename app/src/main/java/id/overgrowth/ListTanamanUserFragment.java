package id.overgrowth;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import id.overgrowth.adapter.AdListTanamanUser;
import id.overgrowth.model.MTanamanUser;


/**
 * A simple {@link Fragment} subclass.
 */
public class ListTanamanUserFragment extends Fragment {
    private ArrayList<MTanamanUser> tanamanUserArrayList = new ArrayList<>();
    private View parentView;
    private RecyclerView recyclerView;
    private AdListTanamanUser adapter;

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
        recyclerView.setHasFixedSize(true);

        return rootView;
    }

    public void dummyTanamanUser(){
        tanamanUserArrayList.add(new MTanamanUser(1,1,"Pepaya","Buah",43,"Tanaman yang sehat","foto.png","Hujan","",""));
        tanamanUserArrayList.add(new MTanamanUser(1,1,"Pepaya","Buah",43,"Tanaman yang sehat","foto.png","Hujan","",""));
        tanamanUserArrayList.add(new MTanamanUser(1,1,"Pepaya","Buah",43,"Tanaman yang sehat","foto.png","Hujan","",""));
        tanamanUserArrayList.add(new MTanamanUser(1,1,"Pepaya","Buah",43,"Tanaman yang sehat","foto.png","Hujan","",""));
        tanamanUserArrayList.add(new MTanamanUser(1,1,"Pepaya","Buah",43,"Tanaman yang sehat","foto.png","Hujan","",""));
        tanamanUserArrayList.add(new MTanamanUser(1,1,"Pepaya","Buah",43,"Tanaman yang sehat","foto.png","Hujan","",""));
    }
}
