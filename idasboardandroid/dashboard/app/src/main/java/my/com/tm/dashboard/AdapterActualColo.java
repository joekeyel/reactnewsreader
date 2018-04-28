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

import my.com.tm.dashboard.R;


public class AdapterActualColo extends ArrayAdapter implements Filterable {

    private List<ModelActualColo> GatedList;
    private List<ModelActualColo> orig;
    private int resource;
    private LayoutInflater inflator;

    public AdapterActualColo(Context context, int resource, List<ModelActualColo> objects) {
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
                final ArrayList<ModelActualColo> results = new ArrayList<ModelActualColo>();
                if (orig == null)
                    orig = GatedList;
                if (constraint != null) {
                    if (orig != null && orig.size() > 0) {
                        for (final ModelActualColo g : orig) {
                            if (g.getOld().toLowerCase()
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
                GatedList = (ArrayList<ModelActualColo>) results.values;
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
        TextView old,neww,region,migdate,onplan,datem;

        old = (TextView) convertView.findViewById(R.id.satu);
        neww = (TextView) convertView.findViewById(R.id.dua);
        region = (TextView) convertView.findViewById(R.id.tiga);
        migdate = (TextView) convertView.findViewById(R.id.empat);
        onplan = (TextView) convertView.findViewById(R.id.lima);

        old.setText(GatedList.get(position).getOld());
        neww.setText(GatedList.get(position).getNewc());
        region.setText(GatedList.get(position).getRegion());
        migdate.setText(GatedList.get(position).getMigdate());
        onplan.setText(GatedList.get(position).getPlan());

        if(!GatedList.get(position).getPlan().equals("Yes")){

            convertView.setBackgroundColor(Color.RED);
        }else{
             //convertView.setBackgroundColor(Color.parseColor("#ffffff"));
            convertView.setBackgroundColor(Color.parseColor("#ffffff"));
        }

        return convertView;
    }
}

