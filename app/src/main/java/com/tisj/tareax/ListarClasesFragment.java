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
import android.widget.Toast;

import com.tisj.tareax.adapters.ClaseAdapter;
import com.tisj.tareax.adapters.EstudianteAdapter;
import com.tisj.tareax.modelo.Clase;
import com.tisj.tareax.modelo.Estudiante;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ListarClasesFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ListarClasesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListarClasesFragment extends Fragment {
    private String TAG = ListarEstudiantesFragment.class.getSimpleName();

    private ProgressDialog pDialog;
    private ListView lv;

    private static String url = "http://diegonicolas.webcindario.com/ListarClases.php";

    private ArrayList<Clase> listaClases;

    private OnFragmentInteractionListener mListener;

    public ListarClasesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ListarClasesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ListarClasesFragment newInstance(String param1, String param2) {
        ListarClasesFragment fragment = new ListarClasesFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.fragment_listar_clases, container, false);

        listaClases = new ArrayList<>();
        lv = (ListView)vista.findViewById(R.id.listaClases);
        new GetClases().execute();

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

    private class GetClases extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(ListarClasesFragment.this.getActivity());
            pDialog.setMessage("Cargando las clases...");
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
                    JSONArray contacts = jsonObj.getJSONArray("Clases");

                    // looping through All Contacts
                    for (int i = 0; i < contacts.length(); i++) {
                        JSONObject c = contacts.getJSONObject(i);

                        String id = c.getString("id");
                        String fecha = c.getString("fecha");
                        String tema = c.getString("tema");

                        // tmp hash map for single contact
                        Clase clase = new Clase();

                        // adding each child node to HashMap key => value
                        clase.setId(id);
                        clase.setFecha(fecha);
                        clase.setTema(tema);

                        // adding contact to contact list
                        listaClases.add(clase);
                    }
                } catch (final JSONException e) {
                    Log.e(TAG, "Json error: " + e.getMessage());
                    ListarClasesFragment.this.getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(ListarClasesFragment.this.getActivity().getApplicationContext(),
                                    "Json error: " + e.getMessage(),
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    });
                }
            } else {
                Log.e(TAG, "No se pudieron obtener los alumnos.");
                ListarClasesFragment.this.getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(ListarClasesFragment.this.getActivity().getApplicationContext(),
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
            ListAdapter adapter = new ClaseAdapter(ListarClasesFragment.this.getActivity(), 0, listaClases);

            lv.setAdapter(adapter);
        }

    }
}
