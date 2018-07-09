package my.com.tm.FIBO;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by joe on 1/25/2016.
 */
public class fibersdfadaptor extends ArrayAdapter implements Filterable {

    private List<fibermodel> ttmodellist;
    private List<fibermodel> orig;
    private int resource;
    private LayoutInflater inflator;

    public fibersdfadaptor(Context context, int resource, List<fibermodel> objects) {
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
                final ArrayList<fibermodel> results = new ArrayList<fibermodel>();
                if (orig == null)
                    orig = ttmodellist;
                if (constraint != null) {
                    if (orig != null && orig.size() > 0) {
                        for (final fibermodel g : orig) {
                            if (g.getRoute().toLowerCase()
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
                                          FilterResults results) {
                ttmodellist = (ArrayList<fibermodel>) results.values;
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
    public View getView(final int position, View convertView, ViewGroup parent) {

        if(convertView==null){

            convertView = inflator.inflate(resource,null);
        }



        Button delsfp = (Button)convertView.findViewById(R.id.delsfp);

        if(ttmodellist.get(position).getStatus()== null){

            convertView.setBackgroundColor(Color.WHITE);
            delsfp.setVisibility(View.INVISIBLE);

            TextView routename = (TextView)convertView.findViewById(R.id.middleroutename);
            routename.setText(ttmodellist.get(position).getRoute());

            TextView core = (TextView)convertView.findViewById(R.id.middlecore);
            core.setText(ttmodellist.get(position).getCore());


            TextView routeport1 = (TextView)convertView.findViewById(R.id.middleroutenameport1);
            routeport1.setText(ttmodellist.get(position).getRouteport1());

            TextView routeport2 = (TextView)convertView.findViewById(R.id.middleroutenameport2);
            routeport2.setText(ttmodellist.get(position).getRouteport2());


            TextView coreport1 = (TextView)convertView.findViewById(R.id.middlecoreport1);
            coreport1.setText(ttmodellist.get(position).getCoreport1());

            TextView coreport2 = (TextView)convertView.findViewById(R.id.middlecoreport2);
            coreport2.setText(ttmodellist.get(position).getCoreport2());


            TextView rowport1 = (TextView)convertView.findViewById(R.id.middlerowport1);
            rowport1.setText(ttmodellist.get(position).getRowport1());

            TextView rowport2 = (TextView)convertView.findViewById(R.id.middlerowport2);
            rowport2.setText(ttmodellist.get(position).getRowport2());


            TextView verport1 = (TextView)convertView.findViewById(R.id.middleverport1);
            verport1.setText(ttmodellist.get(position).getVerport1());

            TextView verport2 = (TextView)convertView.findViewById(R.id.middleverport2);
            verport2.setText(ttmodellist.get(position).getVerport2());

            TextView block1 = (TextView)convertView.findViewById(R.id.middleblockport1);
            block1.setText(ttmodellist.get(position).getBlockport1());

            TextView block2 = (TextView)convertView.findViewById(R.id.middleblockport2);
            block2.setText(ttmodellist.get(position).getBlockport2());


            TextView pinport1 = (TextView)convertView.findViewById(R.id.middlepinport1);
            pinport1.setText(ttmodellist.get(position).getPinportport1());

            TextView pinport2 = (TextView)convertView.findViewById(R.id.middlepinport2);
            pinport2.setText(ttmodellist.get(position).getPinportport2());


            TextView indexport1 = (TextView)convertView.findViewById(R.id.middleindexport1);
            indexport1.setText(ttmodellist.get(position).getIndexport1());

            TextView indexport2 = (TextView)convertView.findViewById(R.id.middleindexport2);
            indexport2.setText(ttmodellist.get(position).getIndexport2());


            TextView manufacture1 = (TextView)convertView.findViewById(R.id.middlemanufactureport1);
            manufacture1.setText(ttmodellist.get(position).getManufactureport1());

            TextView manufacture2 = (TextView)convertView.findViewById(R.id.middlemanufactureport2);
            manufacture2.setText(ttmodellist.get(position).getManufacturedport2());

            TextView type1 = (TextView)convertView.findViewById(R.id.middletypeport1);
            type1.setText(ttmodellist.get(position).getTypeport1());

            TextView type2 = (TextView)convertView.findViewById(R.id.middletypeport2);
            type2.setText(ttmodellist.get(position).getTypeport2());


        }else{

            convertView.setBackgroundColor(Color.RED);
            delsfp.setVisibility(View.VISIBLE);
            delsfp.setBackgroundColor(Color.RED);

            delsfp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                ttmodellist.remove(position);
                notifyDataSetChanged();



                }
            });
        }



        return convertView;
    }
}
