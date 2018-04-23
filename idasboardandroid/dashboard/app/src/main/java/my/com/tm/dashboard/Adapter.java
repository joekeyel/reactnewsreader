package my.com.tm.dashboard;

/**
 * Created by user on 15/12/2017.
 */


import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import my.dashboard.R;


public class Adapter extends ArrayAdapter implements Filterable {

    private List<Model> GatedList;
    private List<Model> orig;
    private int resource;
    private LayoutInflater inflator;

    public Adapter(Context context, int resource, List<Model> objects) {
        super(context, resource, objects);

        GatedList = objects;
        this.resource = resource;
        inflator = (LayoutInflater)getContext().getSystemService(context.LAYOUT_INFLATER_SERVICE);
    }



    public Filter getFilter() {
        return new Filter() {

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                final FilterResults oReturn = new FilterResults();
                final ArrayList<Model> results = new ArrayList<Model>();
                if (orig == null)
                    orig = GatedList;
                if (constraint != null) {
                    if (orig != null && orig.size() > 0) {
                        for (final Model g : orig) {
                            if (g.getPlanmigrate().toLowerCase()
                                    .contains(constraint.toString()))
                                results.add(g);
//                            if (g.getServiceNo().toLowerCase()
//                                    .contains(constraint.toString()))
//                                results.add(g);
//                            if (g.getReferencenumber().toLowerCase()
//                                    .contains(constraint.toString()))
//                                results.add(g);

                        }
                    }
                    oReturn.values = results;
                }
                return oReturn;
            }

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint,
                                          FilterResults results) {
                GatedList = (ArrayList<Model>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return GatedList.size();
    }

    @Override
    public Object getItem(int position) {
        return GatedList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView==null){

            convertView = inflator.inflate(resource,null);
        }


        TextView planmigrate,migratedcabinet,cabinetmonitoring,migrationsubb;
        TextView migrationefiber,unresolvedtt,totaltt;
        TextView faultrate,mttr;

        planmigrate = (TextView) convertView.findViewById(R.id.satu);
        migratedcabinet = (TextView) convertView.findViewById(R.id.tiga);
        cabinetmonitoring = (TextView) convertView.findViewById(R.id.dua);
        migrationsubb = (TextView) convertView.findViewById(R.id.empat);
        migrationefiber = (TextView) convertView.findViewById(R.id.dua);
        unresolvedtt = (TextView) convertView.findViewById(R.id.tiga);
        totaltt = (TextView) convertView.findViewById(R.id.enam);
        faultrate = (TextView) convertView.findViewById(R.id.empat);
        mttr = (TextView) convertView.findViewById(R.id.lapan);

        planmigrate.setText(GatedList.get(position).getPlanmigrate());
        migratedcabinet.setText(GatedList.get(position).getMigratedcabinet());
        cabinetmonitoring.setText(GatedList.get(position).getCabinetmonitoring());
        migrationsubb.setText(GatedList.get(position).getMigrationsubb());
        migrationefiber.setText(GatedList.get(position).getMigrationefiber());
        unresolvedtt.setText(GatedList.get(position).getUnresolvedtt());
        totaltt.setText(GatedList.get(position).getTotaltt());
        faultrate.setText(GatedList.get(position).getFaultrate());
        mttr.setText(GatedList.get(position).getMttr());

        if(!GatedList.get(position).getPlanmigrate().equals("COMPLETED")){
            //ltg

            convertView.setBackgroundColor(Color.RED);
        }else{

            convertView.setBackgroundColor(Color.parseColor("#FF4AA73D"));
        }

        return convertView;
    }
}

