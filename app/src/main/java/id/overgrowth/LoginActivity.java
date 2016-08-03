package id.overgrowth;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import id.overgrowth.utility.InternetCheck;
import id.overgrowth.utility.OkHttpRequest;
import id.overgrowth.utility.SessionManager;
import id.overgrowth.utility.UrlApi;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener, View.OnClickListener {
    private static final int RC_SIGN_IN = 1;
    private GoogleSignInOptions gso;
    private GoogleApiClient mGoogleApiClient;
    private SignInButton buttonSignIn;
    private String id_user,email,nama,url_foto;
    private SessionManager session;
    private Intent intent;
    private ProgressDialog progressDialog;
    private RequestBody requestBody;
    private boolean statusProgressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        inisialisasi();
        buttonSignIn.setOnClickListener(this);
    }

    private void inisialisasi(){
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        buttonSignIn = (SignInButton) findViewById(R.id.button_sign_in_googleplus);
        buttonSignIn.setSize(SignInButton.SIZE_STANDARD);
        buttonSignIn.setScopes(gso.getScopeArray());
        session = new SessionManager(getBaseContext());
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Sign In");
        progressDialog.setMessage("Loading..");
        progressDialog.setIndeterminate(false);
        progressDialog.setCancelable(true);
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Toast.makeText(LoginActivity.this, connectionResult.getErrorMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_sign_in_googleplus:
                if(InternetCheck.isNetworkConnected(LoginActivity.this)){
                    if (InternetCheck.isNetworkAvailable(LoginActivity.this)){
                        signIn();
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
                        pdCanceller.postDelayed(progressRunnable, 40000);

                    } else {
                        showDialog();

                    }
                } else {
                    showDialog();
                }

                break;
        }
    }

    private void showDialog() {
        final AlertDialog.Builder popDialog = new AlertDialog.Builder(this);
        final LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View Viewlayout = inflater.inflate(R.layout.dialog_coba_lagi,(ViewGroup) findViewById(R.id.layout_dialog_coba_lagi));
        popDialog.setIcon(R.mipmap.ic_alert);
        popDialog.setTitle("Gagal Login");
        popDialog.setView(Viewlayout);
        popDialog.setCancelable(false);
        // Button OK
        popDialog.setPositiveButton(android.R.string.ok,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = getIntent();
                        finish();
                        startActivity(intent);
                    }
                })
                // Button Cancel
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                                finish();
                            }
                        });
        popDialog.create();
        popDialog.show();
    }

    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }

    private void handleSignInResult(GoogleSignInResult result) {
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.

            GoogleSignInAccount akun = result.getSignInAccount();;
            id_user = akun.getId();
            nama = akun.getDisplayName();
            email = akun.getEmail();

            Uri urlFoto = akun.getPhotoUrl();
            url_foto = String.valueOf(urlFoto);

            session.createLoginSession(id_user, nama, email, url_foto);
            session.setAlarm();
            if(session.isLoggedIn()) {
                finishLogin();
            }
        }
    }

    private void finishLogin() {
        Log.i("NamaUser:",nama);
        Log.i("IDUser:",id_user);
        Log.i("EmailUser",email);
        Log.i("URLfotoUser:",url_foto);
        requestBody = new FormBody.Builder()
                .add("id_user", id_user)
                .add("email", email)
                .add("nama", nama)
                .add("url_foto", url_foto)
                .build();

        try {
            OkHttpRequest.postDataToServer(UrlApi.urlRegistrasi,requestBody).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    e.printStackTrace();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String message = "";
                    int statusCode = 0;
                    try {
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        message = jsonObject.getString("message");
                        statusCode = jsonObject.getInt("statusCode");

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    final String finalMessage = message;
                    final int finalStatusCode = statusCode;
                    LoginActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (statusProgressDialog == true){
                                progressDialog.dismiss();
                                statusProgressDialog = false;
                            }
                            Toast.makeText(LoginActivity.this, finalMessage, Toast.LENGTH_SHORT).show();
                            if(finalStatusCode == 200) {
                                intent = new Intent(getBaseContext(),MainActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        }
                    });
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
