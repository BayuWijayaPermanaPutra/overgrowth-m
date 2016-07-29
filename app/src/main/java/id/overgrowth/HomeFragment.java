package id.overgrowth;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListAdapter;

import id.overgrowth.adapter.AdArrayWithIcon;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    Button buttonMulai;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        buttonMulai = (Button) rootView.findViewById(R.id.button_mulai);
        //button popup wind
        buttonMulai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDialogPopUp();
            }
        });
        return rootView;
    }

    private void setDialogPopUp() {
        final String [] items = new String[] {"Pilih tanaman langsung", "Lihat rekomendasi"};
        final Integer[] icons = new Integer[] {R.mipmap.ic_plant, R.mipmap.ic_toolsiram};
        ListAdapter adapter = new AdArrayWithIcon(getActivity(), items, icons);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setAdapter(adapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent;
                switch (i) {
                    case 0 :
                        intent = new Intent(getActivity(),PilihKategoriActivity.class);
                        startActivity(intent);
                        break;
                    case 1 :
                        intent = new Intent(getActivity(),MulaiTanamPilihanActivity.class);
                        startActivity(intent);
                        break;
                }
            }
        });
        builder.create();
        builder.show();
    }

}
