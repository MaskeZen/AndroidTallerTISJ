package com.tisj.tareax;


import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.tisj.tareax.adapters.EstudianteAdapter;
import com.tisj.tareax.modelo.Estudiante;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class ListarEstudiantesActivity extends AppCompatActivity {

    private String TAG = ListarEstudiantesActivity.class.getSimpleName();

    private ProgressDialog pDialog;
    private ListView lv;
    // URL to get contacts JSON
    private static String url = "http://diegonicolas.webcindario.com/ListarEstudiantes.php";
    //ArrayList<HashMap<String, String>> listaEstudiantes;
    ArrayList<Estudiante> listaEstudiantes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_estudiantes);

        listaEstudiantes = new ArrayList<>();
        lv = (ListView)findViewById(R.id.listaEstudiantes);

        new GetEstudiantes().execute();
    }

    private class GetEstudiantes extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(ListarEstudiantesActivity.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();

            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(url, "GET", null);

            Log.e(TAG, "Response from url: " + jsonStr);

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    // Getting JSON Array node
                    JSONArray contacts = jsonObj.getJSONArray("Estudiantes");

                    // looping through All Contacts
                    for (int i = 0; i < contacts.length(); i++) {
                        JSONObject c = contacts.getJSONObject(i);

                        String nombre = c.getString("nombre");
                        String cedula = c.getString("cedula");
                        String mail = c.getString("mail");
                        String imagenUrl = c.getString("foto");

                        // tmp hash map for single contact
                        Estudiante estudiante = new Estudiante();

                        // adding each child node to HashMap key => value
                        estudiante.setNombre(nombre);
                        estudiante.setCedula(cedula);
                        estudiante.setMail(mail);
                        estudiante.setImagenUrl(imagenUrl);
                        estudiante.setImagen(sh.getImagen(imagenUrl));

                        // adding contact to contact list
                        listaEstudiantes.add(estudiante);
                    }
                } catch (final JSONException e) {
                    Log.e(TAG, "Json error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json error: " + e.getMessage(),
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    });
                }
            } else {
                Log.e(TAG, "No se pudieron obtener los alumnos.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "No se pudieron obtener los alumnos del servidor!",
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();
            /**
             * Updating parsed JSON data into ListView
             * */

            //Activity activity, int textViewResourceId, ArrayList<Estudiante> _estudiantes)
            ListAdapter adapter = new EstudianteAdapter(ListarEstudiantesActivity.this, 0, listaEstudiantes);

            lv.setAdapter(adapter);
        }

    }

}
