package com.tisj.tareax.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.tisj.tareax.R;
import com.tisj.tareax.modelo.Ejercicio;

import java.util.ArrayList;

/**
 * Created by maske on 04/11/2016.
 */
public class EjercicioAdapter extends ArrayAdapter<Ejercicio> {

    private Activity activity;
    private ArrayList<Ejercicio> ejercicios;
    private static LayoutInflater inflater = null;

    public EjercicioAdapter (Activity activity, int textViewResourceId, ArrayList<Ejercicio> _ejercicios) {
        super(activity, textViewResourceId, _ejercicios);
        try {
            this.activity = activity;
            this.ejercicios = _ejercicios;
            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        } catch (Exception e) {

        }
    }

    public int getCount() {
        return ejercicios.size();
    }

    public Ejercicio getItem(Ejercicio position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    public static class ViewHolder {

        public TextView ejercicioNumero;
        public ImageView ejercicioImagen;

    }




    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        final ViewHolder holder;
        try {
            if (convertView == null) {
                vi = inflater.inflate(R.layout.list_ejercicio, null);
                holder = new ViewHolder();

                holder.ejercicioNumero = (TextView) vi.findViewById(R.id.ejercicioNumero);
                holder.ejercicioImagen = (ImageView) vi.findViewById(R.id.ejercicioImagen);

                vi.setTag(holder);
            }
            else {
                holder = (ViewHolder) vi.getTag();
            }

            holder.ejercicioNumero.setText("Número: " + ejercicios.get(position).getNumero());
            holder.ejercicioImagen.setImageBitmap(ejercicios.get(position).getImagen());

        }
        catch (Exception e) {

        }

        return vi;
    }


}
