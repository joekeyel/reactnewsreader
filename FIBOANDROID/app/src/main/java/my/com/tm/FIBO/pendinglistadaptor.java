package my.com.tm.FIBO;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by joe on 1/25/2016.
 */
public class pendinglistadaptor extends ArrayAdapter implements Filterable {

    private List<fibermodel2> ttmodellist;
    private List<fibermodel2> orig;
    private int resource;
    private LayoutInflater inflator;

    public pendinglistadaptor(Context context, int resource, List<fibermodel2> objects) {
        super(context, resource, objects);

        ttmodellist = objects;
        this.resource = resource;
        inflator = (LayoutInflater)getContext().getSystemService(context.LAYOUT_INFLATER_SERVICE);
    }

    public Filter getFilter() {
        return new Filter() {

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                final FilterResults oReturn = new FilterResults();
                final ArrayList<fibermodel2> results = new ArrayList<fibermodel2>();
                if (orig == null)
                    orig = ttmodellist;
                if (constraint != null) {
                    if (orig != null && orig.size() > 0) {
                        for (final fibermodel2 g : orig) {
                            if (g.getCustomername().toLowerCase()
                                    .contains(constraint.toString()))
                                results.add(g);
                            if (g.getOrderid().toLowerCase()
                                    .contains(constraint.toString()))
                                results.add(g);
                            if (g.getSiteid().toLowerCase()
                                    .contains(constraint.toString()))
                                results.add(g);

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
                ttmodellist = (ArrayList<fibermodel2>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return ttmodellist.size();
    }

    @Override
    public Object getItem(int position) {
        return ttmodellist.get(position);
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


        TextView customername;
        TextView orderid;
        TextView siteid;
        TextView nettype;
        TextView workgroup;


        customername = (TextView) convertView.findViewById(R.id.customernametv);
        orderid = (TextView) convertView.findViewById(R.id.orderidtv);
        siteid = (TextView) convertView.findViewById(R.id.siteidtv);
        nettype = (TextView) convertView.findViewById(R.id.netypetv);
        workgroup = (TextView) convertView.findViewById(R.id.workgrouptv);



        customername.setText(ttmodellist.get(position).getCustomername());
        orderid.setText(ttmodellist.get(position).getOrderid());
        nettype.setText(ttmodellist.get(position).getNettype());
        siteid.setText(ttmodellist.get(position).getSiteid());
        workgroup.setText(ttmodellist.get(position).getWorkgroup());




        return convertView;
    }
}