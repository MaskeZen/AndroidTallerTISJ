package com.tisj.tareax;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.tisj.tareax.adapters.ComentarioAdapter;
import com.tisj.tareax.modelo.Comentario;
import com.tisj.tareax.modelo.Ejercicio;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link EjercicioFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link EjercicioFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EjercicioFragment extends Fragment {

    private String TAG = ListarEjerciciosFragment.class.getSimpleName();

    private ProgressDialog pDialog;
    private ListView lv;
    private static String url = "http://diegonicolas.webcindario.com/ListarEjercicios.php?IdPractico=";
    private Ejercicio ejercicio;
    private OnFragmentInteractionListener mListener;

    private static String ARG_EJERCICIO = "ejercicio";

    public EjercicioFragment() {
        // Required empty public constructor
    }

    public static EjercicioFragment newInstance(Ejercicio ejercicioParam) {

        EjercicioFragment fragment = new EjercicioFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_EJERCICIO, ejercicioParam);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            ejercicio = (Ejercicio) getArguments().getSerializable(ARG_EJERCICIO);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View vista = inflater.inflate(R.layout.fragment_ejercicio, container, false);

        TextView titulo = (TextView)vista.findViewById(R.id.ejercicioFragmentNumero);
        titulo.setText("Ejercicio número: " + ejercicio.getNumero());
        ImageView imagen = (ImageView)vista.findViewById(R.id.ejercicioFragmentImagen);
        imagen.setImageBitmap(ejercicio.getImagen());

        ArrayList<Comentario> comentarios = ejercicio.getComentarios();
        ListAdapter adapter = new ComentarioAdapter(EjercicioFragment.this.getActivity(), 0, comentarios);

        lv = (ListView)vista.findViewById(R.id.listaComentarios);
        lv.setAdapter(adapter);


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
}
