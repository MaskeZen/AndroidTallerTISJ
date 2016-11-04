package com.tisj.tareax;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.tisj.tareax.modelo.Estudiante;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DetalleEstudianteFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DetalleEstudianteFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetalleEstudianteFragment extends Fragment {
    private String TAG = DetalleEstudianteFragment.class.getSimpleName();

    private ProgressDialog pDialog;
    private ProgressDialog pDialog2;
    // URL para obtener los datos
    private static String urlDetalle = "http://diegonicolas.webcindario.com/DetalleEstudiante.php?IdEstudiante=";
    private static String urlFaltas = "http://diegonicolas.webcindario.com/InasistenciasEstudiante.php?IdEstudiante=";

    private Estudiante estudiante;

    private OnFragmentInteractionListener mListener;

    public DetalleEstudianteFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DetalleEstudiante.
     */
    // TODO: Rename and change types and number of parameters
    public static DetalleEstudianteFragment newInstance(String param1, String param2) {
        DetalleEstudianteFragment fragment = new DetalleEstudianteFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }*/
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.fragment_detalle_estudiante, container, false);

        estudiante = new Estudiante();
        new GetDetalleEstudiante().execute();
        new GetDetalleEstudianteFaltas().execute();
        // Inflate the layout for this fragment
        if (pDialog.isShowing())
            pDialog.dismiss();
        return vista;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    private class GetDetalleEstudiante extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(DetalleEstudianteFragment.this.getActivity());
            pDialog.setMessage("Cargando el detalle del estudiante...");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();

            // Making a request to url and getting response
            String paramCedula =  getArguments().getString("cedula");
            String jsonStr = sh.makeServiceCall(urlDetalle+paramCedula, "GET", null);

            Log.e(TAG, "Response from url: " + jsonStr);

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    // Getting JSON Array node
                    JSONArray contacts = jsonObj.getJSONArray("Estudiante");

                    // looping through All Contacts
                    for (int i = 0; i < contacts.length(); i++) {
                        JSONObject c = contacts.getJSONObject(i);

                        String nombre = c.getString("nombre");
                        String cedula = c.getString("cedula");
                        String mail = c.getString("mail");
                        String imagenUrl = c.getString("foto");

                        // adding each child node to HashMap key => value
                        estudiante.setNombre(nombre);
                        estudiante.setCedula(cedula);
                        estudiante.setMail(mail);
                        estudiante.setImagenUrl(imagenUrl);
                        estudiante.setImagen(sh.getImagen(imagenUrl));
                    }
                } catch (final JSONException e) {
                    Log.e(TAG, "Json error: " + e.getMessage());
                    DetalleEstudianteFragment.this.getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(DetalleEstudianteFragment.this.getActivity().getApplicationContext(),
                                    "Json error: " + e.getMessage(),
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    });
                }
            } else {
                Log.e(TAG, "No se pudo obtener el detalle del estudiante.");
                DetalleEstudianteFragment.this.getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(DetalleEstudianteFragment.this.getActivity().getApplicationContext(),
                                "No se pudo obtener el detalle del estudiante del servidor!",
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

            TextView nombre = (TextView) getView().findViewById(R.id.estudianteDetalleNombre);
            nombre.setText(estudiante.nombre);
            TextView cedula = (TextView) getView().findViewById(R.id.estudianteDetalleCedula);
            cedula.setText("C.I.: "+estudiante.cedula);
            TextView mail = (TextView) getView().findViewById(R.id.estudianteDetalleMail);
            mail.setText("MAIL: "+estudiante.mail);
            ImageView imagen = (ImageView) getView().findViewById(R.id.estudianteDetalleImagen);
            imagen.setImageBitmap(estudiante.imagen);
        }

    }

    private class GetDetalleEstudianteFaltas extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog2 = new ProgressDialog(DetalleEstudianteFragment.this.getActivity());
            pDialog2.setMessage("Cargando las inasistencias...");
            pDialog2.setCancelable(false);
            pDialog2.show();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();

            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(urlFaltas+ getArguments().getString("cedula"), "GET", null);

            Log.e(TAG, "Response from url: " + jsonStr);

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    String faltas = jsonObj.getString("Faltas");

                    estudiante.setFaltas(faltas);

                } catch (final JSONException e) {
                    Log.e(TAG, "Json error: " + e.getMessage());
                    DetalleEstudianteFragment.this.getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(DetalleEstudianteFragment.this.getActivity().getApplicationContext(),
                                    "Json error: " + e.getMessage(),
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    });
                }
            } else {
                Log.e(TAG, "No se pudieron obtener las inasistencias.");
                DetalleEstudianteFragment.this.getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(DetalleEstudianteFragment.this.getActivity().getApplicationContext(),
                                "No se pudieron obtener las inasistencias del servidor!",
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
            if (pDialog2.isShowing())
                pDialog2.dismiss();

            TextView faltas = (TextView) getView().findViewById(R.id.estudianteDetalleFaltas);
            faltas.setText("FALTAS: "+estudiante.faltas);
        }

    }
}
