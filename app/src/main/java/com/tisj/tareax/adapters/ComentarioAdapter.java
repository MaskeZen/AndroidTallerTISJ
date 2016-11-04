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
import com.tisj.tareax.modelo.Comentario;

import java.util.ArrayList;

/**
 * Created by maske on 04/11/2016.
 */
public class ComentarioAdapter extends ArrayAdapter<Comentario> {

    private Activity activity;
    private ArrayList<Comentario> comentarios;
    private static LayoutInflater inflater = null;

    public ComentarioAdapter (Activity activity, int textViewResourceId, ArrayList<Comentario> _comentarios) {
        super(activity, textViewResourceId, _comentarios);
        try {
            this.activity = activity;
            this.comentarios = _comentarios;
            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        } catch (Exception e) {

        }
    }

    public int getCount() {
        return comentarios.size();
    }

    public Comentario getItem(Comentario position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    public static class ViewHolder {

        public TextView comentarioCiEstudiante;
        public TextView comentarioFecha;
        public TextView comentarioContenido;

    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        final ViewHolder holder;
        try {
            if (convertView == null) {
                vi = inflater.inflate(R.layout.list_comentario, null);
                holder = new ViewHolder();

                holder.comentarioCiEstudiante = (TextView) vi.findViewById(R.id.comentarioCiEstudiante);
                holder.comentarioFecha = (TextView) vi.findViewById(R.id.comentarioFecha);
                holder.comentarioContenido = (TextView) vi.findViewById(R.id.comentarioContenido);

                vi.setTag(holder);
            }
            else {
                holder = (ViewHolder) vi.getTag();
            }

            holder.comentarioCiEstudiante.setText("Estudiante: " + comentarios.get(position).getCiEstudiante());
            holder.comentarioContenido.setText(comentarios.get(position).getContenido());
            holder.comentarioFecha.setText(comentarios.get(position).getFecha());

        }
        catch (Exception e) {

        }

        return vi;
    }


}
