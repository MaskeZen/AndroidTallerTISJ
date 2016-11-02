package com.tisj.tareax;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.tisj.tareax.adapters.EstudianteAdapter;
import com.tisj.tareax.modelo.Estudiante;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ListarEstudiantesFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ListarEstudiantesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListarEstudiantesFragment extends Fragment {

    private String TAG = ListarEstudiantesFragment.class.getSimpleName();

    private ProgressDialog pDialog;
    private ListView lv;
    // URL to get contacts JSON
    private static String url = "http://diegonicolas.webcindario.com/ListarEstudiantes.php";
    //ArrayList<HashMap<String, String>> listaEstudiantes;
    private ArrayList<Estudiante> listaEstudiantes;

    private OnFragmentInteractionListener mListener;

    public ListarEstudiantesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     *
     * @return A new instance of fragment ListarEstudiantesFragment.
     */
    public static ListarEstudiantesFragment newInstance(ArrayList<Estudiante> listaEstudiantes) {

        ListarEstudiantesFragment fragment = new ListarEstudiantesFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        listaEstudiantes = new ArrayList<>();
//        lv = (ListView)this.getActivity().findViewById(R.id.listaEstudiantes);
//
//        new GetEstudiantes().execute();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View vista = inflater.inflate(R.layout.fragment_listar_estudiantes, container, false);

        listaEstudiantes = new ArrayList<>();
        lv = (ListView)vista.findViewById(R.id.listaEstudiantes);
        new GetEstudiantes().execute();

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

    private class GetEstudiantes extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(ListarEstudiantesFragment.this.getActivity());
            pDialog.setMessage("Cargando los estudiantes...");
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
                    ListarEstudiantesFragment.this.getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(ListarEstudiantesFragment.this.getActivity().getApplicationContext(),
                                    "Json error: " + e.getMessage(),
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    });
                }
            } else {
                Log.e(TAG, "No se pudieron obtener los alumnos.");
                ListarEstudiantesFragment.this.getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(ListarEstudiantesFragment.this.getActivity().getApplicationContext(),
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
            ListAdapter adapter = new EstudianteAdapter(ListarEstudiantesFragment.this.getActivity(), 0, listaEstudiantes);

            lv.setAdapter(adapter);
        }

    }

}
