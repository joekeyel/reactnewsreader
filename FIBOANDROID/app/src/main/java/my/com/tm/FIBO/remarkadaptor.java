package my.com.tm.FIBO;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

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
public class remarkadaptor extends ArrayAdapter implements Filterable {

    private List<remarkmodel> ttmodellist;
    private List<remarkmodel> orig;
    private int resource;
    private LayoutInflater inflator;

    public remarkadaptor(Context context, int resource, List<remarkmodel> objects) {
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
                final ArrayList<remarkmodel> results = new ArrayList<remarkmodel>();
                if (orig == null)
                    orig = ttmodellist;
                if (constraint != null) {
                    if (orig != null && orig.size() > 0) {
                        for (final remarkmodel g : orig) {
                            if (g.getCreatedby().toLowerCase()
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
                ttmodellist = (List<remarkmodel>) results.values;
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


        TextView remark;
        TextView createdby;
        TextView date;
         ImageView imagepost = null;

        final ImageView markerimage;

        remark = (TextView) convertView.findViewById(R.id.tvremark);
        createdby = (TextView) convertView.findViewById(R.id.remarkcreatedby);
        date = (TextView) convertView.findViewById(R.id.createddate);
        markerimage = (ImageView)convertView.findViewById(R.id.profilepic);
        imagepost = (ImageView)convertView.findViewById(R.id.postimage);
        imagepost.setVisibility(View.INVISIBLE);

       remark.setText(ttmodellist.get(position).getRemark());
       createdby.setText(ttmodellist.get(position).getCreatedby());
        date.setText(ttmodellist.get(position).getDatetime());


        if(ttmodellist.get(position).getImageid() != null) {



            //this part to load the image

            FirebaseStorage storage = FirebaseStorage.getInstance();
            StorageReference storageRef = storage.getReference();


            final ImageView finalImagepost = imagepost;
            finalImagepost.setVisibility(View.VISIBLE);
            storageRef.child("postimage" + File.separator + ttmodellist.get(position).getImageid() + ".jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    // TODO: handle uri


                    Context context = finalImagepost.getContext();

                    finalImagepost.invalidate();

                    Picasso.with(context).load(uri).networkPolicy(NetworkPolicy.NO_CACHE).into(finalImagepost);


                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle any errors
                }
            });

        }else{

            imagepost.setVisibility(View.INVISIBLE);
        }


        return convertView;
    }
}
