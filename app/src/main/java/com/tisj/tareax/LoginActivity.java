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

import com.tisj.tareax.auxiliar.Usuario;
import com.tisj.tareax.modelo.Estudiante;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnLogin;
    Button btnCheckConexion;
    Button btnSinLogin;

    //Para bloquear la interfaz en tareas Async
    private ProgressDialog pDialog;

    public EditText user;
    public EditText pass;
    boolean login = false;
    //public Estudiante estudiante;

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

        findViewById(R.id.menuPrincipal).setOnClickListener(this);

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
            case R.id.chequearConexion:
                TextView conexionText = (TextView)findViewById(R.id.conexionTxt);
                break;
            case R.id.menuPrincipal:
                intento = new Intent(LoginActivity.this, MenuActivity.class);
                intento.putExtra("cedula",user.getText().toString());
                startActivity(intento);
                finish();
                break;
        }
    }

    private class obtenerLogin extends AsyncTask<String,Void,Void> {

        String cedula = user.getText().toString();
        String password = pass.getText().toString();
        //boolean login;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(LoginActivity.this);
            pDialog.setMessage("Procesando información...");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected Void doInBackground(String... strings){

            HttpHandler sh = new HttpHandler();
            login = sh.getLogin("http://diegonicolas.webcindario.com/Login.php", "POST", cedula, password);
            if(login) {
                String jsonStr = sh.makeServiceCall("http://diegonicolas.webcindario.com/DetalleEstudiante.php?IdEstudiante=" + cedula, "GET", null);
                if (jsonStr != null) {
                    try {
                        JSONObject jsonObj = new JSONObject(jsonStr);
                        JSONObject jsonEstudiante = jsonObj.getJSONArray("Estudiante").getJSONObject(0);

                        String nombre = jsonEstudiante.getString("nombre");
                        String cedula = jsonEstudiante.getString("cedula");
                        String mail = jsonEstudiante.getString("mail");
                        String telefono = jsonEstudiante.getString("telefono");
                        String imagenUrl = jsonEstudiante.getString("foto");

                        // tmp hash map for single contact
                        Estudiante estudiante = new Estudiante();

                        // adding each child node to HashMap key => value
                        estudiante.setNombre(nombre);
                        estudiante.setCedula(cedula);
                        //estudiante.setTelefono(telefono);
                        estudiante.setMail(mail);
                        estudiante.setImagenUrl(imagenUrl);
                        estudiante.setImagen(sh.getImagen(imagenUrl));

                        Usuario.setEstudiante(estudiante);

                    } catch (final JSONException e) {

                    }
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            Intent intento;
            // Dismiss the progress dialog
            if (pDialog.isShowing()){
                pDialog.dismiss();
            }

            if(login){

                intento = new Intent(LoginActivity.this, MenuActivity.class);
                intento.putExtra("cedula",user.getText().toString());
                startActivity(intento);
                finish();
            }
            else{
                Toast.makeText(getApplicationContext(), "Login incorrecto", Toast.LENGTH_SHORT).show();
            }
        }
    }


}
