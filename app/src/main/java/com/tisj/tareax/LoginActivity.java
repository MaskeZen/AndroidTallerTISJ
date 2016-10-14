package com.tisj.tareax;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnLogin;
    Button btnCheckConexion;
    Button btnSinLogin;

    EditText user;
    EditText pass;

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
                String usuario = user.getText().toString();
                Toast.makeText(getApplicationContext(),usuario,Toast.LENGTH_SHORT).show();
                break;
            case R.id.iniciarSinLogin:
                intento = new Intent(this.getApplicationContext(), ListarEstudiantesActivity.class);
                startActivity(intento);
                break;
            case R.id.chequearConexion:
                TextView conexionText = (TextView)findViewById(R.id.conexionTxt);

                ConnectivityManager connMgr = (ConnectivityManager)
                        getSystemService(Context.CONNECTIVITY_SERVICE);

                NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
                if (networkInfo != null && networkInfo.isConnected()) {
                    conexionText.setText("Hay Conexión :)");
                } else {
                    conexionText.setText("No hay Conexión :(");
                }
                break;
        }
    }

    private class obtenerLogin extends AsyncTask<String,Void,Void> {

        String resultado;

        @Override
        protected Void doInBackground(String... strings) {


            return null;
        }
    }


}
