package id.overgrowth;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

import id.overgrowth.utility.AlarmReceiver;
import id.overgrowth.utility.SessionManager;

public class PengaturanActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TextView titleToolbar;
    private Switch switchAlarm;
    private SessionManager session;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pengaturan);
        createObject();
        initView();
        setToolbar();

        if(session.alarmAktif()) {
            //set the switch to ON
            switchAlarm.setChecked(true);
        } else {
            switchAlarm.setChecked(false);
        }
        setSwitchListener();


        //check the current state before we display the screen
        if(switchAlarm.isChecked()){
            session.setAlarm();
        }
        else {
            session.unsetAlarm();
        }
    }
    private void initView(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        titleToolbar = (TextView) findViewById(R.id.title_toolbar);
        switchAlarm = (Switch) findViewById(R.id.switch_alarm_setting);
    }
    private void createObject(){
        session = new SessionManager(PengaturanActivity.this);
    }

    private void setToolbar(){
        setSupportActionBar(toolbar);
        titleToolbar.setText("Pengaturan");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void setSwitchListener(){

        //attach a listener to check for changes in state
        switchAlarm.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                if(isChecked){
                    Toast.makeText(PengaturanActivity.this, "Alarm pengingat siram tanaman ON!", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(PengaturanActivity.this, "Alarm pengingat siram tanaman OFF!", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
