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

import com.tisj.tareax.adapters.PracticoAdapter;
import com.tisj.tareax.modelo.Estudiante;
import com.tisj.tareax.modelo.Practico;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ListarPracticosFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ListarPracticosFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListarPracticosFragment extends Fragment {

    private String TAG = ListarEstudiantesFragment.class.getSimpleName();

    private ProgressDialog pDialog;
    private ListView lv;

    private static String url = "http://diegonicolas.webcindario.com/ListarPracticos.php";
    private ArrayList<Practico> listaPracticos;
    private OnFragmentInteractionListener mListener;

    public ListarPracticosFragment() {
        // Required empty public constructor
    }


    public static ListarPracticosFragment newInstance() {

        ListarPracticosFragment fragment = new ListarPracticosFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View vista = inflater.inflate(R.layout.fragment_listar_practicos, container, false);

        listaPracticos = new ArrayList<>();
        lv = (ListView)vista.findViewById(R.id.listaPracticos);
        new GetPracticos().execute();

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

    private class GetPracticos extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(ListarPracticosFragment.this.getActivity());
            pDialog.setMessage("Cargando los prácticos...");
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
                    JSONArray contacts = jsonObj.getJSONArray("Practicos");

                    // looping through All Contacts
                    for (int i = 0; i < contacts.length(); i++) {
                        JSONObject c = contacts.getJSONObject(i);

                        String id = c.getString("id");
                        String numero = c.getString("numero");

                        // tmp hash map for single contact
                        Practico practico= new Practico();

                        // adding each child node to HashMap key => value
                        practico.setId(id);
                        practico.setNumero(numero);

                        // adding contact to contact list
                        listaPracticos.add(practico);
                    }
                } catch (final JSONException e) {
                    ListarPracticosFragment.this.getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(ListarPracticosFragment.this.getActivity().getApplicationContext(),
                                    "Json error: " + e.getMessage(),
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    });
                }
            } else {
                Log.e(TAG, "No se pudieron obtener los practicos.");
                ListarPracticosFragment.this.getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(ListarPracticosFragment.this.getActivity().getApplicationContext(),
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
            ListAdapter adapter = new PracticoAdapter(ListarPracticosFragment.this.getActivity(), 0, listaPracticos);

            lv.setAdapter(adapter);
        }

    }





}
