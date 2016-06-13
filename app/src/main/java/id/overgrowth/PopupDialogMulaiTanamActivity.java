package id.overgrowth;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class PopupDialogMulaiTanamActivity extends AppCompatActivity {
    private LinearLayout buttonLayoutMulaiTanam;
    private RelativeLayout buttonLayoutUnggahFoto;
    private Intent intent;
    private Button buttonUnggah;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup_dialog_mulai_tanam);

        initView();

        buttonLayoutUnggahFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(getApplication(),PilihKategoriActivity.class);
                startActivity(intent);
            }
        });

        buttonLayoutMulaiTanam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(getApplicationContext(),MulaiTanamPilihanActivity.class);
                startActivity(intent);
            }
        });

        buttonUnggah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(PopupDialogMulaiTanamActivity.this,PilihKategoriActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initView(){
        buttonLayoutMulaiTanam = (LinearLayout) findViewById(R.id.button_layout_mulai_menanam);
        buttonLayoutUnggahFoto = (RelativeLayout) findViewById(R.id.button_layout_unggah_tanaman);
        buttonUnggah = (Button) findViewById(R.id.button_unggah_foto_tanaman);
    }
}
