package my.com.tm.FIBO;

import android.content.Context;
import android.location.Address;
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
public class adressadapter extends ArrayAdapter implements Filterable {

    private List<Address> ttmodellist;
    private List<Address> orig;
    private int resource;
    private LayoutInflater inflator;

    public adressadapter(Context context, int resource, List<Address> objects) {
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
                final ArrayList<Address> results = new ArrayList<>();
                if (orig == null)
                    orig = ttmodellist;
                if (constraint != null) {
                    if (orig != null && orig.size() > 0) {

                        for(int i=0;i<orig.size();i++){
                            if(orig.get(i).getAddressLine(0).toLowerCase().contains(constraint.toString()))
                                results.add(orig.get(i));

                        }
//                        for (final Address g : orig) {
//                            if (g.getAddressLine(0).toLowerCase()
//                                    .contains(constraint.toString()))
//                                results.add(g);
//
//
//                        }
                    }
                    oReturn.values = results;
                }
                return oReturn;
            }

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint,
                                          FilterResults results) {
                ttmodellist = (ArrayList<Address>) results.values;
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


        TextView state;


        state = (TextView) convertView.findViewById(R.id.statename);


       state.setText( ttmodellist.get(position).getAddressLine(0));


        //this part to load the image


        return convertView;
    }
}