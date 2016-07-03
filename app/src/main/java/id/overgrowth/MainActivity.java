package id.overgrowth;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.PopupWindow;
import android.view.ViewGroup.LayoutParams;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.HashMap;

import id.overgrowth.adapter.AdArrayWithIcon;
import id.overgrowth.utility.SessionManager;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    DrawerLayout drawer;
    NavigationView navigationView;
    Toolbar mToolbar;
    Button buttonMulai;
    private SessionManager session;
    private HashMap<String, String> user;
    private ImageView fotoUser;
    private TextView namaUser;
    private TextView emailUser;
    private TextView logout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        if(!session.isLoggedIn()){
            Intent login = new Intent(this, LoginActivity.class);
            startActivity(login);

            finish();
            return;
        }


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

        if (session.isLoggedIn()){
            user = session.getUserDetails();
            fotoUser = (ImageView) findViewById(R.id.image_foto_header);
            emailUser = (TextView) findViewById(R.id.txt_email_header);
            namaUser = (TextView) findViewById(R.id.txt_nama_header);
            logout = (TextView) findViewById(R.id.txt_button_logout_header);

            Picasso.with(getBaseContext()).load(user.get(SessionManager.KEY_URL_FOTO_USER)).into(fotoUser);
            namaUser.setText(user.get(SessionManager.KEY_NAMAUSER));
            emailUser.setText(user.get(SessionManager.KEY_EMAILUSER));
            logout.setOnClickListener(this);
        }
        //button popup window
        buttonMulai.setOnClickListener(this);

    }
    private void initView() {
        navigationView = (NavigationView) findViewById(R.id.fragment_navigation_drawer);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        buttonMulai = (Button) findViewById(R.id.button_mulai);

        session = new SessionManager(this);
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
        switch (view.getId()) {
            case R.id.button_mulai : setDialogPopUp();
                break;
            case R.id.txt_button_logout_header : logoutUserAccount();
                break;
            default: break;
        }
    }

    private void logoutUserAccount() {
        session.logoutUser();
    }

    private void setDialogPopUp() {
        final String [] items = new String[] {"Unggah Foto Tanaman", "Mulai Menanam"};
        final Integer[] icons = new Integer[] {R.mipmap.ic_plant, R.mipmap.ic_toolsiram};
        ListAdapter adapter = new AdArrayWithIcon(this, items, icons);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setAdapter(adapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent;
                switch (i) {
                    case 0 :
                        intent = new Intent(getApplication(),PilihKategoriActivity.class);
                        startActivity(intent);
                        break;
                    case 1 :
                        intent = new Intent(getApplication(),MulaiTanamPilihanActivity.class);
                        startActivity(intent);
                        break;
                }
            }
        });
        builder.create();
        builder.show();
    }
}
