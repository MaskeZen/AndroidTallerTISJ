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
import com.tisj.tareax.modelo.Clase;
import com.tisj.tareax.modelo.Estudiante;

import java.util.ArrayList;

/**
 * Created by Nicolás on 11/3/2016.
 */
public class ClaseAdapter extends ArrayAdapter<Clase> {

    private Activity activity;
    private ArrayList<Clase> clases;
    private static LayoutInflater inflater = null;

    public ClaseAdapter (Activity activity, int textViewResourceId, ArrayList<Clase> _clases) {
        super(activity, textViewResourceId, _clases);
        try {
            this.activity = activity;
            this.clases = _clases;
            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        } catch (Exception e) {
        }
    }

    public int getCount() {
        return clases.size();
    }

    public Clase getItem(Clase position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    public static class ViewHolder {
        public TextView claseId;
        public TextView claseFecha;
        public TextView claseTema;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        final ViewHolder holder;
        try {
            if (convertView == null) {
                vi = inflater.inflate(R.layout.list_clase, null);
                holder = new ViewHolder();

                holder.claseId = (TextView) vi.findViewById(R.id.claseId);
                holder.claseFecha = (TextView) vi.findViewById(R.id.claseFecha);
                holder.claseTema = (TextView) vi.findViewById(R.id.claseTema);

                vi.setTag(holder);
            }
            else {
                holder = (ViewHolder) vi.getTag();
            }

            holder.claseId.setText("ID: " + clases.get(position).id);
            holder.claseFecha.setText("Fecha: " + clases.get(position).fecha);
            holder.claseTema.setText(clases.get(position).tema);
        }
        catch (Exception e) {
           String error = e.getMessage();
        }

        return vi;
    }
}
