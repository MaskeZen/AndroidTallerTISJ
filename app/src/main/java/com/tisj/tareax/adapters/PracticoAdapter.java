package com.tisj.tareax.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.tisj.tareax.R;
import com.tisj.tareax.modelo.Estudiante;
import com.tisj.tareax.modelo.Practico;

import java.util.ArrayList;

/**
 * Created by maske on 03/11/2016.
 */
public class PracticoAdapter extends ArrayAdapter<Practico> {

    private Activity activity;
    private ArrayList<Practico> practicos;
    private static LayoutInflater inflater = null;

    public PracticoAdapter(Activity activity, int textViewResourceId, ArrayList<Practico> _practicos) {
        super(activity, textViewResourceId, _practicos);
        try {
            this.activity = activity;
            this.practicos = _practicos;
            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        } catch (Exception e) {

        }
    }

    public int getCount() {
        return practicos.size();
    }

    public Practico getItem(Practico position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    public static class ViewHolder {
        public TextView practicoId;
        public TextView practicoNumero;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        final ViewHolder holder;
        try {
            if (convertView == null) {
                vi = inflater.inflate(R.layout.list_practico, null);
                holder = new ViewHolder();

                holder.practicoId = (TextView) vi.findViewById(R.id.practicoId);
                holder.practicoNumero = (TextView) vi.findViewById(R.id.practicoNombre);

                vi.setTag(holder);
            }
            else {
                holder = (ViewHolder) vi.getTag();
            }

            holder.practicoId.setText(practicos.get(position).getId());
            holder.practicoNumero.setText("Numero: " + practicos.get(position).getNumero());
        }
        catch (Exception e) {

        }

        return vi;
    }




}
