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
import com.tisj.tareax.modelo.Estudiante;

import java.util.ArrayList;

/**
 * Created by maske on 28/10/2016.
 */
public class EstudianteAdapter extends ArrayAdapter<Estudiante> {

    private Activity activity;
    private ArrayList<Estudiante> estudiantes;
    private static LayoutInflater inflater = null;

    public EstudianteAdapter (Activity activity, int textViewResourceId, ArrayList<Estudiante> _estudiantes) {
        super(activity, textViewResourceId, _estudiantes);
        try {
            this.activity = activity;
            this.estudiantes = _estudiantes;
            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        } catch (Exception e) {

        }
    }

    public int getCount() {
        return estudiantes.size();
    }

    public Estudiante getItem(Estudiante position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    public static class ViewHolder {
        public TextView estudianteNombre;
        public TextView estudianteCedula;
        public TextView estudianteMail;
        public ImageView estudianteImagen;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        final ViewHolder holder;
        try {
            if (convertView == null) {
                vi = inflater.inflate(R.layout.list_estudiante, null);
                holder = new ViewHolder();

                holder.estudianteNombre = (TextView) vi.findViewById(R.id.estudianteNombre);
                holder.estudianteCedula = (TextView) vi.findViewById(R.id.estudianteCedula);
                holder.estudianteMail = (TextView) vi.findViewById(R.id.estudianteMail);
                holder.estudianteImagen = (ImageView) vi.findViewById(R.id.estudianteImagen);

                vi.setTag(holder);
            }
            else {
                holder = (ViewHolder) vi.getTag();
            }

            holder.estudianteNombre.setText(estudiantes.get(position).nombre);
            holder.estudianteCedula.setText("C.I.: " + estudiantes.get(position).cedula);
            holder.estudianteMail.setText(estudiantes.get(position).mail);
            holder.estudianteImagen.setImageBitmap(estudiantes.get(position).imagen);
        }
        catch (Exception e) {

        }

        return vi;
    }
}