package my.com.tm.FIBO;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import static my.com.tm.FIBO.MyActivity.scaleDown;

public class image extends AppCompatActivity {


   String updatedby,orderid,siteid;
   ImageView frameimage1,powercableimage1,iucimageuplink1,ftbfocimage1,epenpeimage1,fdffocimage1;
    String imagename;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);


        Intent i = getIntent();
         updatedby = i.getStringExtra("updatedby");
        orderid = i.getStringExtra("orderid");
        siteid = i.getStringExtra("siteid");


        frameimage1 = (ImageView)findViewById(R.id.oltimage1);
        powercableimage1 = (ImageView)findViewById(R.id.powercableimage);
        iucimageuplink1 = (ImageView)findViewById(R.id.iucuplinkimage);
        ftbfocimage1 = (ImageView)findViewById(R.id.ftbfocimage);
        epenpeimage1 = (ImageView)findViewById(R.id.epenpeimage);
        fdffocimage1 = (ImageView)findViewById(R.id.fdffocimage);

        loadimage(frameimage1,siteid+"_iucchasis.jpg");
        loadimage(powercableimage1,siteid+"_power1.jpg");
        loadimage(iucimageuplink1,siteid+"_uplinkiuc.jpg");
        loadimage(ftbfocimage1,siteid+"_ftbfoc.jpg");
        loadimage(epenpeimage1,siteid+"_epenpe.jpg");
        loadimage(fdffocimage1,siteid+"_fdffoc.jpg");

    }




    public void selectgallerywall1(View view) {


         imagename = view.getTag().toString();








            // Perform action on click
            Intent camera_intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            File file = getFile(siteid + "_"+imagename );

            Uri apkURI = FileProvider.getUriForFile(
                    image.this,
                    image.this.getApplicationContext()
                            .getPackageName() + ".provider", file);


            camera_intent.putExtra(MediaStore.EXTRA_OUTPUT,

                    //photoURI
                    apkURI

            );


            startActivityForResult(camera_intent, 1);




    }

    public void selectcamera(View view) {


        imagename = view.getTag().toString();


        // Perform action on click
        Intent camera_intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File file = getFile(siteid + "_"+imagename );

        Uri apkURI = FileProvider.getUriForFile(
                image.this,
                image.this.getApplicationContext()
                        .getPackageName() + ".provider", file);


        camera_intent.putExtra(MediaStore.EXTRA_OUTPUT,

                //photoURI
                apkURI

        );


        startActivityForResult(camera_intent, 2);




    }

    private File getFile(String filename){


        File Folder = new File(Environment.getExternalStorageDirectory() +
                File.separator +"DCIM");

        if(!Folder.exists()){

            Folder.mkdir();
        }

        File image_file = new File(Folder,filename+".jpg");

        return image_file;


    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {




        if(requestCode == 1){
            if (data != null) {
                Uri contentURI = data.getData();


                final String path = Environment.getExternalStorageDirectory() +
                        File.separator + "DCIM/" + siteid + "_"+imagename+ ".jpg";

                Bitmap bmp = null;
                try {
                    bmp = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e("Error image Galery", e.toString());
                }
                Bitmap photo = Bitmap.createScaledBitmap(bmp, 1280, 1024, true);

                ByteArrayOutputStream bytes = new ByteArrayOutputStream();

                photo.compress(Bitmap.CompressFormat.JPEG, 80, bytes);

                File f = new File(Environment.getExternalStorageDirectory()
                        + File.separator + "DCIM/" + siteid + "_"+imagename+ ".jpg");
                try {
                    f.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                FileOutputStream fo = null;
                try {
                    fo = new FileOutputStream(f);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                try {
                    fo.write(bytes.toByteArray());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    fo.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }


              if(imagename.contains("iucchasis")){

                    frameimage1.setImageDrawable(Drawable.createFromPath(path));

                }
                if(imagename.contains("power1")){

                    powercableimage1.setImageDrawable(Drawable.createFromPath(path));

                }
                if(imagename.contains("uplinkiuc")){

                    iucimageuplink1.setImageDrawable(Drawable.createFromPath(path));

                }
                if(imagename.contains("ftbfoc")){

                    ftbfocimage1.setImageDrawable(Drawable.createFromPath(path));

                }
                if(imagename.contains("epenpe")){

                    epenpeimage1.setImageDrawable(Drawable.createFromPath(path));

                }
                if(imagename.contains("fdffoc")){

                    fdffocimage1.setImageDrawable(Drawable.createFromPath(path));

                }


            }



            // Create a storage reference from our app
            FirebaseStorage storage = FirebaseStorage.getInstance();
            StorageReference storageRef = storage.getReference();
            File file = getFile(siteid + "_"+imagename);
//                    Uri photoURI = FileProvider.getUriForFile(MyActivity.this,
            //       "my.com.tm.moapps.remoteandroid.fileprovider",
            //        file);

            Uri apkURI = FileProvider.getUriForFile(
                    image.this,
                    image.this.getApplicationContext()
                            .getPackageName() + ".provider", file);
            StorageReference riversRef = storageRef.child("FIBO" + File.separator + apkURI.getLastPathSegment());
            UploadTask uploadTask = riversRef.putFile(apkURI);



            // Register observers to listen for when the download is done or if it fails
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {


                    Toast.makeText(image.this, "Failed Upload To Server", Toast.LENGTH_SHORT)
                            .show();

                    // Handle unsuccessful uploads
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                    Uri downloadUrl = taskSnapshot.getDownloadUrl();

                    Toast.makeText(image.this, "Save to server", Toast.LENGTH_SHORT)
                            .show();

                }
            });


        }


        if(requestCode == 2){



            final String path = Environment.getExternalStorageDirectory() +
                    File.separator + "DCIM/" + siteid + "_"+imagename+ ".jpg";

            Bitmap bmp = BitmapFactory.decodeFile(path);

            Bitmap photo = scaleDown(bmp, 800, true);

            ByteArrayOutputStream bytes = new ByteArrayOutputStream();

            photo.compress(Bitmap.CompressFormat.JPEG, 40, bytes);

            File f = new File(Environment.getExternalStorageDirectory()
                    + File.separator + "DCIM/" + siteid + "_"+imagename+ ".jpg");
            try {
                f.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            FileOutputStream fo = null;
            try {
                fo = new FileOutputStream(f);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            try {
                fo.write(bytes.toByteArray());
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                fo.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            if(imagename.contains("iucchasis")){

                frameimage1.setImageDrawable(Drawable.createFromPath(path));

            }
            if(imagename.contains("power1")){

                powercableimage1.setImageDrawable(Drawable.createFromPath(path));

            }
            if(imagename.contains("uplinkiuc")){

                iucimageuplink1.setImageDrawable(Drawable.createFromPath(path));

            }
            if(imagename.contains("ftbfoc")){

                ftbfocimage1.setImageDrawable(Drawable.createFromPath(path));

            }
            if(imagename.contains("epenpe")){

                epenpeimage1.setImageDrawable(Drawable.createFromPath(path));

            }
            if(imagename.contains("fdffoc")){

                fdffocimage1.setImageDrawable(Drawable.createFromPath(path));

            }

            FirebaseStorage storage = FirebaseStorage.getInstance();
            StorageReference storageRef = storage.getReference();
            File file = getFile(siteid + "_"+imagename);
//                    Uri photoURI = FileProvider.getUriForFile(MyActivity.this,
            //       "my.com.tm.moapps.remoteandroid.fileprovider",
            //        file);

            Uri apkURI = FileProvider.getUriForFile(
                    image.this,
                    image.this.getApplicationContext()
                            .getPackageName() + ".provider", file);
            StorageReference riversRef = storageRef.child("FIBO" + File.separator  + siteid + "_"+imagename+".jpg");
            UploadTask uploadTask = riversRef.putFile(apkURI);

            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {


                    Toast.makeText(image.this, "Failed Upload To Server", Toast.LENGTH_SHORT)
                            .show();

                    // Handle unsuccessful uploads
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.

                    Toast.makeText(image.this, "Upload To Server", Toast.LENGTH_SHORT)
                            .show();




                }
            });


        }
    }


    public void loadimage(final ImageView view, final String imagename1){


        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();







        storageRef.child("FIBO" + File.separator+imagename1).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                // TODO: handle uri

                Context context = view.getContext();

                view.invalidate();

                Picasso.with(context).load(uri).networkPolicy(NetworkPolicy.NO_CACHE).into(view);


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Hand
                //
                //
                // le any errors

                Toast.makeText(image.this, "Failed  To view image"+ imagename1+" "+exception , Toast.LENGTH_SHORT).show();
            }
        });


    }



}
