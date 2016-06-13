package id.overgrowth;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.view.ViewGroup.LayoutParams;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    DrawerLayout drawer;
    NavigationView navigationView;
    Toolbar mToolbar;
    Button buttonMulai;

    Button buttonUnggahFoto;
    Button buttonMulaiMenanam;

    PopupWindow popupWindow;
    LinearLayout layoutOfPopup;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, mToolbar, R.string.drawer_open, R.string.drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.openDrawer(GravityCompat.START);
            }
        });
        //header
        View header = LayoutInflater.from(this).inflate(R.layout.nav_header,null);
        navigationView.addHeaderView(header);

        //button popup window
        buttonMulai.setOnClickListener(this);

    }
    private void initView() {
        navigationView = (NavigationView) findViewById(R.id.fragment_navigation_drawer);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        buttonMulai = (Button) findViewById(R.id.button_mulai);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        else {
            finish();
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.button_mulai) {
            setPopup();
        }
    }

    private void setPopup() {
        final AlertDialog.Builder popDialog = new AlertDialog.Builder(this);
        final LayoutInflater inflater = (LayoutInflater) this.getSystemService(LAYOUT_INFLATER_SERVICE);
        final View Viewlayout = inflater.inflate(R.layout.activity_popup_dialog_mulai_tanam, (ViewGroup) findViewById(R.id.layout_popup_mulaitanaman));
        popDialog.setView(Viewlayout);
        popDialog.create();
        popDialog.show();
    }

    private void setPopupWindow(){
        buttonUnggahFoto = new Button(this);
        Spannable buttonUnggahLabel = new SpannableString("Unggah Foto Tanaman");
        //buttonUnggahLabel.setSpan(new ImageSpan(getApplicationContext(), R.mipmap.ic_plant, ImageSpan.ALIGN_BOTTOM), 0, , Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        buttonUnggahFoto.setBackgroundColor(getResources().getColor(R.color.white));
        buttonUnggahFoto.setText(buttonUnggahLabel);

        buttonMulaiMenanam = new Button(this);
        Spannable buttonMenanamLabel = new SpannableString("Mulai Menanaman");
        //buttonMenanamLabel.setSpan(new ImageSpan(getApplicationContext(), R.mipmap.ic_toolsiram, ImageSpan.ALIGN_BOTTOM), 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        buttonMulaiMenanam.setBackgroundColor(getResources().getColor(R.color.white));
        buttonMulaiMenanam.setText(buttonMenanamLabel);

        layoutOfPopup = new LinearLayout(this);
        layoutOfPopup.setOrientation(LinearLayout.VERTICAL);
        layoutOfPopup.setBackgroundColor(getResources().getColor(R.color.white));
        LayoutParams paramsButton = new LayoutParams(300,
                LayoutParams.WRAP_CONTENT);
        layoutOfPopup.addView(buttonUnggahFoto);
        layoutOfPopup.addView(buttonMulaiMenanam);

        popupWindow = new PopupWindow(layoutOfPopup,LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
        popupWindow.setContentView(layoutOfPopup);
    }
}
