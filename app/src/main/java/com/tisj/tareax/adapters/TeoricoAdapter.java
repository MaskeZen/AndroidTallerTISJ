package com.tisj.tareax.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.tisj.tareax.R;
import com.tisj.tareax.modelo.Teorico;

import java.util.ArrayList;

/**
 * Created by maske on 03/11/2016.
 */
public class TeoricoAdapter extends ArrayAdapter<Teorico> {

    private Activity activity;
    private ArrayList<Teorico> teoricos;
    private static LayoutInflater inflater = null;

    public TeoricoAdapter(Activity activity, int textViewResourceId, ArrayList<Teorico> _teoricos) {
        super(activity, textViewResourceId, _teoricos);
        try {
            this.activity = activity;
            this.teoricos = _teoricos;
            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        } catch (Exception e) {

        }
    }


    public int getCount() {
        return teoricos.size();
    }

    public Teorico getItem(Teorico position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    public static class ViewHolder {
        public TextView teoricoId;
        public TextView teoricoNumero;
        public TextView teoricoPdf;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        final ViewHolder holder;
        try {
            if (convertView == null) {
                vi = inflater.inflate(R.layout.list_teorico, null);
                holder = new ViewHolder();

                holder.teoricoId = (TextView) vi.findViewById(R.id.teoricoId);
                holder.teoricoNumero = (TextView) vi.findViewById(R.id.teoricoNumero);
                holder.teoricoPdf = (TextView) vi.findViewById(R.id.teoricoPdf);

                vi.setTag(holder);
            }
            else {
                holder = (ViewHolder) vi.getTag();
            }

            holder.teoricoId.setText(teoricos.get(position).getId());
            holder.teoricoNumero.setText("Numero: " + teoricos.get(position).getNumero());
            holder.teoricoPdf.setText(teoricos.get(position).getPdf());
        }
        catch (Exception e) {

        }

        return vi;
    }




}
