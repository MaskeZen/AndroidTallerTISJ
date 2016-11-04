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

import com.tisj.tareax.adapters.TeoricoAdapter;
import com.tisj.tareax.modelo.Teorico;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ListarTeoricosFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ListarTeoricosFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class ListarTeoricosFragment extends Fragment {


    private String TAG = ListarEstudiantesFragment.class.getSimpleName();

    private ProgressDialog pDialog;
    private ListView lv;

    private static String url = "http://diegonicolas.webcindario.com/ListarTeoricos.php";
    private ArrayList<Teorico> listaTeoricos;
    private OnFragmentInteractionListener mListener;

    public static ListarTeoricosFragment newInstance( ) {
        ListarTeoricosFragment fragment = new ListarTeoricosFragment();
        return fragment;
    }
    public ListarTeoricosFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.fragment_listar_teoricos, container, false);

        listaTeoricos = new ArrayList<>();
        lv = (ListView)vista.findViewById(R.id.listaTeoricos);
        new GetTeoricos().execute();

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
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    private class GetTeoricos extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(ListarTeoricosFragment.this.getActivity());
            pDialog.setMessage("Cargando los teóricos...");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();

            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(url, "GET", null);

            Log.e(TAG, "datos obtenidos: " + jsonStr);

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    // Getting JSON Array node
                    JSONArray contacts = jsonObj.getJSONArray("Teoricos");

                    // looping through All Contacts
                    for (int i = 0; i < contacts.length(); i++) {
                        JSONObject c = contacts.getJSONObject(i);

                        String id = c.getString("id");
                        String numero = c.getString("numero");
                        String pdf = c.getString("pdf");

                        // tmp hash map for single contact
                        Teorico teorico = new Teorico();

                        // adding each child node to HashMap key => value
                        teorico.setId(id);
                        teorico.setNumero(numero);
                        teorico.setPdf(pdf);

                        // adding contact to contact list
                        listaTeoricos.add(teorico);
                    }
                } catch (final JSONException e) {
                    ListarTeoricosFragment.this.getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(ListarTeoricosFragment.this.getActivity().getApplicationContext(),
                                    "Json error: " + e.getMessage(),
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    });
                }
            } else {
                Log.e(TAG, "No se pudieron obtener los teoricos.");
                ListarTeoricosFragment.this.getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(ListarTeoricosFragment.this.getActivity().getApplicationContext(),
                                "No se pudieron obtener los teoricos del servidor!",
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
            ListAdapter adapter = new TeoricoAdapter(ListarTeoricosFragment.this.getActivity(), 0, listaTeoricos);

            lv.setAdapter(adapter);
        }

    }



}
