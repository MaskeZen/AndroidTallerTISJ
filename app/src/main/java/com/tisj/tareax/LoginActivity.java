package com.tisj.tareax;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnLogin;
    Button btnCheckConexion;
    Button btnSinLogin;

    public EditText user;
    public EditText pass;
    boolean login = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnLogin = (Button)findViewById(R.id.login);
        btnLogin.setOnClickListener(this);

        btnCheckConexion = (Button)findViewById(R.id.chequearConexion);
        btnCheckConexion.setOnClickListener(this);

        btnSinLogin = (Button)findViewById(R.id.iniciarSinLogin);
        btnSinLogin.setOnClickListener(this);

        user = (EditText)findViewById(R.id.user);
        pass = (EditText)findViewById(R.id.pass);
    }

    @Override
    public void onClick(View view) {
        Intent intento;
        switch (view.getId()){
            case R.id.login:
                String cedula = user.getText().toString();
                String password = pass.getText().toString();

                new obtenerLogin().execute();

                break;
            case R.id.iniciarSinLogin:
                intento = new Intent(this.getApplicationContext(), ListarEstudiantesActivity.class);
                startActivity(intento);
                break;
            case R.id.chequearConexion:
                TextView conexionText = (TextView)findViewById(R.id.conexionTxt);

//                ConnectivityManager connMgr = (ConnectivityManager)
//                        getSystemService(Context.CONNECTIVITY_SERVICE);
//
//                NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
//                if (networkInfo != null && networkInfo.isConnected()) {
//                    conexionText.setText("Hay Conexión :)");
//                } else {
//                    conexionText.setText("No hay Conexión :(");
//                }




                break;
        }
    }

    private class obtenerLogin extends AsyncTask<String,Void,Void> {


        String cedula = user.getText().toString();
        String password = pass.getText().toString();
        //boolean login;

        @Override
        protected Void doInBackground(String... strings) {

            HttpHandler sh = new HttpHandler();
            login = sh.getLogin("http://diegonicolas.webcindario.com/Login.php", "POST", cedula, password);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            Intent intento;
            if(login){
                Toast.makeText(getApplicationContext(), "Login exitoso", Toast.LENGTH_SHORT).show();
                intento = new Intent(getApplicationContext(), ListarEstudiantesActivity.class);
                startActivity(intento);
            }
            else{
                Toast.makeText(getApplicationContext(), "Login incorrecto", Toast.LENGTH_SHORT).show();
            }
        }
    }


}
