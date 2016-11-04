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
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.tisj.tareax.adapters.EjercicioAdapter;
import com.tisj.tareax.modelo.Comentario;
import com.tisj.tareax.modelo.Ejercicio;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ListarEjerciciosFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ListarEjerciciosFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListarEjerciciosFragment extends Fragment {

    private String TAG = ListarEjerciciosFragment.class.getSimpleName();

    private ProgressDialog pDialog;
    private ListView lv;
    private static String url = "http://diegonicolas.webcindario.com/ListarEjercicios.php?IdPractico=";
    private ArrayList<Ejercicio> listaEjercicios;
    private OnFragmentInteractionListener mListener;

    private static String ARG_PRACTICO = "practico";
    private String practicoId;

    public ListarEjerciciosFragment() {
        // Required empty public constructor
    }

    public static ListarEjerciciosFragment newInstance(String practicoIdParam) {
        ListarEjerciciosFragment fragment = new ListarEjerciciosFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PRACTICO, practicoIdParam);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            practicoId = getArguments().getString(ARG_PRACTICO);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View vista = inflater.inflate(R.layout.fragment_listar_ejercicios, container, false);

        listaEjercicios = new ArrayList<>();
        TextView titulo = (TextView)vista.findViewById(R.id.ejerciciosFragmentTitulo);
        titulo.setText("Ejercicios del Practico " + practicoId);
        lv = (ListView)vista.findViewById(R.id.listaEjercicios);
        //new ObtenerEjercicios.execute();
        new ObtenerEjercicios().execute();

        // Inflate the layout for this fragment
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

    private class ObtenerEjercicios extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(ListarEjerciciosFragment.this.getActivity());
            pDialog.setMessage("Cargando los ejercicios...");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();

            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(url+practicoId, "GET", null);

            Log.e(TAG, "datos obtenidos: " + jsonStr);

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    // Getting JSON Array node
                    JSONArray ejercicios = jsonObj.getJSONArray("Ejercicios");

                    // looping through All Contacts
                    for (int i = 0; i < ejercicios.length(); i++) {
                        JSONObject c = ejercicios.getJSONObject(i);

                        String idpractico = c.getString("idpractico");
                        String numero = c.getString("numero");
                        String imagenUrl = c.getString("imagen");

                        // tmp hash map for single contact
                        Ejercicio ejercicio = new Ejercicio();

                        // adding each child node to HashMap key => value
                        ejercicio.setIdPractico(idpractico);
                        ejercicio.setNumero(numero);
                        ejercicio.setImagenUrl(imagenUrl);
                        ejercicio.setImagen(sh.getImagen(imagenUrl));

                        JSONArray comentarios = c.getJSONArray("comentarios");
                        for(int comInd = 0; comInd < comentarios.length(); comInd++){
                            JSONObject comentarioJS = comentarios.getJSONObject(comInd);

                            Comentario comentario = new Comentario();

                            comentario.setIdPractico(idpractico);
                            comentario.setCiEstudiante(comentarioJS.getString("ciestudiante"));
                            comentario.setIdEjercicio(comentarioJS.getString("idejercicio"));
                            comentario.setFecha(comentarioJS.getString("fecha"));
                            comentario.setFecha(comentarioJS.getString("fecha"));

                            ejercicio.getComentarios().add(comentario);
                        }

                        // adding contact to contact list
                        listaEjercicios.add(ejercicio);
                    }
                } catch (final JSONException e) {
                    ListarEjerciciosFragment.this.getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(ListarEjerciciosFragment.this.getActivity().getApplicationContext(),
                                    "Json error: " + e.getMessage(),
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    });
                }
            } else {
                Log.e(TAG, "No se pudieron obtener los ejercicios.");
                ListarEjerciciosFragment.this.getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(ListarEjerciciosFragment.this.getActivity().getApplicationContext(),
                                "No se pudieron obtener los ejercicios del servidor!",
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

            ListAdapter adapter = new EjercicioAdapter(ListarEjerciciosFragment.this.getActivity(), 0, listaEjercicios);

            lv.setAdapter(adapter);

        }
    }

}
