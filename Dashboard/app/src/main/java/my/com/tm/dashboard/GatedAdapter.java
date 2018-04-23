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


public class GatedAdapter extends ArrayAdapter implements Filterable {

    private List<GatedModel> GatedList;
    private List<GatedModel> orig;
    private int resource;
    private LayoutInflater inflator;

    public GatedAdapter(Context context, int resource, List<GatedModel> objects) {
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
                final ArrayList<GatedModel> results = new ArrayList<GatedModel>();
                if (orig == null)
                    orig = GatedList;
                if (constraint != null) {
                    if (orig != null && orig.size() > 0) {
                        for (final GatedModel g : orig) {
                            if (g.getCabinet().toLowerCase()
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
                                          Filter.FilterResults results) {
                GatedList = (ArrayList<GatedModel>) results.values;
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


        TextView cabinet;
        TextView ipaddress;
        TextView status,task,team,sequence,finalstatus,datem;

        cabinet = (TextView) convertView.findViewById(R.id.satu);
        ipaddress = (TextView) convertView.findViewById(R.id.dua);
        status = (TextView) convertView.findViewById(R.id.tiga);
        task = (TextView) convertView.findViewById(R.id.empat);
        team = (TextView) convertView.findViewById(R.id.lima);
        sequence = (TextView) convertView.findViewById(R.id.enam);
        finalstatus = (TextView) convertView.findViewById(R.id.tujuh);
        datem = (TextView) convertView.findViewById(R.id.lapan);

        cabinet.setText(GatedList.get(position).getCabinet());
        ipaddress.setText(GatedList.get(position).getIpaddress());
        status.setText(GatedList.get(position).getStatus());
        task.setText(GatedList.get(position).getTask());
        team.setText(GatedList.get(position).getTeam());
        sequence.setText(GatedList.get(position).getSequence());
        finalstatus.setText(GatedList.get(position).getFinalstatus());
        datem.setText(GatedList.get(position).getDatem());

        if(!GatedList.get(position).getFinalstatus().equals("COMPLETED")){
            //ltg

            convertView.setBackgroundColor(Color.RED);
        }else{

            convertView.setBackgroundColor(Color.GRAY);
        }

        return convertView;
    }
}

